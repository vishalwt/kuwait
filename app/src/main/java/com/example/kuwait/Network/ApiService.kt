package com.example.kuwait.Network

import com.example.kuwait.Model.ConvertedResponse
import com.example.kuwait.Model.Currencies
import com.example.kuwait.Model.IBANResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("fixer/latest")
    suspend fun getCurrency(): Currencies

    @GET("fixer/convert")
    suspend fun getPost(@Query("to") to :String, @Query("from") from :String, @Query("amount") amount :String): ConvertedResponse

    @GET("bank_data/iban_validate")
    suspend fun validateIban(@Query("iban_number") iban_number :String): IBANResponse
}