package io.mjimenez.aoc2018.day4

import java.io.File

fun main(args: Array<String>) {
    val lines = File("input4").readLines()
    val records = parseInput(lines)
    val worklog = records.sorted().toWorklog()

    val guardIdsToMinuteSlices = worklog.map { it.guardId to it.shifts.flatMap { shift -> shift.toMinuteSlices() } }

    //There are some guards who don't fall asleep: #2063, #3109 and #2957

    val (guardId, slices) = guardIdsToMinuteSlices.maxBy { (_, slices) ->
        slices.groupBy { it }.values.maxBy { it.count() }?.size ?: 0
    }!!
    val minuteMostAsleep = slices.groupBy { it }.values.maxBy { it.count() }!!.first()

    println("Guard: $guardId")
    println("Minute most asleep: $minuteMostAsleep")
    println("Result: ${guardId.toInt() * minuteMostAsleep}")
}