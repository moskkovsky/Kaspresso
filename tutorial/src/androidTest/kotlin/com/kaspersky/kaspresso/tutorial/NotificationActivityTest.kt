package com.kaspersky.kaspresso.tutorial

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutorial.screen.MainScreen
import com.kaspersky.kaspresso.tutorial.screen.NotificationActivityScreen
import com.kaspersky.kaspresso.tutorial.screen.NotificationScreen
import org.junit.Rule
import org.junit.Test

class NotificationActivityTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkNotification() = run {
        step("Переходим на экран уведомлений") {
            MainScreen {
                notificationActivityButton {
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }
        step("Вызываем уведомления") {
            NotificationActivityScreen {
                showNotificationButton.isVisible()
                showNotificationButton.isClickable()
                showNotificationButton.click()
            }
        }
        step("Проверяем текст уведомления") {
            NotificationScreen {
                title.isDisplayed()
                title.hasText("Notification Title")
                content.isDisplayed()
                content.hasText("Notification Content")
            }
        }
    }
}
