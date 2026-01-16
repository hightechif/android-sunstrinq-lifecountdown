package com.sunstrinq.lifecountdown.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunstrinq.lifecountdown.utils.DeathUtils
import java.time.LocalDate

data class TimeRemaining(
    val years: Int,
    val months: Int,
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
    val millis: Int
)

@Composable
fun LifeCountdownHero(
    birthDate: LocalDate,
    modifier: Modifier = Modifier
) {
    // State for the ticking numbers
    var timeLeft by remember { mutableStateOf(DeathUtils.detailedRemainingTime(birthDate)) }

    // Update the timer every 10ms for the "live" feeling
    LaunchedEffect(Unit) {
        while (true) {
            timeLeft = DeathUtils.detailedRemainingTime(birthDate)
            withFrameMillis { it } // Sync with display refresh rate
        }
    }

    val progress = DeathUtils.lifeProgress(birthDate)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(280.dp)
        ) {
            // 1. Progress Ring (Donut Chart)
            Canvas(modifier = Modifier.size(240.dp)) {
                val strokeWidth = 20.dp.toPx()
                // Background Circle (Life Lived)
                drawCircle(
                    color = Color.LightGray.copy(alpha = 0.2f),
                    style = Stroke(width = strokeWidth)
                )
                // Foreground Arc (Life Remaining)
                drawArc(
                    color = Color(0xFF00BCD4), // Cyan/Teal
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }

            // 2. The Big Number & 3. Sub-Counters
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${timeLeft.years}",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 72.sp
                    )
                )
                Text(
                    text = "YEARS REMAINING",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${timeLeft.months}M ${timeLeft.days}D",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }

                // 4. Ticking Seconds & Milliseconds
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${timeLeft.hours}h ${timeLeft.minutes}m ",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = String.format("%02d.%03d", timeLeft.seconds, timeLeft.millis),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF00E5FF) // High contrast Cyan
                    )
                    Text(
                        text = "s",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF00E5FF)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LifeCountdownHeroPreview() {
    val birthDate = LocalDate.of(1998, 9, 14)
    LifeCountdownHero(birthDate = birthDate)
}