package `in`.kumudan.notes.Respository

import android.view.KeyEvent.DispatcherState
import `in`.kumudan.notes.model.Note
import `in`.kumudan.notes.model.NoteDatabaseDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRespository @Inject constructor(private val noteDatabaseDAO:NoteDatabaseDAO){
    suspend fun addNote(note:Note)=noteDatabaseDAO.insert(note)
    suspend fun updateNote(note: Note)=noteDatabaseDAO.update(note)
    suspend fun deleteNote(note: Note)=noteDatabaseDAO.deleteNote(note)
    suspend fun deleteAllNotes()=noteDatabaseDAO.deleteAll()
    suspend fun getNoteById(id:String):List<Note> =noteDatabaseDAO.getNoteById(id)
    fun getAllNotes(): Flow<List<Note>> =noteDatabaseDAO
        .getNotes()
        .flowOn(Dispatchers.IO)
        .conflate()


}