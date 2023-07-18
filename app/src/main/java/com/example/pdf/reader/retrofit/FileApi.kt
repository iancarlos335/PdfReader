package com.example.pdf.reader.retrofit

import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.http.Multipart
import retrofit2.http.Part

interface FileApi {

    @Multipart
    suspend fun uploadImage(
        @Part pdfDocument: MultipartBody.Part
    )

    companion object {
        val intance by lazy {
            Retrofit.Builder()
                .baseUrl("http://192.168.30.92:8000/")
                .build()
                .create(FileApi::class.java)
        }
    }
}