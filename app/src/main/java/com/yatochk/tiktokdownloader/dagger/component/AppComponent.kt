package com.yatochk.tiktokdownloader.dagger.component

import android.content.Context
import com.yatochk.tiktokdownloader.dagger.module.AppModule
import com.yatochk.tiktokdownloader.dagger.module.ModelModule
import com.yatochk.tiktokdownloader.dagger.module.PresenterModule
import com.yatochk.tiktokdownloader.model.Model
import com.yatochk.tiktokdownloader.presenter.DownloadPresenter
import com.yatochk.tiktokdownloader.presenter.GalleryPresenter
import com.yatochk.tiktokdownloader.presenter.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ModelModule::class, PresenterModule::class, AppModule::class])
interface AppComponent {
    val mainPresenter: MainPresenter
    val galleryPresenter: GalleryPresenter
    val downloadPresenter: DownloadPresenter
    val model: Model
    val context: Context
}