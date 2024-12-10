package day3

import utils.readFile
import kotlin.math.absoluteValue


fun main() {
    val line = readFile("src/main/kotlin/day3/data.txt").joinToString("")

    val matchResults = "mul\\((\\d+),(\\d+)\\)".toRegex().findAll(line)
    val result = matchResults
        .map {
            val a = it.groups[1]!!.value.toInt()
            val b = it.groups[2]!!.value.toInt()
            println("mul($a,$b)")
            a * b
        }
        .reduce{a,b -> a + b}

    println("result: $result")

}