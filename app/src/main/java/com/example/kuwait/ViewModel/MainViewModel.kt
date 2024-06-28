package com.example.kuwait.ViewModel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kuwait.Repository.MainRepository
import com.example.kuwait.Util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val postStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val _postStateFlow: StateFlow<ApiState> = postStateFlow
    val currencyList: MutableLiveData<List<String>> = MutableLiveData()
    val _currencyList: LiveData<List<String>> = currencyList

    var sourceCurrency: MutableLiveData<String> = MutableLiveData("KWD")
    var targetCurrency: MutableLiveData<String> = MutableLiveData("")
    var amount: MutableLiveData<String> = MutableLiveData("")

    init {
        getCurrency()
    }

    fun getCurrency() = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
        mainRepository.getCurrency()
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect { data ->
                postStateFlow.value = ApiState.Success(data)
                currencyList.value = data.rates.keys.toList()
                Log.v("sfsfa", "fsf")
            }
    }

    fun isValidForm(target: String?, amount: String?): Boolean {
        // validate
        if (TextUtils.isEmpty(target)) {
            return false
        }
        if (TextUtils.isEmpty(amount)) {
            return false
        }
        return true
    }

    fun postData(target: String, source: String, am: String) = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
        mainRepository.getPost(target, source, am)
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect { data ->
                postStateFlow.value = ApiState.SuccessPost(data)
            }
    }

}