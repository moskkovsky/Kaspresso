package com.kaspersky.kaspresso.tutorial

import com.kaspersky.kaspresso.internal.exceptions.AdbServerException
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test

class AdbTest : TestCase() {
    /**
     * Проверяем, что слово emulator есть в списки девайсов
     */
    @Test
    fun test() {
        val result = adbServer.performAdb("devices")
        assertTrue("emulator" in result.first())
    }

    /**
     * Проверяем негативный тест с несущуствующей командой
     */
    @Test
    fun negativeTest() {
        val command = "undefined_command"
        try {
            adbServer.performAdb(command)
        } catch (e: AdbServerException) {
            assertTrue("unknown command $command" in e.message)
        }
    }

    /**
     * Проверяем, что приложение есть в списке скаченных
     */
    @Test
    fun shellTest() {
        val packages = adbServer.performShell("pm list packages")
        assertTrue(device.targetContext.packageName in packages.first())
    }

}
