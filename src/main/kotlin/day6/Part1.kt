package day6

import utils.readFile

private enum class Direction1 {
    UP, RIGHT, DOWN, LEFT
}

private data class Position1(
    val x: Int,
    val y: Int
)

fun main() {
    val lines = readFile("src/main/kotlin/day6/data.txt")

    var y = lines.indexOfFirst { it.contains("^") }
    var x = lines[y].indexOf("^")

    var direction = Direction1.UP

    val positions = mutableSetOf<Position1>()

    while (x >= 0 && x < lines[0].length
        && y >= 0 && y < lines.size
    ) {
        positions.add(Position1(x, y))

        direction = when (direction) {
            Direction1.UP -> if (y > 0 && lines[y - 1][x] == '#') Direction1.RIGHT else Direction1.UP
            Direction1.RIGHT -> if (x + 1 < lines[y].length && lines[y][x + 1] == '#') Direction1.DOWN else Direction1.RIGHT
            Direction1.DOWN -> if (y + 1 < lines.size && lines[y + 1][x] == '#') Direction1.LEFT else Direction1.DOWN
            Direction1.LEFT -> if (x > 0 && lines[y][x - 1] == '#') Direction1.UP else Direction1.LEFT
        }

        when (direction) {
            Direction1.UP -> y -= 1
            Direction1.RIGHT -> x += 1
            Direction1.DOWN -> y += 1
            Direction1.LEFT -> x -= 1
        }
    }

    println("count: ${positions.size}; positions: $positions")
}