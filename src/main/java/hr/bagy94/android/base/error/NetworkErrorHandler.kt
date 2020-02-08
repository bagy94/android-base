package hr.bagy94.android.base.error

import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

sealed class NetworkError:Error
data class NoInternetConnectionError(val message:String):NetworkError()
data class ServerError(val message: String, val code:Int):NetworkError()
data class ParseResponseError(val message: String?,val rawResponse:String?):NetworkError()
data class APIError(val rawResponse:String?, val httpCode:Int) : NetworkError()
data class TimeoutError(val message: String?):NetworkError()

interface NetworkErrorHandler : ErrorHandler{
    override fun parseError(throwable: Throwable): Error {
        return when(throwable){
            is HttpException -> handleHttp(throwable)
            is JsonParseException-> handleParse(throwable)
            is SocketTimeoutException -> handleTimeout(throwable)
            is IOException -> handleNoConnection(throwable)
            else -> UnknownError(throwable.message)
        }
    }

    fun handleHttp(exception: HttpException) : Error {
        return when(val httpCode = exception.code()){
            in 400..499 -> handleAPIError(APIError(exception.response()?.errorBody()?.string(),httpCode))
            in 500..600 -> ServerError(exception.message(),httpCode)
            else -> UnknownError(exception.message)
        }
    }

    fun handleTimeout(thr:SocketTimeoutException): Error = TimeoutError(thr.message)

    fun handleNoConnection(thr:IOException) : Error = NoInternetConnectionError(thr.toString())

    fun handleParse(thr:JsonParseException) : Error = ParseResponseError(thr.message,thr.localizedMessage)

    fun handleAPIError(apiError: APIError) : Error
}