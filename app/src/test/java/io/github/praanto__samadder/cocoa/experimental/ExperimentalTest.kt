package io.github.praanto__samadder.cocoa.experimental

import io.github.praanto__samadder.cocoa.fire.Helpers
import io.github.praanto__samadder.cocoa.fire.Timestamps
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class ExperimentalTest {

    @Test
    fun unixTimestampToDateString_Test() {
        val timestamp: Long = 1599436800
        val date = Instant.ofEpochSecond(timestamp).toString()
        val year = date.substring(0, 4).toInt()
        val month = date.substring(5, 7).toInt()
        val dayOfMonth = date.substring(8, 10).toInt()

        val mStringTime = Helpers.StringTime(dayOfMonth, month, year)

        val newDayOfMonth = mStringTime.day
        val newMonth = mStringTime.month
        val newYear = mStringTime.year

        val expected = newDayOfMonth + newMonth + newYear

        assertEquals(expected, "7 September, 2020")
    }

    @Test
    fun dateStringToUnixTimestamp() {
        val input = "7 September 2020"
        val inputSplit = input.split(" ")
        var dayOfMonth = inputSplit[0]
        val month = inputSplit[1]
        val year = inputSplit[2]

        var m = "00"

        when (month) {
            "January"   -> m = "01"
            "February"  -> m = "02"
            "March"     -> m = "03"
            "April"     -> m = "04"
            "May"       -> m = "05"
            "June"      -> m = "06"
            "July"      -> m = "07"
            "August"    -> m = "08"
            "September" -> m = "09"
            "October"   -> m = "10"
            "November"  -> m = "11"
            "December"  -> m = "12"
        }

        if (inputSplit[0].length == 1) {
            dayOfMonth = "0$dayOfMonth"
        }

        val s = "$dayOfMonth/$m/$year"

        val dateString = "$s 00:00:00 UTC"

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss z", Locale.US)
        val date = sdf.parse(dateString)!!
        val epoch = date.time

        val expected = (epoch / 1000).toString()

        assertEquals(expected, "1599436800")

    }

    @Test
    fun one() {
        val a = 1599436800
        val b = 1599373800
        assertEquals(a - b, 1599436800)
    }
}