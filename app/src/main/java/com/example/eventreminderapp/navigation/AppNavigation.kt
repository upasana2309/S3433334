package com.example.eventreminderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eventreminderapp.screens.*
import com.example.eventreminderapp.viewmodel.ReminderViewModel
import com.example.eventreminderapp.viewmodel.HolidayViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: ReminderViewModel
) {
    val holidayViewModel = HolidayViewModel()

    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController)
        }

        composable("home_screen") {
            HomeScreen(navController, viewModel)
        }

        composable("add_reminder") {
            AddReminderScreen(navController, viewModel)
        }

        composable("edit_reminder/{id}") { backStackEntry ->
            val reminderId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            EditReminderScreen(navController, viewModel, reminderId)
        }

        composable("holiday_events") {
            HolidayEventsScreen(navController, holidayViewModel)
        }
    }
}
