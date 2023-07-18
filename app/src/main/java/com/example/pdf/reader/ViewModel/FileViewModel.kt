package com.example.pdf.reader.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdf.reader.retrofit.FileRepository
import kotlinx.coroutines.launch
import java.io.File

class FileViewModel(
    private val repository: FileRepository = FileRepository()
) : ViewModel() {

    fun uploadFile(file: File) {
        viewModelScope.launch {
            repository.uploadImage(file)
        }
    }
}