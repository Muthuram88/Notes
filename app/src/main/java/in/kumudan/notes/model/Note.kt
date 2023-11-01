package `in`.kumudan.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.Instant
import java.util.Date
import java.util.UUID

@Entity(tableName = "Note_table")
data class Note(
    @PrimaryKey
    val id:UUID=UUID.randomUUID(),
    @ColumnInfo(name = "Note_title")
    var title:String,
    @ColumnInfo(name="Note_description")
    var notes:String,
    @ColumnInfo(name="Note_entry_date")
    var entryDate: Date = Date.from(Instant.now())
    )

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

class UUIDConverter{
    @TypeConverter
    fun fromUUID(uuid: UUID):String?{
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string:String):UUID?{
        return UUID.fromString(string)
    }
}