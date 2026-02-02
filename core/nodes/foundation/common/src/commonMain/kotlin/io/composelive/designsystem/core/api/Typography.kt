package io.composelive.designsystem.core.api

public class Typography(
    public val h1: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Light,
            fontSize = 96.sp,
//            lineHeight = 112.sp,
//            letterSpacing = (-1.5).sp
        ),
    public val h2: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Light,
            fontSize = 60.sp,
//            lineHeight = 72.sp,
//            letterSpacing = (-0.5).sp
        ),
    public val h3: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp,
//            lineHeight = 56.sp,
//            letterSpacing = 0.sp
        ),
    public val h4: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 34.sp,
//            lineHeight = 36.sp,
//            letterSpacing = 0.25.sp
        ),
    public val h5: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.sp
        ),
    public val h6: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.15.sp
        ),
    public val subtitle1: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.15.sp
        ),
    public val subtitle2: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.1.sp
        ),
    public val body1: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.5.sp
        ),
    public val body2: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
//            lineHeight = 20.sp,
//            letterSpacing = 0.25.sp
        ),
    public val button: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
//            lineHeight = 16.sp,
//            letterSpacing = 1.25.sp
        ),
    public val caption: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
//            lineHeight = 16.sp,
//            letterSpacing = 0.4.sp
        ),
    public val overline: TextStyle =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
//            lineHeight = 16.sp,
//            letterSpacing = 1.5.sp
        )
)
