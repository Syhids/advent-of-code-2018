package io.mjimenez.results

import java.io.File

fun main(args: Array<String>) {
    val result = getDuplicatedFrequency()
    println(result)
}

private val duplicatedFreqs = mutableSetOf<Int>()
private var currentFreq = 0

private fun getDuplicatedFrequency(): Int {
    File("input1")
        .readLines()
        .asSequence()
        .map { it.substring(0, 1) to it.substring(1).toInt() }
        .map { (symbol, number) ->
            when (symbol) {
                "+" -> number
                "-" -> -number
                else -> 0
            }
        }
        .forEach {
            currentFreq += it

            if (duplicatedFreqs.contains(currentFreq))
                return currentFreq

            duplicatedFreqs += currentFreq
        }

    return getDuplicatedFrequency()
}