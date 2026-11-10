package com.example.uthtodo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.data.model.AppContent
import com.example.uthtodo.ui.components.AppLogoMark
import com.example.uthtodo.ui.theme.AppColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
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
            // LOGO APP BOX
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AppLogoMark(
                    size = 60.dp,
                    backgroundColor = Color.Transparent,
                    checkCircleColor = AppColors.Primary,
                    checkColor = Color.White,
                    firstLineColor = AppColors.Primary,
                    secondCircleColor = Color(0xFFAFC2FF),
                    secondLineColor = Color(0xFFAFC2FF),
                    drawBackground = false
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TITLE & SLOGAN
            Text(
                text = AppContent.APP_NAME,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = AppContent.SLOGAN,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.9f),
                fontFamily = FontFamily.SansSerif
            )
        }

        // BOTTOM INDICATOR LINE & VERSION
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White.copy(alpha = 0.7f))
            )

            Text(
                text = AppContent.VERSION,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 11.sp,
                letterSpacing = 0.1.sp
            )
        }
    }
}
