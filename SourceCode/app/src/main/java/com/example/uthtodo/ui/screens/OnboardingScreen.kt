package com.example.uthtodo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.data.model.AppContent
import com.example.uthtodo.data.model.OnboardingPage
import com.example.uthtodo.ui.components.AppLogoMark
import com.example.uthtodo.ui.components.AppWordmark
import com.example.uthtodo.ui.theme.AppColors

@Composable
fun OnboardingScreen(
    pages: List<OnboardingPage>,
    onFinished: () -> Unit
) {
    if (pages.isEmpty()) return

    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    val page = pages[currentIndex.coerceIn(pages.indices)]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        // Logo UTH Todo hai màu giống bộ nhận diện trong ảnh.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppLogoMark(size = 23.dp)
            Spacer(modifier = Modifier.width(7.dp))
            AppWordmark(fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = onFinished,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    horizontal = 4.dp,
                    vertical = 0.dp
                )
            ) {
                Text(
                    text = AppContent.SKIP,
                    color = AppColors.TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Minh họa đặt chính giữa vùng phía trên tiêu đề như ảnh tham khảo.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(178.dp),
            contentAlignment = Alignment.Center
        ) {
            page.illustration.Draw()
        }

        Spacer(modifier = Modifier.weight(0.72f))

        Text(
            text = page.title,
            modifier = Modifier.widthIn(max = 250.dp),
            color = AppColors.TextPrimary,
            fontSize = 19.sp,
            lineHeight = 24.sp,
            letterSpacing = (-0.3).sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(9.dp))

        Text(
            text = page.description,
            modifier = Modifier.widthIn(max = 300.dp),
            color = AppColors.TextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            letterSpacing = 0.sp
        )

        Spacer(modifier = Modifier.height(27.dp))

        PageIndicator(
            pageCount = pages.size,
            selectedIndex = currentIndex
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (currentIndex < pages.lastIndex) {
                    currentIndex += 1
                } else {
                    onFinished()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(9.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.Primary,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = if (currentIndex == pages.lastIndex) {
                    AppContent.START_NOW
                } else {
                    AppContent.CONTINUE
                },
                fontSize = 13.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp
            )
        }
    }
}

@Composable
private fun PageIndicator(
    pageCount: Int,
    selectedIndex: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(
                        width = if (index == selectedIndex) 18.dp else 6.dp,
                        height = 6.dp
                    )
                    .clip(CircleShape)
                    .background(
                        if (index == selectedIndex) {
                            AppColors.Primary
                        } else {
                            AppColors.IndicatorInactive
                        }
                    )
            )
        }
    }
}
