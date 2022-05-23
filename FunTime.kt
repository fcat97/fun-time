import java.text.SimpleDateFormat
import java.util.*

fun main() {
    println(make pretty (2 days earlier from today))
}

val now: Date get() = Date()
val today: Date get() = now
val yesterday: Date get() = one day earlier from today
val tomorrow: Date get() = one day more from today

sealed class Modifier
object more: Modifier()
object earlier: Modifier()

sealed class Command
object make: Command()

infix fun Command.pretty(x: Date): String {
    return SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US).format(x)
}

object one
infix fun one.day(x: Modifier): Long {
    return when (x) {
        is more -> 86400_000L
        is earlier -> -1 * 86400_000L
    }
}
infix fun one.hour(x: Modifier): Long {
    return when (x) {
        is more -> 3600_000L
        is earlier -> -1 * 3600_000L
    }
}
infix fun one.minute(x: Modifier): Long {
    return when (x) {
        is more -> 60_000L
        is earlier -> -1 * 60_000L
    }
}
infix fun one.second(x: Modifier): Long {
    return when (x) {
        is more -> 1_000L
        is earlier -> -1 * 1_000L
    }
}

infix fun Long.from(a: Date): Date {
    return Date(this + a.time)
}

infix fun Int.days(x: Modifier): Long {
    return when (x) {
        is more -> this * 86400_000L
        is earlier -> -1 * this * 86400_000L
    }
}

infix fun Int.hours(x: Modifier): Long {
    return when (x) {
        is more -> this * 3600_000L
        is earlier -> -1 * this * 3600_000L
    }
}
infix fun Int.minutes(x: Modifier): Long {
    return when (x) {
        is more -> this * 60_000L
        is earlier -> -1 * this * 60_000L
    }
}
infix fun Int.seconds(x: Modifier): Long {
    return when (x) {
        is more -> this * 1_000L
        is earlier -> -1 * this * 1_000L
    }
}
