package day1

import utils.readFile
import kotlin.math.absoluteValue


fun main() {
    val lines = readFile("src/main/kotlin/day1/data.txt")

    val listA = arrayListOf<Int>()
    val listB = arrayListOf<Int>()

    lines
        .map { it.split(" ") }
        .forEach {
            listA.add(it[0].toInt())
            listB.add(it.last().toInt())
        }

    listA.sort()
    listB.sort()

    //println("ListA: ${listA}")
    //println("ListB: ${listB}")

    val distance = listA
        .zip(listB) { a, b -> (a - b).absoluteValue}
        .reduce {a,b -> a + b}

    println("distance: $distance")
}