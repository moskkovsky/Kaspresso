package com.kaspersky.kaspresso.tutorial.tests
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.interceptors.watcher.testcase.impl.screenshot.ScreenshotStepWatcherInterceptor
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutorial.MainActivity
import com.kaspersky.kaspresso.tutorial.afterlogin.AfterLoginActivity
import com.kaspersky.kaspresso.tutorial.data.TestData
import com.kaspersky.kaspresso.tutorial.scenario.LoginScenario
import org.junit.Rule
import org.junit.Test

class LoginActivityGeneratedDataTest: TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple().apply {
        stepWatcherInterceptors.add(ScreenshotStepWatcherInterceptor(screenshots))
    }
) {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Test
    fun loginSuccessfulIfUsernameAndPasswordCorrect() {
        run {
            val username = TestData.generateUsername()
            val password = TestData.generatePassword()
            testLogger.i("KASPRESSO","Авторизация с данными: Username: $username, Password: $password")
            step("Авторизуемся с логином $username и паролем $password" ) {
                scenario(
                    LoginScenario(
                        username = username,
                        password = password
                    )
                )
            }
            step("Проверяем, что успешно авторизовались") {
                device.activities.isCurrent(AfterLoginActivity::class.java)
            }
        }
    }
}
