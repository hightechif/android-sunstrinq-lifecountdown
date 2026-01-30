package com.sunstrinq.lifecountdown.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LifeCountdownItem(progress: Float, text: String) {
    Box(
        modifier = Modifier.size(140.dp),
        contentAlignment = Alignment.Center,
    ) {
        LifeCircle(
            progress = progress,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(16.dp)
            )
        }
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