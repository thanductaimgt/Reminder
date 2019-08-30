package zalo.taitd.reminder

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Remind(
    @NonNull @PrimaryKey var id: Int,
    var time: Date = Date(),
    var description:String,
    var isEnabled: Boolean = true
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readSerializable() as Date,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeSerializable(time)
        parcel.writeString(description)
        parcel.writeByte(if (isEnabled) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Remind> {
        override fun createFromParcel(parcel: Parcel): Remind {
            return Remind(parcel)
        }

        override fun newArray(size: Int): Array<Remind?> {
            return arrayOfNulls(size)
        }
    }
}