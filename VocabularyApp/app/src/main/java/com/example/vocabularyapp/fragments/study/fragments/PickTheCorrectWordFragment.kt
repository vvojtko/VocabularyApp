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
import com.example.vocabularyapp.databinding.FragmentPickTheCorrectWordBinding
import com.example.vocabularyapp.model.Word
import com.example.vocabularyapp.data.WordsRecyclerWithListAdapter
import com.example.vocabularyapp.fragments.add.WordViewModel
import com.example.vocabularyapp.ui.ToggleState
import com.google.android.material.bottomnavigation.BottomNavigationView

class PickTheCorrectWordFragment : Fragment() {

    private val wordsViewModel: WordViewModel by viewModels()
    private var questionNumber: Int = 0
    private var correctAnswers: Int = 0
    private lateinit var binding: FragmentPickTheCorrectWordBinding

    private var buttonClickedNumber: Int = 0
    private var answers = ArrayList<String>()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        handleBackButton()
        binding = FragmentPickTheCorrectWordBinding.inflate(inflater, container, false)
        val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val languageSetting = sharedPrefs?.getInt("languageConfiguration", 0)





        binding.score.text = "$correctAnswers/$questionNumber"
       // binding.score.text = languageSetting.toString()
        if(languageSetting == 1) {
            assignWordsForLanguageOne()
        }else{
            assignWordsForLanguageTwo()
        }
        binding.nextButton.setOnClickListener{
            if(languageSetting == 1) {
                assignWordsForLanguageOne()
            }else{
                assignWordsForLanguageTwo()
            }
            switchButtonsOn()
            answers.clear()
            binding.score.text = "$correctAnswers/$questionNumber"
        }
        return binding.root
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

    private fun assignWordsForLanguageOne(){
        val words = wordsViewModel.getSixWords()

        words.observe(viewLifecycleOwner) {
            words ->
            if(words.isNotEmpty()) {
                val random = (0..5).random()
                binding.button2.text = words[0].wordLanguageOne
                binding.button3.text = words[1].wordLanguageOne
                binding.button4.text = words[2].wordLanguageOne
                binding.button5.text = words[3].wordLanguageOne
                binding.button6.text = words[4].wordLanguageOne
                binding.button7.text = words[5].wordLanguageOne
                answers.add(words[0].wordLanguageTwo)
                answers.add(words[1].wordLanguageTwo)
                answers.add(words[2].wordLanguageTwo)
                answers.add(words[3].wordLanguageTwo)
                answers.add(words[4].wordLanguageTwo)
                answers.add(words[5].wordLanguageTwo)

                binding.textView.text = words[random].wordLanguageTwo
            }

            buttons()

        }
    }
    private fun assignWordsForLanguageTwo(){

        val words = wordsViewModel.getSixWords()

        words.observe(viewLifecycleOwner) {
            words ->
            if(words.isNotEmpty()) {
                val random = (0..5).random()
                binding.button2.text = words[0].wordLanguageTwo
                binding.button3.text = words[1].wordLanguageTwo
                binding.button4.text = words[2].wordLanguageTwo
                binding.button5.text = words[3].wordLanguageTwo
                binding.button6.text = words[4].wordLanguageTwo
                binding.button7.text = words[5].wordLanguageTwo
                answers.add(words[0].wordLanguageOne)
                answers.add(words[1].wordLanguageOne)
                answers.add(words[2].wordLanguageOne)
                answers.add(words[3].wordLanguageOne)
                answers.add(words[4].wordLanguageOne)
                answers.add(words[5].wordLanguageOne)

                binding.textView.text = words[random].wordLanguageOne
            }

            buttons()

        }
    }
    private fun switchButtonsOn(){
        binding.button2.isEnabled = true
        binding.button2.isClickable = true

        binding.button3.isEnabled = true
        binding.button3.isClickable = true

        binding.button4.isEnabled = true
        binding.button4.isClickable = true

        binding.button5.isEnabled = true
        binding.button5.isClickable = true

        binding.button6.isEnabled = true
        binding.button6.isClickable = true

        binding.button7.isEnabled = true
        binding.button7.isClickable = true


        binding.button2.setBackgroundColor(ContextCompat.getColor(binding.button2.context, R.color.button_color))

        binding.button3.setBackgroundColor(ContextCompat.getColor(binding.button3.context, R.color.button_color))

        binding.button4.setBackgroundColor(ContextCompat.getColor(binding.button4.context, R.color.button_color))

        binding.button5.setBackgroundColor(ContextCompat.getColor(binding.button5.context, R.color.button_color))

        binding.button6.setBackgroundColor(ContextCompat.getColor(binding.button6.context, R.color.button_color))

        binding.button7.setBackgroundColor(ContextCompat.getColor(binding.button7.context, R.color.button_color))
    }
    private fun switchButtonsOff(){
        binding.button2.isEnabled = false
        binding.button2.isClickable = false
        binding.button3.isEnabled = false
        binding.button3.isClickable = false
        binding.button4.isEnabled = false
        binding.button4.isClickable = false
        binding.button5.isEnabled = false
        binding.button5.isClickable = false
        binding.button6.isEnabled = false
        binding.button6.isClickable = false
        binding.button7.isEnabled = false
        binding.button7.isClickable = false
    }

