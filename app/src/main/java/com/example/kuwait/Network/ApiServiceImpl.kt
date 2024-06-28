package com.example.kuwait.Network

import com.example.kuwait.Model.ConvertedResponse
import com.example.kuwait.Model.Currencies
import com.example.kuwait.Model.IBANResponse
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getCurrency(): Currencies = apiService.getCurrency()

    suspend fun getPost(target:String, source:String, am:String): ConvertedResponse = apiService.getPost(target,source,am)

    suspend fun validateIban( iban :String): IBANResponse = apiService.validateIban(iban)

}