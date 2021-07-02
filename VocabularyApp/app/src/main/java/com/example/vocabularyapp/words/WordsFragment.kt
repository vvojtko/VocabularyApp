package com.example.vocabularyapp.words

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vocabularyapp.R
import com.example.vocabularyapp.databinding.FragmentWordsBinding
import com.example.vocabularyapp.model.Word
import com.example.vocabularyapp.data.WordsRecyclerWithListAdapter
import com.example.vocabularyapp.fragments.add.WordViewModel

private const val PROXIMITY_KEY = "proximity"

private const val GRID_COLUMN_COUNT = 1

class WordsFragment : Fragment() {

    private lateinit var wordsRecyclerAdapter: WordsRecyclerWithListAdapter
    private lateinit var wordsFragmentBinding: FragmentWordsBinding
  //  private val wordsViewModel: WordsViewModel by viewModels()
    private val wordViewModel: WordViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wordsFragmentBinding = FragmentWordsBinding.inflate(inflater, container, false)

       addData()

        wordsFragmentBinding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(filterString: String?):Boolean {
                return true
            }
            override fun onQueryTextChange(filterString: String?): Boolean {
                wordsRecyclerAdapter.filter.filter(filterString)


                return false

            }
        })

        wordsFragmentBinding.floatingActionButton2.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_words_to_navigation_addWordFragment)
        }

        setHasOptionsMenu(true)

        addWordsRecyclerView()
        return wordsFragmentBinding.root
    }


    private fun addWordsRecyclerView() {
       val listWords = wordsFragmentBinding.wordList
        listWords.hasFixedSize()
        val gridLayoutManager = GridLayoutManager(context, 1)
        listWords.layoutManager = gridLayoutManager

        wordsRecyclerAdapter = WordsRecyclerWithListAdapter(context)
        listWords.adapter = wordsRecyclerAdapter

    }


    private fun addData() {
        val wordModelList = wordViewModel.getWords()
        wordModelList.observe(viewLifecycleOwner) { words ->
            wordsRecyclerAdapter.setData(words as ArrayList<Word>) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.delete_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteAllUsers()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            wordViewModel.deleteAllWords()
            Toast.makeText(requireContext(), "Successfully removed all words!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete all words?")
        builder.setMessage("Are you sure you want to delete all words")
        builder.create().show()
    }
}

