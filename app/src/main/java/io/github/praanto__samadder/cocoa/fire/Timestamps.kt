package io.github.praanto__samadder.cocoa.fire

import java.text.SimpleDateFormat
import java.time.ZoneOffset.UTC
import java.util.*

object Timestamps {

    class StringTime(_dayOfMonth: Int, _month: Int, _year: Int) {
        private val day = "$_dayOfMonth "
        private val month = when (_month + 1) {
            1 -> "January "
            2 -> "February "
            3 -> "March "
            4 -> "April "
            5 -> "May "
            6 -> "June "
            7 -> "July "
            8 -> "August "
            9 -> "September "
            10 -> "October "
            11 -> "November "
            12 -> "December "

            else -> "January, "
        }
        private val year = "$_year"
        val date = day + month + year
    }

    /**
     * @param _dateString A string that represents a human readable date
     * @sample 7 September 2020
     */
    fun convertToUnixTime(_dateString: String) : Long {
        val inputSplit = _dateString.split(" ")
        var dayOfMonth = inputSplit[0]
        val month = inputSplit[1]
        val year = inputSplit[2]

        var s1 = "00"

        when (month) {
            "January"   -> s1 = "01"
            "February"  -> s1 = "02"
            "March"     -> s1 = "03"
            "April"     -> s1 = "04"
            "May"       -> s1 = "05"
            "June"      -> s1 = "06"
            "July"      -> s1 = "07"
            "August"    -> s1 = "08"
            "September" -> s1 = "09"
            "October"   -> s1 = "10"
            "November"  -> s1 = "11"
            "December"  -> s1 = "12"
        }

        if (inputSplit[0].length == 1) {
            dayOfMonth = "0$dayOfMonth"
        }

        val s = "$dayOfMonth/$s1/$year"

        val dateString = "$s 00:00:00 UTC"

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss z", Locale.US)
        val date = sdf.parse(dateString)!!
        return date.time
    }
}