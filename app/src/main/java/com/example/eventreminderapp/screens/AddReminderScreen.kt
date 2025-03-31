//package com.example.eventreminderapp.screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import com.example.eventreminderapp.data.model.Reminder
//import com.example.eventreminderapp.viewmodel.ReminderViewModel
//
//@Composable
//fun AddReminderScreen(navController: NavHostController, viewModel: ReminderViewModel) {
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
//        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row {
//            Button(
//                onClick = {
//                    viewModel.addReminder(Reminder(title = title, description = description, date = "", time = ""))
//                    navController.popBackStack() // ✅ Go back to home after saving
//                }
//            ) {
//                Text("Save")
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            Button(onClick = { navController.popBackStack() }) { // ✅ Back button
//                Text("Cancel")
//            }
//        }
//    }
//}






package com.example.eventreminderapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventreminderapp.data.model.Reminder
import com.example.eventreminderapp.viewmodel.ReminderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(navController: NavHostController, viewModel: ReminderViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Reminder") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        viewModel.addReminder(
                            Reminder(title = title, description = description, date = "", time = "")
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Reminder")
            }
        }
    }
}
