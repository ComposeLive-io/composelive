# Clive Lazy Layout Architecture

This document explains the relationship between host and live code for Clive lazy layouts,
including the data flow, component responsibilities, and optimization strategies.

## Overview

Clive lazy layouts implement a split-runtime architecture where:

- **Live code** runs in a JS runtime (Zipline) and describes the UI using Compose
- **Host code** runs natively on iOS/Android and renders the UI using Compose UI

This separation allows UI updates without app releases, but introduces a communication overhead
that requires careful optimization.

## Key Architecture Pattern: The Loading Window

The central challenge in Clive lazy layouts is the **bridge latency** between live and host code.
To minimize placeholder display and ensure smooth scrolling, the system uses a **loading window**
concept:

### What is the Loading Window?

Instead of loading only visible items, the system preloads a window of items around the visible
items. This window:

1. **Reduces placeholder display**: Items are ready before the user scrolls to them
2. **Has static size**: Prevents unnecessary recomposition and data transfer
3. **Adapts to scroll direction**: Preloads more items in the scrolling direction

### Window Size Example

```
Total items:        [0][1][2][3][4][5][6][7][8][9][10][11][12][13][14]
                             ↓ visible ↓
Visible items:               [3][4][5][6]
Loading window:        [1][2][3][4][5][6][7][8]  (size = 8)
                 preloaded ↑              ↑ preloaded
```

## Component Architecture

### Live Side Components

#### 1. LazyLayoutState

**Location**: `LazyLayoutState.kt`

**Responsibilities**:

- Tracks scroll position and loading strategy
- Manages programmatic scroll requests
- Coordinates with loading strategy to determine which items to load

**Key Methods**:

- `loadRanges()`: Calculates which items should be in the loading window
- `onUserScroll()`: Reacts to user scroll events from the host
- `programmaticScroll()`: Initiates programmatic scrolls (e.g., scroll-to-top)

```kotlin
public open class LazyLayoutState(
    public val strategy: LoadingStrategy,
) {
    public fun loadRanges(
        intervals: IntervalList<LazyLayoutIntervalContent.Interval>,
        writeFirstsTo: IntArray,
        writeLastsTo: IntArray,
    ) {
        strategy.loadRanges(intervals, writeFirstsTo, writeLastsTo)
    }
}
```

#### 2. LoadingStrategy

**Location**: `strategy/LoadingStrategy.kt`

**Responsibilities**:

- Determines the loading window boundaries
- Adapts to scroll direction (up/down)
- Maintains first/last visible indices

**Implementation**: `StaticSizeLoadingStrategy`

- Maintains a fixed-size window (default: 20 items)
- Preloads more items in the scroll direction

```kotlin
public interface LoadingStrategy {
    public val firstVisibleIndex: Int
    public val lastVisibleIndex: Int

    public fun loadRanges(
        intervals: IntervalList<LazyLayoutIntervalContent.Interval>,
        writeFirstsTo: IntArray,
        writeLastsTo: IntArray,
    )
}
```

#### 3. LazyLayoutIntervals

**Location**: `LazyLayoutIntervals.kt`

**Responsibilities**:

- Iterates through intervals (sections of items)
- Calculates load ranges for each interval
- Manages key recycling for efficient composition
- Wraps items in `ShallowWrapper` that makes host see separate items
- Adds `reuse` modifier that allows Redwood to reuse live nodes more efficient

**Key Feature**: This is where the loading window is applied to actual items. Items outside
the window render as placeholders.

```kotlin
@Composable
public fun <TInterval : LazyLayoutIntervalContent.Interval> LazyLayoutIntervals(
    state: LazyLayoutState,
    itemProvider: LazyLayoutItemProvider<TInterval>,
    items: @Composable (
        interval: TInterval,
        itemsBefore: Int,
        itemsAfter: Int,
        placeholder: @Composable () -> Unit,
        items: @Composable () -> Unit,
    ) -> Unit
)
```

### Host Side Components

#### 1. FoundationLazyList / FoundationLazyGrid

**Location**: `FoundationLazyList.kt`, `FoundationLazyGrid.kt`

**Responsibilities**:

- Wraps native Compose UI lazy components (`LazyColumn`, `LazyRow`, `LazyVerticalGrid`, etc.)
- Manages native scroll state
- Sends visible items changes back to live code
- Handles programmatic scroll requests from live code

**Key Interactions**:

