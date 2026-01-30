package com.sunstrinq.lifecountdown.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunstrinq.lifecountdown.data.UserPreferencesRepository.UserPreferences
import com.sunstrinq.lifecountdown.ui.composable.LifeCountdownHero
import com.sunstrinq.lifecountdown.ui.composable.LifeCountdownItem
import com.sunstrinq.lifecountdown.utils.DeathUtils
import com.sunstrinq.lifecountdown.utils.format
import java.time.LocalDate

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    birthDate: LocalDate,
    lifeExpectancy: Int,
    onSettingsClick: () -> Unit
) {
    val progress = DeathUtils.lifeProgress(birthDate, lifeExpectancy)
    val daysLeft = DeathUtils.daysLeft(birthDate, lifeExpectancy)
    val weeksLeft = DeathUtils.weeksLeft(birthDate, lifeExpectancy)
    val monthsLeft = DeathUtils.monthsLeft(birthDate, lifeExpectancy)
    val yearsLeft = DeathUtils.yearsLeft(birthDate, lifeExpectancy)

    val stats = listOf(
        "Days Left" to daysLeft.toDouble(),
        "Weeks Left" to weeksLeft.toDouble(),
        "Months Left" to monthsLeft.toDouble(),
        "Years Left" to yearsLeft.toDouble()
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Row
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "LIFE REMAINING",
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // Hero
        LifeCountdownHero(
            birthDate = birthDate,
            lifeExpectancyYears = lifeExpectancy
        )

        Text(
            text = "Life Expectancy: $lifeExpectancy years.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Grid of Stats
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(stats) { (label, value) ->
                LifeCountdownItem(
                    progress = progress, // Or calculate individual progress if it made sense, but it's all same life
                    text = "${value.format()}\n$label"
                )
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    val birthDate = LocalDate.of(1997, 1, 12)
    val lifeExpectancy = 70

    val userPreferences = UserPreferences(birthDate, lifeExpectancy)
    DashboardScreen(
        modifier = Modifier.background(Color.White),
        birthDate = userPreferences.birthDate!!,
        lifeExpectancy = userPreferences.lifeExpectancy,
        onSettingsClick = { }
    )
}