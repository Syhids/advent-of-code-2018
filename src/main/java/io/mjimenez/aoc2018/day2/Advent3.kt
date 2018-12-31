package io.mjimenez.aoc2018.day2

import java.io.File

typealias Frequencies = List<Int>

fun main(args: Array<String>) {
    val lines = File("input2").readLines()
    val textsFrequencies = lines.map { it.frequencies() }

    (2..64)
        .map { freq -> textsFrequencies.count { it.contains(freq) } }
        .filter { it > 0 }
        .fold(1) { acc, i -> acc * i }
        .also { println(it) }
}

fun String.frequencies(): Frequencies = this.getLettersFrequencyMap()
    .filterValues { it > 1 }
    .values.distinct()

private fun String.getLettersFrequencyMap() = mutableMapOf<Char, Int>()
    .also { map -> this.forEach { letter -> map[letter] = 1 + (map[letter] ?: 0) } }