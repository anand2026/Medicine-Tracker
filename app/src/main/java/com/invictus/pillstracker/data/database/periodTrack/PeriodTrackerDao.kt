package com.invictus.pillstracker.data.database.periodTrack

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PeriodTrackerDao {

    @Query("SELECT * FROM period_tracker ORDER BY startDate ASC")
    fun getPeriodList(): Flow<List<PeriodTrackModel>>

    @Query("SELECT * FROM period_tracker ORDER BY startDate ASC")
    fun getPeriodListNormal(): List<PeriodTrackModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeriodItem(diaryItemData: PeriodTrackModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeriodItemList(diaryItemData: List<PeriodTrackModel>)

    @Delete
    suspend fun deletePeriod(item: PeriodTrackModel)

    @Query("DELETE FROM period_tracker")
    suspend fun deleteAll()
}