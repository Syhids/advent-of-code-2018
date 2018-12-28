package io.mjimenez.results

import java.io.File

fun main(args: Array<String>) {
    val lines = File("input3").readLines()

    val claims = lines.map { it.toClaim() }

    val allTiles = claims.flatMap { it.toTiles() }

    val map = getFrequencyOfTiles(allTiles)

    map.filterValues { it > 1 }
        .count()
        .also {
            println(it)
        }
}

private fun getFrequencyOfTiles(tiles: List<Tile>): Map<Tile, Int> =
    mutableMapOf<Tile, Int>().also { map ->
        tiles.forEach {
            map[it] = 1 + (map[it] ?: 0)
        }
    }

data class Tile(val x: Int, val y: Int)

data class Claim(
    val id: String,
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int
)

typealias Grid = List<Pair<Int, Int>>

fun Claim.toTiles() =
    gridOf(w, h)
        .translateTo(x, y)
        .map { (x, y) -> Tile(x, y) }

fun gridOf(width: Int, height: Int): Grid =
    (0 until width).flatMap { x -> (0 until height).map { y -> x to y } }

fun Grid.translateTo(byX: Int, byY: Int) =
    map { (gridX, gridY) -> gridX + byX to gridY + byY }

//Assume input is valid
fun String.toClaim(): Claim =
    this.substring(1)
        .split(" ")
        .let { splits ->
            val coords = splits[2].substringBefore(":").split(",").map { it.toInt() }
            val size = splits[3].split("x").map { it.toInt() }
            Claim(
                id = splits[0],
                x = coords[0],
                y = coords[1],
                w = size[0],
                h = size[1]
            )
        }