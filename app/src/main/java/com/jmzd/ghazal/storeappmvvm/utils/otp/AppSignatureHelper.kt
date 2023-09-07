package com.jmzd.ghazal.storeappmvvm.utils.otp

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class AppSignatureHelper @Inject constructor(@ApplicationContext context: Context) : ContextWrapper(context) {
    val appSignatures: ArrayList<String>
        @SuppressLint("PackageManagerGetSignatures")
        get() {
            val appCodes = ArrayList<String>()
            try {
                val packageName = packageName
                val packageManager = packageManager
                val signatures = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures
                signatures.forEach {
                    val hash = hash(packageName, it.toCharsString())
                    if (hash != null) {
                        appCodes.add(String.format("%s", hash))
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Log.v(tag, "Unable to find package to obtain hash.", e)
            }
            return appCodes
        }

    companion object {
        private val tag = AppSignatureHelper::class.java.simpleName
        private const val HASH_TYPE = "SHA-256"
        private const val NUM_HASHED_BYTES = 9
        private const val NUM_BASE64_CHAR = 11
        private fun hash(packageName: String, signature: String): String? {
            val appInfo = "$packageName $signature"
            try {
                val messageDigest = MessageDigest.getInstance(HASH_TYPE)
                messageDigest.update(appInfo.toByteArray(StandardCharsets.UTF_8))
                var hashSignature = messageDigest.digest()
                hashSignature = Arrays.copyOfRange(hashSignature, 0, NUM_HASHED_BYTES)
                var base64Hash = Base64.encodeToString(hashSignature, Base64.NO_PADDING or Base64.NO_WRAP)
                base64Hash = base64Hash.substring(0, NUM_BASE64_CHAR)
                Log.v(tag + "sms_sample_test", String.format("pkg: %s -- hash: %s", packageName, base64Hash))
                return base64Hash
            } catch (e: NoSuchAlgorithmException) {
                Log.v(tag + "sms_sample_test", "hash:NoSuchAlgorithm", e)
            }
            return null
        }
    }
}