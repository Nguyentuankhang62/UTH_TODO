package com.example.uthtodo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.data.model.AppContent
import com.example.uthtodo.ui.theme.AppColors

/** Logo dấu tích 2 dòng dùng thống nhất ở Splash và thanh đầu Onboarding. */
@Composable
fun AppLogoMark(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    backgroundColor: Color = AppColors.Primary,
    checkCircleColor: Color = Color.White,
    checkColor: Color = AppColors.Primary,
    firstLineColor: Color = Color.White,
    secondCircleColor: Color = Color(0xFFAFC2FF),
    secondLineColor: Color = Color(0xFFAFC2FF),
    drawBackground: Boolean = true
) {
    val corner = size * 0.23f

    Box(
        modifier = modifier
            .size(size)
            .then(
                if (drawBackground) {
                    Modifier
                        .clip(RoundedCornerShape(corner))
                        .background(backgroundColor)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.width(size * 0.66f),
            verticalArrangement = Arrangement.spacedBy(size * 0.10f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size * 0.24f)
                        .clip(CircleShape)
                        .background(checkCircleColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        color = checkColor,
                        fontSize = (size.value * 0.17f).sp,
                        lineHeight = (size.value * 0.18f).sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Spacer(Modifier.width(size * 0.10f))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(size * 0.105f)
                        .clip(CircleShape)
                        .background(firstLineColor)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size * 0.24f)
                        .clip(CircleShape)
                        .border(size * 0.035f, secondCircleColor, CircleShape)
                )
                Spacer(Modifier.width(size * 0.10f))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(size * 0.105f)
                        .clip(CircleShape)
                        .background(secondLineColor)
                )
            }
        }
    }
}

/** Wordmark UTH đậm + Todo màu xanh theo trang nhận diện. */
@Composable
fun AppWordmark(
    fontSize: TextUnit = 14.sp,
    uthColor: Color = AppColors.TextPrimary,
    todoColor: Color = AppColors.Primary,
    showSubtitle: Boolean = false,
    subtitleColor: Color = AppColors.TextSecondary
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = uthColor)) {
                    append("UTH ")
                }
                withStyle(SpanStyle(fontWeight = FontWeight.Normal, color = todoColor)) {
                    append("Todo")
                }
            },
            fontSize = fontSize,
            lineHeight = fontSize * 1.15f,
            letterSpacing = (-0.25).sp
        )

        if (showSubtitle) {
            Text(
                text = AppContent.SLOGAN,
                color = subtitleColor,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                letterSpacing = 0.sp
            )
        }
    }
}
