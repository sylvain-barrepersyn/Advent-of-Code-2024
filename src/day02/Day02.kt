package day02

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.asReports().map { it.status(tolerance = false) }.count { it == Report.Status.SAFE }
    }

    fun part2(input: List<String>): Int {
        return input.asReports().map { it.status(tolerance = true) }.count { it == Report.Status.SAFE }
    }

    val input = readInput("day02/input")
    part1(input).println()
    part2(input).println()
}

@JvmInline
private value class Level(val value: Int) : Comparable<Level> {
    override fun compareTo(other: Level) = value.compareTo(other.value)

    operator fun minus(other: Level): Int = value - other.value
}

private class Report(private val levels: List<Level>) {

    fun status(tolerance: Boolean): Status {
        return if (!tolerance) {
            statusWithNoTolerance(levels)
        } else {
            statusWithTolerance()
        }
    }

    private fun statusWithTolerance(): Status {
        for (indice in levels.indices) {
            val levelsWithRemovedOneAtIndice = levels.filterIndexed { index, _ -> index != indice }
            if (statusWithNoTolerance(levelsWithRemovedOneAtIndice) == Status.SAFE) return Status.SAFE
        }
        return Status.UNSAFE
    }

    companion object {
        private fun statusWithNoTolerance(levels: List<Level>): Status {
            val isAscending = levels[0] - levels[1] < 0
            val acceptedRangeBetweenNextAndCurrent = if (isAscending) -3..-1 else 1..3

            for (indice in 1..<levels.size) {
                if (levels[indice - 1] - levels[indice] !in acceptedRangeBetweenNextAndCurrent) return Status.UNSAFE
            }

            return Status.SAFE
        }
    }

    enum class Status {
        SAFE,
        UNSAFE
    }
}

private fun List<String>.asReports() = map { it.asLevels() }.map { Report(it) }

private fun String.asLevels() = split(" ").map { levelAsString -> Level(levelAsString.toInt()) }