package com.sunstrinq.lifecountdown.ui.composable

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectionDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    val date = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate()
                    onDateSelected(date)
                }
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
