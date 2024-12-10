package day2

import utils.readFile
import kotlin.math.absoluteValue


fun main() {
    val lines = readFile("src/main/kotlin/day2/data.txt")

    val result = lines
        .filter() {
            val values = it.split(" ").map(String::toInt)
            val safe = isSafe(values)
            if(safe) {
                //println("line $values is safe")
                return@filter true
            }
            repeat(values.count()) {i ->
                val copy = values.toMutableList()
                copy.removeAt(i)
                val stillSafe = isSafe(copy)
                if(stillSafe) {
                    //println("line $values is still safe by removing index $i")
                    return@filter true
                }
            }
            //println("line $values is unsafe")
            false
        }
        .count()

    println("result: $result")

}

private fun isSafe(values: List<Int>): Boolean {
    if (values[0] == values[1]) {
        return false
    }
    val increasing = values[0] < values[1]
    return values
        .filterIndexed { index, value ->
            if (index == 0) return@filterIndexed false
            val distance = values[index] - values[index - 1]
            if (distance.absoluteValue < 1 || distance.absoluteValue > 3) return@filterIndexed false
            return@filterIndexed distance > 0 == increasing
        }
        .count() + 1 == values.count()
}