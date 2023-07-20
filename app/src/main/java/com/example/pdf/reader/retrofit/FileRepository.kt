package com.example.pdf.reader.retrofit

import com.example.pdf.reader.retrofit.api.FileApi
import com.example.pdf.reader.retrofit.constant.FileApiConst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class FileRepository(
    private val api: FileApi,
) {

    suspend fun uploadPdf(file: File): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                api.uploadPdf(
                    pdfDocument = MultipartBody.Part
                        .createFormData(
                            FileApiConst.TYPE_PDF,
                            file.name,
                            file.asRequestBody()
                        )
                )
                true
            } catch (e: HttpException) {
                e.printStackTrace()
                false
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }


    }
}