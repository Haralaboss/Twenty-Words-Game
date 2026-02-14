package com.savvi.twentywords.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.savvi.twentywords.BackConfirmationDialog

@Composable
fun EndRoundScreen(
    navController: NavController,
    currentTeam: Int,
    nextTeam: Int,
    currentRoundScore: Int, // Points gained in the current round
    nextRound: Int // Next round number
) {
    var showBackDialog by remember { mutableStateOf(false) }

    BackHandler {
        showBackDialog = true
    }

    if (showBackDialog) {
        BackConfirmationDialog(
            onConfirm = {
                showBackDialog = false // Dismiss dialog IMMEDIATELY
                navController.popBackStack("menu", inclusive = false) // Go back to menu
            },
            onDismiss = { showBackDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display current team's score for the round
        Text(

            text = when (currentRoundScore) {
                0 -> "Ομάδα $currentTeam δυστυχώς δε βρήκατε καμμία λέξη σε αυτό το γύρο!"
                1 -> "Ομάδα $currentTeam βρήκατε $currentRoundScore λέξη σε αυτό το γύρο!"
                else -> "Ομάδα $currentTeam βρήκατε $currentRoundScore λέξεις σε αυτό το γύρο!"
            },
            fontSize = 24.sp,
            color = Charcoal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Display message for the next team
        Text(
            text = "Ομάδα $nextTeam, ετοιμάσου!",
            fontSize = 20.sp,
            color = Charcoal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Button to start the next round
        Button(
            onClick = {
                // Navigate back to the GameScreen with the next team and round
                navController.navigate("game/${nextTeam - 1}/$nextRound") {
                    popUpTo("game") { inclusive = true } // Clear the back stack
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = "Εκκίνηση Γύρου",
                fontSize = 18.sp
            )
        }
    }
}