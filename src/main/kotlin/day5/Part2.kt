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

    val invalidUpdates = updatesList.filter {
        val valid = isValid(it, orderingMapForward)
        //println("update $it is valid $valid\n")
        !valid
    }

    //println("invalidUpdates: $invalidUpdates")

    val result = invalidUpdates
        .map { fix(it, orderingMapForward) }
        .map {
            //println("fixedUpdate: $it")
            it[it.size / 2]
        }
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

private fun fix(invalidUpdate: List<Int>, orderingMapForward: Map<Int, List<Int>>): List<Int> {
    invalidUpdate.dropLast(1).forEachIndexed { index, v ->
        val validFollowingValue = orderingMapForward[v]
        if (validFollowingValue == null) {
            return@forEachIndexed
        }
        var targetIndex = invalidUpdate.drop(index + 1).indexOfFirst { !validFollowingValue.contains(it) }

        if (targetIndex == -1) {
            return@forEachIndexed
        }
        targetIndex += index + 1
        //println("update1 $invalidUpdate value $v at index $index new targetIndex $targetIndex")
        val validUpdate = invalidUpdate.toMutableList()
        val removedValue = validUpdate.removeAt(targetIndex)
        validUpdate.add(index, removedValue)
        if (isValid(validUpdate, orderingMapForward)) {
            return validUpdate
        }
        return fix(validUpdate, orderingMapForward)
    }

    val reversedInvalidUpdate = invalidUpdate.reversed()
    reversedInvalidUpdate.dropLast(1).forEachIndexed { index, v ->
        val validFollowingValue = orderingMapForward[v]
        if (validFollowingValue == null) {
            return@forEachIndexed
        }
        var targetIndex = reversedInvalidUpdate.drop(index + 1).indexOfFirst {
            validFollowingValue.contains(it)
        }
        if (targetIndex == -1) {
            return@forEachIndexed
        }

        targetIndex += index + 1

        if (index != targetIndex) {
            //println("update2 $invalidUpdate; $reversedInvalidUpdate value $v at index $index new targetIndex $targetIndex")
            var validUpdate = reversedInvalidUpdate.toMutableList()
            val removedValue = validUpdate.removeAt(targetIndex)
            validUpdate.add(index, removedValue)
            validUpdate = validUpdate.reversed().toMutableList()
            if (isValid(validUpdate, orderingMapForward)) {
                return validUpdate
            }
            return fix(validUpdate, orderingMapForward)
        }
    }
    throw RuntimeException("Couldn't find issue")
}