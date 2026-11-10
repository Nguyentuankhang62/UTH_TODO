package com.example.uthtodo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.data.model.Task
import com.example.uthtodo.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskSheet(
    onDismiss: () -> Unit,
    onSaveTask: (Task) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("study") } // "study" or "life"
    var date by remember { mutableStateOf("Thứ Tư, 21/10") }
    var time by remember { mutableStateOf("19:00") }
    var remind by remember { mutableStateOf(true) }
    var remindTime by remember { mutableStateOf("Trước 15 phút") }
    var priority by remember { mutableStateOf("medium") } // "low", "medium", "high"

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color(0xFFF7F8FA),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Hủy", color = AppColors.Primary, fontSize = 15.sp)
                }
                Text(
                    text = "Việc mới",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary
                )
                TextButton(
                    onClick = {
                        if (title.isNotBlank()) {
                            onSaveTask(
                                Task(
                                    title = title,
                                    note = note,
                                    category = category,
                                    date = date,
                                    time = time,
                                    priority = priority,
                                    remind = remind,
                                    remindTime = remindTime
                                )
                            )
                            onDismiss()
                        }
                    }
                ) {
                    Text("Lưu", color = AppColors.Primary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }

            // Input Fields Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("Tên công việc (VD: Ôn chương 4 – Giải thuật)", color = Color.Gray, fontSize = 15.sp) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(color = Color(0xFFF0F0F0))

                    TextField(
                        value = note,
                        onValueChange = { note = it },
                        placeholder = { Text("Thêm ghi chú (không bắt buộc)", color = Color.Gray, fontSize = 14.sp) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Category Section (Nhóm)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Nhóm", fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CategorySegment(
                        label = "Học tập",
                        isSelected = category == "study",
                        modifier = Modifier.weight(1f),
                        onClick = { category = "study" }
                    )
                    CategorySegment(
                        label = "Cuộc sống",
                        isSelected = category == "life",
                        modifier = Modifier.weight(1f),
                        onClick = { category = "life" }
                    )
                }
            }

            // Options List Card (Ngày, Giờ, Nhắc tôi)
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(10.dp))
                            Text("Ngày", fontSize = 14.sp, color = AppColors.TextPrimary)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF2F4F7))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(date, fontSize = 13.sp, color = AppColors.TextPrimary)
                        }
                    }

                    HorizontalDivider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(horizontal = 14.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(10.dp))
                            Text("Giờ", fontSize = 14.sp, color = AppColors.TextPrimary)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF2F4F7))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(time, fontSize = 13.sp, color = AppColors.TextPrimary)
                        }
                    }

                    HorizontalDivider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(horizontal = 14.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text("Nhắc tôi", fontSize = 14.sp, color = AppColors.TextPrimary)
                                Text(remindTime, fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                        Switch(
                            checked = remind,
                            onCheckedChange = { remind = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = AppColors.SwitchGreen
                            )
                        )
                    }
                }
            }

            // Priority Section (Mức ưu tiên)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Mức ưu tiên", fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFF4)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PrioritySegment("Thấp", isSelected = priority == "low", modifier = Modifier.weight(1f)) { priority = "low" }
                        PrioritySegment("Vừa", isSelected = priority == "medium", modifier = Modifier.weight(1f)) { priority = "medium" }
                        PrioritySegment("Cao", isSelected = priority == "high", modifier = Modifier.weight(1f)) { priority = "high" }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Submit Button
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSaveTask(
                            Task(
                                title = title,
                                note = note,
                                category = category,
                                date = date,
                                time = time,
                                priority = priority,
                                remind = remind,
                                remindTime = remindTime
                            )
                        )
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
            ) {
                Text("Lưu công việc", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
private fun CategorySegment(
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) Color(0xFFEBF3FE) else Color.White)
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) AppColors.Primary else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) AppColors.Primary else AppColors.TextPrimary
        )
    }
}

@Composable
private fun PrioritySegment(
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) AppColors.TextPrimary else Color.Gray
        )
    }
}
