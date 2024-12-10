package day5

import utils.readFile


fun main() {
    val lines = readFile("src/main/kotlin/day5/data.txt")
    val separatorIndex = lines.indexOf("")
    val orderingList = lines.dropLast(lines.count() - separatorIndex)
    val updatesList = lines.drop(separatorIndex + 1)
        .map { it.split(",").map(String::toInt) }

    val orderingMapForward = orderingList.map { it.split("|") }.groupBy({ it[0].toInt() }, { it[1].toInt() })

    //println("orderingMapForward: $orderingMapForward")
    //println("orderingMapBack: $orderingMapBack")
    //println("updatesList: $updatesList")

    val result = updatesList.filter {
        val valid = isValid(it, orderingMapForward)
        //println("update $it is valid $valid\n")
        valid
    }
        .map { it[it.size / 2] }
        .reduce { a, b -> a + b }

    println("result: $result")
}

private fun isValid(
    update: List<Int>,
    orderingMapForward: Map<Int, List<Int>>
): Boolean {
    val invalidForward = update.dropLast(1).filterIndexed { index, v ->
        val validFollowingValue = orderingMapForward[v]
        val invalid = if (validFollowingValue == null) {
            false
        } else {
            update.drop(index + 1).none { validFollowingValue.contains(it) }
        }
        //println("update1 $update value $v at index $index is valid ${!invalid}")
        return@filterIndexed invalid
    }.any()
    if (invalidForward) {
        return false
    }

    return update.reversed().dropLast(1).filterIndexed { index, v ->
        val valid = update.reversed().drop(index + 1).none {
            val validFollowingValue = orderingMapForward[v]
            return@none if (validFollowingValue == null) {
                false
            } else {
                validFollowingValue.contains(it)
            }
        }
        //println("update2 $update value $v at index $index is valid $valid")
        return@filterIndexed !valid
    }.none()
}