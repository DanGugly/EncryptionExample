package com.example.encryptionexample

import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.example.encryptionexample.databinding.ActivityMainBinding
import com.example.encryptionexample.security.DecryptionProcess
import com.example.encryptionexample.security.EncryptionProcess

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val encryptor : EncryptionProcess = EncryptionProcess()
    private val decryptor : DecryptionProcess by lazy {
        DecryptionProcess()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.decrypt.setOnClickListener {
            decryptText()
        }

        binding.encrypt.setOnClickListener {
            encryptText()
        }
    }

    private fun decryptText() {
        val decryptedText = encryptor.encryption?.let {
            encryptor.iv?.let { iv ->
                decryptor.decryptData(ALIAS_KEY, it, iv)
            }
        } ?: "No data to decrypt"
        binding.textView.text = decryptedText
    }

    private fun encryptText() {
        val valueEncrypted = encryptor.encryptText(binding.editText.text.toString(), ALIAS_KEY)
        valueEncrypted?.let {
            binding.textView.text = Base64.encodeToString(valueEncrypted, Base64.DEFAULT)
        }
    }

    companion object{
        private const val ALIAS_KEY = "ANDROID_KEY"
    }
}