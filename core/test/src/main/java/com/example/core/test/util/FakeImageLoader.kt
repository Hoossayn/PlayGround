package com.example.core.test.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.test.core.app.ApplicationProvider
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.test.FakeImageLoaderEngine

@OptIn(ExperimentalCoilApi::class)
val engine = FakeImageLoaderEngine.Builder()
    .intercept({ it is String && it.endsWith(".jpg") }, ColorDrawable(Color.RED))
    .intercept({ it is String && it.endsWith(".png") }, ColorDrawable(Color.GRAY))
    .default(ColorDrawable(Color.BLUE))
    .build()

@OptIn(ExperimentalCoilApi::class)
val fakeSuccessImageLoader = ImageLoader.Builder(ApplicationProvider.getApplicationContext())
    .components { add(engine) }
    .build()

val fakeErrorImageLoader = ImageLoader.Builder(ApplicationProvider.getApplicationContext())
    .build()
