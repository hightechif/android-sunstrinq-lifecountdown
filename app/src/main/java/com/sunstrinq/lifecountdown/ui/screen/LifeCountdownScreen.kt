package com.sunstrinq.lifecountdown.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.sunstrinq.lifecountdown.ui.composable.DateSelectionDialog
import com.sunstrinq.lifecountdown.ui.composable.LifeExpectancyDialog
import com.sunstrinq.lifecountdown.ui.theme.LifeCountdownTheme
import com.sunstrinq.lifecountdown.ui.viewmodel.LifeCountdownViewModel

@Composable
fun LifeCountdownScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: LifeCountdownViewModel = koinViewModel()

    val userPreferences by viewModel.userPreferences.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showExpectancyDialog by remember { mutableStateOf(false) }
    var showSettingsSheet by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DateSelectionDialog(
            onDateSelected = { date ->
                viewModel.updateBirthDate(date)
            },
            onDismiss = { }
        )
    }

    if (showExpectancyDialog) {
        LifeExpectancyDialog(
            initialValue = userPreferences.lifeExpectancy,
            onConfirm = { years ->
                viewModel.updateLifeExpectancy(years)
            },
            onDismiss = { }
        )
    }

    if (showSettingsSheet) {
        SettingsBottomSheet(
            onDismiss = { },
            onEditDate = { },
            onEditExpectancy = { }
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (userPreferences.birthDate == null) {
            // Welcome / Setup Screen
            SetupScreen(onSetupClick = { })
        } else {
            // Dashboard
            DashboardScreen(
                birthDate = userPreferences.birthDate!!,
                lifeExpectancy = userPreferences.lifeExpectancy,
                onSettingsClick = { }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    onDismiss: () -> Unit,
    onEditDate: () -> Unit,
    onEditExpectancy: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                "Settings",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            SettingsItem(
                title = "Edit Date of Birth",
                onClick = onEditDate
            )
            SettingsItem(
                title = "Edit Life Expectancy",
                onClick = onEditExpectancy
            )
        }
    }
}

@Composable
fun SettingsItem(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
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