package com.example.vocabularyapp.data

import android.content.Context

object Injection {
    fun getDatabase(context: Context) =
            VocabularyAppRoomDatabase.getDatabase(context)!!
}