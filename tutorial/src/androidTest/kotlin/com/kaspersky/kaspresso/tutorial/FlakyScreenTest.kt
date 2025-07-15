package com.kaspersky.kaspresso.tutorial

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutorial.screen.FlakyScreen
import com.kaspersky.kaspresso.tutorial.screen.MainScreen
import org.junit.Rule
import org.junit.Test

class FlakyScreenTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkFlakyScreen() = run {
        step("Переходим на экран Flaky Activity") {
            MainScreen {
                flakyActivityButton {
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }
        step("Проверяем видимость элементов на экране") {
            FlakyScreen {
                text1.isVisible()
                text2.isVisible()
                text3.isVisible()
                text4.isVisible()
                text5.isVisible()
                progressBar1.isVisible()
                progressBar2.isVisible()
                progressBar3.isVisible()
                progressBar4.isVisible()
                progressBar5.isVisible()
            }
        }
        step("Проверка видимости первого текста после анимации") {
            FlakyScreen {
                flakySafely(3000) { // под капотом 10 сек ожидание
                    text1.hasText(R.string.text_1)
                    progressBar1.isGone() // Проверяем, что ProgressBar невидимый
                }
            }
        }
        step("Проверка видимости второго текстовые блоки после анимации") {
            FlakyScreen {
                flakySafely(3000) { // под капотом 10 сек ожидание
                    text1.hasText(R.string.text_1)
                    progressBar1.isGone() // Проверяем, что ProgressBar невидимый
                }
            }
        }
        step("Проверяем оставшиеся текстовые блоки") {
            FlakyScreen {
                flakySafely(15000) {
                    text3.hasText(R.string.text_3)
                    progressBar3.isGone()
                    text4.hasText(R.string.text_4)
                    progressBar4.isGone()
                    text5.hasText(R.string.text_5)
                    progressBar5.isGone()
                }
            }
        }
    }
}
