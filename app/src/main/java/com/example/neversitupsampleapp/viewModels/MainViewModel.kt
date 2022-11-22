package com.example.neversitupsampleapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neversitupsampleapp.data.CurrentPriceDbData
import com.example.neversitupsampleapp.db.AppDatabase
import com.example.neversitupsampleapp.others.Resource
import com.example.neversitupsampleapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val db: AppDatabase
) : ViewModel() {

    private val _res = MutableLiveData<Resource<List<CurrentPriceDbData>>>()

    val res: LiveData<Resource<List<CurrentPriceDbData>>>
        get() = _res

    init {
        getCurrentPrices()
    }

    private fun getCurrentPrices() = viewModelScope.launch {
        while (currentCoroutineContext().isActive) {
            _res.postValue(Resource.loading(null))
            mainRepository.getCurrentPrice().let {
                if (it.isSuccessful) {
                    it.body().let { data ->
                        val dbData = CurrentPriceDbData(
                            time = data?.time?.updated,
                            usdPrice = data?.bpi?.USD?.rate,
                            gbpPrice = data?.bpi?.GBP?.rate,
                            eurPrice = data?.bpi?.EUR?.rate
                        )
                        viewModelScope.launch(Dispatchers.IO) {
                            db.priceDao().insert(dbData)
                            _res.postValue(Resource.success(db.priceDao().getAll()))
                        }
                    }

                } else {
                    _res.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
            delay(1000 * 60)    // called for every 1 minute
        }
    }

}