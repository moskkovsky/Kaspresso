package com.kaspersky.kaspresso.tutorial

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutorial.screen.MainScreen
import android.media.AudioManager
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.tutorial.screen.MakeCallActivityScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MakeCallActivityRuleTest : TestCase() {
    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CALL_PHONE // Разрешение на звонок эмулятору
    )

    @Test
    fun checkSuccessCall() = before {
    }.after {
        device.phone.cancelCall(PHONE_NUMBER)
    }.run {
        step("Открываем экран") {
            MainScreen {
                makeCallActivityButton {
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }
        step("Проверяем элементы на экране") {
            MakeCallActivityScreen {
                inputNumber.isVisible()
                inputNumber.hasHint(R.string.phone_number_hint)
                makeCallButton.isVisible()
                makeCallButton.isClickable()
                makeCallButton.hasText(R.string.make_call_btn)
            }
        }
        step("Звоним по номеру $PHONE_NUMBER") {
            MakeCallActivityScreen {
                inputNumber.replaceText(PHONE_NUMBER)
                makeCallButton.click()
            }
        }
        step("Проверяем звонок по номеру $PHONE_NUMBER") {
            flakySafely { // тайм-аут, чтобы успел позвонить
                val manager = device.context.getSystemService(AudioManager::class.java)
                assertTrue(manager.mode == AudioManager.MODE_IN_CALL)
            }
        }
    }
    companion object {
        private const val PHONE_NUMBER = "111"
    }
}
