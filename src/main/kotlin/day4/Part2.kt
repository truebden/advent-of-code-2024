package day4

import utils.readFile

fun main() {
    val lines = readFile("src/main/kotlin/day4/data.txt")

    val count = lines
        .drop(1)
        .dropLast(1)
        .mapIndexed { y, line ->
            line
                .drop(1)
                .dropLast(1)
                .mapIndexed inner@{ x, char ->
                    if (char == 'A') {
                        return@inner findAround(x + 1, y + 1, lines)
                    }
                    0
                }
                .reduce { a, b -> a + b }
        }
        .reduce { a, b -> a + b }



    println("result: $count")

}

private fun findAround(x: Int, y: Int, lines: List<String>): Int {
    //M M
    // A
    //S S
    if (lines[y - 1][x - 1] == 'M'
        && lines[y - 1][x + 1] == 'M'
        && lines[y + 1][x - 1] == 'S'
        && lines[y + 1][x + 1] == 'S'
    ) {
        println("XXX found for $x:$y")
        return 1
    }
    //S S
    // A
    //M M
    if (lines[y - 1][x - 1] == 'S'
        && lines[y - 1][x + 1] == 'S'
        && lines[y + 1][x - 1] == 'M'
        && lines[y + 1][x + 1] == 'M'
    ) {
        println("XXX found for $x:$y")
        return 1
    }
    //S M
    // A
    //S M
    if (lines[y - 1][x - 1] == 'S'
        && lines[y - 1][x + 1] == 'M'
        && lines[y + 1][x - 1] == 'S'
        && lines[y + 1][x + 1] == 'M'
    ) {
        println("XXX found for $x:$y")
        return 1
    }
    //M S
    // A
    //M S
    if (lines[y - 1][x - 1] == 'M'
        && lines[y - 1][x + 1] == 'S'
        && lines[y + 1][x - 1] == 'M'
        && lines[y + 1][x + 1] == 'S'
    ) {
        println("XXX found for $x:$y")
        return 1
    }
    return 0
}
