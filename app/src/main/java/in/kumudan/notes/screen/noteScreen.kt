package `in`.kumudan.notes.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.kumudan.notes.R
import `in`.kumudan.notes.component.InputTextBox
import `in`.kumudan.notes.data.NoteDataSource
import `in`.kumudan.notes.model.Note
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun noteScreen(navController: NavController?,notesViewModel: NotesViewModel,id:String?="New") {
    var note=listOf(Note(title="",notes=""))
    if(id!="New"){
        note=notesViewModel.notesList.collectAsState().value.filter{ note->
            note.id.toString()==id
        }
    }

    //note=notesViewModel.getNoteById(id).get(0)

    if(note.isEmpty()){
        note=listOf(Note(title="",notes=""))
    }

    var titleTextFieldText by remember {
        mutableStateOf(note[0].title)
    }
    var notesTextFieldText by remember {
        mutableStateOf(note[0].notes)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = stringResource(R.string.content_description_notification_icon),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.clickable {
                            navController?.popBackStack()
                        }
                    )
                }
            )
        }) {
        Surface(modifier = Modifier.padding(it)) {
            Column {
                InputTextBox(text=titleTextFieldText,
                    onTextChange ={ text->
                                  if(text.all{char->
                                         char.isLetter() || char.isWhitespace() || char.isDigit()
                                      })titleTextFieldText=text
                                  },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Enter the Title",
                    label = "Title",
                    fontWeight = FontWeight.Bold
                )

                InputTextBox(text=notesTextFieldText,
                    onTextChange ={ text->
                             if(text.all{char->
                                char.isLetter() || char.isWhitespace()|| char.isDigit()
                            })notesTextFieldText=text},
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    placeholder = "Enter the notes",
                    singleLine = false,
                    maxLine = 50
                    ,
                    label = "Notes")
                Button(onClick = {
                                 if(titleTextFieldText.isNotEmpty() && notesTextFieldText.isNotEmpty()){
                                    if(id!="New"){
                                        val note=Note(id= UUID.fromString(id),title=titleTextFieldText,notes=notesTextFieldText)
                                        notesViewModel.updateNote(note)
                                    }else{
                                        val note=Note(title=titleTextFieldText,notes=notesTextFieldText)
                                        notesViewModel.addNote(note)
                                    }

                                 }
                    navController?.popBackStack()
                },
                    modifier= Modifier.fillMaxWidth()
                    ) {
                    Text(text = "Save")
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun noteScreenPreview() {
    noteScreen(navController = null, viewModel<NotesViewModel>())
}