package com.sucis400.BeginToBeFit

import com.sucis400.BeginToBeFit.PerformedActivity
import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class User(
    val name: String,
    val weight: Float,
    val age: Int,
    val performedActivities: List<PerformedActivity>,
    val skillExperience: Float
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }



    fun readFile(filePath: String): String {
        return File(filePath).readText()
    }

    fun loadUserFromFile(filePath: String): User {
        val json = readFile(filePath)
        return User.fromJson(json)
    }

    companion object {
        fun fromJson(json: String): User {
            val type = object : TypeToken<User>() {}.type
            return Gson().fromJson(json, type)
        }
    }
}