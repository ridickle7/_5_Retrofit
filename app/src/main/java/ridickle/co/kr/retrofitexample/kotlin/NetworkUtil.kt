package ridickle.co.kr.retrofitexample.kotlin

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ridickle.co.kr.retrofitexample.java.NetworkInterface

/**
 * Created by ridickle on 2017. 10. 7..
 * retrofit 설계 참고
 * 1. http://gun0912.tistory.com/50
 * 2. http://developer88.tistory.com/67
 */

object NetworkUtil {
    lateinit var retrofit: Retrofit
    var service: KotlinNetworkInterface? = null
    var baseURL = "http://52.79.87.95:3001"

    // Json Parser 추가
    // 인터페이스 연결

    fun getInstance(): KotlinNetworkInterface {
        if (service == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(requestHeader)
                    .addConverterFactory(GsonConverterFactory.create())     // Json Parser 추가
                    .build()

            service = retrofit.create(KotlinNetworkInterface::class.java)   // 인터페이스 연결
        }

        return service!!
    }


    private val requestHeader: OkHttpClient
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                    .addInterceptor(interceptor).build()
        }
}
