package com.example.uthtodo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.data.model.AppContent
import com.example.uthtodo.ui.components.AppLogoMark
import com.example.uthtodo.ui.components.AppWordmark
import com.example.uthtodo.ui.theme.AppColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1_800)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App icon nền trắng như mockup Splash trong ảnh mẫu.
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .background(Color.White, RoundedCornerShape(17.dp)),
                contentAlignment = Alignment.Center
            ) {
                AppLogoMark(
                    size = 53.dp,
                    backgroundColor = Color.Transparent,
                    checkCircleColor = AppColors.Primary,
                    checkColor = Color.White,
                    firstLineColor = AppColors.Primary,
                    secondCircleColor = AppColors.SoftBlue,
                    secondLineColor = AppColors.SoftBlue,
                    drawBackground = false
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            AppWordmark(
                fontSize = 27.sp,
                uthColor = Color.White,
                todoColor = Color.White,
                showSubtitle = true,
                subtitleColor = Color.White
            )
        }

        Text(
            text = AppContent.VERSION,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            color = Color.White,
            fontSize = 8.sp,
            lineHeight = 11.sp
        )
    }
}
