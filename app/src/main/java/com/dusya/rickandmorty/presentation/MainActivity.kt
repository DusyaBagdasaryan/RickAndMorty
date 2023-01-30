package com.dusya.rickandmorty.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dusya.rickandmorty.R
import com.dusya.rickandmorty.databinding.ActivityMainBinding

/**
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, RickAndMortyFragment.newInstance(), RickAndMortyFragment.TAG)
            .commit()
    }
}