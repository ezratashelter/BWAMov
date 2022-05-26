package com.example.bwamov.sign.signup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bwamov.home.HomeActivity
import com.example.bwamov.R
import com.example.bwamov.utils.Preferences
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*

class SignUpPhotoscreenActivity : AppCompatActivity(), PermissionListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath: Uri

    lateinit var storage : FirebaseStorage
    lateinit var storageReferensi : StorageReference
    lateinit var preferences: Preferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)
        
        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()
        
        val tv_hello = findViewById<TextView>(R.id.tv_hello)
        
        tv_hello.text = "Selamat Datang,\n"+intent.getStringExtra("nama")

        val iv_add = findViewById<ImageView>(R.id.iv_add)
        iv_add.setOnClickListener { 
            if (statusAdd) {
                statusAdd = false
                val btn_save = findViewById<Button>(R.id.btn_save)
                btn_save.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_baseline_delete_forever_24)
                val iv_profile = findViewById<ImageView>(R.id.iv_profile)
                iv_profile.setImageResource(R.drawable.user_pic)
            } else {
                ImagePicker.with(this)
                    .cameraOnly()
                    .start()
            }
        }
        val btn_home = findViewById<Button>(R.id.btn_home6)
        btn_home.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        val btn_save = findViewById<Button>(R.id.btn_save)
        btn_save.setOnClickListener {
            if (filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                Log.v("tamvan", "file uri Upload 2"+filePath)

                val ref = storageReferensi.child("images/" +UUID.randomUUID().toString())
                ref.putFile(filePath).addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this@SignUpPhotoscreenActivity, "Uploaded", Toast.LENGTH_LONG).show()

                    ref.downloadUrl.addOnSuccessListener {
                        preferences.setValues("url", it.toString())
                    }
                    finishAffinity()
                    var goHome = Intent (this@SignUpPhotoscreenActivity, HomeActivity::class.java)
                    startActivity(goHome)
                }
                    .addOnFailureListener{
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {
                        taskSnapshot -> var progress = 100.0 + taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload"+progress.toInt()+"%")
                    }
            }
        }
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak bisa menambahkan photo profile", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        p0: com.karumi.dexter.listener.PermissionRequest?,
        p1: PermissionToken?
    ) {

    }
    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah? klik tombol nanti aja", Toast.LENGTH_LONG).show()
    }
    /*@SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            val iv_profile = findViewById<ImageView>(R.id.iv_profile)

            filePath = data.getData()!!
            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)
            val btn_save = findViewById<Button>(R.id.btn_save)
            btn_save.visibility = View.VISIBLE
            val iv_add = findViewById<ImageView>(R.id.iv_add)
            iv_add.setImageResource(R.drawable.ic_baseline_delete_forever_24)
            iv_profile.setImageResource(R.drawable.user_pic)

        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            statusAdd = true
            filePath = data?.data!!

            val iv_profile = findViewById<ImageView>(R.id.iv_profile)
            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)
            val btn_save = findViewById<Button>(R.id.btn_save)
            btn_save.visibility = View.VISIBLE
            val iv_add = findViewById<ImageView>(R.id.iv_add)
            iv_add.setImageResource(R.drawable.ic_baseline_delete_forever_24)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}