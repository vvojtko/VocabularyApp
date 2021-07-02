package com.example.vocabularyapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vocabularyapp.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(version = 1, entities = [Word::class])

abstract class VocabularyAppRoomDatabase : RoomDatabase(){

    abstract fun wordDao(): WordDao


    companion object {
        private var instance: VocabularyAppRoomDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.Main)

        fun getDatabase(context: Context): VocabularyAppRoomDatabase? {
            synchronized(this) {
                if (instance == null) {
                    instance =
                        Room.databaseBuilder<VocabularyAppRoomDatabase>(
                            context.applicationContext,
                            VocabularyAppRoomDatabase::class.java,
                            "vocabulary_database"
                        )
                            .allowMainThreadQueries()
                            .addCallback(roomDatabaseCallback(context))
                            //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build()
                }
                return instance!!
            }
        }

        private fun roomDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    coroutineScope.launch(Dispatchers.IO) {
                        populatedDatabase(context, getDatabase(context)!!)
                    }
                }
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("migrate", "Doing a migrate from version 1 to 2")
// This is where we make relevant database data changes,
// or copy data from old table to a new table.
// Deals with the migration from version 1 to version 2
            }
        }

        private fun populatedDatabase(context: Context, instance: VocabularyAppRoomDatabase) {
            val word = Word(
                0,
                "english",
                "angielski",
                "english to angielski"
            )
            val word2 = Word(
                0,
                "english1",
                "angielski1",
                "english to angielski2"
            )
            val wordList = mutableListOf(
                word,
                word2
            )
            val dao = instance.wordDao()
            dao.insertMultipleWords(wordList)
        }
    }
}
