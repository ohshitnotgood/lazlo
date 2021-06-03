package io.github.praanto__samadder.cocoa.model

import org.junit.Test
import org.junit.Assert.assertEquals

class SensitiveCredentialsTest {

    @Test
    fun sensitiveCredTestCompare () {
        val cred = SensitiveCredentials(device = "device", boardId = "buildId", brand = "brand",
            manufacturer = "manufacturer", board = "board", bootloader = "bootloader", product = "product")

        val expected = cred.compare(cred)
        assertEquals(expected, true)
    }

}