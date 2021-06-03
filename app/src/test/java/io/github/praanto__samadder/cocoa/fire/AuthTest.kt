package io.github.praanto__samadder.cocoa.fire

import org.junit.Assert.assertEquals
import org.junit.Test

class AuthTest {

    @Test
    fun firebaseTimestampToStringTime () {
        val sampleData = "Mon Oct 05 00:00:00 GMT+06:00 2020"
        val splitSampleData = sampleData.split(" ")

        val year = splitSampleData[5]
        val dayOfMonth = splitSampleData[2].toInt().toString()
        val month = splitSampleData[1]

        val expected = "$dayOfMonth $month $year"

        assertEquals(expected, "5 Oct 2020")
    }
}