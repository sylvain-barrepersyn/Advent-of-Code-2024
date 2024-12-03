package day03

import println
import readInput

fun main() {
    fun part1(input: List<String>) = input.joinToString("").let { SimpleCorruptedMemoryInterpreter.interpret(it) }
    fun part2(input: List<String>) = input.joinToString("").let { CorruptedMemoryWithEnablersInterpreter.interpret(it) }

    val input = readInput("day03/input")
    part1(input).println()
    part2(input).println()
}

private fun interface CorruptedMemoryInterpreter {

    fun interpret(source: String): Int
}

private object SimpleCorruptedMemoryInterpreter : CorruptedMemoryInterpreter {

    private const val MULTIPLICATION_GROUP_NAME = "multiplication"

    private val CORRUPTED_INSTRUCTION_REGEX = Regex("""(?<$MULTIPLICATION_GROUP_NAME>mul\(\d{1,3},\d{1,3}\))""")

    override fun interpret(source: String): Int {
        return CORRUPTED_INSTRUCTION_REGEX
            .findAll(source)
            .map { it.groups[MULTIPLICATION_GROUP_NAME]!!.value }
            .map { it.asMultiplicandAndMultiplier() }
            .sumOf { it.multiply() }
    }

    private fun String.asMultiplicandAndMultiplier() =
        substring(4, lastIndex).split(",").let { it[0].toInt() to it[1].toInt() }

    private fun Pair<Int, Int>.multiply() = first * second
}

private object CorruptedMemoryWithEnablersInterpreter : CorruptedMemoryInterpreter {

    private const val ACTIVE_SECTION_ENABLER_PREFIX = "do()"

    private const val INACTIVE_SECTION_ENABLER_PREFIX = "don't()"

    override fun interpret(source: String): Int {
        return source
            // split by active sections
            .split(ACTIVE_SECTION_ENABLER_PREFIX)
            // remove all elements after a don't() until next active section
            .joinToString { it.substringBefore(INACTIVE_SECTION_ENABLER_PREFIX) }
            // interpret this string in normal way without disabled sections
            .let { SimpleCorruptedMemoryInterpreter.interpret(it) }
    }
}