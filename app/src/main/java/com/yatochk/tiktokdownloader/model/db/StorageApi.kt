package com.yatochk.tiktokdownloader.model.db

import java.io.File

interface StorageApi {
    fun getFiles(): ArrayList<File>
    fun writeFile(data: ByteArray)
}