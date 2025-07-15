package com.kaspersky.kaspresso.tutorial

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutorial.afterlogin.AfterLoginActivity
import com.kaspersky.kaspresso.tutorial.login.LoginActivity
import com.kaspersky.kaspresso.tutorial.scenario.LoginScenario
import org.junit.Rule
import org.junit.Test

class LoginActivityTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()


    @Test
    fun loginSuccessfulIfUsernameAndPasswordCorrect() {
        run {
            scenario(
                LoginScenario(
                    username = "123456",
                    password = "123456"
                )
            )
            step("Проверяем, что перешли на следующий экран") {
                device.activities.isCurrent(AfterLoginActivity::class.java) // следующий экран
            }
        }
    }

    @Test
    fun loginUnsuccessfulIfPasswordIncorrect() {
        run {
            scenario(
                LoginScenario(
                    username = "123456",
                    password = "12345"
                )
            )
            step("Check current screen") {
                device.activities.isCurrent(LoginActivity::class.java)
            }
        }
    }
}
