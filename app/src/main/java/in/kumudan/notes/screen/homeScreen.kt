package `in`.kumudan.notes.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.kumudan.notes.Navigations.Data.NoteScreens
import `in`.kumudan.notes.R
import `in`.kumudan.notes.component.notesGrid
import `in`.kumudan.notes.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homeScreen(navController: NavController?,notesViewModel: NotesViewModel) {
    val notesList = remember{
        mutableListOf<Note>()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
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

                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController?.navigate(route = NoteScreens.NoteScreen.name+"/New")},
                shape = RoundedCornerShape(6.dp),
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add New Notes")
            }
        }) {
        Surface(modifier = Modifier.padding(it)) {
            noteListLayout(notesViewModel =notesViewModel , navController = navController)
        }

    }
}

@Composable
fun noteListLayout(notesViewModel: NotesViewModel,navController: NavController?){
    val context= LocalContext.current

    val noteList=notesViewModel.notesList.collectAsState().value
    if(noteList.isEmpty()){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,) {
            Text(text = " Write Some Notes to Add",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.surfaceTint,
                textAlign = TextAlign.Center)
        }
    }
    else{
        Column{
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
            ){
                items(noteList){note->
                    notesGrid(note = note,
                        onItemClick = {                            id->
                            Log.d("TAG", "noteList: $id")
                            navController?.navigate(route = NoteScreens.NoteScreen.name+"/$id")
                        },
                        onLongPress = {currentNote->
                            notesViewModel.removeNote(currentNote)
                            Toast.makeText(context,"Notes Deleted",Toast.LENGTH_SHORT).show()
                        })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun homeScreenPreview() {
    homeScreen(null, viewModel<NotesViewModel>())
}