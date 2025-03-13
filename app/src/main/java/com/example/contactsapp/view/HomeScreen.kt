package com.example.contactsapp.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.contactsapp.data.ContactUiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: ContactUiState
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(visible = state.loading) {
            CircularProgressIndicator()
        }

        ContactsList(
            modifier = Modifier.fillMaxSize(),
            contacts = state.contacts
        )
    }
}