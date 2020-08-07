package hr.bagy94.android.base.network

import android.net.Uri
import hr.bagy94.android.base.file.FileManager
import hr.bagy94.android.base.file.ReadFileListener
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

typealias ProgressUploadListener = (currentProgressPercentage: Int, lastUpdatedPercentage: Int, uploadedBytes: Long, totalBytes: Int) -> Unit

open class ProgressRequestFile (val mediaType: MediaType?, val fileUri:Uri, private val fileManager: FileManager, var listener: ProgressUploadListener? = null) : RequestBody(){
    /** Returns the Content-Type header for this body. */
    override fun contentType(): MediaType? = mediaType

    /** Writes the content of this request to [sink]. */
    override fun writeTo(sink: BufferedSink) {
        var uploaded: Long = 0
        var lastUpdatedPercentage = 0
        fileManager.read(uri = fileUri){ buffer, read, totalBytes, progress ->
            sink.write(buffer,0,read)
            uploaded += read
            if (progress - lastUpdatedPercentage >= 1) {
                listener?.invoke(progress,lastUpdatedPercentage, uploaded, totalBytes)
                lastUpdatedPercentage = progress
            }
        }
    }
}