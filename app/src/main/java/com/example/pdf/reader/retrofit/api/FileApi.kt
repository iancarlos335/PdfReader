package com.example.pdf.reader.retrofit.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {

    @POST("/pdf-to-speech/pdf/")
    @Multipart
    suspend fun uploadPdf(
        @Part pdfDocument: MultipartBody.Part
    ) : ResponseBody
}