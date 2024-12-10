package day4

import utils.readFile

fun main() {
    val lines = readFile("src/main/kotlin/day4/data.txt")

    val count = lines.mapIndexed { y, line ->
        line
            .mapIndexed inner@{ x, char ->
                if (char == 'X') {
                    return@inner findAround(x, y, lines)
                }
                0
            }
            .reduce { a, b -> a + b }
    }
        .reduce { a, b -> a + b }



    println("result: $count")

}

private fun findAround(x: Int, y: Int, lines: List<String>): Int {
    var count = 0
    //top left
    if (x >= 3 && y >= 3
        && lines[y - 1][x - 1] == 'M'
        && lines[y - 2][x - 2] == 'A'
        && lines[y - 3][x - 3] == 'S'
    ) {
        count += 1
    }
    //top
    if (y >= 3
        && lines[y - 1][x] == 'M'
        && lines[y - 2][x] == 'A'
        && lines[y - 3][x] == 'S'
    ) {
        count += 1
    }
    //top right
    if (x < lines[y].length - 3 && y >= 3
        && lines[y - 1][x + 1] == 'M'
        && lines[y - 2][x + 2] == 'A'
        && lines[y - 3][x + 3] == 'S'
    ) {
        count += 1
    }
    //right
    if (x < lines[y].length - 3
        && lines[y][x + 1] == 'M'
        && lines[y][x + 2] == 'A'
        && lines[y][x + 3] == 'S'
    ) {
        count += 1
    }
    //bottom right
    if (x < lines[y].length - 3 && y < lines.count() - 3
        && lines[y + 1][x + 1] == 'M'
        && lines[y + 2][x + 2] == 'A'
        && lines[y + 3][x + 3] == 'S'
    ) {
        count += 1
    }
    //bottom
    if (y < lines.count() - 3
        && lines[y + 1][x] == 'M'
        && lines[y + 2][x] == 'A'
        && lines[y + 3][x] == 'S'
    ) {
        count += 1
    }
    //bottom left
    if (x >= 3 && y < lines.count() - 3
        && lines[y + 1][x - 1] == 'M'
        && lines[y + 2][x - 2] == 'A'
        && lines[y + 3][x - 3] == 'S'
    ) {
        count += 1
    }
    //left
    if (x >= 3
        && lines[y][x - 1] == 'M'
        && lines[y][x - 2] == 'A'
        && lines[y][x - 3] == 'S'
    ) {
        count += 1
    }
    return count
}
