package hr.bagy94.android.base.error

import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class NetworkError:Error
data class NoInternetConnectionError(val message:String) : NetworkError()
data class ServerError(val message: String, val code:Int) : NetworkError()
data class ParseResponseError(val message: String?,val rawResponse:String?) : NetworkError()
open class APIError<T>(val errorObject:T?, val httpCode: Int) : NetworkError()
data class TimeoutError(val message: String?) : NetworkError()

interface NetworkErrorHandler: ErrorHandler{
    override fun parseError(throwable: Throwable): Error {
        return when(throwable){
            is HttpException -> handleHttp(throwable)
            is JsonParseException-> handleParse(throwable)
            is SocketTimeoutException -> handleTimeout(throwable)
            is UnknownHostException,is IOException, is ConnectException -> handleNoConnection(throwable)
            else -> UnknownError(throwable.message)
        }
    }

    fun handleHttp(exception: HttpException) : Error {
        return when(val httpCode = exception.code()){
            in 400..499 -> handleAPIError(exception.response()?.errorBody()?.string(),httpCode)
            in 500..600 -> handleServerError(exception,httpCode)
            else -> UnknownError(exception.message)
        }
    }

    fun handleTimeout(thr:SocketTimeoutException): Error = TimeoutError(thr.message)

    fun handleNoConnection(thr:Throwable) : Error = NoInternetConnectionError(thr.toString())

    fun handleParse(thr:JsonParseException) : Error = ParseResponseError(thr.message,thr.localizedMessage)

    fun handleServerError(exc:HttpException, httpCode: Int) : Error = ServerError(exc.message(),httpCode)

    fun handleAPIError(errorBody:String?, httpCode: Int) : Error
}