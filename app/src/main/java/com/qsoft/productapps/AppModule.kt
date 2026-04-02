package com.qsoft.productapps

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.qsoft.feed_data.worker.FavoriteClearScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AppModule : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var favoriteClearScheduler: FavoriteClearScheduler

    override fun onCreate() {
        super.onCreate()
        favoriteClearScheduler.schedule()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}