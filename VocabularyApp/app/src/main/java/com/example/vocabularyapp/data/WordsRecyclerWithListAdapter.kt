package com.example.vocabularyapp.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularyapp.databinding.WordItemBinding
import com.example.vocabularyapp.model.Word
import com.example.vocabularyapp.ui.MainActivity
import com.example.vocabularyapp.words.WordsFragmentDirections
import kotlin.collections.ArrayList

class WordsRecyclerWithListAdapter(
        private val context: Context?) :
        RecyclerView.Adapter<WordsRecyclerWithListAdapter.WordsViewHolder>(), Filterable {

    private var dataSet = ArrayList<Word>()
    private var filteredDataSet = ArrayList<Word>()
    var clickListener: View.OnClickListener? = null
    private var languageSetting : Int = 0


    inner class WordsViewHolder(
            itemView: View,
            val wordOne: TextView,
            val wordTwo: TextView,
            val wordDesc: TextView
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener(clickListener)

        }
//
//        val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
//        val languageOne = sharedPrefs?.getString("languageOne", "language one")
//        addWordBinding.languageOne.text = languageOne
//        val languageTwo = sharedPrefs?.getString("languageTwo","language two")
//        addWordBinding.languageTwo.text = languageTwo
        fun bindDataSet(word: Word) {
            wordOne.text = word.wordLanguageOne
            wordTwo.text = word.wordLanguageTwo
            wordDesc.text = word.wordDescription


        }

    }
    override fun getItemCount(): Int = filteredDataSet.size



    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): WordsViewHolder {
        val wordItemBinding = WordItemBinding.inflate(LayoutInflater.from(context), parent, false)
        if(context is MainActivity){

            val sharedPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val languageOne = sharedPrefs?.getString("languageOne", "language one")
            wordItemBinding.textViewLanguageOne.text = languageOne + ":"
            val languageTwo = sharedPrefs?.getString("languageTwo","language two")
            wordItemBinding.textViewLanguageTwo.text = languageTwo + ":"

        }
            return WordsViewHolder(
                    wordItemBinding.wordCard,
                    wordItemBinding.wordTextViewLanguageOne,
                    wordItemBinding.wordTextViewLanguageTwo,
                    wordItemBinding.wordDescTextView
            )
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
    holder.bindDataSet(filteredDataSet[position])

    holder.itemView.setOnClickListener {
        val action = WordsFragmentDirections.actionNavigationWordsToUpdateFragment(filteredDataSet[position])
        holder.itemView.findNavController().navigate(action)
    }
    }

    fun setData(dataSet: ArrayList<Word>) {
      this.dataSet = dataSet
      this.filteredDataSet = dataSet as ArrayList<Word>
      this.notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var resultList = ArrayList<Word>()
           var filterResult = FilterResults()
                if(constraint.toString().isEmpty()) {
                    resultList = dataSet
                }
                else {
                    var searchChr = constraint.toString().toLowerCase()
                    for (word in dataSet) {
                        if (word.wordDescription.contains(searchChr) || word.wordLanguageOne.contains(searchChr) || word.wordLanguageTwo.contains(searchChr)) {
                            resultList.add(word)
                        }

                    }


                }

                filterResult.values = resultList
                filterResult.count - resultList.size
                return filterResult

            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                filteredDataSet = filterResults!!.values as ArrayList<Word>
                notifyDataSetChanged()
            }

        }
    }


}