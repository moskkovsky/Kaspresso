package com.kaspersky.kaspresso.tutorial.screenshot_tests

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.DocLocScreenshotTestCase
import com.kaspersky.kaspresso.tutorial.login.LoginActivity
import com.kaspersky.kaspresso.tutorial.screen.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginActivityScreenshots : DocLocScreenshotTestCase(locales = "en, fr") {
    @get:Rule
    val activityRule = activityScenarioRule<LoginActivity>()

    @Test
    fun takeScreenshots() = run {
        step("Проверка видимости элементов") {
            LoginScreen {
                inputUsername.isVisible()
                inputPassword.isVisible()
                loginButton.isVisible()
                captureScreenshot("Initial state")
            }
        }
    }
}