```kotlin
// Send visible items changes to live code
LaunchedEffect(lastVisibleItemIndex) {
    lastVisibleItemIndex?.let { lastVisibleItemIndex ->
        throttler.send(
            firstIndex = state.firstVisibleItemIndex,
            lastIndex = lastVisibleItemIndex,
        )
    }
}

// Handle scroll requests from live code
LaunchedEffect(scrollItemIndex) {
    scrollItemIndex?.let { itemIndex ->
        if (itemIndex.animated) {
            state.animateScrollToItem(index = itemIndex.index)
        } else {
            state.scrollToItem(index = itemIndex.index)
        }
    }
}
```

#### 2. VisibleItemsChangeThrottler

**Location**: `lazylayout/VisibleItemsChangeThrottler.kt`

**Responsibilities**:

- Manages visible items change notifications to live code
- Implements a request-response pattern with change IDs
- Handles delayed visible items changes during rapid scrolling
- Prevents overwhelming the bridge with too many updates

**Protocol**:

1. Host detects visible items change → sends `(firstIndex, lastIndex, id)` to live
2. Live receives visible items change → updates strategy → acknowledges with `id`
3. Host receives acknowledgment → can send next visible items change

```kotlin
public class VisibleItemsChangeThrottler {
    public var callback: ((firstIndex: Int, lastIndex: Int, id: Int) -> Unit)?

    public fun send(firstIndex: Int, lastIndex: Int)
    public fun receivedVisibleItemsChangedId(changedId: Int)
}
```

#### 3. LazyChildren

**Location**: `lazylayout/LazyChildren.kt`

**Responsibilities**:

- Manages the loaded window of widgets on the host side
- Renders items from the window or placeholders for missing items
- Maintains widget lifecycle (insert, move, remove)
- Triggers recomposition when window changes

**Rendering Logic**:

```kotlin
@Composable
public fun Render(virtualIndex: Int, modifier: Modifier = Modifier) {
    val holder = widgetFor(virtualIndex)
    Item(holder, modifier)
}

private fun widgetFor(virtualIndex: Int): WidgetHolder {
    val realIndex = virtualIndex - itemsBefore
    val holder = _loadedWindow.getOrNull(realIndex)
    return if (holder != null) {
        holder  // Use loaded widget
    } else {
        placeholder()  // Show placeholder
    }
}
```

#### 4. BaseLazyItems / FoundationLazyListItems

**Location**: `lazylayout/BaseLazyItems.kt`, `FoundationLazyListItems.kt`

**Responsibilities**:

- Bridge between live code's interval structure and host's widget structure
- Manages `itemsBefore` and `itemsAfter` for placeholder spacing
- Coordinates placeholder and actual items

```kotlin
public interface BaseLazyItems : ChangeListener {
    public val placeholder: ComposeWidgetChildren
    public val items: LazyChildren
    public fun itemsBefore(itemsBefore: Int)
    public fun itemsAfter(itemsAfter: Int)
}
```

## Data Flow

### 1. User Scroll Flow

```
User scrolls on device
    ↓
Host: FoundationLazyList detects visible items change
    ↓
Host: VisibleItemsChangeThrottler.send(firstIndex, lastIndex, id)
    ↓
[BRIDGE - Zipline communication]
    ↓
Live: LazyList.visibleItemsChanged(firstIndex, lastIndex, id)
    ↓
Live: LazyLayoutState.onUserScroll(firstIndex, lastIndex)
    ↓
Live: LoadingStrategy updates visible range
    ↓
Live: LazyLayoutIntervals recalculates loading window
    ↓
Live: New items/placeholder structure sent to host
    ↓
[BRIDGE]
    ↓
Host: LazyChildren receives updated items
    ↓
Host: LazyChildren.Render() shows items/placeholders
    ↓
Host: VisibleItemsChangeThrottler.receivedVisibleItemsChangedId(id)
```

### 2. Programmatic Scroll Flow

```
Live code calls: state.programmaticScroll(index, animated)
    ↓
Live: LoadingStrategy.scrollTo(index)
    ↓
Live: LazyLayoutIntervals recalculates window
    ↓
Live: scrollItemIndex sent to host
    ↓
[BRIDGE]
    ↓
Host: FoundationLazyList receives scrollItemIndex
    ↓
Host: LazyListState.scrollToItem() or animateScrollToItem()
    ↓
Host: Visible items change triggers (back to User Scroll Flow)
```

### 3. Initial Load Flow

```
Live: LazyList composed with content
    ↓
Live: LazyLayoutIntervals created with LoadingStrategy
    ↓
Live: loadRanges() calculates initial window (usually starts at index 0)
    ↓
Live: Items within window rendered, items outside become placeholders
    ↓
[BRIDGE]
    ↓
Host: FoundationLazyListItems receives items
    ↓
Host: LazyChildren tracks items and renders
    ↓
Host: User sees initial items with placeholders for later items
```

## Optimization Strategies

### 1. Static Window Size

The loading window maintains a fixed size to:

