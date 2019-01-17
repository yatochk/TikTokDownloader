package com.yatochk.tiktokdownloader.dagger

import android.app.Application
import com.yatochk.tiktokdownloader.dagger.component.AppComponent
import com.yatochk.tiktokdownloader.dagger.component.DaggerAppComponent
import com.yatochk.tiktokdownloader.dagger.module.AppModule
import com.yatochk.tiktokdownloader.dagger.module.ModelModule
import com.yatochk.tiktokdownloader.dagger.module.PresenterModule

class App : Application() {
    companion object {
        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .modelModule(ModelModule())
            .presenterModule(PresenterModule())
            .build()
    }
}