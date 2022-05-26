package com.example.bwamov.sign.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bwamov.R
import com.example.bwamov.sign.signin.SignInActivity
import com.example.bwamov.sign.signin.User
import com.google.firebase.database.*


class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sNama: String
    lateinit var sEmail: String

    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mDatabase: DatabaseReference
    lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val iv_back = findViewById<ImageView>(R.id.iv_back)
        iv_back.setOnClickListener {
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        val button = findViewById<Button>(R.id.btn_lanjutkan)
        button.setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username2)
            val password = findViewById<EditText>(R.id.et_password2)
            val nama = findViewById<EditText>(R.id.et_nama)
            val email = findViewById<EditText>(R.id.et_email)

            sUsername = username.text.toString()
            sPassword = password.text.toString()
            sNama = nama.text.toString()
            sEmail = email.text.toString()

            if (sUsername.equals("")) {
                username.error = "Silahkan isi username anda"
                username.requestFocus()
            } else if (sPassword.equals("")) {
                password.error = "Silahkan isi password anda"
                password.requestFocus()
            } else if (sNama.equals("")) {
                nama.error = "Silahkan isi nama anda"
                nama.requestFocus()
            } else if (sEmail.equals("")) {
                email.error = "Silahkan isi email anda"
                email.requestFocus()
            } else {
                saveUsername(sUsername, sPassword, sNama, sEmail)
            }
        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        val user = User()
        user.email = sEmail
        user.username = sUsername
        user.password = sPassword
        user.nama = sNama

        if (sUsername != null){
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(sUsername: String, data: User) {
        mDatabaseReference.child(sUsername).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null){
                    mDatabaseReference.child(sUsername).setValue(data)

                    val intent = Intent(this@SignUpActivity, SignUpPhotoscreenActivity::class.java).putExtra("nama", data?.nama)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}