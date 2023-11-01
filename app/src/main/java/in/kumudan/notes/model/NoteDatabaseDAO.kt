package `in`.kumudan.notes.model

import androidx.compose.runtime.MutableState
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDAO {
    @Query("SELECT * FROM Note_table")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE_TABLE WHERE ID=:id")
    suspend fun getNoteById(id:String):List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note:Note)

    @Query("DELETE FROM Note_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note:Note)
}
