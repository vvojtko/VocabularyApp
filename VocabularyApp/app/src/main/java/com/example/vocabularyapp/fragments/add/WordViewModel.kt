package com.example.vocabularyapp.fragments.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vocabularyapp.repository.VocabularyRepository
import com.example.vocabularyapp.model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application): AndroidViewModel(application) {
    private val repository: VocabularyRepository = VocabularyRepository(application)
    var wordsList: LiveData<List<Word>> = repository.getAllWords()

    fun insertWord(word: Word){
        repository.insert(word)
    }

    fun updateWord(word: Word){
        repository.updateWord(word)
    }
    fun delete(word: Word){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWord(word)
        }
    }

    fun deleteAllWords(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllWords()
        }
    }

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


}