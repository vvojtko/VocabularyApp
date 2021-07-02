package com.example.vocabularyapp.fragments.update

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vocabularyapp.R
import com.example.vocabularyapp.databinding.FragmentUpdateBinding
import com.example.vocabularyapp.fragments.add.WordViewModel
import com.example.vocabularyapp.model.Word
import com.example.vocabularyapp.ui.ToggleState
import com.google.android.material.bottomnavigation.BottomNavigationView

class UpdateFragment : Fragment() {

    private lateinit var binding : FragmentUpdateBinding
    private lateinit var wordViewModel: WordViewModel
    private val args by navArgs<UpdateFragmentArgs>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        handleBackButton()
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val languageOne = sharedPrefs?.getString("languageOne", "language one")
        binding.languageOne.text = languageOne
        val languageTwo = sharedPrefs?.getString("languageTwo","language two")
        binding.languageTwo.text = languageTwo

        binding.addWordLanguageOne.setText(args.currentWord.wordLanguageOne)
        binding.addWordLanguageTwo.setText(args.currentWord.wordLanguageTwo)
        binding.addWordDescription.setText(args.currentWord.wordDescription)


        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        binding.addButton.setOnClickListener {
            updateItem()
        }
        setHasOptionsMenu(true)


        return binding.root
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

    override fun onDestroyView() {
        val parent = requireActivity() as ToggleState
        parent.setNavigationDrawerState(true)

        // Display the BottomNavigationView
        val bnv: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)
        bnv.isVisible = true

        super.onDestroyView()
    }

    private fun updateItem() {
        val languageOne = binding.addWordLanguageOne.text.toString()
        val languageTwo = binding.addWordLanguageTwo.text.toString()
        val description = binding.addWordDescription.text.toString()

        if (binding.addWordLanguageOne.text.isNotEmpty() || binding.addWordLanguageTwo.text.isNotEmpty()) {
            // Create word object
            val updatedWord = Word(args.currentWord.id, languageOne, languageTwo, description)
            // Update current word
            wordViewModel.updateWord(updatedWord)
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_navigation_words)

        }else{

            Toast.makeText(context, "Please fill out word's fields", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            wordViewModel.delete(args.currentWord)
            Toast.makeText(requireContext(), "Successfully removed: ${args.currentWord.wordLanguageOne}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_navigation_words)
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete ${args.currentWord.wordLanguageOne}?")
        builder.setMessage("Are you sure you want to delete ${args.currentWord.wordLanguageOne}")
        builder.create().show()
    }
}