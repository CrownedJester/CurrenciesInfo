package com.crownedjester.soft.currenciesinfo.representation.compose_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ComposableSwitch(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    height: Dp = 18.dp,
    width: Dp = 36.dp,
    borderWidth: Dp = 2.dp,
    thumbCheckedColor: Color,
    thumbUncheckedColor: Color,
    trackCheckedColor: Color = Color.Transparent,
    trackUncheckedColor: Color = Color.Transparent,
    gapBetweenTrackAndThumbEdge: Dp = 3.dp
) {

    var isChecked by remember { mutableStateOf(isFavorite) }

    val thumbRadius = height / 2 - gapBetweenTrackAndThumbEdge

    val animatedValue =
        if (isChecked) with(LocalDensity.current) {
            (width - thumbRadius - gapBetweenTrackAndThumbEdge).toPx()
        }
        else with(LocalDensity.current) {
            (thumbRadius + gapBetweenTrackAndThumbEdge).toPx()
        }

    val animatePosition by animateFloatAsState(targetValue = animatedValue)

    Canvas(modifier = modifier
        .size(width, height)
        .background(color = if (isChecked) trackCheckedColor else trackUncheckedColor)
        .border(width = borderWidth, shape = RoundedCornerShape(72), color = Color.Black)
        .pointerInput(Unit) {
            detectTapGestures {
                isChecked = !isChecked
                onCheckedChange(isChecked)
            }
        }) {

        drawCircle(
            color = if (isChecked) thumbCheckedColor else thumbUncheckedColor,
            radius = thumbRadius.toPx(),
            center = Offset(animatePosition, size.height / 2),
        )

    }
}
