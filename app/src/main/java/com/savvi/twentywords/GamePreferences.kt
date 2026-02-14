package com.savvi.twentywords

import android.content.Context

class GamePreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("game_settings", Context.MODE_PRIVATE)

    fun saveSettings(time: Int, teams: Int, rounds: Int) {
        sharedPreferences.edit().apply {
            putInt("time", time)
            putInt("teams", teams)
            putInt("rounds", rounds)
            apply()
        }
    }

    fun getTime(): Int {
        return sharedPreferences.getInt("time", 30) // Default to 30 seconds
    }

    fun getTeams(): Int {
        return sharedPreferences.getInt("teams", 1) // Default to 1 team
    }

    fun getRounds(): Int {
        return sharedPreferences.getInt("rounds", 1) // Default to 1 round
    }

    // Save team scores
    fun saveTeamScores(scores: List<Int>) {
        sharedPreferences.edit().apply {
            putString("teamScores", scores.joinToString(","))
            apply()
        }
    }

    // Load team scores
    fun getTeamScores(teams: Int): List<Int> {
        val scoresString = sharedPreferences.getString("teamScores", null)
        return scoresString?.split(",")?.map { it.toInt() } ?: List(teams) { 0 }
    }

    // Reset team scores
    fun resetTeamScores(teams: Int) {
        sharedPreferences.edit().apply {
            putString("teamScores", List(teams) { 0 }.joinToString(","))
            apply()
        }
    }
}