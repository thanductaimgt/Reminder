package zalo.taitd.reminder

import androidx.room.*

@Dao
interface DownloadTaskDao {
    @Query("SELECT * FROM Remind")
    fun getAllReminds(): List<Remind>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemind(remind: Remind)

    @Update
    fun updateRemind(remind: Remind)

    @Query("DELETE FROM Remind where id = :remindId")
    fun deleteRemind(remindId: Int)

    @Query("select * from Remind where id = :remindId")
    fun getRemind(remindId: Int):Remind
}