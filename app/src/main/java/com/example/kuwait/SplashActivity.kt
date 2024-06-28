package com.example.kuwait

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kuwait.databinding.SplashActivityBinding

class SplashActivity  : AppCompatActivity() {
    private lateinit var binding: SplashActivityBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ibanToolButton.setOnClickListener {
            val intent = Intent(this,IbanValidatorActivity::class.java)
            startActivity(intent)
        }

        binding.currencyBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}