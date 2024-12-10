package day6

import utils.readFile
import java.util.stream.Collectors


private enum class Direction {
    UP, RIGHT, DOWN, LEFT
}

private data class Position(
    val x: Int,
    val y: Int
)

fun main() {
    val startTime = System.currentTimeMillis()
    val lines = readFile("src/main/kotlin/day6/data.txt")
    //println("OriginalMap:\n${lines.joinToString("\n")}")

    val startY = lines.indexOfFirst { it.contains("^") }
    val startX = lines[startY].indexOf("^")

    val positions = calcPossiblePositions(startX, startY, lines)
    println("count: ${positions.size}; positions: ${positions.size}")

    positions.remove(Position(startX, startY))

    val possibleBlockingPositions = positions.entries.parallelStream().filter {
        val adapted = lines.toMutableList()
        val line = adapted[it.key.y].toCharArray()
        line[it.key.x] = '#'
        adapted[it.key.y] = String(line)
        //println("Map:\n${adapted.joinToString("\n")}")
        try {
            calcPossiblePositions(startX, startY, adapted)
            false
        } catch (e: IllegalStateException) {
            true
        }
    }
        .map { it.key }
        .toList()

    println("blocking position count: ${possibleBlockingPositions.size}; \nTime: ${System.currentTimeMillis()-startTime}")
}

private fun calcPossiblePositions(
    startX: Int,
    startY: Int,
    lines: List<String>
): MutableMap<Position, MutableSet<Direction>> {
    var direction = Direction.UP
    val positions = mutableMapOf<Position, MutableSet<Direction>>()
    var x = startX
    var y = startY

    while (x >= 0 && x < lines[0].length
        && y >= 0 && y < lines.size
    ) {

        val newDirection = when (direction) {
            Direction.UP -> if (y > 0 && lines[y - 1][x] == '#') Direction.RIGHT else Direction.UP
            Direction.RIGHT -> if (x + 1 < lines[y].length && lines[y][x + 1] == '#') Direction.DOWN else Direction.RIGHT
            Direction.DOWN -> if (y + 1 < lines.size && lines[y + 1][x] == '#') Direction.LEFT else Direction.DOWN
            Direction.LEFT -> if (x > 0 && lines[y][x - 1] == '#') Direction.UP else Direction.LEFT
        }
        if(direction != newDirection) {
            direction = newDirection
            continue
        }

        positions.compute(Position(x, y)) { k, v ->
            val directions = v ?: mutableSetOf()
            check(directions.add(direction))
            directions
        }

        when (direction) {
            Direction.UP -> y -= 1
            Direction.RIGHT -> x += 1
            Direction.DOWN -> y += 1
            Direction.LEFT -> x -= 1
        }
    }
    return positions
}