package com.androidgang.findmyphone.database

import androidx.room.*
import com.androidgang.findmyphone.models.Metrics


@Dao
interface MetricsDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMetrics(metrics: Metrics)

    @Transaction
    @Query("SELECT * FROM metrics")
    suspend fun getMetrics() : List<Metrics>

    @Transaction
    @Query("DELETE FROM metrics")
    suspend fun deleteAllMetrics()




}