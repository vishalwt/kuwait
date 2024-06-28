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
import com.example.kuwait.ViewModel.MainViewModel
import com.example.kuwait.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
     private val mainViewModel:MainViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenStarted {
            mainViewModel._postStateFlow.collect {it->
                when(it){
                    is ApiState.Loading->{
                        binding.parent.isVisible=false
                        binding.progressBar.isVisible=true
                    }
                    is ApiState.Failure -> {
                        binding.parent.isVisible = false
                        binding.progressBar.isVisible = false
                        toasMsg(it.msg.toString())
                        binding.convertedValue.text = ""

                    }
                    is ApiState.Success->{
                        binding.parent.isVisible = true
                        binding.progressBar.isVisible = false
                    }
                    is ApiState.SuccessPost->{
                        binding.parent.isVisible = true
                        binding.progressBar.isVisible = false
                        binding.convertedValue.text = "${mainViewModel.sourceCurrency.value} ${mainViewModel.amount.value} = ${mainViewModel.targetCurrency.value} ${it.data.result} "
                    }
                    is ApiState.Empty->{

                    }

                    else -> {}
                }
            }
        }



        mainViewModel._currencyList.observe(this, Observer {
            it?.let {
                targetDataSet(it)
            }
        })

        binding.convertButton.setOnClickListener {
            mainViewModel.amount.value = binding.inputAmount.text.toString()
            if (mainViewModel.isValidForm(mainViewModel.targetCurrency.value,mainViewModel.amount.value)){
                mainViewModel.postData(mainViewModel.targetCurrency.value!!,mainViewModel.sourceCurrency.value!!,mainViewModel.amount.value!!)
            }else{
                binding.convertedValue.text = ""
                Toast.makeText(this,"Please fill all data",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun targetDataSet(it:List<String>){
        val aa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, it)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTarget.setAdapter(aa)
        binding.spinnerTarget.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position1: Int,
                id: Long
            ) {
                if (it.size>position1) {
                    mainViewModel.targetCurrency.value = it.get(position1)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // todo for nothing selected
            }
        })
    }

    fun toasMsg(msg:String){
        Toast.makeText(this,"$msg, Please Retry",Toast.LENGTH_SHORT).show()
    }

}