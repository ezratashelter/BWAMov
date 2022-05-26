package com.example.bwamov.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bwamov.home.HomeActivity
import com.example.bwamov.R
import com.example.bwamov.sign.signup.SignUpActivity
import com.example.bwamov.utils.Preferences
import com.google.firebase.database.*


class SignInActivity : AppCompatActivity() {

    lateinit var iUsername:String
    lateinit var iPassword:String

    lateinit var mDatabase : DatabaseReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(this)

        preferences.setValues("onboarding", "1")
        if (preferences.getValues("status").equals("1")){
            finishAffinity()

            val goHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        val btn_daftar = findViewById<Button>(R.id.btn_daftar3)
        btn_daftar.setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username)
            iUsername = username.text.toString()
            val password = findViewById<EditText>(R.id.et_password)
            iPassword = password.text.toString()

            if (iUsername.equals("")){
                username.error = "Silahkan ketik username anda!"
                username.requestFocus()
            } else if (iPassword.equals("")){
                password.error = "Silahkan ketik password anda!"
                password.requestFocus()
            } else {
                pushLogin(iUsername, iPassword)
            }
            }
        val btn_home = findViewById<Button>(R.id.btn_home5)
        btn_home.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null){
                    Toast.makeText(this@SignInActivity, "Username tidak ditemukan", Toast.LENGTH_LONG).show()
                } else {
                    if (user.password.equals(iPassword)){
                        preferences.setValues("nama", user.nama.toString())
                        preferences.setValues("username", user.username.toString())
                        preferences.setValues("url", user.url.toString())
                        preferences.setValues("emai", user.email.toString())
                        preferences.setValues("saldo", user.saldo.toString())
                        preferences.setValues("status", "1")

                        val intent = Intent (this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Password anda salah", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}