package com.sucis400.BeginToBeFit

import com.google.gson.Gson
import java.io.File

data class Exercise(
    val id: String,
    val description: String,
    val repRange: IntRange,
    val weightRange: IntRange,
    val magicFactor: Double
) {
    companion object {
        fun loadFromJson(filePath: String): Exercise {
            val jsonContent = File(filePath).readText()
            return Gson().fromJson(jsonContent, Exercise::class.java)
        }
    }
}