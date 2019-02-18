package com.downloaderfor.tiktok.dagger.component

import android.content.Context
import com.downloaderfor.tiktok.dagger.module.AppModule
import com.downloaderfor.tiktok.dagger.module.ModelModule
import com.downloaderfor.tiktok.dagger.module.PresenterModule
import com.downloaderfor.tiktok.model.Model
import com.downloaderfor.tiktok.model.db.StorageApi
import com.downloaderfor.tiktok.model.download.TikTokApi
import com.downloaderfor.tiktok.presenter.DownloadPresenter
import com.downloaderfor.tiktok.presenter.GalleryPresenter
import com.downloaderfor.tiktok.presenter.MainPresenter
import com.downloaderfor.tiktok.presenter.PreviewPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ModelModule::class, PresenterModule::class, AppModule::class])
interface AppComponent {
    val mainPresenter: MainPresenter
    val galleryPresenter: GalleryPresenter
    val downloadPresenter: DownloadPresenter
    val previewPresenter: PreviewPresenter
    val model: Model
    val context: Context
    val tikTokApi: TikTokApi
    val storageApi: StorageApi
}