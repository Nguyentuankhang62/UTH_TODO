
package com.example.uthtodo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.ui.components.AppLogoMark

@Composable
fun LoginScreen(
    onGoogleClick: () -> Unit,
    onGuestClick: () -> Unit,
    errorMessage: String? = null,
    isLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(Modifier.height(65.dp))

        AppLogoMark(size = 58.dp)

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Chào mừng đến với",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1E)
        )

        Text(
            text = "UTH Todo",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2F5BEA)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Đăng nhập để lưu công việc và đồng bộ\ntrên mọi thiết bị của bạn.",
            fontSize = 13.sp,
            lineHeight = 19.sp,
            color = Color(0xFF777777)
        )

        Spacer(Modifier.weight(1f))

        // Đăng nhập Google
        OutlinedButton(
            onClick = onGoogleClick,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF222222)
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "G",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4285F4)
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = "Tiếp tục với Google",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "hoặc",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray,
            fontSize = 12.sp
        )

        Spacer(Modifier.height(12.dp))

        TextButton(
            onClick = onGuestClick,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Dùng thử không cần tài khoản",
                color = Color(0xFF2F5BEA),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Hiển thị lỗi nếu đăng nhập thất bại
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(Modifier.height(35.dp))

        Text(
            text = "Khi tiếp tục, bạn đồng ý với Điều khoản sử dụng và Chính sách quyền riêng tư của UTH Todo.",
            fontSize = 9.sp,
            lineHeight = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp)
        )
    }
}
