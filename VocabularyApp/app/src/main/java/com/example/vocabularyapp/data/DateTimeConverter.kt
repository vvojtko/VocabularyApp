/**
 * A Room converter object for converting time in seconds since
 * Jan 1970 to LocalDateTime obejcts and vice versa. Room will
 * use this automatically when a conversion is needed.
 * @author Chris Loftus
 * @version 1
 */
package com.example.vocabularyapp.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object DateTimeConverter {

    @TypeConverter
    @JvmStatic
    fun toLocalDate(timestamp: Long): LocalDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(timestamp),
        TimeZone.getDefault().toZoneId()
    )

    @TypeConverter
    @JvmStatic
    fun toTimestamp(locaDateTime: LocalDateTime): Long =
        locaDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
