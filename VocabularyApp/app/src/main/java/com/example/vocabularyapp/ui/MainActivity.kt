package com.example.vocabularyapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vocabularyapp.R
import com.example.vocabularyapp.databinding.ActivityMainBinding
import com.example.vocabularyapp.databinding.WordItemBinding

class MainActivity : AppCompatActivity(), ToggleState {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawer: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//
//       val fragmentThirdScreen = ThirdScreen()
//       supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_container, fragmentThirdScreen).commit()
        val sharedPrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val configurationNum = sharedPrefs?.getInt("configurationAfterFirstRun", 0)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.nav_open_drawer,
            R.string.nav_close_drawer

        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

//
//        toggle.toolbarNavigationClickListener = View.OnClickListener {
//            onBackPressed()
//
//            setNavigationDrawerState(true)
//        }

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_configuration -> findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_configuration)
                else -> Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show()
            }
            true
        }
        toggle.toolbarNavigationClickListener = View.OnClickListener {
            onBackPressed()
        }
        val navView = binding.bottomNavView
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_study, R.id.navigation_words
            )

        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
           // toggle.syncState()
            if(destination.id == R.id.splashFragment || destination.id == R.id.viewPagerFragment) {
                navView.visibility = View.GONE
               // setNavigationDrawerState(true)
                supportActionBar?.hide()
            }else {
                navView.visibility = View.VISIBLE
              //  setNavigationDrawerState(true)
                toggle.syncState()
                supportActionBar?.show()
                 }

        }
        if(configurationNum == 0){
            navController.addOnDestinationChangedListener{_, destination, _->
                if(destination.id == R.id.navigation_configuration){
                    navView.visibility = View.GONE
                    supportActionBar?.hide()
                }
            }
        }
//        else {
//            navController.addOnDestinationChangedListener { _, destination, _ ->
//                if (destination.id == R.id.navigation_configuration) {
//                    setNavigationDrawerState(false)
//                }
//            }
//        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_addWordFragment || destination.id == R.id.navigation_pickTheCorrectWordFragment || destination.id == R.id.navigation_configuration) {
                setNavigationDrawerState(false)
                navView.visibility = View.GONE
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
      //  setNavigationDrawerState(true)


    }


    override fun setNavigationDrawerState(isEnabled: Boolean){
        if (isEnabled){
            toggle.isDrawerIndicatorEnabled = true
        } else {
            toggle.isDrawerIndicatorEnabled = false
            // There is a night and day version on this under res
           toggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        toggle.syncState()
    }


}

