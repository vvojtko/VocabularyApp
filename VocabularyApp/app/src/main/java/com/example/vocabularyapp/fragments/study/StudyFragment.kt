package com.example.vocabularyapp.fragments.study

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vocabularyapp.R
import com.example.vocabularyapp.databinding.FragmentStudyBinding
import com.example.vocabularyapp.fragments.add.WordViewModel

class StudyFragment : Fragment() {

    private lateinit var binding: FragmentStudyBinding
    private var languageSetting : Int = 0
    private val wordsViewModel: WordViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudyBinding.inflate(inflater,container, false)

       // var languageSetting : Int = 1
        val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val languageOne = sharedPrefs?.getString("languageOne", "language one")
        val languageTwo = sharedPrefs?.getString("languageTwo","language two")



        binding.languageOneButton.text = languageOne
        binding.languageTwoButton.text = languageTwo

        binding.languageOneButton.setOnClickListener {
            languageSetting = 1
            val editor = sharedPrefs?.edit()
            editor?.putInt("languageConfiguration", languageSetting)?.apply()
        }
        binding.languageTwoButton.setOnClickListener {
            languageSetting = 2
            val editor = sharedPrefs?.edit()
            editor?.putInt("languageConfiguration", languageSetting)?.apply()
        }

     //   editor?.putString("languageTwo", languageTwo)?.apply()
        binding.button2.setOnClickListener {
            val words = wordsViewModel.getSixWords()
            words.observe(viewLifecycleOwner) { words ->
                if (words.size < 6) {
                    Toast.makeText(
                        requireContext(),
                        "Not enough words in your vocabulary to play the Pick The Correct Word. You need 6 words in your vocabulary",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    findNavController().navigate(R.id.action_navigation_study_to_pickTheCorrectWordFragment)
                }
            }

        }
        binding.button3.setOnClickListener {
            val words = wordsViewModel.getSixWords()
            words.observe(viewLifecycleOwner) { words ->
                if (words.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Not enough words in your vocabulary to play the Input The Correct Word. You need 1 word in your vocabulary",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    findNavController().navigate(R.id.action_navigation_study_to_inputTheCorrectWordFragment)
                }
            }
        }
        binding.button4.setOnClickListener {
            val words = wordsViewModel.getSixWords()
            words.observe(viewLifecycleOwner) { words ->
                if (words.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Not enough words in your vocabulary to play the Anagram. You need 1 word in your vocabulary",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    findNavController().navigate(R.id.action_navigation_study_to_anagramFragment)
                }
            }
        }
        binding.button5.setOnClickListener {
            val words = wordsViewModel.getSixWords()
            words.observe(viewLifecycleOwner) { words ->
                if (words.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Not enough words in your vocabulary to play Guess the Word. You need 1 word in your vocabulary",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    findNavController().navigate(R.id.action_navigation_study_to_answerBasedOnDescriptionFragment)
                }
            }
        }

        return binding.root
    }


}