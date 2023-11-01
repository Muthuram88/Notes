package `in`.kumudan.notes.Navigations.Data

import java.lang.IllegalArgumentException

enum class NoteScreens {
    HomeScreen,
    NoteScreen;
    companion object{
    fun fromRoute(route:String?):NoteScreens
                =when(route?.substringBefore("/")){
                    HomeScreen.name->HomeScreen
                    NoteScreen.name->NoteScreen
                    null->HomeScreen
                    else->throw IllegalArgumentException("Route $route is not in record")
                }
    }
}