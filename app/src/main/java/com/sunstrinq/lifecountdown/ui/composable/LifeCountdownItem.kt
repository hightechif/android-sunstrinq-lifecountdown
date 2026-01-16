package com.sunstrinq.lifecountdown.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LifeCountdownItem(progress: Float, text: String) {

    LifeCircle(
        progress = progress,
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Text(
            text = text,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 11.sp,
            maxLines = 3,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun LifeCountDownItemPreview() {
    LifeCountdownItem(
        0.5f,
        "1,000 Days Left"
    )
}