- Maximize composition reuse
- Prevent bridge overload
- Ensure predictable memory usage

### 2. Direction-Aware Preloading

`StaticSizeLoadingStrategy` preloads more items in the scroll direction:

- Scrolling down: 75% of window below, 25% above
- Scrolling up: 25% of window below, 75% above
- Static: 50% above, 50% below

### 3. Key Recycling

`LazyLayoutKeyRecycler` enables Compose to reuse compositions:

- Stable keys for items even when window shifts
- Reduces composition overhead
- Improves scrolling performance

### 4. Placeholder Strategy

When items fall outside the loaded window:

- Placeholders maintain layout stability
- Prevent visual jumps during loading
- Can be customized per interval

### 5. Visible items Change Batching

`VisibleItemsChangeThrottler` prevents bridge spam:

- Only one visible items change in-flight at a time
- Delayed changes queued and sent after acknowledgment
- ID-based tracking ensures correct ordering

## Widget Structure Mapping

### Live Side Structure

```kotlin
LazyList(state = state) {
    items(count = 100) { index ->
        // Item content
    }
}
```

Maps to:

### Host Side Structure

```kotlin
FoundationLazyList {
    FoundationLazyListItems {
        items = LazyChildren(
            itemsBefore = 20,  // Placeholders before window
            itemsAfter = 70,   // Placeholders after window
            loadedWindow = [
                // 10 actual widgets in ShallowWrapper-s (window size = 10)
            ]
        )
    }
}
```

## Common Patterns

### Adding a Lazy List

**Live Code**:

```kotlin
val state = rememberLazyListState()

LazyList(
    isVertical = true,
    state = state,
    contentPadding = PaddingValues(16.dp),
) {
    items(count = items.size) { index ->
        Item(items[index])
    }
}
```

**Host Code**: Automatically handled by `FoundationLazyList`

### Programmatic Scroll

**Live Code**:

```kotlin
val state = rememberLazyListState()

// Scroll to item 50 with animation
state.programmaticScroll(firstIndex = 50, animated = true)

// Scroll to top without animation
state.programmaticScroll(firstIndex = 0, animated = false)
```

### Custom Loading Strategy

Implement `LoadingStrategy` for custom behavior:

```kotlin
class CustomLoadingStrategy : LoadingStrategy {
    override val firstVisibleIndex: Int
    override val lastVisibleIndex: Int

    override fun loadRanges(
        intervals: IntervalList<LazyLayoutIntervalContent.Interval>,
        writeFirstsTo: IntArray,
        writeLastsTo: IntArray,
    ) {
        // Custom logic to determine loading window
    }
}
```

## Performance Considerations

### Window Size Tuning

- **Too small**: More placeholders, worse user experience
- **Too large**: Higher memory usage, slower bridge transfers
- **Recommended**: 15-30 items depending on item complexity

### Bridge Optimization

- Minimize data in each item
- Use stable keys
- Avoid deep object graphs
- Prefer primitives in item data

### Host Rendering

- Keep item composations lightweight
- Use `reuse` modifier where appropriate
- Avoid expensive calculations in item content

## Debugging Tips

1. **Check visible items updates**: Monitor `VisibleItemsChangeThrottler.send()` calls
2. **Verify window calculation**: Log `loadRanges()` outputs
3. **Track placeholder display**: Monitor `LazyChildren.widgetFor()` calls
4. **Bridge performance**: Measure round-trip time for visible items changes

## Related Files

### Live Side

- `LazyLayoutState.kt`: State management
- `LazyLayoutIntervals.kt`: Interval rendering
- `strategy/LoadingStrategy.kt`: Window calculation interface
- `strategy/StaticSizeLoadingStrategy.kt`: Default implementation
- `list/LazyList.kt`: List implementation
- `grid/LazyGrid.kt`: Grid implementation

### Host Side

- `FoundationLazyList.kt`: List host implementation
- `FoundationLazyGrid.kt`: Grid host implementation
- `FoundationLazyListItems.kt`: Items container
- `lazylayout/LazyChildren.kt`: Window management
- `lazylayout/VisibleItemsChangeThrottler.kt`: Visible items communication
- `lazylayout/BaseLazyItems.kt`: Base items implementation

## Summary

The Clive lazy layout architecture achieves smooth, responsive scrolling despite bridge latency
through:

1. **Preloading**: Window-based loading ensures items are ready before scrolling
2. **Adaptive strategies**: Direction-aware preloading optimizes for user behavior
3. **Efficient communication**: Visible items batching prevents bridge overload
4. **Smart placeholders**: Maintain layout stability during loading
5. **Key recycling**: Maximizes composition reuse for performance

This architecture enables backend-driven UI updates without sacrificing user experience.
