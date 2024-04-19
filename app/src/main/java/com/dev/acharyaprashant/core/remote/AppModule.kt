package com.dev.acharyaprashant.core.remote
import com.dev.acharyaprashant.core.utils.BASE_URL_1
import com.dev.acharyaprashant.core.utils.BASE_URL_2
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun getHttpClient(httpClient: KtorHttpClient): HttpClient = httpClient.getHttpClient()


    @Provides
    fun provideBaseUrl(): String = BASE_URL_2

    @Provides
    fun provideLogInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
    ): OkHttpClient {

        return OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideRetrofitClient(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .serializeNulls()
                        .setLenient().create()
                )
            ).build()

    }
}