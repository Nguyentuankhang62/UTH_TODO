
package com.example.uthtodo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.data.model.AppContent
import com.example.uthtodo.ui.theme.AppColors

// LOGO ICON
@Composable
fun AppLogoMark(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    backgroundColor: Color = Color(0xFF2F5BEA),
    checkCircleColor: Color = Color.White,
    checkColor: Color = Color(0xFF2F5BEA),
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
            modifier = Modifier.width(size * 0.68f),
            verticalArrangement = Arrangement.spacedBy(size * 0.12f)
        ) {

            // DÒNG CHECKLIST THỨ NHẤT
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(size * 0.25f)
                        .clip(CircleShape)
                        .background(checkCircleColor),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "✓",
                        color = checkColor,
                        fontSize = (size.value * 0.18f).sp,
                        lineHeight = (size.value * 0.19f).sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.width(size * 0.09f))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(size * 0.12f)
                        .clip(CircleShape)
                        .background(firstLineColor)
                )
            }

            // DÒNG CHECKLIST THỨ HAI
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(size * 0.25f)
                        .clip(CircleShape)
                        .border(
                            width = size * 0.04f,
                            color = secondCircleColor,
                            shape = CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(size * 0.09f))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(size * 0.12f)
                        .clip(CircleShape)
                        .background(secondLineColor)
                )
            }
        }
    }
}

// LOGO CHỮ UTH TODO
@Composable
fun AppWordmark(
    fontSize: TextUnit = 15.sp,
    uthColor: Color = Color(0xFF1C1C1E),
    todoColor: Color = Color(0xFF2F5BEA),
    showSubtitle: Boolean = false,
    subtitleColor: Color = Color(0xFF66666D)
) {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {

        Text(
            text = buildAnnotatedString {

                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = uthColor
                    )
                ) {
                    append("UTH ")
                }

                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Medium,
                        color = todoColor
                    )
                ) {
                    append("Todo")
                }
            },
            fontSize = fontSize,
            lineHeight = fontSize * 1.2f,
            fontFamily = FontFamily.SansSerif,
            letterSpacing = (-0.5).sp
        )

        if (showSubtitle) {

            Text(
                text = AppContent.SLOGAN,
                color = subtitleColor,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp
            )
        }
    }
}
