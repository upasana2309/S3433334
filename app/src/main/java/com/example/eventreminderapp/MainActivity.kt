package com.example.eventreminderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.eventreminderapp.navigation.AppNavigation
import com.example.eventreminderapp.notifications.NotificationHelper
import com.example.eventreminderapp.viewmodel.ReminderViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createNotificationChannel(this)
        setContent {
            val navController = rememberNavController()
            val viewModel: ReminderViewModel = viewModel()
            AppNavigation(navController, viewModel)
        }
    }
}
