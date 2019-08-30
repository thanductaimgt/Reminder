package zalo.taitd.reminder

import android.app.Application
import androidx.room.Room

class ReminderApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, ReminderDatabase::class.java, "Reminder_Database").build()
    }

    companion object{
        lateinit var database:ReminderDatabase
    }
}