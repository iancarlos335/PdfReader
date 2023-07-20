package com.example.pdf.reader

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pdf.reader.retrofit.api.FileApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var imgView: ImageView
    lateinit var btnChange: Button
    lateinit var btnUpload: Button
    lateinit var imageUri: Uri

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        imgView.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()

    }

    private fun setup() {
        imgView = findViewById(R.id.imageView)
        btnChange = findViewById(R.id.btnChange)
        btnChange.setOnClickListener { contract.launch("image/*") }
        btnUpload = findViewById(R.id.btnUpload)
        btnUpload.setOnClickListener { upload() }
    }

    private fun upload() {
        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "image.png")

        val inputStream = contentResolver.openInputStream(imageUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("profile", file.name, requestBody)

        val retrofit =
            Retrofit.Builder().baseUrl("http://192.168.30.92:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FileApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.uploadPdf(part)
            Log.d("Response", response.toString())
        }
    }
}