package com.example.sneakersapp.features.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.sneakersapp.MyApplication
import com.example.sneakersapp.R
import com.example.sneakersapp.core.util.ActionListnerCallback
import com.example.sneakersapp.databinding.ActivityMainBinding
import com.example.sneakersapp.features.model.Sneaker
import com.example.sneakersapp.features.viewmodel.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private var homeSelected = false
    private var cartSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        fragmentManager = supportFragmentManager
        binding.llHome.isSelected = true
        addReplaceFragment(HomeFragment(), false, false)
    }

    override fun onResume() {
        super.onResume()
        binding.llHome.setOnClickListener {
            selectHomeButton(it)
        }
        binding.llCart.setOnClickListener {
            selectCartButton(it)
        }
    }


    fun selectHomeButton(view: View) {
        /*binding.llHome.isSelected = !homeSelected
        homeSelected = !homeSelected
        binding.llCart.isSelected = false
        cartSelected = false
        addReplaceFragment(HomeFragment(), false, false)*/
        val fragment = fragmentManager.findFragmentById(R.id.fragment_container)
        if (!view.isSelected) {
            view.isSelected = true
            binding.llCart.isSelected = false
            addReplaceFragment(HomeFragment(), false, false)
        } else if(fragment is SneakerDetailsFragment || fragment is CartFragment) {
            addReplaceFragment(HomeFragment(), false, false)
        }
    }

    fun selectCartButton(view: View) {
        /*binding.llCart.isSelected = !cartSelected
        cartSelected = !cartSelected
        binding.llHome.isSelected = false
        homeSelected = false
        addReplaceFragment(CartFragment(), false, false)*/
        val fragment = fragmentManager.findFragmentById(R.id.fragment_container)
        if (!view.isSelected) {
            view.isSelected = true
            binding.llHome.isSelected = false
            addReplaceFragment(CartFragment(), false, false)
        } else if(fragment is SneakerDetailsFragment || fragment is HomeFragment) {
            addReplaceFragment(CartFragment(), false, false)
        }
    }

    private fun addReplaceFragment(
        fragment: Fragment,
        addFragment: Boolean,
        addToBackStack: Boolean
    ) {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        }
        val transaction = fragmentManager.beginTransaction()
        if (addFragment) {
            transaction.add(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
        } else {
            transaction.replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
        }
        if (addToBackStack) {
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commit()
    }

    /*override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment != null && fragment is CartFragment) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }*/
}