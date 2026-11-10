package com.example.uthtodo.data.model

data class SubTask(
    val id: String = "",
    val title: String = "",
    val done: Boolean = false
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "title" to title,
        "done" to done
    )

    companion object {
        fun fromMap(map: Map<*, *>): SubTask {
            return SubTask(
                id = map["id"] as? String ?: "",
                title = map["title"] as? String ?: "",
                done = map["done"] as? Boolean ?: false
            )
        }
    }
}

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

    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "title" to title,
        "note" to note,
        "category" to category,
        "date" to date,
        "time" to time,
        "priority" to priority,
        "remind" to remind,
        "remindTime" to remindTime,
        "repeat" to repeat,
        "done" to done,
        "subtasks" to subtasks.map { it.toMap() }
    )

    companion object {
        fun fromMap(map: Map<String, Any?>): Task {
            val subtasks = (map["subtasks"] as? List<*>)
                .orEmpty()
                .mapNotNull { it as? Map<*, *> }
                .map(SubTask::fromMap)
            return Task(
                id = map["id"] as? String ?: "",
                title = map["title"] as? String ?: "",
                note = map["note"] as? String ?: "",
                category = map["category"] as? String ?: "study",
                date = map["date"] as? String ?: "",
                time = map["time"] as? String ?: "",
                priority = map["priority"] as? String ?: "medium",
                remind = map["remind"] as? Boolean ?: true,
                remindTime = map["remindTime"] as? String ?: "Trước 15 phút",
                repeat = map["repeat"] as? String ?: "Không",
                done = map["done"] as? Boolean ?: false,
                subtasks = subtasks
            )
        }
    }
}
