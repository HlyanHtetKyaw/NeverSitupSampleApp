package com.example.neversitupsampleapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neversitupsampleapp.databinding.ActivityMainBinding
import com.example.neversitupsampleapp.others.Status
import com.example.neversitupsampleapp.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        observeViewModel()
        binding.btnConvert.setOnClickListener {
            startActivity(
                Intent(
                    this, ConverterActivity::class.java
                )
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = MainAdapter()
        binding.rvCurrency.layoutManager = LinearLayoutManager(this)
        binding.rvCurrency.adapter = adapter
    }

    private fun observeViewModel() {
        mainViewModel.res.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    binding.rvCurrency.visibility = View.VISIBLE
                    it.data.let { res ->
                        runOnUiThread { adapter.submitList(res!!) }
                    }
                }

                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.rvCurrency.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    binding.rvCurrency.visibility = View.VISIBLE
                    Snackbar.make(
                        binding.rootView,
                        "Something went wrong", Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
