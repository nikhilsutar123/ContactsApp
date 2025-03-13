package com.example.contactsapp.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.collection.emptyLongSet
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.*
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contactsapp.R
import com.example.contactsapp.model.ContactViewModel
import com.example.contactsapp.util.ContactPermissionTextProvider
import com.example.contactsapp.util.PermissionDialog
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun MainApp() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { AppBar() }) { padding ->


        val showMainContent = remember {
            mutableStateOf(false)
        }
        val showRationale = remember {
            mutableStateOf(false)
        }
        val isPermanentlyDeclined = remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        val contactPermissionResultLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
            ) { isGranted ->
                if (isGranted)
                    showMainContent.value = isGranted
                else
                    showRationale.value = true
                isPermanentlyDeclined.value =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        android.Manifest.permission.READ_CONTACTS
                    )
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

        if (showRationale.value) {
            PermissionDialog(
                permission = ContactPermissionTextProvider(),
                isPermanentlyDeclined = isPermanentlyDeclined.value,
                onDismiss = { showRationale.value = false },
                onOkClick = {
                    showRationale.value = false
                    contactPermissionResultLauncher.launch(android.Manifest.permission.READ_CONTACTS)
                },
                onGotoAppSettingsCLick = {
                    context.startActivity(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                    )
                },
                modifier = Modifier
            )
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