package com.example.kuwait.ViewModel

import android.text.TextUtils
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
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class IbanValidatorViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val postStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val _postStateFlow: StateFlow<ApiState> = postStateFlow


    fun isValidateIbanNumber(iban: String): Boolean {
        var res = true
        val ibanRegex = "^([A-Z]{2}[ '+'\\'+'-]?[0-9]{2})(?=(?:[ '+'\\'+'-]?[A-Z0-9]){9,30}$)((?:[ '+'\\'+'-]?[A-Z0-9]{3,5}){2,7})([ '+'\\'+'-]?[A-Z0-9]{1,3})?$"
        val MyPattern: Pattern = Pattern.compile(ibanRegex)
        if (iban.isEmpty()){
            return false
        }else {
            val MyMatcher: Matcher = MyPattern.matcher(iban)
            if (!MyMatcher.matches()) {
                res = false
            }
        }
        return res
    }

    fun validateIban(iban: String) = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
        mainRepository.validateIban(iban)
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect { data ->
                postStateFlow.value = ApiState.SuccessIBAN(data)
            }
    }

}