package ridickle.co.kr.retrofitexample;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by ridickle on 2017. 10. 7..
 * retrofit 설계 참고
 * 1. http://gun0912.tistory.com/50
 * 2. http://developer88.tistory.com/67
 */

public class NetworkHelper {

    public static NetworkHelper networkHelper;
    public Retrofit retrofit;
    public static NetworkInterface service;
    public static String baseURL = "http://211.249.50.198:8080";

    private NetworkHelper(){
    }

    public static NetworkInterface getInstance(){
        if(networkHelper == null){
            service = new Retrofit.Builder()
                    .baseUrl("baseURL")
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create()) // Json Parser 추가
                    .build().create(NetworkInterface.class);            // 인터페이스 연결

        }
        return service;
    }

    private static OkHttpClient getRequestHeader() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        return client;
    }
}
