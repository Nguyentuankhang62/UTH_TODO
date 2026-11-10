package com.example.uthtodo.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.data.model.Task
import com.example.uthtodo.ui.theme.AppColors

@Composable
fun TaskListScreen(
    tasks: List<Task>,
    selectedCategoryFilter: String? = null,
    onToggleTask: (String) -> Unit,
    onTaskClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember(selectedCategoryFilter) {
        mutableStateOf(selectedCategoryFilter ?: "all")
    }

    // Filter tasks
    val filteredTasks = tasks.filter { task ->
        val matchesSearch = searchQuery.isBlank() || task.title.contains(searchQuery, ignoreCase = true)
        val matchesCategory = when (selectedFilter) {
            "study" -> task.category.lowercase() in listOf("study", "học tập")
            "life" -> task.category.lowercase() in listOf("life", "cuộc sống")
            else -> true
        }
        matchesSearch && matchesCategory
    }

    val pendingTasks = filteredTasks.filter { !it.done }
    val completedTasks = filteredTasks.filter { it.done }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // TOP HEADER
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Danh sách",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TextPrimary
                    )

                    Text(
                        text = "${pendingTasks.size} việc chưa xong",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            // SEARCH BAR
            item {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Tìm công việc", color = Color.Gray, fontSize = 14.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Tìm kiếm", tint = Color.Gray, modifier = Modifier.size(20.dp))
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFECEEF2),
                        unfocusedContainerColor = Color(0xFFECEEF2),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )
            }

            // FILTER CHIPS ROW ("Tất cả", "Học tập", "Cuộc sống")
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChipPill(
                        label = "Tất cả",
                        isSelected = selectedFilter == "all",
                        onClick = { selectedFilter = "all" }
                    )
                    FilterChipPill(
                        label = "Học tập",
                        isSelected = selectedFilter == "study",
                        onClick = { selectedFilter = "study" }
                    )
                    FilterChipPill(
                        label = "Cuộc sống",
                        isSelected = selectedFilter == "life",
                        onClick = { selectedFilter = "life" }
                    )
                }
            }

            // SECTION: CẦN LÀM
            if (pendingTasks.isNotEmpty()) {
                item {
                    Text(
                        text = "CẦN LÀM",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(pendingTasks) { task ->
                    TaskListTaskItem(
                        task = task,
                        onToggle = { onToggleTask(task.id) },
                        onClick = { onTaskClick(task.id) }
                    )
                }
            }

            // SECTION: ĐÃ XONG
            if (completedTasks.isNotEmpty()) {
                item {
                    Text(
                        text = "ĐÃ XONG",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                items(completedTasks) { task ->
                    TaskListTaskItem(
                        task = task,
                        onToggle = { onToggleTask(task.id) },
                        onClick = { onTaskClick(task.id) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun FilterChipPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) AppColors.TextPrimary else Color.White)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else AppColors.TextPrimary
        )
    }
}

@Composable
private fun TaskListTaskItem(
    task: Task,
    onToggle: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox Circle
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (task.done) AppColors.Primary else Color.Transparent)
                    .clickable(onClick = onToggle),
                contentAlignment = Alignment.Center
            ) {
                if (task.done) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Hoàn thành",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Surface(
                        shape = CircleShape,
                        color = Color.Transparent,
                        border = BorderStroke(1.5.dp, Color(0xFFD0D5DD)),
                        modifier = Modifier.fillMaxSize()
                    ) {}
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Task Title and details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (task.done) Color.Gray else AppColors.TextPrimary,
                    textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                )

                if (!task.done) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val isStudy = task.category.lowercase() in listOf("study", "học tập")
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isStudy) Color(0xFFEBF3FE) else Color(0xFFFFF4E5))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (isStudy) "Học tập" else "Cuộc sống",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isStudy) AppColors.Primary else Color(0xFFE67E22)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = task.date,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // High Priority Badge ("Gấp")
            if (!task.done && task.isUrgent) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFFFEAEA))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Gấp",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE53935)
                    )
                }
            }
        }
    }
}
