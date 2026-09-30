package co.edu.ue.network;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // URL base del backend
    // 10.0.2.2 = localhost desde el emulador Android
    // 8080 = puerto por defecto de Spring Boot
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

    public static ApiService getApiService() {
        if (apiService == null) {
            // Interceptor para ver las peticiones HTTP en logcat (útil en desarrollo)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}