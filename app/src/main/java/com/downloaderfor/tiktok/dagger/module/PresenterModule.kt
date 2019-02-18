package com.downloaderfor.tiktok.dagger.module

import com.downloaderfor.tiktok.dagger.App
import com.downloaderfor.tiktok.presenter.DownloadPresenter
import com.downloaderfor.tiktok.presenter.GalleryPresenter
import com.downloaderfor.tiktok.presenter.MainPresenter
import com.downloaderfor.tiktok.presenter.PreviewPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideMainPresenter(): MainPresenter = MainPresenter(App.component.model)

    @Provides
    @Singleton
    fun provideGalleryPresenter(): GalleryPresenter = GalleryPresenter(App.component.model)

    @Provides
    @Singleton
    fun provideDownloadPresenter(): DownloadPresenter = DownloadPresenter(App.component.model)

    @Provides
    @Singleton
    fun providePreviewPresenter(): PreviewPresenter = PreviewPresenter(App.component.model)
}