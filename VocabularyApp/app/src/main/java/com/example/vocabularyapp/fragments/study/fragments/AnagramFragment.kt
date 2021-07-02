package com.example.vocabularyapp.fragments.study.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vocabularyapp.R
import com.example.vocabularyapp.databinding.FragmentAnagramBinding
import com.example.vocabularyapp.fragments.add.WordViewModel
import com.example.vocabularyapp.model.Word
import com.example.vocabularyapp.ui.ToggleState
import com.google.android.material.bottomnavigation.BottomNavigationView

class AnagramFragment : Fragment() {


    private val wordsViewModel: WordViewModel by viewModels()
    private lateinit var binding: FragmentAnagramBinding
    private lateinit var word: Word

    private var questionNumber: Int = 0
    private var correctAnswers: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        handleBackButton()
        binding = FragmentAnagramBinding.inflate(inflater, container, false)

        val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val languageSetting = sharedPrefs?.getInt("languageConfiguration", 1)

        if(languageSetting == 1) {
            getWordForLanguageOne()
            checkAnswerForLanguageOne()
        }else{
            getWordForLanguageTwo()
            checkAnswerForLanguageTwo()
        }

        binding.button9.setOnClickListener {
            if(languageSetting == 1){
                getWordForLanguageOne()
            }else{
                getWordForLanguageTwo()
            }
            binding.answer.setBackgroundColor(ContextCompat.getColor(binding.answer.context, R.color.button_color))
            binding.score.text = "$correctAnswers/$questionNumber"
        }

        return binding.root
    }
    override fun onDestroyView() {
        val parent = requireActivity() as ToggleState
        parent.setNavigationDrawerState(true)

        // Display the BottomNavigationView
        val bnv: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)
        bnv.isVisible = true

        super.onDestroyView()
    }
    
    private fun handleBackButton() {
        // When back button is pressed we will navigate up the fragment
        // hierarchy. navigateUp will pop the fragment back stack automatically.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
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
    private fun getWordForLanguageOne(){
        word = wordsViewModel.getOneWord()
        val charArray = word.wordLanguageOne.toCharArray()
        charArray.shuffle()
        binding.textView4.text = charArray.concatToString()
    }

    private fun getWordForLanguageTwo(){
        word = wordsViewModel.getOneWord()
        val charArray = word.wordLanguageTwo.toCharArray()
        charArray.shuffle()
        binding.textView4.text = charArray.concatToString()
    }

    private fun checkAnswerForLanguageOne() {
        binding.answer.setOnClickListener {
            if(binding.userAnswer.text.toString().equals(word.wordLanguageOne, ignoreCase = true)){
                binding.answer.setBackgroundColor(ContextCompat.getColor(binding.answer.context, R.color.green))
                questionNumber += 1
                correctAnswers += 1
            }else{
                binding.answer.setBackgroundColor(ContextCompat.getColor(binding.answer.context, R.color.red))
                questionNumber += 1
            }
        }
    }
    private fun checkAnswerForLanguageTwo() {
        binding.answer.setOnClickListener {
            if(binding.userAnswer.text.toString().equals(word.wordLanguageTwo, ignoreCase = true)){
                binding.answer.setBackgroundColor(ContextCompat.getColor(binding.answer.context, R.color.green))
                questionNumber += 1
                correctAnswers += 1
            }else{
                binding.answer.setBackgroundColor(ContextCompat.getColor(binding.answer.context, R.color.red))
                questionNumber += 1
            }
        }
    }
}