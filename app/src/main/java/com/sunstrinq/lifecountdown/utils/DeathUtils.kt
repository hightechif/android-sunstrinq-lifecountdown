package com.sunstrinq.lifecountdown.utils

import com.sunstrinq.lifecountdown.ui.composable.TimeRemaining
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object DeathUtils {

    // Assumptions (cold, statistical, unapologetic)
    const val LIFE_EXPECTANCY_YEARS = 70f
    private const val DAYS_PER_YEAR = 365.25
    private const val DAYS_PER_MONTH = DAYS_PER_YEAR / 12.0
    private const val DAYS_PER_WEEK = 7.0

    // ---------- Core time calculations ----------

    fun daysLived(birthDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(birthDate, LocalDate.now())
            .coerceAtLeast(0)
    }

    fun totalLifeDays(
        lifeExpectancyYears: Float = LIFE_EXPECTANCY_YEARS
    ): Long {
        return (lifeExpectancyYears * DAYS_PER_YEAR).toLong()
    }

    fun daysLeft(
        birthDate: LocalDate,
        lifeExpectancyYears: Float = LIFE_EXPECTANCY_YEARS
    ): Long {
        val left = totalLifeDays(lifeExpectancyYears) - daysLived(birthDate)
        return left.coerceAtLeast(0)
    }

    // ---------- Remaining time in human-friendly units ----------

    fun weeksLeft(
        birthDate: LocalDate,
        lifeExpectancyYears: Float = LIFE_EXPECTANCY_YEARS
    ): Long {
        return (daysLeft(birthDate, lifeExpectancyYears) / DAYS_PER_WEEK).toLong()
    }

    fun monthsLeft(
        birthDate: LocalDate,
        lifeExpectancyYears: Float = LIFE_EXPECTANCY_YEARS
    ): Long {
        return (daysLeft(birthDate, lifeExpectancyYears) / DAYS_PER_MONTH).toLong()
    }

    fun yearsLeft(
        birthDate: LocalDate,
        lifeExpectancyYears: Float = LIFE_EXPECTANCY_YEARS
    ): Int {
        return (daysLeft(birthDate, lifeExpectancyYears) / DAYS_PER_YEAR).toInt()
    }

    // ---------- Progress for UI (rings, bars, guilt meters) ----------

    fun lifeProgress(
        birthDate: LocalDate,
        lifeExpectancyYears: Float = LIFE_EXPECTANCY_YEARS
    ): Float {
        return (
                daysLeft(birthDate, lifeExpectancyYears).toFloat() /
                        totalLifeDays(lifeExpectancyYears)
                ).coerceIn(0f, 1f)
    }

    fun detailedRemainingTime(birthDate: LocalDate): TimeRemaining {
        val deathDate = birthDate.plusYears(LIFE_EXPECTANCY_YEARS.toLong()).atStartOfDay()
        val now = LocalDateTime.now()

        val duration = Duration.between(now, deathDate)
        val totalSeconds = duration.seconds

        // Simple breakdown (Note: for exact calendar accuracy, use Period for Y/M/D)
        val years = (totalSeconds / (365 * 24 * 3600)).toInt()
        val remainingAfterYears = totalSeconds % (365 * 24 * 3600)

        val months = (remainingAfterYears / (30 * 24 * 3600)).toInt()
        val remainingAfterMonths = remainingAfterYears % (30 * 24 * 3600)

        val days = (remainingAfterMonths / (24 * 3600)).toInt()
        val remainingAfterDays = remainingAfterMonths % (24 * 3600)

        val hours = (remainingAfterDays / 3600).toInt()
        val minutes = (remainingAfterDays % 3600 / 60).toInt()
        val seconds = (remainingAfterDays % 60).toInt()
        val millis = duration.nano / 1_000_000

        return TimeRemaining(years, months, days, hours, minutes, seconds, millis)
    }

}
