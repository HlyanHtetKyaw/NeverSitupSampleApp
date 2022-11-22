package com.example.neversitupsampleapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.neversitupsampleapp.R
import com.example.neversitupsampleapp.databinding.ActivityConverterBinding
import com.example.neversitupsampleapp.others.Status
import com.example.neversitupsampleapp.viewModels.ConverterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToLong

@AndroidEntryPoint
class ConverterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var currencies = arrayOf("USD", "GBP", "EUR")
    private lateinit var binding: ActivityConverterBinding
    private var selectedCurrency: String? = "USD"
    private var amount: Int? = 0
    private val converterViewModel: ConverterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()
        binding.edtAmount.afterTextChanged {
            amount = if (it.isBlank()) {
                0
            } else {
                it.toInt()
            }
            selectedCurrency?.let { selected ->
                converterViewModel.convertToBTC(amount!!, selected)
            }
        }
        observeViewModel()
    }

    private fun setupSpinner() {
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(binding.spinner)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@ConverterActivity
            prompt = resources.getString(R.string.select_currency)
            gravity = Gravity.CENTER
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        binding.edtAmount.hint = "Enter ${currencies[position]} amount"
        selectedCurrency = currencies[position]
        amount?.let {
            converterViewModel.convertToBTC(it, selectedCurrency!!)
        }
    }


    private fun showToast(
        context: Context = applicationContext,
        message: String,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(context, message, duration).show()
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        converterViewModel.res.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    binding.tvResult.visibility = View.VISIBLE
                    it.data.let { res ->
                        binding.tvResult.text =
                            "$amount $selectedCurrency is equivalent to ${
                                (res!! * 100000.0).roundToLong() / 100000.0
                            } in BTC"
                    }
                }

                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.tvResult.visibility = View.GONE
                }

                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    binding.tvResult.visibility = View.VISIBLE
                    Snackbar.make(
                        binding.rootView,
                        "Something went wrong",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}