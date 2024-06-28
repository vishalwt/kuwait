package com.example.kuwait.Repository

import com.example.kuwait.Model.ConvertedResponse
import com.example.kuwait.Model.Currencies
import com.example.kuwait.Model.IBANResponse
import com.example.kuwait.Network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiServiceImpl: ApiServiceImpl) {

    fun getCurrency():Flow<Currencies> = flow {
        emit(apiServiceImpl.getCurrency())
    }.flowOn(Dispatchers.IO)

    fun getPost(target:String, source:String, am:String):Flow<ConvertedResponse> = flow {
        emit(apiServiceImpl.getPost(target,source,am))
    }.flowOn(Dispatchers.IO)

    fun validateIban(iban:String):Flow<IBANResponse> = flow {
        emit(apiServiceImpl.validateIban(iban))
    }.flowOn(Dispatchers.IO)
}