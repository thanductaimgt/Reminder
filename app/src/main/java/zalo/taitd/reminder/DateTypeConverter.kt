package zalo.taitd.reminder

import androidx.room.TypeConverter
import java.util.Date

class DateTypeConverter {
    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }

    @TypeConverter
    fun toLong(date: Date): Long {
        return date.time
    }
}