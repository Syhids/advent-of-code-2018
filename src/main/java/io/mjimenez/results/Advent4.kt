package io.mjimenez.results

import java.io.File

fun main(args: Array<String>) {
    val lines = File("input2").readLines()

    lines.firstPairMatching(lines) { one, another ->
        one.diffBetweenLetters(another) == 1
    }?.let { (one, another) ->
        println(one.takeLettersInCommon(another))
    }
}

typealias Comparator<T> = (T, T) -> Boolean

fun <T> List<T>.firstPairMatching(other: List<T>, comparator: Comparator<T>): Pair<T, T>? {
    this.forEach { one ->
        other.forEach { another ->
            if (comparator.invoke(one, another))
                return one to another
        }
    }

    return null
}

fun String.diffBetweenLetters(other: String) =
    (0 until length)
        .map { if (this[it] == other[it]) 0 else 1 }
        .sum()

fun String.takeLettersInCommon(other: String) =
    this.filterIndexed { index, c -> c == other[index] }