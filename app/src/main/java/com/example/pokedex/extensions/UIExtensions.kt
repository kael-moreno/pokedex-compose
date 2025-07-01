package com.example.pokedex.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import com.example.pokedex.di.ImageLoaderEntryPoint
import dagger.hilt.android.EntryPointAccessors

@Composable
fun getImageLoader(): ImageLoader {
    val entryPoint = EntryPointAccessors.fromApplication(
        LocalContext.current.applicationContext,
        ImageLoaderEntryPoint::class.java
    )
    return entryPoint.imageLoader()
}