
package com.example.uthtodo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
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

    var currentIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val page = pages[currentIndex.coerceIn(pages.indices)]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {

        // HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppLogoMark(size = 23.dp)

            Spacer(modifier = Modifier.width(8.dp))

            AppWordmark(fontSize = 14.sp)

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = onFinished,
                contentPadding = PaddingValues(
                    horizontal = 5.dp,
                    vertical = 4.dp
                )
            ) {
                Text(
                    text = AppContent.SKIP,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF777777),
                    letterSpacing = 0.sp
                )
            }
        }

        // KHOẢNG CÁCH PHÍA TRÊN MINH HỌA
        Spacer(modifier = Modifier.weight(0.8f))

        // ILLUSTRATION
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp),
            contentAlignment = Alignment.Center
        ) {
            page.illustration.Draw()
        }

        // KHOẢNG CÁCH GIỮA MINH HỌA VÀ NỘI DUNG
        Spacer(modifier = Modifier.weight(0.7f))

        // TITLE
        Text(
            text = page.title,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF1C1C1E),
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp,
            lineHeight = 25.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.4).sp
        )

        Spacer(modifier = Modifier.height(9.dp))

        // DESCRIPTION
        Text(
            text = page.description,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF66666D),
            fontFamily = FontFamily.SansSerif,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        // PAGE INDICATOR
        PageIndicator(
            pageCount = pages.size,
            selectedIndex = currentIndex
        )

        Spacer(modifier = Modifier.height(15.dp))

        // BUTTON
        Button(
            onClick = {
                if (currentIndex < pages.lastIndex) {
                    currentIndex++
                } else {
                    onFinished()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(9.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2F5BEA),
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp
            )
        ) {

            Text(
                text = if (currentIndex == pages.lastIndex) {
                    AppContent.START_NOW
                } else {
                    AppContent.CONTINUE
                },
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.1.sp
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
                    .width(
                        if (index == selectedIndex) 17.dp else 6.dp
                    )
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == selectedIndex) {
                            Color(0xFF2F5BEA)
                        } else {
                            Color(0xFFD9DDE4)
                        }
                    )
            )
        }
    }
}
