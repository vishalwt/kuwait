package com.example.kuwait.Util

import com.example.kuwait.Model.ConvertedResponse
import com.example.kuwait.Model.Currencies
import com.example.kuwait.Model.IBANResponse

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data: Currencies) : ApiState()
    class SuccessPost(val data: ConvertedResponse) : ApiState()
    class SuccessIBAN(val data: IBANResponse) : ApiState()
    object Empty : ApiState()
}