    private fun buttons(){
        binding.button2.setOnClickListener{
            switchButtonsOff()
            if(answers[0] == binding.textView.text){
                questionNumber += 1
                correctAnswers += 1
                binding.button2.setBackgroundColor(ContextCompat.getColor(binding.button2.context, R.color.green))
            }else{

                questionNumber += 1
                binding.button2.setBackgroundColor(ContextCompat.getColor(binding.button2.context, R.color.red))

            }
        }
        binding.button3.setOnClickListener{
            switchButtonsOff()
            if(answers[1] == binding.textView.text) {

                questionNumber += 1
                correctAnswers += 1
                binding.button3.setBackgroundColor(ContextCompat.getColor(binding.button3.context, R.color.green))
            }else{

                questionNumber += 1
                binding.button3.setBackgroundColor(ContextCompat.getColor(binding.button3.context, R.color.red))
            }
            buttonClickedNumber = 3
        }
        binding.button4.setOnClickListener{
            switchButtonsOff()
            if(answers[2] == binding.textView.text) {

                questionNumber += 1
                correctAnswers += 1
                binding.button4.setBackgroundColor(ContextCompat.getColor(binding.button4.context, R.color.green))
            }else{

                binding.button4.setBackgroundColor(ContextCompat.getColor(binding.button4.context, R.color.red))
            }

        }
        binding.button5.setOnClickListener{
            switchButtonsOff()
            if(answers[3] == binding.textView.text) {

                questionNumber += 1
                correctAnswers += 1
                binding.button5.setBackgroundColor(ContextCompat.getColor(binding.button5.context, R.color.green))
            }else{

                questionNumber += 1
                binding.button5.setBackgroundColor(ContextCompat.getColor(binding.button5.context, R.color.red))
            }

        }
        binding.button6.setOnClickListener{
            switchButtonsOff()
            if(answers[4] == binding.textView.text) {

                questionNumber += 1
                correctAnswers += 1
                binding.button6.setBackgroundColor(ContextCompat.getColor(binding.button6.context, R.color.green))
            }else{

                questionNumber += 1
                binding.button6.setBackgroundColor(ContextCompat.getColor(binding.button6.context, R.color.red))
            }

        }
        binding.button7.setOnClickListener{
            switchButtonsOff()
            if(answers[5] == binding.textView.text) {

                questionNumber += 1
                correctAnswers += 1
                binding.button7.setBackgroundColor(ContextCompat.getColor(binding.button7.context, R.color.green))
            }else{

                questionNumber += 1
                binding.button7.setBackgroundColor(ContextCompat.getColor(binding.button7.context, R.color.red))
            }

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



