package com.kaspersky.kaspresso.tutorial

import android.content.res.Configuration
import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.device.exploit.Exploit
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutorial.screen.MainScreen
import com.kaspersky.kaspresso.tutorial.screen.WifiScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class WifiSampleTest : TestCase() {
    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkWifiStatusTest() {
        before {
            device.exploit.setOrientation(Exploit.DeviceOrientation.Portrait)
            device.network.toggleWiFi(true)
        }.after {
            device.exploit.setOrientation(Exploit.DeviceOrientation.Portrait)
            device.network.toggleWiFi(true)
        }
        run {
            step("Открываем главный экран") {
                MainScreen {
                    wifiActivityButton {
                        isVisible()
                        isClickable()
                        click()
                    }
                }
            }
            step("Проверяем корректный wi-fi статус при вкл/выкл ") {
                WifiScreen {
                    checkWifiButton.isVisible()
                    checkWifiButton.isClickable()
                    wifiStatus.hasEmptyText()
                    checkWifiButton.click()
                    wifiStatus.hasText(R.string.enabled_status)
                    device.network.toggleWiFi(false)
                    checkWifiButton.click()
                    wifiStatus.hasText(R.string.disabled_status)

                }
                step("Переворачиваем экран и проверяем wi-fi status") {
                    WifiScreen {
                        device.exploit.rotate()
                        assertTrue(device.context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                        wifiStatus.hasText(R.string.disabled_status)
                    }
                }
            }
        }
    }
}
