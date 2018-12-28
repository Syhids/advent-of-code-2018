package io.mjimenez.results

import java.io.File

fun main(args: Array<String>) {
    File("input1")
        .readLines()
        .map { it.substring(0, 1) to it.substring(1).toInt() }
        .foldRight(0) { t, acc ->
            acc + when (t.first) {
                "+" -> t.second
                "-" -> -t.second
                else -> 0
            }
        }.also {
            println(it)
        }
}