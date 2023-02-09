package com.samiun.mycricket.network.overview
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.samiun.mycricket.data.CricketDatabase
import com.samiun.mycricket.data.CricketRepository
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.network.CricketApi
import com.samiun.mycricket.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class CricketViewModel(application: Application): AndroidViewModel(application){

    private val _countries = MutableLiveData<List<Data>>()
    private val countries: LiveData<List<Data>> = _countries
    private val repository: CricketRepository
    init{
        val cricketDao = CricketDatabase.getDatabase(application).cricketDao()
        repository = CricketRepository(cricketDao)
    }

    fun getCountries(){
        viewModelScope.launch {
            try {
               // Log.d("Overview Fragment", "getCountries: ")
                _countries.value = CricketApi.retrofitService.getCountries(Constants.apikey).data
                countries.value?.let { Log.d("Api", "getCountries: ${it.get(0).name}") }

                countries.value?.let { addCountryList(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.d("Over View Model","$e")
            }
        }
    }

    private fun addCountryList(countryList: List<Data>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCountry(countryList)
        }
    }
}