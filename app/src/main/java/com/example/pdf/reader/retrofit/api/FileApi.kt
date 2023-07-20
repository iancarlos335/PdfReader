package com.example.pdf.reader.retrofit.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {

    @POST("/iaresponse/audio/")
    @Multipart
    suspend fun uploadPdf(
        @Part pdfDocument: MultipartBody.Part
    ) : ResponseBody
}