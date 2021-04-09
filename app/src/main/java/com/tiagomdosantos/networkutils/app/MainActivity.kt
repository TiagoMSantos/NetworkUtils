package com.tiagomdosantos.networkutils.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tiagomdosantos.networkutils.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupUi()
    }

    // region --- SETUP ---
    private fun setupBinding() {
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .also { it.lifecycleOwner = this } as ActivityMainBinding
    }

    private fun setupUi() {
        supportActionBar?.hide()
    }
    // endregion
}