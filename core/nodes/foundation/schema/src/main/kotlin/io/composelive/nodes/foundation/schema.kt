/*
 * Copyright (C) 2022 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.composelive.nodes.foundation

import app.cash.redwood.schema.Schema

@Schema(
    [
        // Widgets
        Box::class,
        Column::class,
        Row::class,
        Spacer::class,
        LazyGrid::class,
        LazyItems::class,
        Pager::class,
        PullToRefreshBox::class,
        Scaffold::class,
        AnimatedVisibility::class,
        Root::class,
        TextField::class,
        Text::class,
        AsyncImage::class,
        Button::class,
        FloatingActionButton::class,
        MotionProgressHolder::class,
        ReuseRoot::class,
        ReuseNode::class,

        // Modifiers
        Reuse::class,
        Padding::class,
        AlignHorizontally::class,
        AlignVertically::class,
        Align::class,
        Width::class,
        Height::class,
        Weight::class,
        FillMaxWidth::class,
        FillMaxHeight::class,
        AspectRatio::class,
        Background::class,
        Clip::class,
        StickyHeader::class,
        Shimmer::class,
        Alpha::class,
        DefaultMinSize::class,
        WrapContentHeight::class,
        LayoutId::class,
    ],
)
interface Foundation
