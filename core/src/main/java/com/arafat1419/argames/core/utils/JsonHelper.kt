package com.arafat1419.argames.core.utils

import java.io.BufferedReader
import java.io.InputStreamReader

object JsonHelper {
    fun readJsonFile(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val content = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            content.append(line)
            line = reader.readLine()
        }
        return content.toString()
    }
}