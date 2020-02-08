package hr.bagy94.android.base.error

interface Error

data class UnknownError(val message:String?):Error
data class SnackBarError(val message: String):Error

interface ErrorHandler{
    fun parseError(throwable: Throwable) : Error
}