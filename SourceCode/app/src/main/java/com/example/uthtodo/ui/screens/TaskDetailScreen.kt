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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.data.model.Task
import com.example.uthtodo.ui.theme.AppColors

@Composable
fun TaskDetailScreen(
    task: Task,
    onBack: () -> Unit,
    onToggleTaskDone: (String) -> Unit,
    onToggleSubTaskDone: (String, String) -> Unit,
    onAddSubTask: (String, String) -> Unit,
    onDeleteSubTask: (String, String) -> Unit,
    onDeleteTask: (String) -> Unit
) {
    var newSubTaskTitle by remember { mutableStateOf("") }
    val completedSubtasks = task.subtasks.count { it.done }
    val totalSubtasks = task.subtasks.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
    ) {
        // HEADER BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = onBack)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Quay lại",
                    tint = AppColors.Primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Danh sách",
                    fontSize = 15.sp,
                    color = AppColors.Primary,
                    fontWeight = FontWeight.Medium
                )
            }

            TextButton(onClick = { /* Handle Edit */ }) {
                Text(
                    text = "Sửa",
                    fontSize = 15.sp,
                    color = AppColors.Primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(12.dp)) }

            // CHIPS ROW (Category & Priority)
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Category Chip
                    val isStudy = task.category.lowercase() in listOf("study", "học tập")
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isStudy) Color(0xFFEBF3FE) else Color(0xFFFFF4E5))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (isStudy) "Học tập" else "Cuộc sống",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isStudy) AppColors.Primary else Color(0xFFE67E22)
                        )
                    }

                    // Priority Chip
                    val isHigh = task.isUrgent
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isHigh) Color(0xFFFFEAEA) else Color(0xFFF0F2F5))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (isHigh) "Ưu tiên cao" else task.priorityDisplayName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isHigh) Color(0xFFE53935) else Color(0xFF4A5568)
                        )
                    }
                }
            }

            // TASK TITLE
            item {
                Text(
                    text = task.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary,
                    lineHeight = 28.sp
                )
            }

            // DETAILS CARD (Hạn chót, Nhắc nhở, Lặp lại)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        DetailItemRow(
                            icon = Icons.Default.DateRange,
                            label = "Hạn chót",
                            value = task.date
                        )

                        HorizontalDivider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(vertical = 10.dp))

                        DetailItemRow(
                            icon = Icons.Default.Notifications,
                            label = "Nhắc nhở",
                            value = task.remindTime
                        )

                        HorizontalDivider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(vertical = 10.dp))

                        DetailItemRow(
                            icon = Icons.Default.Refresh,
                            label = "Lặp lại",
                            value = task.repeat
                        )
                    }
                }
            }

            // SECTION: GHI CHÚ
            if (task.note.isNotBlank()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Ghi chú",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(14.dp),
                            elevation = CardDefaults.cardElevation(0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = task.note,
                                fontSize = 14.sp,
                                color = AppColors.TextPrimary,
                                modifier = Modifier.padding(14.dp),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            // SECTION: VIỆC NHỎ
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Việc nhỏ",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )

                        if (totalSubtasks > 0) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                LinearProgressIndicator(
                                    progress = { completedSubtasks.toFloat() / totalSubtasks.toFloat() },
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp)),
                                    color = AppColors.Primary,
                                    trackColor = Color(0xFFE2E8F0)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$completedSubtasks/$totalSubtasks",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            task.subtasks.forEachIndexed { index, subtask ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Subtask Checkbox
                                    Box(
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clip(CircleShape)
                                            .background(if (subtask.done) AppColors.Primary else Color.Transparent)
                                            .clickable { onToggleSubTaskDone(task.id, subtask.id) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (subtask.done) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
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

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        text = subtask.title,
                                        fontSize = 14.sp,
                                        color = if (subtask.done) Color.Gray else AppColors.TextPrimary,
                                        textDecoration = if (subtask.done) TextDecoration.LineThrough else TextDecoration.None,
                                        modifier = Modifier.weight(1f)
                                    )

                                    // Action Icons (Edit & Delete)
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Sửa",
                                        tint = Color.LightGray,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clickable { /* Edit */ }
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Xóa",
                                        tint = Color(0xFFE53935),
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clickable { onDeleteSubTask(task.id, subtask.id) }
                                    )
                                }

                                if (index < task.subtasks.lastIndex) {
                                    HorizontalDivider(color = Color(0xFFF5F5F5))
                                }
                            }

                            // Add Subtask Row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextField(
                                    value = newSubTaskTitle,
                                    onValueChange = { newSubTaskTitle = it },
                                    placeholder = { Text("+ Thêm việc nhỏ", color = Color.Gray, fontSize = 14.sp) },
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    modifier = Modifier.weight(1f)
                                )

                                TextButton(
                                    onClick = {
                                        if (newSubTaskTitle.isNotBlank()) {
                                            onAddSubTask(task.id, newSubTaskTitle)
                                            newSubTaskTitle = ""
                                        }
                                    }
                                ) {
                                    Text("Thêm", color = AppColors.Primary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }

            // BOTTOM ACTION BUTTONS
            item {
                Column(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onToggleTaskDone(task.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary
                        )
                    ) {
                        Text(
                            text = if (task.done) "Đánh dấu chưa hoàn thành" else "Đánh dấu hoàn thành",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    TextButton(
                        onClick = {
                            onDeleteTask(task.id)
                            onBack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Xóa công việc",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE53935)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailItemRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.TextPrimary
        )
    }
}
