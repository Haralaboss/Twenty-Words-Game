package com.savvi.twentywords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.savvi.twentywords.ui.theme.TwentyWordsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pre-load the JSON
        lifecycleScope.launch(Dispatchers.IO) {
            WordRepository.load(this@MainActivity)
        }

        setContent {
            TwentyWordsTheme {
                Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            )  {
                val navController = rememberNavController()
                AppNavigation(navController = navController, context = this)
                }
            }

        }
    }
}