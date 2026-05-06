package com.example.alya_love.pertemuan_3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.MainActivity
import com.example.alya_love.RegisterActivity
import com.example.alya_love.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("LOGIN", MODE_PRIVATE)

        if (sharedPref.getBoolean("isLogin", false)) {
            moveToMain()
        }

        binding.btnLogin.setOnClickListener {
            validateAndLogin()
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateAndLogin() {
        val username = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        binding.tvError.visibility = View.GONE
        binding.tvError.text = ""

        when {
            username.isEmpty() -> {
                showError("Username tidak boleh kosong!")
                return
            }
            password.isEmpty() -> {
                showError("Password tidak boleh kosong!")
                return
            }
        }

        // Rule 1: Username = Password
        if (username == password) {
            saveLoginState()
            moveToMain()
            return
        }

        // Rule 2: Cek dari SharedPreferences
        val savedUsername = sharedPref.getString("username", "")
        val savedPassword = sharedPref.getString("password", "")

        if (username == savedUsername && password == savedPassword) {
            saveLoginState()
            moveToMain()
        } else {
            showError("Username atau Password salah!")
        }
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    private fun saveLoginState() {
        val editor = sharedPref.edit()
        editor.putBoolean("isLogin", true)
        editor.apply()
    }

    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}