package com.example.kuwait

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
 import com.example.kuwait.Util.ApiState
import com.example.kuwait.ViewModel.IbanValidatorViewModel
import com.example.kuwait.ViewModel.MainViewModel
import com.example.kuwait.databinding.ActivityMainBinding
import com.example.kuwait.databinding.IbanValidatorActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class IbanValidatorActivity : AppCompatActivity() {
    private lateinit var binding: IbanValidatorActivityBinding
     private val ibanValidatorViewModel: IbanValidatorViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= IbanValidatorActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenStarted {
            ibanValidatorViewModel._postStateFlow.collect {it->
                when(it){
                    is ApiState.Loading->{
                        binding.progressBar.isVisible=true
                        binding.validatedValue.text =""
                    }
                    is ApiState.Failure -> {

                        binding.progressBar.isVisible = false
                        binding.validatedValue.text =""
                        toasMsg(it.msg.toString())

                    }
                    is ApiState.SuccessIBAN->{

                        binding.progressBar.isVisible = false
                        val valueText = "Bank Name:-  ${it.data.bank_data.name}\n" +
                                "Account Number:-  ${it.data.iban_data.account_number}\n" +
                                "Bank Code:-  ${it.data.iban_data.bank_code}\n" +
                                "BBAN:-  ${it.data.iban_data.BBAN}\n" +
                                "Country:-  ${it.data.iban_data.country}\n" +
                                "Country Code:-  ${it.data.iban_data.country_code}"
                        binding.validatedValue.text =  valueText
                    }
                    is ApiState.Empty->{

                    }

                    else -> {}
                }
            }
        }


        binding.validateButton.setOnClickListener {
            val iban = binding.inputIban.text.toString()
            if (ibanValidatorViewModel.isValidateIbanNumber(iban)){
                ibanValidatorViewModel.validateIban(iban)
            }else{
                binding.validatedValue.text =""
                toasMsg("Please Enter Valid IBAN")
            }
        }
    }



    fun toasMsg(msg:String){
        Toast.makeText(this,"$msg, Please Retry",Toast.LENGTH_LONG).show()
    }

}