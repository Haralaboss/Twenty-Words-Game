package com.savvi.twentywords.ui.theme

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.savvi.twentywords.GamePreferences
import com.savvi.twentywords.R

@Composable
fun SettingsScreen(navController: NavController, context: Context) {
    var time by remember { mutableIntStateOf(60) } // Default time per round
    var teams by remember { mutableIntStateOf(2) } // Default number of teams
    var rounds by remember { mutableIntStateOf(1) } // Default number of rounds

    val gamePreferences = remember { GamePreferences(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp,8.dp,8.dp,8.dp)
    ) {

        Image(
            painterResource(R.drawable.logo4), stringResource( R.string.logo)

        )

        // Select Time per Round
        Column(
            Modifier.background(Bittersweet, RoundedCornerShape(8.dp))
        ){
            Column(Modifier.fillMaxWidth().padding(1.dp,1.dp,5.dp,5.dp).background(Sunglow, RoundedCornerShape(8.dp))) {
                Text("Χρόνος γύρου: $time δευτερόλεπτα", Modifier.align(Alignment.CenterHorizontally), fontWeight = FontWeight.Bold )
            }

        }

        Row(Modifier.padding(8.dp).fillMaxWidth()){
            Button(onClick = { time = 30},
                Modifier.fillMaxWidth().weight(1F).background(Sunglow, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(4.dp),
            ){
                Text("30")
            }

            Spacer(modifier = Modifier.width(2.dp))

            Button(onClick = { time = 60},
                Modifier.fillMaxWidth().weight(1F).background(Sunglow, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(2.dp)){
                Text("60")
            }

            Spacer(modifier = Modifier.width(2.dp))

            Button(onClick = { time = 90},
                Modifier.fillMaxWidth().weight(1F).background(Sunglow, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(4.dp)){
                Text("90")
            }

            Spacer(modifier = Modifier.width(2.dp))

            Button(onClick = { time = 120},
                Modifier.fillMaxWidth().weight(1F).background(Sunglow, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(4.dp)){
                Text("120")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            Modifier.background(Bittersweet, RoundedCornerShape(8.dp))
        ){
            Column(Modifier.fillMaxWidth().padding(1.dp,1.dp,5.dp,5.dp).background(Sunglow, RoundedCornerShape(8.dp))) {
                // Select Number of Teams (1 to 8)
                Text("Αριθμός ομάδων (1 εώς 8):", Modifier.align(Alignment.CenterHorizontally))

                Text("Ομάδες: $teams", Modifier.align(Alignment.CenterHorizontally), fontWeight = FontWeight.Bold)
            }

        }


        Slider(
            value = teams.toFloat(),
            onValueChange = { teams = it.toInt() },
            valueRange = 1f..8f,
            steps = 7, // 1 to 8 (inclusive)
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Column(
            Modifier.background(Bittersweet, RoundedCornerShape(8.dp))
        ){
            Column(Modifier.fillMaxWidth().padding(1.dp,1.dp,5.dp,5.dp).background(Sunglow, RoundedCornerShape(8.dp))) {
                // Select Number of Rounds (1 to 10)
                Text("Αριθμός γύρων (1 εώς 10):", Modifier.align(Alignment.CenterHorizontally))

                Text("Γύροι: $rounds", Modifier.align(Alignment.CenterHorizontally), fontWeight = FontWeight.Bold)
            }

        }

        Slider(
            value = rounds.toFloat(),
            onValueChange = { rounds = it.toInt() },
            valueRange = 1f..10f,
            steps = 9, // 1 to 10 (inclusive)
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Start Game Button
        Button(
            onClick = {
                gamePreferences.saveSettings(time, teams, rounds)
                gamePreferences.resetTeamScores(teams) // Reset scores for the new game
                navController.navigate("game/0/1") // Start with Team 1 (index 0)
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Εκκίνηση")
        }
    }
}