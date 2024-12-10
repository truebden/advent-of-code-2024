package day7

import utils.readFile


fun main() {
    val lines = readFile("src/main/kotlin/day7/data.txt")

    val result = lines
        .map {
            val (result, allValues) = it.split(":")
            val values = allValues.substring(1).split(" ").map { it.toLong() }
            result.toLong() to values
        }
        .filter { pair ->
            val operatorCombinations = generatePermutationsIterative(pair.second.size - 1)
            operatorCombinations.any { operatorCombination ->
                val calcValue = pair.second.reduceIndexed { index, acc, v ->
                    when (operatorCombination[index - 1]) {
                        '+' -> acc + v
                        '*' -> acc * v
                        else -> throw IllegalStateException("Unknown operator!")
                    }
                }
                calcValue == pair.first
            }
        }
        .map { it.first }
        .reduce { acc, result -> acc + result }

    println("result: $result")
}

private const val operators = "*+"
private fun generatePermutationsIterative(n: Int): List<String> {
    val result = mutableListOf<String>()
    val totalPermutations = Math.pow(operators.length.toDouble(), n.toDouble()).toInt()

    for (i in 0 until totalPermutations) {
        val builder = StringBuilder()
        var num = i
        for (j in 0 until n) {
            builder.append(operators[num % operators.length])
            num /= operators.length
        }
        result.add(builder.toString())
    }
    return result
}