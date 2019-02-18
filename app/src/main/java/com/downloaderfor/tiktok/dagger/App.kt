package com.downloaderfor.tiktok.dagger

import android.app.Application
import com.downloaderfor.tiktok.R
import com.downloaderfor.tiktok.dagger.component.AppComponent
import com.downloaderfor.tiktok.dagger.component.DaggerAppComponent
import com.downloaderfor.tiktok.dagger.module.AppModule
import com.downloaderfor.tiktok.dagger.module.ModelModule
import com.downloaderfor.tiktok.dagger.module.PresenterModule
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class App : Application() {
    companion object {
        var adCount = 0
        var isDownloaded = false
            set(value) {
                if (value) {
                    component.galleryPresenter.update()
                    component.mainPresenter.downloadComplete()
                }
                field = value
            }
        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        val config = YandexMetricaConfig.newConfigBuilder(getString(R.string.appmetrica_key)).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)

        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .modelModule(ModelModule())
            .presenterModule(PresenterModule())
            .build()
    }
}