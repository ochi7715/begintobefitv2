package com.sucis400.BeginToBeFit

import com.google.gson.Gson

data class PerformedActivity(
    val exerciseUniqueID: String,
    val numRepsPerformed: Int,
    val numSetsPerformed: Int,
    val workoutStartTime: Long,
    val workoutEndTime: Long,
    val workoutDifficultyRating: Int,
    val workoutNotes: String
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}