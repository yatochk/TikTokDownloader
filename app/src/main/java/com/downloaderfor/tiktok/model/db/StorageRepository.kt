package com.downloaderfor.tiktok.model.db

import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.util.Log
import com.downloaderfor.tiktok.dagger.App
import com.downloaderfor.tiktok.utils.TIK_TOK_FOLBER
import java.io.File
import java.lang.Long.compare
import java.util.*


class StorageRepository : StorageApi {
    override fun deleteFile(path: String) {
        val file = File(path)
        file.delete()

        MediaScannerConnection.scanFile(
            App.component.context,
            arrayOf(
                Environment.getExternalStorageDirectory().absolutePath +
                        TIK_TOK_FOLBER + file.name
            ), null
        ) { newPath, newUri ->
            Log.i("ExternalStorage", "Scanned $newPath:")
            Log.i("ExternalStorage", "-> uri=$newUri")
        }
    }

    override fun getFiles(): ArrayList<File> {
        val filesList = ArrayList<File>()
        val targetPath =
            Environment.getExternalStorageDirectory().absolutePath + TIK_TOK_FOLBER
        val targetDirector = File(targetPath)
        val files = targetDirector.listFiles()

        try {
            Arrays.sort(files) { f1, f2 ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    compare((f2 as File).lastModified(), (f1 as File).lastModified())
                } else {
                    when {
                        (f1 as File).lastModified() > (f2 as File).lastModified() -> -1
                        f1.lastModified() < f2.lastModified() -> +1
                        else -> 0
                    }
                }
            }
            filesList.addAll(files)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return filesList
    }

    override fun writeFile(data: ByteArray, listener: ((String) -> Unit)?) {
        val fileName = "TikTokVideo_" + UUID.randomUUID().toString().substring(0, 5) + ".mp4"
        val file: File
        try {
            file = File(Environment.getExternalStorageDirectory().absolutePath + TIK_TOK_FOLBER, fileName)
            val directory = File(Environment.getExternalStorageDirectory().absolutePath + TIK_TOK_FOLBER)
            directory.mkdirs()

            val fs = file.outputStream()
            fs.write(data, 0, data.size)
            fs.close()

            MediaScannerConnection.scanFile(
                App.component.context,
                arrayOf(
                    Environment.getExternalStorageDirectory().absolutePath +
                            TIK_TOK_FOLBER + fileName
                ), null
            ) { newPath, newUri ->
                Log.i("ExternalStorage", "Scanned $newPath:")
                Log.i("ExternalStorage", "-> uri=$newUri")
            }
        } catch (e: Exception) {
            return
        }

        listener?.invoke(file.absolutePath)
    }
}