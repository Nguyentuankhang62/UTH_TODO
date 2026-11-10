package com.example.uthtodo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.ui.theme.AppColors

enum class NavTab {
    HOME, LIST, NOTIFICATIONS, SETTINGS
}

@Composable
fun BottomNavBar(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    onFabClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        // Bar container
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .shadow(8.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                icon = Icons.Default.Home,
                label = "Trang chủ",
                isSelected = currentTab == NavTab.HOME,
                onClick = { onTabSelected(NavTab.HOME) }
            )

            NavItem(
                icon = Icons.AutoMirrored.Filled.List,
                label = "Danh sách",
                isSelected = currentTab == NavTab.LIST,
                onClick = { onTabSelected(NavTab.LIST) }
            )

            // Space for center FAB
            Spacer(modifier = Modifier.width(48.dp))

            NavItem(
                icon = Icons.Default.Notifications,
                label = "Thông báo",
                isSelected = currentTab == NavTab.NOTIFICATIONS,
                onClick = { onTabSelected(NavTab.NOTIFICATIONS) }
            )

            NavItem(
                icon = Icons.Default.Settings,
                label = "Cài đặt",
                isSelected = currentTab == NavTab.SETTINGS,
                onClick = { onTabSelected(NavTab.SETTINGS) }
            )
        }

        // Center Floating Action Button (+)
        FloatingActionButton(
            onClick = onFabClick,
            shape = CircleShape,
            containerColor = AppColors.Primary,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(6.dp),
            modifier = Modifier
                .size(52.dp)
                .align(Alignment.TopCenter)
                .offset(y = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Thêm mới",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) AppColors.Primary else Color(0xFF999999)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = color
        )
    }
}
