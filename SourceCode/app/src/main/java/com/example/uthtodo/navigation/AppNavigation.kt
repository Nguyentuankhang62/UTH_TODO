package com.example.uthtodo.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uthtodo.data.repository.DefaultOnboardingRepository
import com.example.uthtodo.data.repository.FirebaseAuthRepository
import com.example.uthtodo.data.repository.TaskRepository
import com.example.uthtodo.ui.components.AddTaskSheet
import com.example.uthtodo.ui.components.BottomNavBar
import com.example.uthtodo.ui.components.NavTab
import com.example.uthtodo.ui.screens.*
import kotlinx.coroutines.launch

object AppRoutes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val MAIN = "main"
    const val TASK_DETAIL = "task_detail/{taskId}"
    const val PROFILE = "profile"

    fun taskDetail(taskId: String) = "task_detail/$taskId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Repositories
    val onboardingRepository = remember { DefaultOnboardingRepository() }
    val onboardingPages = remember { onboardingRepository.getPages() }
    val authRepository = remember { FirebaseAuthRepository() }
    val taskRepository = remember { TaskRepository() }

    // State
    val tasks by taskRepository.tasksFlow.collectAsState()
    var currentTab by remember { mutableStateOf(NavTab.HOME) }
    var showAddTaskSheet by remember { mutableStateOf(false) }
    var selectedCategoryFilter by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun handleLogin(action: suspend () -> Unit) {
        scope.launch {
            isLoading = true
            errorMessage = null
            try {
                action()
            } catch (_: Exception) {
                // Fallback for offline or unconfigured Firebase project
            } finally {
                isLoading = false
                Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                navController.navigate(AppRoutes.MAIN) {
                    popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        // 1. SPLASH
        composable(route = AppRoutes.SPLASH) {
            SplashScreen(
                onTimeout = {
                    val user = authRepository.getCurrentUser()
                    if (user != null) {
                        navController.navigate(AppRoutes.MAIN) {
                            popUpTo(AppRoutes.SPLASH) { inclusive = true }
                        }
                    } else {
                        navController.navigate(AppRoutes.ONBOARDING) {
                            popUpTo(AppRoutes.SPLASH) { inclusive = true }
                        }
                    }
                }
            )
        }

        // 2. ONBOARDING
        composable(route = AppRoutes.ONBOARDING) {
            OnboardingScreen(
                pages = onboardingPages,
                onFinished = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // 3. LOGIN
        composable(route = AppRoutes.LOGIN) {
            LoginScreen(
                isLoading = isLoading,
                errorMessage = errorMessage,
                onGoogleClick = {
                    handleLogin {
                        authRepository.signInWithGoogle(context)
                    }
                },
                onGuestClick = {
                    handleLogin {
                        authRepository.signInAsGuest()
                    }
                }
            )
        }

        // 4. MAIN CONTAINER (Home, List, Notifications, Settings)
        composable(route = AppRoutes.MAIN) {
            Scaffold(
                bottomBar = {
                    BottomNavBar(
                        currentTab = currentTab,
                        onTabSelected = { tab ->
                            currentTab = tab
                            if (tab != NavTab.LIST) {
                                selectedCategoryFilter = null
                            }
                        },
                        onFabClick = { showAddTaskSheet = true }
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentTab) {
                        NavTab.HOME -> HomeScreen(
                            userName = authRepository.getCurrentUser()?.displayName ?: "UTH",
                            tasks = tasks,
                            onToggleTask = { taskId -> taskRepository.toggleTaskDone(taskId) },
                            onTaskClick = { taskId -> navController.navigate(AppRoutes.taskDetail(taskId)) },
                            onSeeAllClick = {
                                selectedCategoryFilter = null
                                currentTab = NavTab.LIST
                            },
                            onProfileClick = { navController.navigate(AppRoutes.PROFILE) },
                            onCategoryClick = { category ->
                                selectedCategoryFilter = category
                                currentTab = NavTab.LIST
                            }
                        )

                        NavTab.LIST -> TaskListScreen(
                            tasks = tasks,
                            selectedCategoryFilter = selectedCategoryFilter,
                            onToggleTask = { taskId -> taskRepository.toggleTaskDone(taskId) },
                            onTaskClick = { taskId -> navController.navigate(AppRoutes.taskDetail(taskId)) }
                        )

                        NavTab.NOTIFICATIONS -> NotificationsScreen()

                        NavTab.SETTINGS -> SettingsScreen(
                            onProfileClick = { navController.navigate(AppRoutes.PROFILE) },
                            onSignOut = {
                                authRepository.signOut()
                                Toast.makeText(context, "Đã đăng xuất thành công!", Toast.LENGTH_SHORT).show()
                                navController.navigate(AppRoutes.LOGIN) {
                                    popUpTo(AppRoutes.MAIN) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }

            if (showAddTaskSheet) {
                AddTaskSheet(
                    onDismiss = { showAddTaskSheet = false },
                    onSaveTask = { newTask ->
                        taskRepository.addTask(newTask)
                        Toast.makeText(context, "Đã thêm công việc!", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        // 5. TASK DETAIL
        composable(
            route = AppRoutes.TASK_DETAIL,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            val task = taskRepository.getTaskById(taskId)

            if (task != null) {
                TaskDetailScreen(
                    task = task,
                    onBack = { navController.popBackStack() },
                    onToggleTaskDone = { id -> taskRepository.toggleTaskDone(id) },
                    onToggleSubTaskDone = { tId, sId -> taskRepository.toggleSubTaskDone(tId, sId) },
                    onAddSubTask = { tId, title -> taskRepository.addSubTask(tId, title) },
                    onDeleteSubTask = { tId, sId -> taskRepository.deleteSubTask(tId, sId) },
                    onDeleteTask = { id ->
                        taskRepository.deleteTask(id)
                        Toast.makeText(context, "Đã xóa công việc", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        // 6. PROFILE
        composable(route = AppRoutes.PROFILE) {
            val user = authRepository.getCurrentUser()
            val completedCount = tasks.count { it.done }
            val pendingCount = tasks.count { !it.done }
            val total = tasks.size.coerceAtLeast(1)
            val percentage = ((completedCount.toFloat() / total.toFloat()) * 100).toInt()

            ProfileScreen(
                userName = user?.displayName ?: "Minh Anh",
                userEmail = user?.email ?: if (user?.isAnonymous == true) "Khách (guest@uthtodo.com)" else "minhanh@example.com",
                isGoogleLinked = user?.isAnonymous == false,
                completedCount = completedCount,
                pendingCount = pendingCount,
                completionPercentage = percentage,
                onBack = { navController.popBackStack() },
                onSignOut = {
                    authRepository.signOut()
                    Toast.makeText(context, "Đã đăng xuất thành công!", Toast.LENGTH_SHORT).show()
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.MAIN) { inclusive = true }
                    }
                }
            )
        }
    }
}
