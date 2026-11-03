package com.example.uthtodo.data.model

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Lớp trừu tượng mô tả cách vẽ minh họa.
 * Các lớp con ghi đè Draw() để tạo ra từng kiểu minh họa khác nhau (đa hình).
 */
abstract class OnboardingIllustration {
    @Composable
    abstract fun Draw()
}

/** Minh họa ba dòng công việc, trong đó dòng đầu đã hoàn thành. */
object ChecklistIllustration : OnboardingIllustration() {
    @Composable
    override fun Draw() {
        IllustrationPanel {
            repeat(3) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(27.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(if (index == 0) IllustrationColors.Blue else Color.White)
                            .border(
                                width = 1.dp,
                                color = if (index == 0) IllustrationColors.Blue else IllustrationColors.Gray,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (index == 0) {
                            Text(
                                text = "✓",
                                color = Color.White,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(7.dp))

                    Box(
                        modifier = Modifier
                            .width(if (index == 0) 58.dp else 48.dp)
                            .height(4.dp)
                            .clip(CircleShape)
                            .background(IllustrationColors.Line)
                    )
                }
            }
        }
    }
}

/** Minh họa hai nhóm công việc Học tập và Cuộc sống. */
object CategoryIllustration : OnboardingIllustration() {
    @Composable
    override fun Draw() {
        IllustrationPanel {
            CategoryCard(
                title = AppContent.STUDY_CATEGORY,
                tagColor = IllustrationColors.StudyTag
            )
            CategoryCard(
                title = AppContent.LIFE_CATEGORY,
                tagColor = IllustrationColors.LifeTag
            )
        }
    }
}

/** Minh họa chuông và nội dung nhắc nhở. */
object ReminderIllustration : OnboardingIllustration() {
    @Composable
    override fun Draw() {
        Box(
            modifier = Modifier
                .size(width = 126.dp, height = 148.dp)
                .background(IllustrationColors.Panel, RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = AppContent.NOTIFICATION_DESCRIPTION,
                        tint = IllustrationColors.Blue,
                        modifier = Modifier.size(27.dp)
                    )
                }

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = AppContent.REMINDER_SAMPLE,
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    color = Color(0xFF303030),
                    fontSize = 7.sp
                )
            }
        }
    }
}

@Composable
private fun IllustrationPanel(
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 126.dp, height = 148.dp)
            .background(IllustrationColors.Panel, RoundedCornerShape(18.dp))
            .padding(horizontal = 7.dp, vertical = 28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
private fun CategoryCard(
    title: String,
    tagColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 7.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .background(tagColor, RoundedCornerShape(5.dp))
                .padding(horizontal = 6.dp, vertical = 3.dp),
            color = Color(0xFF53617E),
            fontSize = 7.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .width(65.dp)
                .height(4.dp)
                .clip(CircleShape)
                .background(IllustrationColors.Line)
        )
    }
}

private object IllustrationColors {
    val Blue = Color(0xFF2F5BEA)
    val Panel = Color(0xFFEEF0FB)
    val Gray = Color(0xFFC8CDD5)
    val Line = Color(0xFFE0E2E7)
    val StudyTag = Color(0xFFE6ECFF)
    val LifeTag = Color(0xFFFFEEDC)
}
