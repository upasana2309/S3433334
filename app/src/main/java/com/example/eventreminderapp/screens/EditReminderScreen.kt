package com.example.eventreminderapp.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventreminderapp.data.model.Reminder
import com.example.eventreminderapp.viewmodel.ReminderViewModel
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("DefaultLocale", "NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReminderScreen(navController: NavHostController, viewModel: ReminderViewModel, reminderId: Int) {
    val context = LocalContext.current
    var reminder by remember { mutableStateOf<Reminder?>(null) }

    LaunchedEffect(Unit) {
        reminder = viewModel.allReminders.first().find { it.id == reminderId }
    }

    reminder?.let {
        var title by remember { mutableStateOf(it.title) }
        var description by remember { mutableStateOf(it.description) }
        var date by remember { mutableStateOf(it.date) }
        var time by remember { mutableStateOf(it.time) }

        val calendar = remember {
            Calendar.getInstance().apply {
                timeInMillis = it.timestamp
            }
        }

        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Edit Reminder") })
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Date Picker
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                date = "$day/${month + 1}/$year"
                                calendar.set(year, month, day)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }) {
                    OutlinedTextField(
                        value = date,
                        onValueChange = {},
                        label = { Text("Select Date") },
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Time Picker
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                time = String.format("%02d:%02d", hour, minute)
                                calendar.set(Calendar.HOUR_OF_DAY, hour)
                                calendar.set(Calendar.MINUTE, minute)
                                calendar.set(Calendar.SECOND, 0)
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false
                        ).show()
                    }) {
                    OutlinedTextField(
                        value = time,
                        onValueChange = {},
                        label = { Text("Select Time") },
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Button(
                    onClick = {
                        if (title.isNotBlank() && date.isNotBlank() && time.isNotBlank()) {
                            try {
                                val selectedDate = LocalDate.parse(date, formatter)

                                if (viewModel.holidayDates.contains(selectedDate)) {
                                    Toast.makeText(
                                        context,
                                        "Cannot update reminder to a public holiday.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@Button
                                }

                                val updated = it.copy(
                                    title = title,
                                    description = description,
                                    date = date,
                                    time = time,
                                    timestamp = calendar.timeInMillis
                                )
                                viewModel.updateReminder(updated)
                                navController.popBackStack()

                            } catch (e: Exception) {
                                Toast.makeText(context, "Invalid date format", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Reminder")
                }
            }
        }
    }
}
