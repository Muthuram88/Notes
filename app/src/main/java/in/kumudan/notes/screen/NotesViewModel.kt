package `in`.kumudan.notes.screen

import android.util.Log
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kumudan.notes.Respository.NoteRespository
import `in`.kumudan.notes.data.NoteDataSource
import `in`.kumudan.notes.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository:NoteRespository) :ViewModel() {

    //var notesList= mutableStateListOf<Note>()
    private val _notesList= MutableStateFlow<List<Note>>(emptyList())
    val notesList=_notesList.asStateFlow()

    init {
        viewModelScope.launch ( Dispatchers.IO ){
            repository.getAllNotes().distinctUntilChanged()
                .collect{listOfNotes->
                    if(listOfNotes.isNullOrEmpty()){
                        Log.d("TAG", "Empty ")
                        _notesList.value=listOfNotes
                    }
                    else{
                        _notesList.value=listOfNotes
                    }

                }
        }
    }
    fun addNote(note:Note)=viewModelScope.launch{repository.addNote(note)}
    fun updateNote(note: Note)=viewModelScope.launch { repository.updateNote(note) }
    fun removeNote(note:Note)=viewModelScope.launch { repository.deleteNote(note) }
    suspend fun deleteAll()=viewModelScope.launch { repository.deleteAllNotes() }
    fun getNoteById(id:String): Job =viewModelScope.launch { repository.getNoteById(id) }

}