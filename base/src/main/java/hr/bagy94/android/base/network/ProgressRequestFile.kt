package hr.bagy94.android.base.network

import android.net.Uri
import hr.bagy94.android.base.file.FileManager
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

open class ProgressRequestFile (val mediaType: MediaType?, val fileUri:Uri, private val fileManager: FileManager, var listener: ProgressUploadListener? = null) : RequestBody(){
    interface ProgressUploadListener{
        fun onUploadFile(currentProgressPercentage: Int, lastUpdatedPercentage: Int, uploadedBytes: Long, totalBytes: Int)
    }
    /** Returns the Content-Type header for this body. */
    override fun contentType(): MediaType? = mediaType

    /** Writes the content of this request to [sink]. */
    override fun writeTo(sink: BufferedSink) {
        var uploaded: Long = 0
        var lastUpdatedPercentage = 0
        fileManager.listener = object : FileManager.FileManagerListener{
            override fun onReadBytes(buffer: ByteArray, read: Int, totalBytes: Int, progress: Int) {
                sink.write(buffer,0,read)
                uploaded += read
                if (progress - lastUpdatedPercentage >= 1) {
                    listener?.onUploadFile(progress,lastUpdatedPercentage, uploaded, totalBytes)
                    lastUpdatedPercentage = progress
                }
            }
        }
        fileManager.read(fileUri)
    }
}