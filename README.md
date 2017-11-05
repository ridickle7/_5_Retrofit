# 5. Retrofit

기본 Document 는 [이 링크](http://devflow.github.io/retrofit-kr/) 참고  
  
__A type-safe HTTP client for Android and Java__  
__HTTP API를 자바 인터페이스 형태로 사용할 수 있는 HTTP 통신 라이브러리__

## 1. Retrofit 이전
 
> #### 토막지식  
> UI쓰레드 (메인쓰레드) 에서는 네트워크 통신 작업을 할 수 없음  
	  (NetworkOnMainThreadException 발생)  
	  -> AsyncTask와 연계하여 새로운 쓰레드에서 진행해야함  

### 1. 기본 내장 기능 
  - HttpClient 			(Apache)
  - HttpURLConnection 	(Java)

    위의 두 기술 중 하나를 활용하여 AsyncTask 연계!
      
    한계  
      1. 코드가 복잡  
      2. 사용하기에 어려운 측면이 있음  
      3. HttpClient의 경우 더 이상 새로운 버전을 제공하지 않음 (2016년 기점)

    example Code : [http://mommoo.tistory.com/5](http://mommoo.tistory.com/5)

### 2. Volley 라이브러리

    위의 1번을 개선한 라이브러리이며, __RequestQueue__ 를 활용하여 요청을 관리
  
    한계
      1. 1번보다는 속도가 개선되었으나, 여전히 약간 느린 처리 속도
  

### 3. Retrofit

  위의 1번을 개선하여 Square 사에서 만든 OkHttp 기반 라이브러리  
  
  > OkHttp  
  > 서버 연동 관련 기능만 있는 Square 사 라이브러리  
  
  Retrofit 는? 
  - 어노테이션을 통한 가독성 증가
  - 서버 연동을 위한 기능 선택 가능 (HttpClient, OkHttp 등)
  - response 메시지에 대해 파싱방식 설정 가능 (GSON, XML 등)
  - RXJava 지원
  - 속도가 타 라이브러리와 기능과 비교하여 제일 빠르다 (아래 사진 참고)
    ![Image](http://cfile30.uf.tistory.com/image/2261D04D56BF3EA7169034)

---------------------------- 이렇게 된 이상 Retrofit 으로 간다. ----------------------------

## 2. Retrofit 구성

1. Service Interface 객체  
	각 URI에 매핑된 Call 객체 저장소  
	어노테이션을 활용하며, 각 매핑된 이벤트는 모두 Call 객체를 통해 만들고 활용하는 것이 가능  
	<pre><code>public interface NetworkService {
		@GET("/users/login")
		Call<Network_User> get_userLogin(@Query("id") String id, @Query("password") String password);
		}</code></pre>

2. Retrofit 객체  
	retrofit 객체를 통해 앞서 만든 Call 객체 저장소를 활성화  
	(이를 진행 안할 시 Interface 객체는 그냥 저장소가 될 뿐...)
	<pre><code>Retrofit retrofit = new Retrofit.Builder()
	.baseUrl("https://api.github.com")
	.build();
	
	NetworkService service = retrofit.create(NetworkService.class);</code></pre>
	
3. Call 객체  
	각각의 Call 객체는 Service Interface 객체를 통해 HTTP 요청을 원격 웹서버로 보낼 수 있습니다.
	<pre><code>Call<Network_Authorize> get_userLogin = service.get_userLogin("id", "pw");
	post_userLogin.enqueue(new Callback<Network_User>() {
		@Override
		public void onResponse(Call<Network_User> call, Response<Network_User> response) {
			// response json 파싱하는 과정 필요
			// ...
			Log.d("Login getId : ", response.body().getId());
			Toast.makeText(getApplicationContext(), "비밀번호는 " + response.body().getPassowrd(), Toast.LENGTH_SHORT).show();
		}
	
		@Override
		public void onFailure(Call<Network_Authorize> call, Throwable t) {
			Toast.makeText(getApplicationContext(), "실패 : " + t, Toast.LENGTH_SHORT).show();
		}
	});</code></pre>

4. 기타  
	Retrofit 객체 설정 시 header 및 클라이언트 설정, parser 설정이 가능합니다.  
	<pre><code>retrofit = new Retrofit.Builder()
		.baseUrl(baseURL)
		.client(getRequestHeader())
		.addConverterFactory(GsonConverterFactory.create()) // GSON Parser 추가
		.build();
	
	service = retrofit.create(NetworkInterface.class);  // 인터페이스 연결</code></pre>  
	> #### GSON  
	>   JSON 파싱을 쉽고 간단하게 할 수 있도록 도와주는 외부 라이브러리  
	> ###### 기존 JSON은
	>   1. JSONException 에 대해 일일히 try/catch 문을 적용시켜주어야 한다.
	>   2. 중간 DAO 객체 내에 값을 넣어주는 과정을 거쳐야 한다.  
	> 
	> ##### 하지만!! GSON을 아래와 같이 활용함으로써!! (아래 작업으로 GSON 라이브러리가 바로 적용 됨)
	> <pre><code>// Retrofit 객체 Code 에서 발췌
	> 
	> // ....
	> .addConverterFactory(GsonConverterFactory.create()) //Json Parser 추가
	> // ....</code></pre>
	>  
	> ##### 아래의 과정을 없애줄 수 있다.
	> <pre><code>// Call 객체 Code 에서 발췌
	> 
	> // response json 파싱하는 가정 필요
	> // ...
	> </code></pre>

## 3. Retrofit 활용

### 1. app.gradle의 dependency에 두 줄을 추가한다.
<pre><code>dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    // ...
    
    // retrofit
    compile 'com.squareup.retrofit2:retrofit:2.3.0'
    compile 'com.squareup.retrofit2:converter-gson:2.3.0'
}</code></pre>

### 2. 매니페스트에 INTERNET 퍼미션 추가
<pre><code> <uses-permission android:name="android.permission.INTERNET" /> </code></pre>

### 3. GSON 활용으로 얻어낼 JSON 데이터 클래스를 만듬
주의 사항  
해당 데이터 클래스의 형식이, 가져올 JSON 데이터 형식과 매치되어야 한다.  
즉, __변수(이름)!!!!!!!!__ 을 맞춰줘야 한다.


<pre><code>public class Network_User {
    String id;
    String passowrd;

    public String getId() { return id; }

    public String getPassowrd() {
        return passowrd;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }
}</code></pre>


### 4. Service Interface 객체 생성 및 활성화

<pre><code>// ....
// Retrofit 객체 생성
retrofit = new Retrofit.Builder()
	.baseUrl(baseURL)
	.client(getRequestHeader())
	.addConverterFactory(GsonConverterFactory.create()) // GSON Parser 추가
	.build();
	
// interface 활성화
service = retrofit.create(NetworkInterface.class);  // 인터페이스 연결

//...

// 로그 출력 기능을 retrofit에 추가하기
private static OkHttpClient getRequestHeader() {
	HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
	interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
	OkHttpClient client = new OkHttpClient.Builder()
		.addInterceptor(interceptor).build();
		
	return client;
}</code></pre>

### 5. Call 을 통해 서버와 통신
<pre><code>Call<Network_Authorize> get_userLogin = service.get_userLogin("id", "pw");
	post_userLogin.enqueue(new Callback<Network_User>() {
		@Override
		public void onResponse(Call<Network_User> call, Response<Network_User> response) {
			// 
		}
	
		@Override
		public void onFailure(Call<Network_Authorize> call, Throwable t) {
			// 
		}
	});</code></pre>
	
## 3. 여담

### 1. URI는 동적으로 치환이 가능하게 작성할 수 있다.
<pre><code>// example
// 영문/숫자로 이루어진 문자열을 '{' 와 '}' 로 감싸 정의합니다.
// 매치되는 인수는 '@Path' 로 정의합니다.
@GET("/group/{id}/users")
Call<List<User>> groupList(@Path("id") int groupId);
</code></pre>

> 파리미터 어노테이션 종류  
> @QueryMap : 쿼리를 맵 형태로 보냄 	(ex> @QueryMap Map<String, String> options)  
> @Body : Body 형태로 request를 보냄 (ex> Call<User> createUser(@Body User user); )  
> @Field : FormUrlEncoded 형식으로 보낼 시 설정 
> <pre><code> // example
> @FormUrlEncoded
> @POST("/user/edit")
> Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);</code></pre>
>  @Part : MultiPart 방식으로 보낼 시 선택
> <pre><code> // example
> @Multipart
> @PUT("/user/photo")
> Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);</code></pre>
	
참고
- Retrofit 의 우수성 : http://iw90.tistory.com/123
- OkHttp 와 Retrofit2 : https://tacademy.sktechx.com/front/community/mentoring/viewMentoring.action?seq=1163
- URL / URI : https://blog.lael.be/post/61
- 전체적인 설명 및 가이드라인 : http://flymogi.tistory.com/entry/Retrofit%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EC%9E%90-v202
