package com.sunasterisk.moviedb_51.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.databinding.ActivityMainBinding
import com.sunasterisk.moviedb_51.ui.container.ContainerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.mainFrameLayout, ContainerFragment.newInstance())
            .commit()
    }
}
