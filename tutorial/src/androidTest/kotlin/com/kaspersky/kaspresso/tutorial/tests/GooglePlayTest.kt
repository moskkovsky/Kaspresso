package com.kaspersky.kaspresso.tutorial.tests

import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutorial.screen.GooglePlayScreen
import org.junit.Test

class GooglePlayTest : TestCase() {
    @Test
    fun testNotSignIn() = run {
        step("Открываем Google Play") {
            with(device.targetContext) {
                val intent = packageManager.getLaunchIntentForPackage(GOOGLE_PLAY_PACKAGE)
                startActivity(intent)
            }
        }
        step("Проверяем, что кнопка отображается на экране") {
            GooglePlayScreen {
                signInButton.isDisplayed()
            }
        }
    }

    companion object {
        private const val GOOGLE_PLAY_PACKAGE = "com.android.vending"
    }
}
