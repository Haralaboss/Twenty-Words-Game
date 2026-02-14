package com.savvi.twentywords

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.savvi.twentywords.ui.theme.GameScreen
import com.savvi.twentywords.ui.theme.MenuScreen
import com.savvi.twentywords.ui.theme.SettingsScreen
import com.savvi.twentywords.ui.theme.GameOverScreen
import com.savvi.twentywords.ui.theme.EndRoundScreen
import com.savvi.twentywords.ui.theme.HelpScreen

@Composable
fun AppNavigation(navController: NavHostController, context: Context) {
    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") { MenuScreen(navController) }
        composable("settings") { SettingsScreen(navController, context) }
        composable(
            route = "game/{currentTeam}/{currentRound}",
            arguments = listOf(
                navArgument("currentTeam") { type = NavType.IntType },
                navArgument("currentRound") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val currentTeam = backStackEntry.arguments?.getInt("currentTeam") ?: 0
            val currentRound = backStackEntry.arguments?.getInt("currentRound") ?: 1
            GameScreen(navController, context, currentTeam, currentRound)
        }
        composable(
            route = "endRound/{currentTeam}/{nextTeam}/{currentRoundScore}/{nextRound}",
            arguments = listOf(
                navArgument("currentTeam") { type = NavType.IntType },
                navArgument("nextTeam") { type = NavType.IntType },
                navArgument("currentRoundScore") { type = NavType.IntType },
                navArgument("nextRound") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val currentTeam = backStackEntry.arguments?.getInt("currentTeam") ?: 1
            val nextTeam = backStackEntry.arguments?.getInt("nextTeam") ?: 1
            val currentRoundScore = backStackEntry.arguments?.getInt("currentRoundScore") ?: 0
            val nextRound = backStackEntry.arguments?.getInt("nextRound") ?: 1

            EndRoundScreen(
                navController = navController,
                currentTeam = currentTeam,
                nextTeam = nextTeam,
                currentRoundScore = currentRoundScore,
                nextRound = nextRound
            )
        }
        composable(
            route = "gameOver/{finalScores}",
            arguments = listOf(navArgument("finalScores") { type = NavType.StringType })
        ) { backStackEntry ->
            val finalScores = backStackEntry.arguments
                ?.getString("finalScores")
                ?.split(",")
                ?.map { it.toInt() }
                ?: emptyList()

            GameOverScreen(navController = navController, finalScores = finalScores)
        }

        composable("help") { HelpScreen(navController) }
    }
}

