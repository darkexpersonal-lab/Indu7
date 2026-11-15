
package com.indu.ai

import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var biometricAuth: BiometricAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        biometricAuth = BiometricAuth(this)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.rgb(18, 22, 35))
        }

        val title = TextView(this).apply {
            text = "INDU AI"
            textSize = 34f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "তোমার ব্যক্তিগত AI Assistant"
            textSize = 18f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }

        val status = TextView(this).apply {
            text = "নিরাপত্তা যাচাই করে শুরু করো"
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 30)
        }

        val button = Button(this).apply {
            text = "Fingerprint দিয়ে শুরু করো"

            setOnClickListener {
                biometricAuth.authenticate(
                    onSuccess = {
                        status.text = "INDU AI আনলক হয়েছে!"
                        Toast.makeText(
                            this@MainActivity,
                            "স্বাগতম! INDU AI প্রস্তুত।",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onError = { error ->
                        Toast.makeText(
                            this@MainActivity,
                            error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        }

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(status)
        layout.addView(button)

        setContentView(layout)
    }
}
