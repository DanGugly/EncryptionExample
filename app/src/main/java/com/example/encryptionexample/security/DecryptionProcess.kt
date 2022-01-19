package com.example.encryptionexample.security

import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class DecryptionProcess {

    /**
     * This variable holds the keystore object
     */
    private lateinit var keyStore: KeyStore

    /**
     * This block is run when the class is instantiated
     */
    init {
        keyStore = KeyStore.getInstance(EncryptionProcess.MY_KEY_GEN).apply {
            load(null)
        }
    }

    /**
     * Retrieve the same cipher from encrypt as its a singleton function
     */
    private val cipher : Cipher by lazy {
        Cipher.getInstance(EncryptionProcess.TRANSFORMATION)
    }

    fun decryptData(aliasKey: String, encryptedData : ByteArray, encryptionIv : ByteArray): String{
        val spec = GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(aliasKey), spec)
        return String(cipher.doFinal(encryptedData), Charset.defaultCharset())
    }

    private fun getSecretKey(aliasKey : String): SecretKey {
        return (keyStore.getEntry(aliasKey, null) as KeyStore.SecretKeyEntry).secretKey
    }
}