package com.example.vocabularyapp.fragments.add

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.vocabularyapp.R
import com.example.vocabularyapp.model.Word
import com.example.vocabularyapp.databinding.FragmentAddWordBinding
import com.example.vocabularyapp.ui.ToggleState
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddWordFragment : Fragment(){
    private lateinit var addWordBinding: FragmentAddWordBinding
    private val wordViewModel: WordViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        handleBackButton()
        addWordBinding = FragmentAddWordBinding.inflate(inflater, container, false)
        val addButton = addWordBinding.addButton
        val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val languageOne = sharedPrefs?.getString("languageOne", "language one")
        addWordBinding.languageOne.text = languageOne
        val languageTwo = sharedPrefs?.getString("languageTwo","language two")
        addWordBinding.languageTwo.text = languageTwo
        addButton.setOnClickListener {
            Toast.makeText(requireContext(), "this", Toast.LENGTH_SHORT).show()
            insertWord()
        }
        handleBackButton()
        return addWordBinding.root
    }
    private fun handleBackButton() {
        // When back button is pressed we will navigate up the fragment
        // hierarchy. navigateUp will pop the fragment back stack automatically.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.navigation_words)
                }
            }
        // We add the callback to those that are called when the back button is pressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        // Start by switching off the toggle button in the parent
        // activity so that it doesn't cause navigation drawer
        // to open
        val parent = requireActivity() as ToggleState
        parent.setNavigationDrawerState(false)
    }


    private fun insertWord() {

        if(addWordBinding.addWordLanguageOne.text.isNotEmpty() || addWordBinding.addWordLanguageTwo.text.isNotEmpty()) {
            val word = Word(
                    0,
                    addWordBinding.addWordLanguageOne.text.toString(),
                    addWordBinding.addWordLanguageTwo.text.toString(),
                    addWordBinding.addWordDescription.text.toString()
            )

            wordViewModel.insertWord(word)
            findNavController().navigate(R.id.navigation_words)
        }else{
            Toast.makeText(requireContext(),"Please fill in the words fields", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onDestroyView() {
        val parent = requireActivity() as ToggleState
        parent.setNavigationDrawerState(true)

        // Display the BottomNavigationView
        val bnv: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)
        bnv.isVisible = true

        super.onDestroyView()
    }
}

