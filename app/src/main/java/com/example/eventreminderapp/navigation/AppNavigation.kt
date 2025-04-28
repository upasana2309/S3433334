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
        startDestination = "login_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController)
        }

        composable("home_screen") {
            HomeScreen(navController, viewModel, onLogout = ({ navController.navigate("login_screen") }))
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

        composable("login_screen"){
            LoginScreen(onLogin = ({ _, _ -> navController.navigate("home_screen") }), onSignUp = ({ navController.navigate("signup_screen") }), onForgotPassword = ({ navController.navigate("forgot_password_screen") }))
        }
        composable("signup_screen"){}
        composable("forgot_password_screen"){}

//        composable("login_screen") {
//            LoginScreen(onLogin = ({ _, _ -> navController.navigate("home_screen") }), onSignUp = ({ navController.navigate("signup_screen") }), onForgotPassword = ({ navController.navigate("forgot_password_screen") }))
//        }
//
//        composable("signup_screen") {
//            SignUpScreen(onSignUp = ({ _, _ -> navController.navigate("home_screen") }), onLoginRedirect = ({ navController.navigate("login_screen") }))
//        }
//
//        composable("forgot_password_screen") {
//            ForgotPasswordScreen(
//                onResetPassword = { _ -> navController.navigate("login_screen") },
//                onBackToLogin = { navController.navigate("login_screen") }
//            )
//        }
    }
}