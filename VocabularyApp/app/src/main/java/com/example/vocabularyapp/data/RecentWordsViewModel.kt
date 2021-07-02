package com.example.vocabularyapp.data//package com.example.vocabularyapp.datasource.util
//
//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import com.example.vocabularyapp.datasource.VocabularyRepository
//import org.threeten.bp.LocalDateTime
//
//const val NUM_DAYS_RECENT:Long = 30
//
//
//class RecentWordsViewModel(application: Application):
//    AndroidViewModel(application) {
//        private val repository: VocabularyRepository = VocabularyRepository(application)
//    var recentWords: LiveData<List<Word>> = loadRecentWords()
//    private set
//
//    private fun loadRecentWords(): LiveData<List<Word>> {
//        val endDate = LocalDateTime.now().plusDays(365)
//        val pastDate = LocalDateTime.now().minusDays(NUM_DAYS_RECENT)
//
//        val wordsList : LiveData<List<Word>> = repository.getAllWords()
//        Log.i("V_APP", "Before forEach")
//        wordsList.value?.forEach{
//            Log.i("V_APP", it.wordLanguageOne)
//        }
//
//        return repository.getRecentWords(pastDate, endDate)
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//    }
//}