package com.example.pdf.reader.download

interface Downloader {
    fun downloadFile(url: String): Long
}