package com.example.uthtodo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.uthtodo.data.model.AppContent

/** Màn hình đích tạm thời để kiểm tra điều hướng từ onboarding. */
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = AppContent.HOME_PLACEHOLDER,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = AppContent.HOME_DESCRIPTION, fontSize = 14.sp)
    }
}
