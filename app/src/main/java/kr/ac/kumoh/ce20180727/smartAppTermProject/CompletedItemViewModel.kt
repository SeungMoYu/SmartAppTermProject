package kr.ac.kumoh.ce20180727.smartAppTermProject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CompletedItemViewModel() : ViewModel() {
    private val SERVER_URL = "https://port-0-s23w10backend-5yc2g32mlomir1x0.sel5.cloudtype.app/"
    private val completedItemApi: CompletedItemApi
    private val _completedItemList = MutableLiveData<List<CompletedItem>>()
    val completedItemList: LiveData<List<CompletedItem>>
        get() = _completedItemList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        completedItemApi = retrofit.create(CompletedItemApi::class.java)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = completedItemApi.getCompletedItems()
                _completedItemList.value = response
            } catch (e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }
}