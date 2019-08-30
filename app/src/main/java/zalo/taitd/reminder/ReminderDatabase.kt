package zalo.taitd.reminder

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Remind::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): DownloadTaskDao
}