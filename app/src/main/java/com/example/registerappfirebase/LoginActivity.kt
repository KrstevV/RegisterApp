package com.example.registerappfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.registerappfirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding : ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        auth = Firebase.auth

        loginBinding.textViewRegisterNow.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBinding.loginButton.setOnClickListener {
            val email = loginBinding.emailEdittext.text.toString()
            val password = loginBinding.passwordEdittext.text.toString()
            loginUser(email, password)
        }
    }
    private fun loginUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val intent = Intent(
                            this,
                            MainActivity::class.java
                        )
                        intent.putExtra("email", user?.email)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext, "Username or Password is not correct.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                baseContext, "Username or Password is empty.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}