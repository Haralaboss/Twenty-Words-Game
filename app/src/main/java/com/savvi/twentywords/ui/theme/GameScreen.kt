package com.savvi.twentywords.ui.theme

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.savvi.twentywords.BackConfirmationDialog
import com.savvi.twentywords.GamePreferences
import com.savvi.twentywords.WordRepository
import com.savvi.twentywords.fadingTopBottomEdgesSimplified
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    navController: NavController,
    context: Context,
    initialTeam: Int = 0, // Default to Team 1 (index 0)
    initialRound: Int = 1 // Default to Round 1
) {

    val words = remember { WordRepository.getRandomWords(20) }
    val foundWords = remember { mutableStateListOf<String>() }
    val gamePreferences = remember { GamePreferences(context) }
    val time = remember { gamePreferences.getTime() }
    val teams = remember { gamePreferences.getTeams() }
    val totalRounds = remember { gamePreferences.getRounds() }
    var remainingTime by remember { mutableIntStateOf(time) }
    val teamScores = remember { mutableStateListOf<Int>().apply {
        addAll(gamePreferences.getTeamScores(teams)) // Load saved scores
    } }
    var currentRound by remember { mutableIntStateOf(initialRound) }
    var currentRoundScore by remember { mutableIntStateOf(0) } // Track points gained in the current round

    var showBackDialog by remember { mutableStateOf(false) }

    // Handle back button press
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

    LaunchedEffect(key1 = remainingTime) {
        if (remainingTime > 0) {
            delay(1000L) // Wait for 1 second
            remainingTime--
        } else {
            // Time's up! Check if this is the final round and all teams have played
            val nextTeam = (initialTeam + 1) % teams
            val isLastTeam = nextTeam == 0 // Check if this is the last team for the round
            val nextRound = if (isLastTeam) currentRound + 1 else currentRound

            // Save scores before navigating
            gamePreferences.saveTeamScores(teamScores)

            if (isLastTeam && nextRound > totalRounds) {
                // Game over! Navigate to the GameOverScreen
                navController.navigate("gameOver/${teamScores.joinToString(",")}")
            } else {
                // Navigate to the End Round screen
                val nextTeamForRound = if (isLastTeam) 0 else nextTeam
                navController.navigate("endRound/${initialTeam + 1}/${nextTeamForRound + 1}/$currentRoundScore/${nextRound}")
                currentRoundScore = 0 // Reset current round score
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            Modifier.background(Bittersweet, RoundedCornerShape(8.dp))
        ){
            Column(Modifier.fillMaxWidth().padding(1.dp,1.dp,5.dp,5.dp).background(Sunglow, RoundedCornerShape(8.dp))) {
                // Timer and game info at the top
                Row(
                    modifier = Modifier.fillMaxWidth().padding(15.dp,5.dp,15.dp,5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Χρόνος: $remainingTime", fontWeight = FontWeight.Bold)
                    Text("Ομάδα ${initialTeam + 1}", fontWeight = FontWeight.Bold)
                    Text("Γύρος: $initialRound/$totalRounds", fontWeight = FontWeight.Bold)
                }
            }

        }


        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn with constrained height and centered words
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth().align(Alignment.CenterHorizontally)
        ) {
            val lazyListState = rememberLazyListState()
            val isAtTop: Boolean by remember {
                derivedStateOf {
                    lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
                }
            }

            val isAtBottom by remember {
                derivedStateOf {
                    val layoutInfo = lazyListState.layoutInfo
                    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()

                    // Check if the last visible item is the last item AND if it's fully visible
                    lastVisibleItem != null &&
                            lastVisibleItem.index == layoutInfo.totalItemsCount - 1 &&
                            lastVisibleItem.offset + lastVisibleItem.size <= layoutInfo.viewportEndOffset
                }
            }

            // Animated Dp values for the top and bottom fade heights
            val topFadeHeight by animateDpAsState(
                if (isAtTop) 0.dp else 150.dp,
                animationSpec = tween(durationMillis = 600),
                label = ""
            )

            val bottomFadeHeight by animateDpAsState(

                if (isAtBottom) 0.dp else 20.dp,
                animationSpec = tween(durationMillis = 600),
                label = ""
            )

            LazyColumn(

                modifier = Modifier.fillMaxWidth().fadingTopBottomEdgesSimplified(topFadeHeight, bottomFadeHeight),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(words) { word ->
                    WordItem(
                        word = word,
                        isFound = foundWords.contains(word),
                        onWordFound = {
                            foundWords.add(word)
                            teamScores[initialTeam]++
                            currentRoundScore++

                        },
                        onWordNotFound = {
                            foundWords.remove(word)
                            teamScores[initialTeam]--
                            currentRoundScore--

                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display scores for all teams
        Column {
            teamScores.forEachIndexed { index, score ->
                Text("Ομάδα ${index + 1} Σκορ: $score", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Calculate the next team
                val nextTeam = (initialTeam + 1) % teams
                val isLastTeam = nextTeam == 0 // Check if this is the last team for the round
                val nextRound = if (isLastTeam) currentRound + 1 else currentRound

                // Save scores before navigating
                gamePreferences.saveTeamScores(teamScores)

                if (isLastTeam && nextRound > totalRounds) {
                    // Game over! Navigate to the GameOverScreen
                    navController.navigate("gameOver/${teamScores.joinToString(",")}")
                } else {
                    // Navigate to the End Round screen
                    val nextTeamForRound = if (isLastTeam) 0 else nextTeam
                    navController.navigate("endRound/${initialTeam + 1}/${nextTeamForRound + 1}/$currentRoundScore/${nextRound}")
                    currentRoundScore = 0 // Reset current round score
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Τέλος Γύρου")
        }
    }
}



@Composable
fun WordItem(word: String, isFound: Boolean, onWordFound: () -> Unit, onWordNotFound: () -> Unit) {
    val backgroundColor = if (isFound) LightGreen else Color.White

    Button(
        onClick = {
            if (!isFound) { // Only call onWordFound if the word is not already found
                onWordFound()
            }
            else {
                onWordNotFound()
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)

    ) {
        Text(text = word, color = Color.Black, fontSize = 20.sp)
        Alignment.CenterHorizontally
    }
}