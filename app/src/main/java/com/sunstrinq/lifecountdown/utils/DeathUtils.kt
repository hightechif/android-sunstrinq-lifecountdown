package com.sunstrinq.lifecountdown.utils

import com.sunstrinq.lifecountdown.ui.composable.TimeRemaining
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.temporal.ChronoUnit

object DeathUtils {

    // ---------- Core time calculations ----------

    fun daysLived(birthDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(birthDate, LocalDate.now())
            .coerceAtLeast(0)
    }

    fun totalLifeDays(lifeExpectancyYears: Int): Long {
        // Approximate average days including leap years
        return (lifeExpectancyYears * 365.2425).toLong()
    }

    fun daysLeft(birthDate: LocalDate, lifeExpectancyYears: Int): Long {
        val deathDate = birthDate.plusYears(lifeExpectancyYears.toLong())
        return ChronoUnit.DAYS.between(LocalDate.now(), deathDate)
            .coerceAtLeast(0)
    }

    // ---------- Remaining time in human-friendly units ----------

    fun weeksLeft(birthDate: LocalDate, lifeExpectancyYears: Int): Long {
        val deathDate = birthDate.plusYears(lifeExpectancyYears.toLong())
        return ChronoUnit.WEEKS.between(LocalDate.now(), deathDate)
            .coerceAtLeast(0)
    }

    fun monthsLeft(birthDate: LocalDate, lifeExpectancyYears: Int): Long {
        val deathDate = birthDate.plusYears(lifeExpectancyYears.toLong())
        return ChronoUnit.MONTHS.between(LocalDate.now(), deathDate)
            .coerceAtLeast(0)
    }

    fun yearsLeft(birthDate: LocalDate, lifeExpectancyYears: Int): Int {
        val deathDate = birthDate.plusYears(lifeExpectancyYears.toLong())
        return ChronoUnit.YEARS.between(LocalDate.now(), deathDate).toInt()
            .coerceAtLeast(0)
    }

    // ---------- Progress for UI ----------

    fun lifeProgress(birthDate: LocalDate, lifeExpectancyYears: Int): Float {
        val totalDays = totalLifeDays(lifeExpectancyYears)
        val livedDays = daysLived(birthDate)
        return (livedDays.toFloat() / totalDays).coerceIn(0f, 1f)
    }

    // ---------- Detailed Breakdown (The Logic Core) ----------

    fun detailedRemainingTime(birthDate: LocalDate, lifeExpectancyYears: Int): TimeRemaining {
        val deathDate = birthDate.plusYears(lifeExpectancyYears.toLong())
        val deathDateTime = deathDate.atStartOfDay() // Midnight of the death day
        val now = LocalDateTime.now()

        if (now.isAfter(deathDateTime)) {
            return TimeRemaining(0, 0, 0, 0, 0, 0, 0)
        }

        // 1. Calculate Calendar difference (Year, Month, Day)
        // We compare LocalDate to get accurate calendar periods (accounting for leap years, var month lengths)
        // However, if the current time is AFTER the death time (which is midnight), we effectively
        // need to subtract one day from the period because we haven't reached the full day yet.
        // Wait, death is at 00:00:00. So if now is 10:00:00, we are "consuming" the previous day?
        // Let's use Period between NOW (date) and DeathDate.
        
        var period = Period.between(now.toLocalDate(), deathDate)
        
        // Duration accounts for the time difference within the day
        // Distance from now to the next midnight (or death time) is needed.
        // Logic:
        // Period gives X years, Y months, Z days.
        // But if now is 12:00 PM, and target is 00:00 AM, we might be "borrowing" a day from the period.
        // Actually, Period.between(Today, Future) includes 'Today' if we consider full days?
        // No, Period.between(2023-01-01, 2023-01-02) is 1 day.
        
        // Exact Duration for H/M/S
        val duration = Duration.between(now, deathDateTime)
        
        // If we just use duration.toDays(), that's total days.
        // We want Y/M/D + H/M/S.
        
        // Correct algorithm:
        // 1. Calculate Period between now.toLocalDate() and deathDate.
        // 2. This gives full calendar days.
        // 3. Since target is 00:00:00, and we are at HH:MM:SS, we need to subtract 1 from the days count
        //    because we haven't completed the last full day relative to midnight?
        //    Example: Target = Jan 2nd 00:00. Now = Jan 1st 23:00.
        //    Period(Jan 1, Jan 2) = 1 Day.
        //    Duration = 1 Hour.
        //    We want to show: 0 Days, 1 Hour, 0 Min...
        //    So yes, if now.toLocalTime > 00:00 (which is always true unless exactly midnight), we subtract 1 day from Period.
        
        if (now.toLocalTime().toSecondOfDay() > 0) {
            period = period.minusDays(1)
        }

        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60
        val millis = duration.toMillis() % 1000

        return TimeRemaining(
            years = period.years.coerceAtLeast(0),
            months = period.months.coerceAtLeast(0),
            days = period.days.coerceAtLeast(0),
            hours = hours.toInt(),
            minutes = minutes.toInt(),
            seconds = seconds.toInt(),
            millis = millis.toInt()
        )
    }
}
