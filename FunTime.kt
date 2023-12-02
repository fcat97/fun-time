/**
 * The MIT License (MIT)
 *
 * Copyright [Shahriar Zaman] © 2023
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


/**
 * @author Shahriar Zaman(github/fCat97) 
 * Edited@2023.12.2
 */

package media.uqab.coreUtils.funTime

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun main() {
    println(make pretty (20000 days earlier from now))
    println(make pretty now)
    println(make pretty (today starts at))
}

/**
 * 1 January 1970
 */
val epochStart: Date get() = Calendar.getInstance().apply { clear() }.time

val now: Date get() = Date()
val today: Date get() = now
val yesterday: Date get() = one day earlier from today
val tomorrow: Date get() = one day later from today
val yearFirstDay: Date get() = Calendar.getInstance().apply {
    set(Calendar.DAY_OF_YEAR, 1)
    set(Calendar.HOUR, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}.time

/**
 * USE CASE:
 * if ( today isLaterThan yesterday) {
 *     // do something
 * }
 */
infix fun Date.isLaterThan(date: Date) = this.after(date)
infix fun Date.isEarlierThan(date: Date) = this.before(date)
infix fun Date.isToday(date: Date) = daysBetween(this, date) == 0

/**
 * Returns the difference of time in milliseconds
 */
operator fun Date.minus(date: Date) = this.time - date.time

sealed class Modifier
object later: Modifier()
object earlier: Modifier()

/**
 * USE CASES:
 *
 * val d = one day earlier --> returns -24h as millisecond i.e. -86400_000
 * val x = d from today --> add "d" ms with today and return a [Date] object.
 *
 * so combining the two lines from above we get:
 * val t = one day earlier from today --> returns yesterday as [Date]
 */
object one
infix fun one.day(x: Modifier): Long {
    return when (x) {
        is later -> 86400_000L
        is earlier -> -1 * 86400_000L
    }
}
infix fun one.hour(x: Modifier): Long {
    return when (x) {
        is later -> 3600_000L
        is earlier -> -1 * 3600_000L
    }
}
infix fun one.minute(x: Modifier): Long {
    return when (x) {
        is later -> 60_000L
        is earlier -> -1 * 60_000L
    }
}
infix fun one.second(x: Modifier): Long {
    return when (x) {
        is later -> 1_000L
        is earlier -> -1 * 1_000L
    }
}

/**
 * Adds millisecond with date provided
 */
infix fun Long.from(a: Date): Date {
    return Date(this + a.time)
}

/**
 * Add a day with date provided
 */
infix fun Int.days(x: Modifier): Long {
    return when (x) {
        is later -> this * 86400_000L
        is earlier -> -1 * this * 86400_000L
    }
}

/**
 * Add a hour with date provided
 */
infix fun Int.hours(x: Modifier): Long {
    return when (x) {
        is later -> this * 3600_000L
        is earlier -> -1 * this * 3600_000L
    }
}

/**
 * Add a minute with date provided
 */
infix fun Int.minutes(x: Modifier): Long {
    return when (x) {
        is later -> this * 60_000L
        is earlier -> -1 * this * 60_000L
    }
}

/**
 * Add a Second with date provided
 */
infix fun Int.seconds(x: Modifier): Long {
    return when (x) {
        is later -> this * 1_000L
        is earlier -> -1 * this * 1_000L
    }
}

object make
/**
 * Format a date to readable string
 */
infix fun make.pretty(x: Date): String {
    return SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS a", Locale.ENGLISH).format(x)
}

/**
 * Format a [Date] to specific [pattern] provided
 */
infix fun Date.formatAs(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.ENGLISH).format(this)
}

fun String.toDate(pattern: String): Date? {
    return SimpleDateFormat(pattern, Locale.ENGLISH).parse(this)
}

fun Date.toCalender(): Calendar {
    return Calendar.getInstance().apply {
        timeInMillis = this@toCalender.time
    }
}

/**
 * Format a duration in millisecond to readable time [String]
 */
