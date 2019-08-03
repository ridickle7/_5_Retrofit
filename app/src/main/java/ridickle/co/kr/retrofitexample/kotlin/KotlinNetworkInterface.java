package ridickle.co.kr.retrofitexample.kotlin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ridickle.co.kr.retrofitexample.java.Network_Authorize;

/**
 * Created by ridickle on 2016. 12. 5..
 */

public interface KotlinNetworkInterface {

    // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
    // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
    @GET("/user/login")
    public Call<NetworkModel> get_userLogin(@Query("id") String id, @Query("password") String password);

    @POST("/user/login")
    public Call<NetworkModel> post_userLogin(@Field("id") String id, @Field("password") String password);
}
