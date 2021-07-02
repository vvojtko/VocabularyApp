package com.example.vocabularyapp.fragments.configuration

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vocabularyapp.R
import com.example.vocabularyapp.databinding.FragmentConfigurationBinding
import com.example.vocabularyapp.fragments.add.WordViewModel
import com.example.vocabularyapp.ui.ToggleState
import com.google.android.material.bottomnavigation.BottomNavigationView

class ConfigurationFragment : Fragment() {

    //  private lateinit var wordsFragmentBinding: FragmentWordsBinding
    private lateinit var configurationFragmentBinding: FragmentConfigurationBinding
    lateinit var sharedPreferences: SharedPreferences
    private val wordViewModel: WordViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        configurationFragmentBinding = FragmentConfigurationBinding.inflate(inflater, container, false)



        configurationFragmentBinding.button.setOnClickListener{
            val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

            val configurationNum = sharedPrefs?.getInt("configurationAfterFirstRun", 0)
            if(configurationNum == 1){
                handleBackButton()
            }
            if(configurationNum == 1){
                val builder = AlertDialog.Builder(requireContext())
                builder.setPositiveButton("Yes") { _, _ ->
                    val languageOne = configurationFragmentBinding.languageOneSetting.text.toString()
                    val languageTwo = configurationFragmentBinding.languageTwoSetting.text.toString()
                    val editor = sharedPrefs.edit()
                    editor?.putString("languageOne", languageOne)?.apply()
                    editor?.putString("languageTwo", languageTwo)?.apply()
                    wordViewModel.deleteAllWords()
                    Toast.makeText(requireContext(), "Successfully changed the configuration!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.navigation_home)
                }
                builder.setNegativeButton("No") { _, _ ->

                }
                builder.setTitle("Delete all words?")
                builder.setMessage("Are you sure you want to change languages and delete all words?")
                builder.create().show()
            }else{
                val languageOne = configurationFragmentBinding.languageOneSetting.text.toString()
                val languageTwo = configurationFragmentBinding.languageTwoSetting.text.toString()
                val editor = sharedPrefs?.edit()
                editor?.putString("languageOne", languageOne)?.apply()
                editor?.putString("languageTwo", languageTwo)?.apply()
                editor?.putInt("configurationAfterFirstRun", 1)?.apply()
                findNavController().navigate(R.id.navigation_home)
            }

            // Use the Kotlin extension in the fragment-ktx artifact

        }

        return configurationFragmentBinding.root
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

    override fun onDestroyView() {
        val parent = requireActivity() as ToggleState
        parent.setNavigationDrawerState(true)

        // Display the BottomNavigationView
        val bnv: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)
        bnv.isVisible = true

        super.onDestroyView()
    }

    }


