package com.example.core.common.injection.qualifier

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class NoAuthOkHttpClient
