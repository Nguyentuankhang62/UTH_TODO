package com.example.uthtodo.data.model

data class UserProfile(
    val uid: String = "",
    val displayName: String = "Minh Anh",
    val email: String = "minhanh@example.com",
    val photoUrl: String? = null,
    val isGoogleLinked: Boolean = true,
    val isAnonymous: Boolean = false,
    val completedCount: Int = 3,
    val pendingCount: Int = 4,
    val completionPercentage: Int = 43
)
