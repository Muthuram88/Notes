package `in`.kumudan.notes.Navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import `in`.kumudan.notes.Navigations.Data.NoteScreens
import `in`.kumudan.notes.screen.NotesViewModel
import `in`.kumudan.notes.screen.homeScreen
import `in`.kumudan.notes.screen.noteScreen

@Composable
fun NavigationHosts(notesViewModel: NotesViewModel) {
    val notesNavController = rememberNavController()
    NavHost(
        navController = notesNavController,
        startDestination = NoteScreens.HomeScreen.name
    ) {
        composable(NoteScreens.HomeScreen.name) {
            homeScreen(notesNavController, notesViewModel)
        }
        composable(NoteScreens.NoteScreen.name + "/{Mode}",
            arguments = listOf(navArgument(name = "Mode") { type = NavType.StringType })
        ) { backStackEntry ->
            noteScreen(
                navController = notesNavController,
                notesViewModel,
                backStackEntry.arguments?.getString("Mode")
            )

        }
    }
}