fun Long.toTimeUnit(): String {
    val t = this / 1000
    val h = t / 3600
    val m = (t % 3600) / 60
    val s = (t % 3600) % 60
    return "${h}h:${m}:m${s}s"
}

/**
 * USE CASES:
 *
 * val x = tomorrow starts at --> returns a [Date] object with the time set to 12:00 AM
 */
object at
/**
 * Get starting time of date i.e. 12:00 AM
 */
infix fun Date.starts(at: at): Date {
    val c = Calendar.getInstance()
    c.timeInMillis = this.time
    c[Calendar.HOUR_OF_DAY] = 0
    c[Calendar.MINUTE] = 0
    c[Calendar.SECOND] = 0
    c[Calendar.MILLISECOND] = 0

    return c.time
}

/**
 * Get ending time of a [Date] i.e. 11:59:59:999 PM
 */
infix fun Date.ends(at: at): Date {
    val c = Calendar.getInstance()
    c.timeInMillis = this.time
    c[Calendar.HOUR_OF_DAY] = 23
    c[Calendar.MINUTE] = 59
    c[Calendar.SECOND] = 59
    c[Calendar.MILLISECOND] = 999

    return c.time
}

/**
 * USE CASES:
 *
 * val x = midnight of today -> returns a [Date] object with time set to 12:00 AM
 */
object midnight

/**
 * Get starting time of date i.e. 12:00 AM
 */
infix fun midnight.of(time: Date) = time starts at


/**
 * USE CASES:
 *
 * val x = milliseconds of now --> extract the millisecond passed from this second
 * val x = minutes of now -> returns the amount of minute passed from this hour
 */
sealed class TimeUnits
object milliseconds: TimeUnits()
object seconds: TimeUnits()
object minutes: TimeUnits()
object hours: TimeUnits()
object dateOfMonth: TimeUnits()
object monthOfYear: TimeUnits()
object year: TimeUnits()

/**
 * Get the millisecond of provided time
 */
infix fun milliseconds.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.time = date
    return c[Calendar.MILLISECOND]
}

/**
 * Get the seconds of provided time
 */
infix fun seconds.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.time = date
    return c[Calendar.SECOND]
}

/**
 * Get the minutes of provided time
 */
infix fun minutes.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.time = date
    return c[Calendar.MINUTE]
}

/**
 * Get the hours of provided time
 */
infix fun hours.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.time = date
    return c[Calendar.HOUR_OF_DAY]
}

/**
 * Get the date of provided time
 */
infix fun dateOfMonth.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.time = date
    return c[Calendar.DATE]
}

/**
 * Get the month of provided time.
 * January is 0 and december is 11
 */
infix fun monthOfYear.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.time = date
    return c[Calendar.MONTH]
}

/**
 * Get the date of provided time
 */
infix fun year.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.time = date
    return c[Calendar.YEAR]
}

/**
 * Day difference between two dates.
 * @return absolute difference of dates.
 */
fun daysBetween(date1: Date, date2: Date): Int {
    // earlier one
    val c1 = Calendar.getInstance().apply {
        timeInMillis = if (date1.before(date2)) date1.time else date2.time
    }
    // later one
    val c2 = Calendar.getInstance().apply {
        timeInMillis = if (date2.after(date1)) date2.time else date1.time
    }

    return if (c1[Calendar.YEAR] == c2[Calendar.YEAR]) {
        abs(c1[Calendar.DAY_OF_YEAR] - c2[Calendar.DAY_OF_YEAR])
    } else {
        val yearDiff = c2[Calendar.YEAR] - c1[Calendar.YEAR]
        var dayDiff = c1.getActualMaximum(Calendar.DAY_OF_YEAR) - c1[Calendar.DAY_OF_YEAR] + c2[Calendar.DAY_OF_YEAR]

        // add total days of year for each year when yearDiff >= 2
        for (y in 1..yearDiff) {
            if (y > 1) {
                val cTemp = Calendar.getInstance().apply {
                    timeInMillis = c1.timeInMillis
                    add(Calendar.YEAR, y - 1)
                }
                dayDiff += cTemp.getActualMaximum(Calendar.DAY_OF_YEAR)
            }
        }

        dayDiff
    }
}

