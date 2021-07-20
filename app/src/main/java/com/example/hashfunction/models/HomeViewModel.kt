package com.example.hashfunction.models

import android.util.Log
import androidx.lifecycle.ViewModel
import java.security.MessageDigest

class HomeViewModel: ViewModel() {
    private  var _hashText:String="Empty"
    val hashText get()=_hashText
    fun getHash(algo: String, message: String) {

        val bytes = MessageDigest.getInstance(algo).digest(message.toByteArray())
        _hashText= toHex(bytes);
        Log.i("Mess",hashText.toString());
    }

    private fun toHex(bytes: ByteArray): String {
        return bytes.joinToString("") { "%02x".format(it) }
    }
}