package day3

import utils.readFile


fun main() {
    val line = readFile("src/main/kotlin/day3/data.txt").joinToString("")

    var doMatchIndex = 0
    var dontMatchIndex = 0
    var enable = true
    var result = 0
    while (doMatchIndex != line.length && dontMatchIndex != line.length) {
        if (enable) {
            enable = false
            dontMatchIndex = line.indexOf("don't()", doMatchIndex)
            if (dontMatchIndex < 0) {
                dontMatchIndex = line.length
            }
            result += multiply(line.substring(doMatchIndex, dontMatchIndex))
        } else {
            enable = true
            doMatchIndex = line.indexOf("do()", dontMatchIndex)
            if (doMatchIndex < 0) {
                doMatchIndex = line.length
            }
        }
    }
    println("result: $result")
}

private fun multiply(line: String): Int {
    val matchResults = "mul\\((\\d+),(\\d+)\\)".toRegex().findAll(line)
    val result = matchResults
        .map {
            val a = it.groups[1]!!.value.toInt()
            val b = it.groups[2]!!.value.toInt()
            println("mul($a,$b)")
            a * b
        }
        .reduce { a, b -> a + b }
    return result
}