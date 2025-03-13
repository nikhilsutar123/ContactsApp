package com.example.contactsapp.util

import android.app.AlertDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    permission: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGotoAppSettingsCLick: () -> Unit,
    modifier: Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Permission Required")
        },
        text = {
            Text(permission.getDesc(isPermanentlyDeclined))
        },
        confirmButton = {
            Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalDivider()
                Text(
                    text = if (isPermanentlyDeclined) {
                        "Grant Permission"
                    } else {
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable {
                            if (isPermanentlyDeclined)
                                onGotoAppSettingsCLick()
                            else
                                onOkClick()
                        }
                        .padding(8.dp)
                )
            }
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDesc(isPermanentlyDeclined: Boolean): String
}

class ContactPermissionTextProvider : PermissionTextProvider {
    override fun getDesc(isPermanentlyDeclined: Boolean): String {
        println("permanently declined $isPermanentlyDeclined")
        return if (isPermanentlyDeclined) {
            "It seems you permanently declined contact permission." +
                    "you can go to app settings to grant it."
        } else {
            "This app needs access to your contact to make calls and messages."
        }
    }

}