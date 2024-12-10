package utils

import java.io.File

fun readFile(fileName: String): List<String> {
    return File(fileName).useLines { it.toList() }
}