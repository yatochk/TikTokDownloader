package com.downloaderfor.tiktok.dagger.module

import com.downloaderfor.tiktok.dagger.App
import com.downloaderfor.tiktok.model.Model
import com.downloaderfor.tiktok.model.ModelImpl
import com.downloaderfor.tiktok.model.db.StorageApi
import com.downloaderfor.tiktok.model.db.StorageRepository
import com.downloaderfor.tiktok.model.download.ServerTasks
import com.downloaderfor.tiktok.model.download.TikTokApi
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