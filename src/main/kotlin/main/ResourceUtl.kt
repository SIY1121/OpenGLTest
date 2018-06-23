package main

import java.io.BufferedReader
import java.io.InputStreamReader

fun loadTextFromResource(path: String): String {
    return BufferedReader(InputStreamReader(ClassLoader.getSystemResourceAsStream(path))).readText()
}