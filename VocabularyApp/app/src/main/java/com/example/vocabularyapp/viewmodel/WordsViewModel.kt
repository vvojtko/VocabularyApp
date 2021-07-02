package com.example.vocabularyapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vocabularyapp.repository.VocabularyRepository
import com.example.vocabularyapp.model.Word

class WordsViewModel(application: Application): AndroidViewModel(application) {

    private val repository: VocabularyRepository = VocabularyRepository(application)
    var wordsList: LiveData<List<Word>> = repository.getAllWords()



    fun getWords(): LiveData<List<Word>> {
        wordsList = repository.getAllWords()
        return wordsList
    }

    fun getSixWords(): LiveData<List<Word>> {
        wordsList = repository.getSixWords()
        return wordsList
    }

    fun getOneWord(): Word {
        return repository.getOneWord()
    }


    override fun onCleared() {
        super.onCleared()
    }

}