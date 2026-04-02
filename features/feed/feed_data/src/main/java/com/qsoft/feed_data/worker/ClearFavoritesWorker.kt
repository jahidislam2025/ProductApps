package com.qsoft.feed_data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.qsoft.feed_data.dataSource.local.FeedLocalDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
@HiltWorker
class ClearFavoritesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val feedLocalDataSource: FeedLocalDataSource
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            feedLocalDataSource.clearAllFavorites()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
    companion object {
        const val WORK_NAME = "ClearFavoritesWorker"


    }
}