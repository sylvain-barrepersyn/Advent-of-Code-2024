package day04

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val charMatrix = CharMatrix(input)

        return charMatrix.sumOfIndexed { ordinate, abscissa, value ->
            var xmasCount = 0

            if (value == 'X') {
                // [S....]
                // [A....]
                // [M....]
                // [X....]
                // [.....]
                if (
                    ordinate >= 3 &&
                    charMatrix[ordinate - 1, abscissa] == 'M' &&
                    charMatrix[ordinate - 2, abscissa] == 'A' &&
                    charMatrix[ordinate - 3, abscissa] == 'S'
                ) {
                    xmasCount++
                }

                // [X....]
                // [M....]
                // [A....]
                // [S....]
                // [.....]
                if (
                    ordinate <= charMatrix.height - 4 &&
                    charMatrix[ordinate + 1, abscissa] == 'M' &&
                    charMatrix[ordinate + 2, abscissa] == 'A' &&
                    charMatrix[ordinate + 3, abscissa] == 'S'
                ) {
                    xmasCount++
                }

                // [S....]
                // [.A...]
                // [..M..]
                // [...X.]
                // [.....]
                if (
                    ordinate >= 3 &&
                    abscissa >= 3 &&
                    charMatrix[ordinate - 1, abscissa - 1] == 'M' &&
                    charMatrix[ordinate - 2, abscissa - 2] == 'A' &&
                    charMatrix[ordinate - 3, abscissa - 3] == 'S'
                ) {
                    xmasCount++
                }

                // [...X.]
                // [..M..]
                // [.A...]
                // [S....]
                // [.....]
                if (
                    ordinate <= charMatrix.height - 4 &&
                    abscissa >= 3 &&
                    charMatrix[ordinate + 1, abscissa - 1] == 'M' &&
                    charMatrix[ordinate + 2, abscissa - 2] == 'A' &&
                    charMatrix[ordinate + 3, abscissa - 3] == 'S'
                ) {
                    xmasCount++
                }

                // [SAMX.]
                // [.....]
                // [.....]
                // [.....]
                // [.....]
                if (
                    abscissa >= 3 &&
                    charMatrix[ordinate, abscissa - 1] == 'M' &&
                    charMatrix[ordinate, abscissa - 2] == 'A' &&
                    charMatrix[ordinate, abscissa - 3] == 'S'
                ) {
                    xmasCount++
                }

                // [.XMAS]
                // [.....]
                // [.....]
                // [.....]
                // [.....]
                if (
                    abscissa <= charMatrix.width - 4 &&
                    charMatrix[ordinate, abscissa + 1] == 'M' &&
                    charMatrix[ordinate, abscissa + 2] == 'A' &&
                    charMatrix[ordinate, abscissa + 3] == 'S'
                ) {
                    xmasCount++
                }

                // [.....]
                // [....S]
                // [...A.]
                // [..M..]
                // [.X...]
                if (
                    abscissa <= charMatrix.width - 4 &&
                    ordinate >= 3 &&
                    charMatrix[ordinate - 1, abscissa + 1] == 'M' &&
                    charMatrix[ordinate - 2, abscissa + 2] == 'A' &&
                    charMatrix[ordinate - 3, abscissa + 3] == 'S'
                ) {
                    xmasCount++
                }

                // [.X...]
                // [..M..]
                // [...A.]
                // [....S]
                // [.....]
                if (
                    abscissa <= charMatrix.width - 4 &&
                    ordinate <= charMatrix.height - 4 &&
                    charMatrix[ordinate + 1, abscissa + 1] == 'M' &&
                    charMatrix[ordinate + 2, abscissa + 2] == 'A' &&
                    charMatrix[ordinate + 3, abscissa + 3] == 'S'
                ) {
                    xmasCount++
                }
            }

            xmasCount
        }
    }

    fun part2(input: List<String>): Int {
        val charMatrix = CharMatrix(input)

        return charMatrix.sumOfIndexed { ordinate, abscissa, value ->
            if (
                value == 'A' &&
                abscissa in 1..<(charMatrix.width - 1) &&
                ordinate in 1..<(charMatrix.height - 1)
            ) {
                // [.M.M.]
                // [..A..]
                // [.S.S.]
                if (
                    charMatrix[ordinate - 1, abscissa - 1] == 'M' &&
                    charMatrix[ordinate - 1, abscissa + 1] == 'M' &&
                    charMatrix[ordinate + 1, abscissa - 1] == 'S' &&
                    charMatrix[ordinate + 1, abscissa + 1] == 'S'
                ) {
                    return@sumOfIndexed 1
                }

                // [.S.M.]
                // [..A..]
                // [.S.M.]
                else if (
                    charMatrix[ordinate - 1, abscissa + 1] == 'M' &&
                    charMatrix[ordinate + 1, abscissa + 1] == 'M' &&
                    charMatrix[ordinate - 1, abscissa - 1] == 'S' &&
                    charMatrix[ordinate + 1, abscissa - 1] == 'S'
                ) {
                    return@sumOfIndexed 1
                }

                // [.S.S.]
                // [..A..]
                // [.M.M.]
                else if (
                    charMatrix[ordinate + 1, abscissa - 1] == 'M' &&
                    charMatrix[ordinate + 1, abscissa + 1] == 'M' &&
                    charMatrix[ordinate - 1, abscissa - 1] == 'S' &&
                    charMatrix[ordinate - 1, abscissa + 1] == 'S'
                ) {
                    return@sumOfIndexed 1
                }

                // [.M.S.]
                // [..A..]
                // [.M.S.]
                else if (
                    charMatrix[ordinate - 1, abscissa - 1] == 'M' &&
                    charMatrix[ordinate + 1, abscissa - 1] == 'M' &&
                    charMatrix[ordinate - 1, abscissa + 1] == 'S' &&
                    charMatrix[ordinate + 1, abscissa + 1] == 'S'
                ) {
                    return@sumOfIndexed 1
                }
            }
            return@sumOfIndexed 0
        }
    }

    val input = readInput("day04/input")
    part1(input).println()
    part2(input).println()
}

private class CharMatrix(private val value: Array<CharArray>) {
    val width = value.size
    val height = value[0].size

    val ordinates = 0..value.lastIndex
    val absissas = 0..value[0].lastIndex

    constructor(input: List<String>) : this(input.map { line -> line.toCharArray() }.toTypedArray())

    operator fun get(ordinate: Int, absissa: Int) = value[ordinate][absissa]

    fun sumOfIndexed(action: (ordinate: Int, abscissa: Int, value: Char) -> Int): Int {
        var result = 0;
        for (ordinate in ordinates) {
            for (abscissa in absissas) {
                result += action(ordinate, abscissa, get(ordinate, abscissa))
            }
        }
        return result
    }
}