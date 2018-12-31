package io.mjimenez.aoc2018.day3

import java.io.File

fun main(args: Array<String>) {
    val lines = File("input3").readLines()

    val claims = lines.map { it.toClaim() }

    claims.forEach { claim ->
        val intersectsWithAnyOtherClaim = claims
            .filterNot { it == claim }
            .any { it.intersects(claim) }

        if (!intersectsWithAnyOtherClaim) {
            println("Claim ${claim.id} doesn't intersect")
        }
    }
}

fun Claim.intersects(other: Claim) = when {
    other.x + other.w < x -> false
    x + w < other.x -> false
    other.y + other.h < y -> false
    y + h < other.y -> false
    else -> true
}