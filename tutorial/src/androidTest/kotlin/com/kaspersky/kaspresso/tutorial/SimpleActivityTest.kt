package com.kaspersky.kaspresso.tutorial

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutorial.screen.MainScreen
import com.kaspersky.kaspresso.tutorial.screen.SimpleActivityScreen
import org.junit.Rule
import org.junit.Test

class SimpleActivityTest: TestCase() {

    /**
    Для того, чтобы перед тестом была запущена какая-то активити,
    осуществит запуск указанной activity MainActivity перед запуском теста и закроет после прогона теста
     */
    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() {
        MainScreen {
            simpleActivityButton {
                isVisible()
                isClickable()
                containsText("Simple test")
                click()
            }
        }
        SimpleActivityScreen {
            simpleTitle.isVisible()
            changeTitleButton.isClickable()
            simpleTitle.hasText(R.string.simple_activity_default_title) // Если приложение будет локализовано на другой язык, то тест упадет
            inputText.replaceText("new title")
            changeTitleButton.click()
            simpleTitle.hasText("new title")
        }
    }

}
