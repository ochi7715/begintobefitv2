package com.sucis400.BeginToBeFit.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sucis400.BeginToBeFit.databinding.FragmentNavBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sucis400.BeginToBeFit.R

class MainActivity : AppCompatActivity() {
    var binding: FragmentNavBinding? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNavBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        binding!!.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_dashboard -> replaceFragment(RecentWorkout("workout_data_monday"))
                R.id.navigation_notifications -> replaceFragment(SampleTwoFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = getSupportFragmentManager()
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutNav, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
