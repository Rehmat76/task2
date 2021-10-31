package com.example.task.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


object FileUtils {

    private val TAG = FileUtils::class.java.simpleName

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.ENGLISH).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File = context.filesDir
        return File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
    }

    @Throws(IOException::class)
    fun createFileForUri(context: Context, uri: Uri?): File? {
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        return bitmapToFile(context, bitmap)
    }

    @Throws(IOException::class)
    fun bitmapToFile(context: Context, bitmap: Bitmap): File? {
        val file: File = createImageFile(context)
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitmapData: ByteArray = bos.toByteArray()
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            try {
                fos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return file
    }
}
