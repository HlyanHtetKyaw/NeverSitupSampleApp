package com.example.neversitupsampleapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neversitupsampleapp.others.Resource
import com.example.neversitupsampleapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _res = MutableLiveData<Resource<Float>>()

    val res: LiveData<Resource<Float>>
        get() = _res


    fun convertToBTC(amount: Int, currency: String) {
        viewModelScope.launch {
            _res.postValue(Resource.loading(null))
            mainRepository.getCurrentPrice().let {
                if (it.isSuccessful) {
                    it.body().let { data ->
                        data?.bpi.let { bpi ->
                            var result: Float? = 0f
                            when (currency) {
                                "USD" -> {
                                    result = amount / bpi?.USD?.rate_float?.toFloat()!!
                                }
                                "GBP" -> {
                                    result = amount / bpi?.GBP?.rate_float?.toFloat()!!
                                }
                                "EUR" -> {
                                    result = amount / bpi?.EUR?.rate_float?.toFloat()!!
                                }
                            }
                            _res.postValue(Resource.success(result))
                        }
                    }

                } else {
                    _res.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        }
    }

}