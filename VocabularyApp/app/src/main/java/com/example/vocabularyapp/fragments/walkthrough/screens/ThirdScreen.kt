package com.example.vocabularyapp.fragments.walkthrough.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.vocabularyapp.R
import com.example.vocabularyapp.databinding.FragmentThirdScreenBinding

class ThirdScreen : Fragment() {

    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val next = binding.next
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        next.setOnClickListener {
            viewPager?.currentItem = 3
        }

        return view
    }
}