package `in`.kumudan.notes.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.kumudan.notes.model.NoteDatabase
import `in`.kumudan.notes.model.NoteDatabaseDAO
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase:NoteDatabase):NoteDatabaseDAO
    =noteDatabase.noteDao()

    @Singleton
    @Provides
    fun ProvideAppDatabase(@ApplicationContext context:Context):NoteDatabase
    = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "note_db")
        .fallbackToDestructiveMigration()
        .build()
}