package com.example.uthtodo.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class TaskTest {
    @Test
    fun toMapAndFromMap_preserveTaskAndSubtaskFields() {
        val task = Task(
            id = "task-1",
            title = "Hoàn thành bài tập",
            note = "Nộp trước hạn",
            category = "study",
            date = "Thứ Hai",
            time = "09:00",
            priority = "high",
            remind = true,
            remindTime = "Trước 15 phút",
            repeat = "Không",
            done = false,
            subtasks = listOf(SubTask(id = "subtask-1", title = "Ôn bài", done = true))
        )

        assertEquals(task, Task.fromMap(task.toMap()))
    }

    @Test
    fun fromMap_ignoresMalformedSubtasksAndUsesDefaults() {
        val task = Task.fromMap(
            mapOf(
                "title" to "Việc mới",
                "subtasks" to listOf("invalid", mapOf("title" to "Việc con"))
            )
        )

        assertEquals("Việc mới", task.title)
        assertEquals("medium", task.priority)
        assertEquals(listOf(SubTask(title = "Việc con")), task.subtasks)
    }
}
