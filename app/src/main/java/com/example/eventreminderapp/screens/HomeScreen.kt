package com.example.eventreminderapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventreminderapp.data.model.Reminder
import com.example.eventreminderapp.viewmodel.ReminderFilter
import com.example.eventreminderapp.viewmodel.ReminderViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: ReminderViewModel) {
    val reminders by viewModel.filteredReminders.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedFilter by remember { mutableStateOf(ReminderFilter.ALL) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Reminder") },
                actions = {
                    IconButton(onClick = { navController.navigate("holiday_events") }) {
                        Icon(Icons.Filled.CalendarToday, contentDescription = "Holiday Events")
                    }
                }
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("holiday_events") },
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = "Holiday Events")
                }
                FloatingActionButton(
                    onClick = { navController.navigate("add_reminder") }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Reminder")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.searchReminders(it.text)
                },
                label = { Text("Search Reminders") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            SegmentedControl(
                options = listOf("All", "Upcoming", "Past"),
                selectedIndex = selectedFilter.ordinal
            ) { index ->
                selectedFilter = ReminderFilter.entries.toTypedArray()[index]
                viewModel.setFilter(selectedFilter)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (reminders.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No reminders available. Tap + to add one!")
                }
            } else {
                LazyColumn {
                    items(reminders) { reminder ->
                        var showDialog by remember { mutableStateOf(false) }

                        ReminderItem(
                            reminder = reminder,
                            onEdit = {
                                navController.navigate("edit_reminder/${reminder.id}")
                            },
                            onDelete = { showDialog = true },
                            modifier = Modifier.pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        showDialog = true
                                    }
                                )
                            }
                        )

                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Delete Reminder") },
                                text = { Text("Are you sure you want to delete this reminder?") },
                                confirmButton = {
                                    TextButton(onClick = {
                                        scope.launch {
                                            viewModel.deleteReminder(reminder)
                                        }
                                        showDialog = false
                                    }) {
                                        Text("Delete")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDialog = false }) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReminderItem(reminder: Reminder, onEdit: () -> Unit, onDelete: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onEdit() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // 🌟 Holiday badge
            if (reminder.isHoliday) {
                Text(
                    text = "Holiday",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 4.dp)
                )
            }

            Text(text = reminder.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = reminder.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${reminder.date} at ${reminder.time}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Edit")
                }
                Button(
                    onClick = onDelete,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}


@Composable
fun SegmentedControl(options: List<String>, selectedIndex: Int, onOptionSelected: (Int) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        options.forEachIndexed { index, text ->
            val selected = index == selectedIndex
            val background = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
            val contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

            Surface(
                color = background,
                shape = MaterialTheme.shapes.small,
                tonalElevation = 2.dp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clickable { onOptionSelected(index) }
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = text, color = contentColor)
                }
            }
        }
    }
}
