package com.invictus.pillstracker.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.google.api.client.util.IOUtils
import splitties.init.appCtx
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL


object StorageUtils {

    private fun getDownloadDirectoryLessQ(fileName: String): Uri? {
        val authority = "${appCtx.packageName}.fileprovider"
        val destinyFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        return try {
            FileProvider.getUriForFile(appCtx, authority, destinyFile)
        } catch (e: Exception) {
            null
        }
    }

    fun getImageFromPhotosDirectory(fileName: String): Uri? {
        return try {
            Timber.d("fileName==>>${fileName}")
            val outputDir = File(appCtx.getExternalFilesDir(null), "photos")

            if (!outputDir.exists()) outputDir.mkdir()

            val authority = "${appCtx.packageName}.fileprovider"
            val destinyFile = File(outputDir, fileName)
            FileProvider.getUriForFile(appCtx, authority, destinyFile)

        } catch (e: Exception) {
            Timber.d("==>>Failed to create temporary file.==>>$e")
            getDownloadDirectoryLessQ(fileName)
        }
    }

    fun storeBitmapInPhotosDir(bitmap: Bitmap, fileName: String) {
        try {
            val fileDir = File(appCtx.getExternalFilesDir(null), "photos")
            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }
            val stream = FileOutputStream("$fileDir/$fileName.png")
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getBitmapFromUri(uriLink: String): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(appCtx.contentResolver, Uri.parse(uriLink)))
            } else {
                MediaStore.Images.Media.getBitmap(appCtx.contentResolver, Uri.parse(uriLink))
            }
        } catch (e: Exception) {
            Timber.d(e)
            null
            //ContextCompat.getDrawable(appCtx, R.drawable.ic_photo)?.toBitmap()
        }

        /*return try {
            val out = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
            BitmapFactory.decodeStream(ByteArrayInputStream(out.toByteArray()))
        } catch (e: Exception) {
            null
        }*/
    }

    fun getFileFromStorageUri(uri: Uri): File? {
        try {
            val parcelFileDescriptor = appCtx.contentResolver.openFileDescriptor(uri, "r", null)
            parcelFileDescriptor?.let {
                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                val filePath = File(appCtx.cacheDir, appCtx.contentResolver.getFileName(uri))
                val outputStream = FileOutputStream(filePath)
                IOUtils.copy(inputStream, outputStream)
                return filePath
            } ?: run {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return try {
                uri.toFile()
            } catch (er: Exception) {
                Timber.d(er)
                null
            }
        }
    }

    fun deleteFile(uriLink: String) {
        runCatching {
            val isDeleted = appCtx.contentResolver.delete(Uri.parse(uriLink), null, null)
            Timber.d("isDeleted==>>$isDeleted")
        }
    }

    fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    suspend fun downloadFileFromUrl(urlLink: String): File? {
        try {
            val url = URL(urlLink) // replace with your own URL
            val fileExtension = File(urlLink).name.substringAfterLast(".", "")
            val connection = url.openConnection()
            connection.connect()

            val inputStream = BufferedInputStream(url.openStream())
            val targetFile = File(appCtx.cacheDir, "my_file.${fileExtension}") // replace with your own file name
            val outputStream = FileOutputStream(targetFile)

            val data = ByteArray(1024)
            var count: Int
            while (inputStream.read(data, 0, 1024).also { count = it } != -1) {
                outputStream.write(data, 0, count)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            return targetFile
        } catch (e: Exception) {
            return null
        }
    }

    fun convertFileToBitmap(filePath : String): Bitmap? {
        return try {
            BitmapFactory.decodeFile(filePath)
        }catch (e : Exception) {
            null
        }

    }
}

