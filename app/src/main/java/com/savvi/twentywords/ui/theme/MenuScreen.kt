package com.savvi.twentywords.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.savvi.twentywords.R

@Composable
fun MenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.logo4), stringResource( R.string.logo)

        )
        Button(onClick = { navController.navigate("settings") }, modifier = Modifier.size(250.dp, 90.dp), colors = ButtonDefaults.buttonColors(Color.Transparent)) {
            Image(
                painterResource(R.drawable.play), stringResource( R.string.play)
            )
        }
        Button(onClick = { navController.navigate("help") }, modifier = Modifier.size(250.dp, 90.dp), colors = ButtonDefaults.buttonColors(Color.Transparent)) {
            Image(
                painterResource(R.drawable.help), stringResource( R.string.help)
            )
        }
    }
}