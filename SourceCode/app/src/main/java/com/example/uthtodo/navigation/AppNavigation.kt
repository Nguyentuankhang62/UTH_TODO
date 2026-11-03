package com.example.uthtodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uthtodo.data.repository.DefaultOnboardingRepository
import com.example.uthtodo.ui.screens.HomeScreen
import com.example.uthtodo.ui.screens.OnboardingScreen
import com.example.uthtodo.ui.screens.SplashScreen

object AppRoutes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val onboardingRepository = remember { DefaultOnboardingRepository() }
    val onboardingPages = remember { onboardingRepository.getPages() }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        composable(route = AppRoutes.SPLASH) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(AppRoutes.ONBOARDING) {
                        popUpTo(AppRoutes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = AppRoutes.ONBOARDING) {
            OnboardingScreen(
                pages = onboardingPages,
                onFinished = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.ONBOARDING) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = AppRoutes.HOME) {
            HomeScreen()
        }
    }
}
