package com.fabiocati.aedo.persistence.internal.db

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

internal class AppTypeConverters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromDuration(value: Duration?): Long? = value?.inWholeNanoseconds

    @TypeConverter
    fun toDuration(value: Long?): Duration? = value?.nanoseconds
}
