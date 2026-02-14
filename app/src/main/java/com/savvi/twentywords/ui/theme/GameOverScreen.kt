package com.savvi.twentywords.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GameOverScreen(navController: NavController, finalScores: List<Int>) {

    BackHandler {
        navController.popBackStack("menu", inclusive = false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Game Over!",
            style = MaterialTheme.typography.headlineMedium,
            color = Bittersweet
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Τελικό Σκορ:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        finalScores.forEachIndexed { index, score ->
            Text(
                text = "Ομάδα ${index + 1}: $score",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("menu") },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = "Μενού",
                fontSize = 18.sp
            )
        }
    }
}