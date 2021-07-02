package com.example.vocabularyapp.fragments.walkthrough.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.vocabularyapp.R
import com.example.vocabularyapp.databinding.FragmentViewPagerBinding
import com.example.vocabularyapp.fragments.walkthrough.screens.FirstScreen
import com.example.vocabularyapp.fragments.walkthrough.screens.FourthScreen
import com.example.vocabularyapp.fragments.walkthrough.screens.SecondScreen
import com.example.vocabularyapp.fragments.walkthrough.screens.ThirdScreen
import com.example.vocabularyapp.fragments.walkthrough.viewpager.ViewPagerAdapter
import com.example.vocabularyapp.ui.ToggleState
import com.google.android.material.bottomnavigation.BottomNavigationView


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
            FourthScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter


        return view
    }


//
//    override fun onDestroyView() {
//        val parent = requireActivity() as ToggleState
//        parent.setNavigationDrawerState(true)
//
//        // Display the BottomNavigationView
//        val bnv: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)
//        bnv.isVisible = true
//
//        super.onDestroyView()
//    }
}