package com.downloaderfor.tiktok.model.db

import java.io.File
import java.util.*

interface StorageApi {
    fun getFiles(): ArrayList<File>
    fun writeFile(data: ByteArray, listener: ((String) -> Unit)?)
    fun deleteFile(path: String)
}