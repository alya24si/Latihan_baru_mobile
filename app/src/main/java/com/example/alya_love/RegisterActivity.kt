package com.example.alya_love

import android.app.DatePickerDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import android.widget.Toast
import com.example.alya_love.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE)

        setupDropdownAgama()
        setupDatePicker()
        setupRegisterButton()
        setupLinkToLogin()
    }

    private fun setupDropdownAgama() {
        val agamaList = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, agamaList)
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteAgama)
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun setupDatePicker() {
        binding.etTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val dateString = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.etTanggalLahir.setText(dateString)
            }, year, month, day).show()
        }
    }

    private fun setupRegisterButton() {
        binding.btnRegister.setOnClickListener {
            val isValid = validateForm()
            if (isValid) {
                saveToSharedPreferences()
                Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupLinkToLogin() {
        binding.tvKeLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateForm(): Boolean {
        binding.tvError.visibility = View.GONE
        binding.tvError.text = ""

        val nama = binding.etNama.text.toString().trim()
        val tanggalLahir = binding.etTanggalLahir.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        val selectedGenderId = binding.rgJenisKelamin.checkedRadioButtonId
        val genderRadioButton = findViewById<RadioButton>(selectedGenderId)
        val jenisKelamin = genderRadioButton?.text.toString()

        val agama = (findViewById<AutoCompleteTextView>(R.id.autoCompleteAgama)).text.toString().trim()

        when {
            nama.isEmpty() -> {
                showError("Nama tidak boleh kosong!")
                return false
            }
            tanggalLahir.isEmpty() -> {
                showError("Tanggal Lahir harus diisi!")
                return false
            }
            selectedGenderId == -1 -> {
                showError("Jenis Kelamin harus dipilih!")
                return false
            }
            agama.isEmpty() -> {
                showError("Agama harus dipilih!")
                return false
            }
            username.isEmpty() -> {
                showError("Username tidak boleh kosong!")
                return false
            }
            password.isEmpty() -> {
                showError("Password tidak boleh kosong!")
                return false
            }
            confirmPassword.isEmpty() -> {
                showError("Confirm Password tidak boleh kosong!")
                return false
            }
            password != confirmPassword -> {
                showError("Password dan Confirm Password tidak sama!")
                return false
            }
        }

        return true
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    private fun saveToSharedPreferences() {
        val nama = binding.etNama.text.toString().trim()
        val tanggalLahir = binding.etTanggalLahir.text.toString().trim()

        val selectedGenderId = binding.rgJenisKelamin.checkedRadioButtonId
        val genderRadioButton = findViewById<RadioButton>(selectedGenderId)
        val jenisKelamin = genderRadioButton?.text.toString()

        val agama = (findViewById<AutoCompleteTextView>(R.id.autoCompleteAgama)).text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        with(sharedPreferences.edit()) {
            putString("nama", nama)
            putString("tanggalLahir", tanggalLahir)
            putString("jenisKelamin", jenisKelamin)
            putString("agama", agama)
            putString("username", username)
            putString("password", password)
            apply()
        }
    }
}