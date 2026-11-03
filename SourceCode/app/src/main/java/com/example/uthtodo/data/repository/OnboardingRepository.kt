package com.example.uthtodo.data.repository

import com.example.uthtodo.data.model.CategoryIllustration
import com.example.uthtodo.data.model.ChecklistIllustration
import com.example.uthtodo.data.model.OnboardingPage
import com.example.uthtodo.data.model.ReminderIllustration

/** Hợp đồng cung cấp dữ liệu onboarding cho giao diện. */
interface OnboardingRepository {
    fun getPages(): List<OnboardingPage>
}

/** Nguồn dữ liệu onboarding mặc định của ứng dụng. */
class DefaultOnboardingRepository : OnboardingRepository {
    override fun getPages(): List<OnboardingPage> = listOf(
        OnboardingPage(
            title = "Mọi việc, gọn trong một chỗ",
            description = "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào.",
            illustration = ChecklistIllustration
        ),
        OnboardingPage(
            title = "Chia theo Học tập và Cuộc sống",
            description = "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần lo gì.",
            illustration = CategoryIllustration
        ),
        OnboardingPage(
            title = "Nhắc đúng lúc",
            description = "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.",
            illustration = ReminderIllustration
        )
    )
}
