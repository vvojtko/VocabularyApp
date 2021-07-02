package com.example.vocabularyapp.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.temporal.ChronoUnit

@Parcelize
@Entity(tableName = "words")
data class Word(
        @PrimaryKey(autoGenerate = true)
       // @NonNull
        var id: Int,
        var wordLanguageOne: String,
        var wordLanguageTwo: String,
        var wordDescription: String,
):Parcelable