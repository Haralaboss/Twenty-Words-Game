package com.savvi.twentywords

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.savvi.twentywords.ui.theme.Bittersweet

@Composable
fun BackConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Προσοχή") },
        text = { Text("Θέλετε να επιστρέψετε στο Μενού; Θα χαθεί η πρόοδος του παιχνιδιού.") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Bittersweet)
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Bittersweet)
            ) {
                Text("Άκυρο")
            }
        }
    )
}