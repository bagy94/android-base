package hr.bagy94.base_android.retrofit

/**
 * First add dependency
import okhttp3.OkHttpClient
import retrofit2.Retrofit

fun provideRetrofit(
    baseUrl: String,
    client: OkHttpClient = provideOKHttpClient {

    }
): Retrofit =
    Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .build()

fun provideOKHttpClient(creator: OkHttpClient.Builder.() -> Unit): OkHttpClient {
    val builder = OkHttpClient.Builder()
    creator(builder)
    return builder.build()
}
 */