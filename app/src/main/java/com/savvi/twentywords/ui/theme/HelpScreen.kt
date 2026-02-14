package com.savvi.twentywords.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HelpScreen(navController: NavController) {
    Column(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.background(Charcoal, RoundedCornerShape(8)).padding(16.dp).fillMaxWidth().fillMaxHeight().weight(5f,fill = true)) {

            Text("ΟΔΗΓΙΕΣ", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))

            Spacer(Modifier.height(8.dp))

            Text("1. Χωριστείτε σε ομάδες 2 ατόμων.\n" +
                    "\n2. Σε κάθε γύρο, ένας κρατάει το κινητό, ο αφηγητής, και ο συμπαίκτης του πρέπει να βρει όσες πιο πολλές λέξεις μπορεί στο χρόνο που έχει.\n" +
                    "\n3. Πως γίνεται αυτό; Ο αφηγητής λέει ΜΙΑ λέξη σχετική με τη λέξη που έχει μπροστά του (Πχ. αν λέξη είναι 'Κεραυνός' ο αφηγητής μπορεί να πει Αστραπή). " +
                    "Προφανώς δε μπορεί να χρησιμοποιήσει μέρος της λέξης ή τη ρίζα της. (Παιχνίδι - Παίζω) \n" +
                    "\n4. Αν ο συμπαίκτης μαντέψει σωστά τη λέξη, ο αφηγητής πατάει τη λέξη που βρήκαν και προχωράει στην επόμενη. Αν δεν την μαντέψει, ο αφηγητής λέει μια άλλη λέξη. " +
                    "Σε οποιαδήποτε στιγμή μπορούν να πουν πάσο και να περάσουν στην επόμενη χωρίς να πάρουν πόντο.\n" +
                    "\n5. Νικητήρια ομάδα είναι αυτή που θα βρει τις πιο πολλές λέξεις.\n" +
                    "\n6. Δοκιμάστε και αυτούς τους κανόνες για παραπάνω δυσκολία:\n" +
                    "• Όχι ρήματα\n" +
                    "• Όχι αντώνυμα\n" +
                    "• Όχι λέξεις ίδιας κατηγορίας και αναφορά στην ίδια κατηγορία (Πχ. στο μήλο δεν μπορώ να πω μπανάνα, ούτε φρούτο)\n" +
                    "• Μη διστάσετε να βγάλετε και δικούς σας κανόνες!" , color = Color.White, modifier = Modifier.verticalScroll(rememberScrollState()))
        }
        Row(modifier = Modifier.weight(1f)) {
            Button(
                onClick = { navController.navigate("settings") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                Text("Παίξε")
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { navController.navigate("menu") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                Text("Πίσω")
            }
        }
    }
}