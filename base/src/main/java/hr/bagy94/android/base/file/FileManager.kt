package hr.bagy94.android.base.file

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.webkit.MimeTypeMap
import hr.bagy94.android.base.converter.toBase64
import java.io.InputStream
import kotlin.math.roundToInt


typealias ReadFileListener = (buffer:ByteArray,read:Int, totalBytes:Int, progress:Int) -> Unit

open class FileManager(private val context: Context){
    open fun read(uri:Uri, bufferSize: Int = DEFAULT_BUFFER_SIZE, readListener:ReadFileListener){
        context.contentResolver.openInputStream(uri)?.use {
            readFromInputStream(it,uri, bufferSize,readListener)
        }
    }

    open fun readAll(uri: Uri) : ByteArray?{
        var byteArray:ByteArray? = null
        context.contentResolver.openInputStream(uri)?.use {
            byteArray = it.readBytes()
        }
        return byteArray
    }

    open fun readFromInputStream(inputStream: InputStream, uri: Uri, bufferSize: Int, readListener:ReadFileListener){
        val buffer = ByteArray(bufferSize)
        var read: Int
        var totalRead = 0
        val fileLength = inputStream.available()
        while (inputStream.read(buffer).also { read = it;totalRead += it } != -1) {
            val currentProgressPercentage = (totalRead / fileLength.toFloat() * 100).roundToInt()
            readListener(buffer,read,fileLength, currentProgressPercentage)
        }
    }

    open fun getFileType(uri: Uri):String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
    }

    open fun getAbsolutePath(uri: Uri) : String?{
        var path : String? = null
        val projection = arrayOf("_data", "relative_path")
        val cursor = context.contentResolver.query(uri,projection,null,null,null)
        cursor?.apply {
            if(moveToNext()){
                path = getString(0)
            }
            close()
        }
        return path
    }

    open fun getFileAsBase64Encoded(data:Convertable) : Base64ConvertResult?{
        return when(data){
            is Image -> getImageConverted(data)
            is Document -> getFileConverted(data)
        }
    }

    protected open fun getImageConverted(data:Image): Base64ConvertResult?{
        val type = getFileType(data.uri)
        val image = data.bitmap.toBase64()
        return Base64ConvertResult(image,type)
    }

    protected open fun getFileConverted(data:Document): Base64ConvertResult?{
        return readAll(data.uri)?.let {
            val type = getFileType(data.uri)
            val string = Base64.encodeToString(it, Base64.DEFAULT)
            Base64ConvertResult(string,type)
        }
    }

    data class Base64ConvertResult(val documentBase64:String?, val type:String?)


    companion object{
        const val DEFAULT_BUFFER_SIZE = 2048
    }
}