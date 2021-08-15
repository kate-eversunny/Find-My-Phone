package com.androidgang.findmyphone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androidgang.findmyphone.models.Metrics


@Database(
    entities = [
        Metrics::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MetricsDB : RoomDatabase() {
    abstract val metricsDao: MetricsDao

    companion object {
        @Volatile
        private var INSTANCE: MetricsDB? = null

        fun getInstance(context: Context): MetricsDB {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MetricsDB::class.java,
                    "mertrics.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }

    }



}