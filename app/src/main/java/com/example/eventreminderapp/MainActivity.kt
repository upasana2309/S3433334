package com.example.eventreminderapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.eventreminderapp.navigation.AppNavigation
import com.example.eventreminderapp.notifications.NotificationHelper
import com.example.eventreminderapp.screens.LoginActivity
import com.example.eventreminderapp.viewmodel.ReminderViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createNotificationChannel(this)
        FirebaseApp.initializeApp(this)

        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            setContent {
                val navController = rememberNavController()
                val viewModel: ReminderViewModel = viewModel()
                AppNavigation(navController, viewModel)
            }
        }
    }
}