package com.yatochk.tiktokdownloader.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {
    @Provides
    @Singleton
    internal fun provideContext(): Context = appContext
}