package hr.bagy94.android.base.file

import android.graphics.Bitmap
import android.net.Uri

sealed class Convertable
data class Image(val bitmap: Bitmap, val uri: Uri) : Convertable()
data class Document(val uri: Uri) : Convertable()