package com.gtsl.breakingbad.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gtsl.breakingbad.R
import com.gtsl.breakingbad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setSupportActionBar(toolbar)
            NavigationUI.setupWithNavController(toolbar, findNavController(R.id.fragment_placeholder))
        }
    }
}