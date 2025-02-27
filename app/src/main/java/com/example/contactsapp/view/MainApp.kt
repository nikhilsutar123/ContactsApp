package com.example.contactsapp.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.*
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contactsapp.Manifest
import com.example.contactsapp.R
import com.example.contactsapp.model.ContactViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun MainApp() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { AppBar() }) { padding ->


        val showMainContent = remember {
            mutableStateOf(false)
        }
        val contactPermissionResultLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
            ) { isGranted ->
                showMainContent.value = isGranted
            }

        LaunchedEffect(key1 = Unit) {
            if (!showMainContent.value) {
                contactPermissionResultLauncher.launch(android.Manifest.permission.READ_CONTACTS)
            } else {
                showMainContent.value = true
            }
        }

        if (showMainContent.value) {
            PermissionGrantedScreen(modifier = Modifier.padding(padding))
        }

        val showRationale = remember {
            mutableStateOf(false)
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    CenterAlignedTopAppBar(title = { Text(stringResource(id = R.string.app_name)) })
}

@Composable
fun PermissionGrantedScreen(
    modifier: Modifier
) {
    val viewModel: ContactViewModel = hiltViewModel()
    val state by
    viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = LocalLifecycleOwner.current)
    HomeScreen(state = state, modifier = modifier)
}