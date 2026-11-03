package com.example.uthtodo.data.repository

import com.example.uthtodo.data.model.CategoryIllustration
import com.example.uthtodo.data.model.ChecklistIllustration
import com.example.uthtodo.data.model.ReminderIllustration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class OnboardingRepositoryTest {
    private val repository = DefaultOnboardingRepository()

    @Test
    fun getPages_returnsThreePagesInReferenceOrder() {
        val pages = repository.getPages()

        assertEquals(3, pages.size)
        assertEquals("Mọi việc, gọn trong một chỗ", pages[0].title)
        assertEquals("Chia theo Học tập và Cuộc sống", pages[1].title)
        assertEquals("Nhắc đúng lúc", pages[2].title)
    }

    @Test
    fun getPages_returnsExpectedIllustrationObjects() {
        val pages = repository.getPages()

        assertSame(ChecklistIllustration, pages[0].illustration)
        assertSame(CategoryIllustration, pages[1].illustration)
        assertSame(ReminderIllustration, pages[2].illustration)
    }
}
