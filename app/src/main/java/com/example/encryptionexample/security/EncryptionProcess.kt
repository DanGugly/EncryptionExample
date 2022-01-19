package com.example.encryptionexample.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class EncryptionProcess {

    // Have access to initialization vector from cypher
    var iv : ByteArray? = null

    //Contain our already encrypted text
    var encryption : ByteArray? = null

    private val cipher : Cipher by lazy {
        Cipher.getInstance(TRANSFORMATION)
    }

    /**
     * This method will encrypt the key
     */
    fun encryptText(textToEncrypt : String, aliasKey: String) : ByteArray? {
        cipher.init(Cipher.ENCRYPT_MODE, keyGenerator(aliasKey))
        iv = cipher.iv
        encryption = cipher.doFinal(textToEncrypt.toByteArray())
        return encryption
    }


    /**
     * This method generates a secret key for encryption
     * and needs an aliaskey to create your KeyStore
     */
    private fun keyGenerator(aliasKey : String) : SecretKey{
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, MY_KEY_GEN).apply {
            init(
                KeyGenParameterSpec.Builder(
                    aliasKey,
                    KeyProperties.PURPOSE_ENCRYPT.or(KeyProperties.PURPOSE_DECRYPT)
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
        }
        return keyGenerator.generateKey()
    }

    companion object{
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val MY_KEY_GEN = "AndroidKeyStore"
    }
}