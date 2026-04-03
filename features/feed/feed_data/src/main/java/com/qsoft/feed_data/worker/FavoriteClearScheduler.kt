package com.qsoft.feed_data.worker
import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface FavoriteClearScheduler {
    fun schedule()
    fun cancel()
}

class FavoriteClearSchedulerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FavoriteClearScheduler {

    override fun schedule() {
        val request = OneTimeWorkRequestBuilder<ClearFavoritesWorker>()
            //.setInitialDelay(1, TimeUnit.HOURS)// 3 ঘণ্টা পর clear হবে
            .setInitialDelay(3, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            ClearFavoritesWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    override fun cancel() {
        WorkManager.getInstance(context).cancelUniqueWork(ClearFavoritesWorker.WORK_NAME)
    }
}
