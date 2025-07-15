package com.kaspersky.kaspresso.tutorial

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutorial.screen.MainScreen
import android.media.AudioManager
import android.os.Build
import androidx.test.filters.SdkSuppress
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.tutorial.screen.MakeCallActivityScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MakeCallActivityDevicePermissionsTest : TestCase() {
    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // M - sdk 23
            step("Даем разрешение на звонок по телефону") {
                device.permissions.apply {
                    flakySafely {
                        assertTrue(isDialogVisible())
                        allowViaDialog()
                    }
                }
            }
        }
        step("Проверяем звонок по номеру $PHONE_NUMBER") {
            flakySafely { // тайм-аут, чтобы успел позвонить
                val manager = device.context.getSystemService(AudioManager::class.java)
                assertTrue(manager.mode == AudioManager.MODE_IN_CALL)
            }
        }
    }

    @SdkSuppress(minSdkVersion = 23)
    @Test
    fun checkCallIfPermissionDenied() = run {
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
        step("Отклоняем разрешение на звонок по телефону") {
            device.permissions.apply {
                flakySafely {
                    assertTrue(isDialogVisible())
                    denyViaDialog()
                }
            }
        }
        step("Проверяем, что не позвонили и остались на экране") {
            MakeCallActivityScreen {
                inputNumber.isDisplayed()
                makeCallButton.isDisplayed()
            }
        }
    }

    companion object {
        private const val PHONE_NUMBER = "111"
    }
}
