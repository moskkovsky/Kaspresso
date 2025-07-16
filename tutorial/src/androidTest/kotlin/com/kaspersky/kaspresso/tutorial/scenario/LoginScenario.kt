package com.kaspersky.kaspresso.tutorial.scenario

import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.kaspersky.kaspresso.tutorial.R
import com.kaspersky.kaspresso.tutorial.screen.LoginScreen
import com.kaspersky.kaspresso.tutorial.screen.MainScreen

class LoginScenario(
    private val username: String,
    private val password: String
) : Scenario() {
    override val steps: TestContext<Unit>.() -> Unit = {
        step("Открываем экран Авторизации") {
            device.screenshots.take("before_open_login_screen")
            MainScreen {
                loginActivityButton {
                    isVisible()
                    isClickable()
                    click()
                }
            }
            device.screenshots.take("after_open_login_screen")
        }
        step("Проверяем видимость элементов на экране") {
            device.screenshots.take("check_elements_visibility")
            LoginScreen {
                inputUsername {
                    isVisible()
                    hasHint(R.string.login_activity_hint_username)
                    device.screenshots.take("setup_username")

                }
                inputPassword {
                    isVisible()
                    hasHint(R.string.login_activity_hint_password)
                    device.screenshots.take("setup_password")
                }
                loginButton {
                    isVisible()
                    isClickable()
                    device.screenshots.take("after_click_login")
                }
            }
        }
        step("Try to login") {
            LoginScreen {
                inputUsername {
                    replaceText(username)
                }
                inputPassword {
                    replaceText(password)
                }
                loginButton {
                    click()
                }
            }
        }
    }
}
