package day01

import println
import readInput
import kotlin.math.abs

@JvmInline
private value class LocationId(val value: Int) : Comparable<LocationId> {
    override fun compareTo(other: LocationId) = value.compareTo(other.value)
}

fun main() {
    fun part1(input: List<String>): Int {
        val firstHistorianGroupLocationIds = mutableListOf<LocationId>()
        val secondHistorianGroupLocationIds = mutableListOf<LocationId>()

        input.map { it.asPairOfLocationIds() }.forEach { (firstHistorianGroupLocationId, secondHistorianGroupLocationId) ->
            firstHistorianGroupLocationIds += firstHistorianGroupLocationId
            secondHistorianGroupLocationIds += secondHistorianGroupLocationId
        }

        firstHistorianGroupLocationIds.sort()
        secondHistorianGroupLocationIds.sort()

        return input.indices.sumOf { indice ->
            abs(firstHistorianGroupLocationIds[indice].value - secondHistorianGroupLocationIds[indice].value)
        }
    }

    fun part2(input: List<String>): Int {
        val firstHistorianGroupLocationIds = mutableListOf<LocationId>()
        val secondHistorianGroupLocationIds = mutableMapOf<LocationId, Int>()

        input.map { it.asPairOfLocationIds() }.forEach { (firstHistorianGroupLocationId, secondHistorianGroupLocationId) ->
            firstHistorianGroupLocationIds += firstHistorianGroupLocationId
            secondHistorianGroupLocationIds[secondHistorianGroupLocationId] = (secondHistorianGroupLocationIds[secondHistorianGroupLocationId] ?: 0) + 1
        }

        return input.indices.sumOf { indice ->
            firstHistorianGroupLocationIds[indice].value * (secondHistorianGroupLocationIds[firstHistorianGroupLocationIds[indice]] ?: 0)
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day01/input")
    part1(input).println()
    part2(input).println()
}

private fun String.asPairOfLocationIds(): Pair<LocationId, LocationId> =
    split("   ")
        .let {
            LocationId(it[0].toInt()) to LocationId(it[1].toInt())
        }