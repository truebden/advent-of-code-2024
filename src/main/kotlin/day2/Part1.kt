package day2

import utils.readFile
import kotlin.math.absoluteValue


fun main() {
    val lines = readFile("src/main/kotlin/day2/data.txt")

    val result = lines
        .filter {
            val values = it.split(" ").map(String::toInt)
            if (values[0] == values[1]) {
                return@filter false
            }
            val increasing = values[0] < values[1]
            val safe = values
                .filterIndexed { index, value ->
                    if (index == 0) return@filterIndexed false
                    val distance = values[index] - values[index - 1]
                    if (distance.absoluteValue < 1 || distance.absoluteValue > 3) return@filterIndexed false
                    return@filterIndexed distance > 0 == increasing
                }
                .count() + 1 == values.count()
            //println("line $values is safe: $safe")
            safe
        }
        .count()

    println("result: $result")

}