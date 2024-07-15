package com.invictus.pillstracker.data.database.periodTrack

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.errorprone.annotations.Keep
import java.util.UUID

@Keep
@Entity(tableName = "period_tracker")
data class PeriodTrackModel(
    @PrimaryKey
    val _id: String = UUID.randomUUID().toString(),
    val startDate: Long,
    val endDate: Long,
    val logTime: Long,
)