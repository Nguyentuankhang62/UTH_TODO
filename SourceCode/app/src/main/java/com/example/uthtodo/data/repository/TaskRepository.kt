package com.example.uthtodo.data.repository

import com.example.uthtodo.data.model.SubTask
import com.example.uthtodo.data.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class TaskRepository {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val initialTasks = listOf(
        Task(
            id = "1",
            title = "Nộp bài tập Lập trình di động",
            note = "Nộp file .zip lên hệ thống của trường. Nhớ kèm ảnh chụp màn hình app chạy trên máy ảo.",
            category = "study",
            date = "Hôm nay, 09:00",
            time = "09:00",
            priority = "high",
            remind = true,
            remindTime = "Trước 15 phút",
            repeat = "Không",
            done = false,
            subtasks = listOf(
                SubTask(id = "1-1", title = "Hoàn thiện màn hình đăng nhập", done = true),
                SubTask(id = "1-2", title = "Chụp ảnh màn hình app", done = true),
                SubTask(id = "1-3", title = "Nén file và nộp bài", done = false)
            )
        ),
        Task(
            id = "2",
            title = "Ôn chương 3 – Cấu trúc dữ liệu",
            note = "Tập trung phần cây nhị phân và bảng băm.",
            category = "study",
            date = "Hôm nay, 14:00",
            time = "14:00",
            priority = "medium",
            remind = true,
            remindTime = "Trước 15 phút",
            repeat = "Không",
            done = false,
            subtasks = listOf(
                SubTask(id = "2-1", title = "Đọc lại slide bài giảng", done = true),
                SubTask(id = "2-2", title = "Làm 10 bài tập cuối chương...", done = false)
            )
        ),
        Task(
            id = "3",
            title = "Đi chợ mua đồ cho tuần",
            note = "",
            category = "life",
            date = "Hôm nay, 17:30",
            time = "17:30",
            priority = "medium",
            remind = true,
            remindTime = "Trước 15 phút",
            repeat = "Không",
            done = false
        ),
        Task(
            id = "4",
            title = "Họp nhóm đồ án",
            note = "",
            category = "study",
            date = "Thứ Năm, 20:00",
            time = "20:00",
            priority = "medium",
            remind = true,
            remindTime = "Trước 15 phút",
            repeat = "Không",
            done = false
        ),
        Task(
            id = "5",
            title = "Tập thể dục 30 phút",
            note = "",
            category = "life",
            date = "Hôm nay, 06:30",
            time = "06:30",
            priority = "low",
            remind = false,
            repeat = "Hằng ngày",
            done = true
        ),
        Task(
            id = "6",
            title = "Đọc 20 trang sách",
            note = "",
            category = "life",
            date = "Hôm nay, 21:00",
            time = "21:00",
            priority = "low",
            remind = false,
            repeat = "Hằng ngày",
            done = true
        )
    )

    private val _tasksFlow = MutableStateFlow<List<Task>>(initialTasks)
    val tasksFlow: StateFlow<List<Task>> = _tasksFlow.asStateFlow()

    fun getTaskById(taskId: String): Task? {
        return _tasksFlow.value.find { it.id == taskId }
    }

    fun toggleTaskDone(taskId: String) {
        val updated = _tasksFlow.value.map { task ->
            if (task.id == taskId) {
                val newDone = !task.done
                task.copy(done = newDone)
            } else {
                task
            }
        }
        _tasksFlow.value = updated
        syncToFirebase(taskId)
    }

    fun toggleSubTaskDone(taskId: String, subTaskId: String) {
        val updated = _tasksFlow.value.map { task ->
            if (task.id == taskId) {
                val newSubTasks = task.subtasks.map { sub ->
                    if (sub.id == subTaskId) sub.copy(done = !sub.done) else sub
                }
                task.copy(subtasks = newSubTasks)
            } else {
                task
            }
        }
        _tasksFlow.value = updated
        syncToFirebase(taskId)
    }

    fun addSubTask(taskId: String, title: String) {
        if (title.isBlank()) return
        val updated = _tasksFlow.value.map { task ->
            if (task.id == taskId) {
                val newSubTask = SubTask(
                    id = System.currentTimeMillis().toString(),
                    title = title,
                    done = false
                )
                task.copy(subtasks = task.subtasks + newSubTask)
            } else {
                task
            }
        }
        _tasksFlow.value = updated
        syncToFirebase(taskId)
    }

    fun deleteSubTask(taskId: String, subTaskId: String) {
        val updated = _tasksFlow.value.map { task ->
            if (task.id == taskId) {
                task.copy(subtasks = task.subtasks.filterNot { it.id == subTaskId })
            } else {
                task
            }
        }
        _tasksFlow.value = updated
        syncToFirebase(taskId)
    }

    fun addTask(task: Task) {
        val newTask = task.copy(
            id = if (task.id.isBlank()) System.currentTimeMillis().toString() else task.id
        )
        _tasksFlow.value = listOf(newTask) + _tasksFlow.value
        syncToFirebase(newTask.id)
    }

    fun updateTask(task: Task) {
        val updated = _tasksFlow.value.map {
            if (it.id == task.id) task else it
        }
        _tasksFlow.value = updated
        syncToFirebase(task.id)
    }

    fun deleteTask(taskId: String) {
        _tasksFlow.value = _tasksFlow.value.filterNot { it.id == taskId }
        try {
            val userId = auth.currentUser?.uid ?: "anonymous_user"
            db.collection("users").document(userId).collection("tasks").document(taskId).delete()
        } catch (_: Exception) {
            // Safe fallback if Firebase is not configured online
        }
    }

    private fun syncToFirebase(taskId: String) {
        try {
            val userId = auth.currentUser?.uid ?: "anonymous_user"
            val task = getTaskById(taskId) ?: return
            db.collection("users").document(userId).collection("tasks").document(taskId).set(task)
        } catch (_: Exception) {
            // Safe fallback when Firebase offline
        }
    }
}
