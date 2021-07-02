package com.example.vocabularyapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.vocabularyapp.model.Word

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSingleWord(word: Word)

    @Insert
    fun insertMultipleWords( wordList: List<Word>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("Delete FROM words")
    fun deleteAll()

    @Query("SELECT * FROM words ORDER BY RANDOM() LIMIT 1")
    fun getOneWord(): Word

    @Query("SELECT * FROM words")
    fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * FROM words ORDER BY RANDOM() LIMIT 6")
    fun getSixWords(): LiveData<List<Word>>


}