package com.invictus.pillstracker.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.invictus.pillstracker.data.database.periodTrack.PeriodTrackModel
import com.invictus.pillstracker.data.database.periodTrack.PeriodTrackerDao
import splitties.init.appCtx

@Database(entities = [PeriodTrackModel::class], version = 1, exportSchema = false)
abstract class PillsTrackerDatabase : RoomDatabase() {

    abstract fun periodTrackerDao(): PeriodTrackerDao

    companion object {

        private var INSTANCE: PillsTrackerDatabase? = null

        fun getInstance(): PillsTrackerDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(appCtx, PillsTrackerDatabase::class.java, "pillstracker")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }

    }
}

