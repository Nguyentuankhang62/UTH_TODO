package com.example.uthtodo.data.model

data class SubTask(
    val id: String = "",
    val title: String = "",
    val done: Boolean = false
)

data class Task(
    val id: String = "",
    val title: String = "",
    val note: String = "",
    val category: String = "study", // "study" (Học tập) hoặc "life" (Cuộc sống)
    val date: String = "Hôm nay, 09:00",
    val time: String = "09:00",
    val priority: String = "medium", // "low" (Thấp), "medium" (Vừa), "high" (Cao / Gấp)
    val remind: Boolean = true,
    val remindTime: String = "Trước 15 phút",
    val repeat: String = "Không",
    val done: Boolean = false,
    val subtasks: List<SubTask> = emptyList()
) {
    val categoryDisplayName: String
        get() = when (category.lowercase()) {
            "study", "học tập" -> "Học tập"
            "life", "cuộc sống" -> "Cuộc sống"
            else -> category
        }

    val priorityDisplayName: String
        get() = when (priority.lowercase()) {
            "high", "gấp", "cao" -> "Ưu tiên cao"
            "low", "thấp" -> "Ưu tiên thấp"
            else -> "Ưu tiên vừa"
        }

    val isUrgent: Boolean
        get() = priority.lowercase() in listOf("high", "gấp", "cao")
}
