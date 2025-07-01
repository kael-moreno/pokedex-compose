package com.example.pokedex.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.ImageLoader
import com.example.pokedex.di.ImageLoaderEntryPoint
import com.example.pokedex.ui.theme.White
import dagger.hilt.android.EntryPointAccessors

@Composable
fun getImageLoader(): ImageLoader {
    val entryPoint = EntryPointAccessors.fromApplication(
        LocalContext.current.applicationContext,
        ImageLoaderEntryPoint::class.java
    )
    return entryPoint.imageLoader()
}

@Composable
fun addDefaultBack(navController: NavController? = null) {
    if (navController != null) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = White
            )
        }
    }
}