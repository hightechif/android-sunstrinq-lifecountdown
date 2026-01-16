package com.sunstrinq.lifecountdown.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunstrinq.lifecountdown.ui.composable.LifeCountdownHero
import com.sunstrinq.lifecountdown.ui.composable.LifeCountdownItem
import com.sunstrinq.lifecountdown.ui.theme.LifeCountdownTheme
import com.sunstrinq.lifecountdown.utils.DeathUtils
import com.sunstrinq.lifecountdown.utils.format
import java.time.LocalDate

@Composable
fun LifeCountdownScreen(modifier: Modifier = Modifier) {

    val birthDate = LocalDate.of(1998, 9, 14)

    val progress = DeathUtils.lifeProgress(birthDate)

    val daysLeft = DeathUtils.daysLeft(birthDate)
    val weeksLeft = DeathUtils.weeksLeft(birthDate)
    val monthsLeft = DeathUtils.monthsLeft(birthDate)
    val yearsLeft = DeathUtils.yearsLeft(birthDate)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "LIFE REMAINING",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 27.sp
        )
        // The Hero Header
        LifeCountdownHero(birthDate = birthDate)

        Text(text = "Life Expectancy: ${DeathUtils.LIFE_EXPECTANCY_YEARS.toInt()} years old.")
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LifeCountdownItem(progress, "${daysLeft.toDouble().format()} \nDays Left")
            LifeCountdownItem(progress, "${weeksLeft.toDouble().format()} \nWeeks Left")
            LifeCountdownItem(progress, "${monthsLeft.toDouble().format()} \nMonths Left")
            LifeCountdownItem(progress, "${yearsLeft.toDouble().format()} \nYears Left")
        }
    }
}

@Preview
@Composable
private fun LifeCountdownScreenPreview() {
    LifeCountdownTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LifeCountdownScreen(Modifier.padding(innerPadding))
        }
    }
}