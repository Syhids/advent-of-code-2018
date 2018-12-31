package io.mjimenez.results.advent6

import java.io.File
import java.text.SimpleDateFormat
import java.util.*


private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

fun main(args: Array<String>) {
    val lines = File("input4").readLines()
    val records = parseInput(lines)
    val worklog = records.sorted().toWorklog()

    val mostAsleepGuardEntry = worklog.maxBy { it.shifts.sumBy { shift -> shift.to - shift.from } }!!

    val minuteMostAsleep = mostAsleepGuardEntry.shifts.flatMap { it.toMinuteSlices() }
        .groupBy { it }
        .values
        .maxBy { it.count() }!!
        .first()


    println("Most asleep guard: $mostAsleepGuardEntry")
    println("Minute most asleep: $minuteMostAsleep")
    println("Result: ${minuteMostAsleep * mostAsleepGuardEntry.guardId.toInt()}")
}

typealias Records = List<Record>

data class Record(
    val dateTime: Date,
    val action: Action
) : Comparable<Record> {
    val minuteOfHour: Int
        get() = dateTime.minutes

    override fun compareTo(other: Record): Int =
        dateTime.compareTo(other.dateTime)

    sealed class Action {
        object WakesUp : Action()
        object FallsAsleep : Action()
        data class GuardBeginsShift(val guardId: String) : Action()
    }
}

/**
 * Records must be sorted
 */
fun Records.toWorklog(): Worklog {
    val worklog = mutableListOf<WorklogEntry>()
    var lastFallAsleep: Record? = null
    var lastWorklogEntry: WorklogEntry? = null

    forEach { record ->
        when (val action = record.action) {
            Record.Action.FallsAsleep -> lastFallAsleep = record
            Record.Action.WakesUp -> lastWorklogEntry!!.addShiftFrom(lastFallAsleep!!, record)
            is Record.Action.GuardBeginsShift -> {
                lastWorklogEntry = worklog.firstOrNull { it.guardId == action.guardId } ?: let {
                    val newEntry = WorklogEntry(guardId = action.guardId)
                    worklog.add(newEntry)
                    newEntry
                }
            }
        }
    }

    return worklog
}

typealias Worklog = List<WorklogEntry>

data class WorklogEntry(val guardId: String, val shifts: MutableList<Shift> = mutableListOf()) {
    fun addShiftFrom(lastFallAsleep: Record, lastWakeup: Record) {
        shifts += Shift(lastFallAsleep.minuteOfHour, lastWakeup.minuteOfHour)
    }

    data class Shift(val from: Int, val to: Int) {
        fun toMinuteSlices(): MinuteSlices = (from until to).toList()
    }
}

typealias MinuteSlice = Int
typealias MinuteSlices = List<MinuteSlice>

fun parseInput(lines: List<String>): Records = lines.map {
    Record(
        dateTime = dateTimeFormat.parse(it.substring(1).substringBefore("]")),
        action = when {
            it.contains("falls asleep") -> Record.Action.FallsAsleep
            it.contains("wakes up") -> Record.Action.WakesUp
            else -> Record.Action.GuardBeginsShift(it.substringAfter("#").substringBefore(" "))
        }
    )
}
