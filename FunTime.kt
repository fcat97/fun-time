import java.text.SimpleDateFormat
import java.util.*

val now: Date get() = Date()
val today: Date get() = now
val yesterday: Date get() = one day earlier from today
val tomorrow: Date get() = one day later from today

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
    return SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US).format(x)
}

/**
 * Format a [Date] to specific [pattern] provided
 */
infix fun Date.formatAs(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.US).format(this)
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
    c[Calendar.HOUR] = 0
    c[Calendar.MINUTE] = 0
    c[Calendar.SECOND] = 0
    c[Calendar.MILLISECOND] = 0

    return c.time
}

/**
 * Get ending time of a [Date] i.e. 11:59:00:000 PM
 */
infix fun Date.ends(at: at): Date {
    val c = Calendar.getInstance()
    c.timeInMillis = this.time
    c[Calendar.HOUR] = 23
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

/**
 * Get the millisecond of provided time
 */
infix fun milliseconds.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.timeInMillis = date.time
    return c[Calendar.MILLISECOND]
}

/**
 * Get the seconds of provided time
 */
infix fun seconds.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.timeInMillis = date.time
    return c[Calendar.SECOND]
}

/**
 * Get the minutes of provided time
 */
infix fun minutes.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.timeInMillis = date.time
    return c[Calendar.MINUTE]
}

/**
 * Get the hours of provided time
 */
infix fun hours.of(date: Date): Int {
    val c = Calendar.getInstance()
    c.timeInMillis = date.time
    return c[Calendar.HOUR_OF_DAY]
}
