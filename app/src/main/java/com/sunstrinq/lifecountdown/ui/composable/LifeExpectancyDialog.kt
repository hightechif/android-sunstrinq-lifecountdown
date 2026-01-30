package com.sunstrinq.lifecountdown.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LifeExpectancyDialog(
    initialValue: Int,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialValue.toString()) }
    var isError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Set Life Expectancy") },
        text = {
            Column(verticalArrangement = Arrangement.Center) {
                Text("Enter the age you statistically expect to reach.")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        isError = it.toIntOrNull() == null
                    },
                    label = { Text("Years") },
                    isError = isError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val value = text.toIntOrNull()
                    if (value != null && value > 0) {
                        onConfirm(value)
                    }
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
private fun LifeExpectancyDialogPreview() {
    LifeExpectancyDialog(
        initialValue = 70,
        onConfirm = { },
        onDismiss = { }
    )
}
