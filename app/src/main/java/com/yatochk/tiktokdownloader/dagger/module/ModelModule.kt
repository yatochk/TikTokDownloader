package com.yatochk.tiktokdownloader.dagger.module

import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.model.Model
import com.yatochk.tiktokdownloader.model.ModelImpl
import com.yatochk.tiktokdownloader.model.db.StorageApi
import com.yatochk.tiktokdownloader.model.db.StorageRepository
import com.yatochk.tiktokdownloader.model.download.ServerTasks
import com.yatochk.tiktokdownloader.model.download.TikTokApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideModel(): Model = ModelImpl(App.component.context, App.component.tikTokApi, App.component.storageApi)

    @Provides
    @Singleton
    fun provideServerApi(): TikTokApi = ServerTasks()

    @Provides
    @Singleton
    fun provideStorageApi(): StorageApi = StorageRepository()
}