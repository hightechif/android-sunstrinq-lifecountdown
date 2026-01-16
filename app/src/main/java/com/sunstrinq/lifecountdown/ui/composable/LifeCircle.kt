package com.sunstrinq.lifecountdown.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LifeCircle(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 6.dp,
    color: Color = Color(0xFF228C22),
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        Canvas(modifier = Modifier.matchParentSize()) {
            val stroke = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )

            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = stroke
            )
        }

        content()
    }
}

@Preview
@Composable
fun LifeCirclePreview() {
    LifeCircle(0.5f) {

    }
}