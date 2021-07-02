package com.example.vocabularyapp.repository

import android.app.Application
import com.example.vocabularyapp.data.Injection
import com.example.vocabularyapp.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VocabularyRepository(application: Application) {
    private val wordDao = Injection.getDatabase(application).wordDao()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insert(word: Word){
        coroutineScope.launch(Dispatchers.IO) {
            wordDao.insertSingleWord(word)
        }
    }


    fun deleteWord(word: Word){
        wordDao.deleteWord(word)
    }

    fun deleteAllWords(){
        wordDao.deleteAll()
    }
    fun getOneWord() = wordDao.getOneWord()

    fun getAllWords() = wordDao.getAllWords()

    fun getSixWords() = wordDao.getSixWords()

    fun updateWord(word: Word) = wordDao.updateWord(word)
//
//
//    fun getRecentWords(startDate: LocalDateTime, endDate: LocalDateTime) =
//            wordDao.getWordsAdmittedBetweenDates(startDate, endDate)
}