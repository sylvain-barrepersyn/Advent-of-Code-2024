package day05

import println
import readInput

@JvmInline
private value class Page(val value: Int)

private typealias PageOrderingRules = Map<Page, List<Page>>

private typealias PageUpdate = List<Page>
private typealias PageUpdates = List<PageUpdate>

fun main() {
    fun part1(input: List<String>) = buildComparatorBasedOnPageOrderingRules(input.takeWhile { it != "" })
        .let { comparatorBasedOnPageOrderingRules ->
            PageUpdatesInterpreter
                .interpret(input.takeLastWhile { it != "" })
                .filter { it.isCorrectlyOrderedFollowing(comparatorBasedOnPageOrderingRules) }
                .sumOf { it[it.size / 2].value }
        }

    fun part2(input: List<String>) = buildComparatorBasedOnPageOrderingRules(input.takeWhile { it != "" })
        .let { comparatorBasedOnPageOrderingRules ->
            PageUpdatesInterpreter
                .interpret(input.takeLastWhile { it != "" })
                .filter { !it.isCorrectlyOrderedFollowing(comparatorBasedOnPageOrderingRules) }
                .map { it.sortedWith(comparatorBasedOnPageOrderingRules) }
                .sumOf { it[it.size / 2].value }
        }

    val input = readInput("day05/input")
    part1(input).println()
    part2(input).println()
}

private class ComparatorBasedOnPageOrderingRules(
    private val pageOrderingRules: PageOrderingRules
) : Comparator<Page> {

    override fun compare(first: Page, second: Page) = if (pageOrderingRules[first]?.contains(second) == true) {
        -1
    } else if (pageOrderingRules[second]?.contains(first) == true) {
        1
    } else {
        0
    }
}

private object PageOrderingRulesInterpreter {
    fun interpret(source: Collection<String>): PageOrderingRules = source
        .map {
            it.substringBefore('|') to it.substringAfter('|')
        }
        .groupBy(
            { Page(it.first.toInt()) },
            { Page(it.second.toInt()) }
        )
}

private object PageUpdatesInterpreter {
    fun interpret(source: Collection<String>): PageUpdates = source
        .map {
            it.split(",").map { page -> Page(page.toInt()) }
        }
}

private fun PageUpdate.isCorrectlyOrderedFollowing(comparator: Comparator<Page>): Boolean {
    for (i in indices) {
        for (j in (i + 1)..lastIndex) {
            if (comparator.compare(get(i), get(j)) == 1) {
                return false
            }
        }
    }
    return true
}

private fun buildComparatorBasedOnPageOrderingRules(source: List<String>) =
    ComparatorBasedOnPageOrderingRules(PageOrderingRulesInterpreter.interpret(source))