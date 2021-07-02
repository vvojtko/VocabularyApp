package com.example.vocabularyapp.fragments.walkthrough.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.vocabularyapp.R
import com.example.vocabularyapp.databinding.FragmentFourthScreenBinding

class FourthScreen : Fragment() {
    private lateinit var binding: FragmentFourthScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentFourthScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        val finish = binding.finish
        finish.setOnClickListener {
            viewPager?.currentItem = 4
            findNavController().navigate(
                R.id.action_viewPagerFragment_to_configurationFragment
            )
            onBoardingFinished()
        }

        return view
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()

    }
}