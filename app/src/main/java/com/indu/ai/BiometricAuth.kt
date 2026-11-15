package com.indu.ai

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class BiometricAuth(
    private val activity: FragmentActivity
) {

    fun authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val manager = BiometricManager.from(activity)

        val result = manager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        )

        if (result != BiometricManager.BIOMETRIC_SUCCESS) {
            onError("ফিঙ্গারপ্রিন্ট বা বায়োমেট্রিক সুবিধা পাওয়া যাচ্ছে না")
            return
        }

        val executor = ContextCompat.getMainExecutor(activity)

        val prompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errString.toString())
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError("আবার চেষ্টা করো")
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("INDU AI Security")
            .setSubtitle("পরিচয় নিশ্চিত করতে ফিঙ্গারপ্রিন্ট ব্যবহার করো")
            .setNegativeButtonText("Cancel")
            .build()

        prompt.authenticate(promptInfo)
    }
}
