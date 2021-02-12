package com.jkapps.givemeagif.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jkapps.givemeagif.App
import com.jkapps.givemeagif.MyViewModelFactory
import com.jkapps.givemeagif.R
import com.jkapps.givemeagif.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        subscribeObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            viewModel.getNewGif()
        }
        binding.btnPrev.setOnClickListener {
            viewModel.getPrevious()
        }
    }

    private fun subscribeObservers() {
        viewModel.gif.observe(this) {
            binding.tvDescription.text = it.description
            Glide.with(this)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .load(it.gifUrl?.replace("http", "https"))
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.ivGif)
        }
        viewModel.isErrorDisplaying.observe(this) {
            showOrHideError(it)
        }
        viewModel.isLoading.observe(this) {
            binding.pbLoading.isVisible = it
        }
        viewModel.isButtonEnable.observe(this) {
            binding.btnPrev.isEnabled = it
        }
    }

    private fun showOrHideError(showError : Boolean) {
        if (showError) {
            binding.tvError.apply {
                isVisible = true
                    // to get the height value when an error is showing for the first time
                    this.doOnPreDraw {
                        animate()
                            .translationY(-height.toFloat())
                            .setDuration(0)
                            .start()
                        animate()
                            .translationY(0f)
                            .setDuration(300)
                            .start()
                    }
            }
        } else {
            binding.tvError.apply {
                animate()
                    .translationY(-height.toFloat())
                    .setDuration(300)
                    .withEndAction { isVisible = false }
                    .start()
            }
        }
    }

    private fun initViewModel() {
        val appContainer = (application as App).appContainer
        val factory = MyViewModelFactory(appContainer.repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }
}