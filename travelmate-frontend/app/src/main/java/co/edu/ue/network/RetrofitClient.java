package co.edu.ue.network;

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

<<<<<<< Updated upstream
public class RetrofitClient {

    // URL base del backend
    // 10.0.2.2 = localhost desde el emulador Android
    // 8080 = puerto por defecto de Spring Boot
=======
/**
 * Cliente Retrofit para conectarse con el servidor Spring Boot.
 * 
 * IMPORTANTE: Cambia la URL base según tu configuración:
 * - Emulador Android: usa "http://10.0.2.2:8080" (accede al localhost de tu PC)
 * - Dispositivo físico: usa la IP de tu PC en la red local (ej: "http://192.168.1.5:8080")
 * - Servidor en producción: usa la URL pública del servidor
 */
public class RetrofitClient {

    // URL base del servidor Spring Boot
    // Para emulador Android: 10.0.2.2 apunta al localhost de la máquina anfitriona
>>>>>>> Stashed changes
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

<<<<<<< Updated upstream
    public static ApiService getApiService() {
        if (apiService == null) {
            // Interceptor para ver las peticiones HTTP en logcat (útil en desarrollo)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
=======
    /**
     * Obtiene la instancia única (Singleton) del cliente Retrofit.
     * Si no existe, la crea con la URL base y el converter Gson.
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Interceptor para ver las peticiones HTTP en el logcat (útil para depurar)
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
>>>>>>> Stashed changes
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
<<<<<<< Updated upstream

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
=======
        }
        return retrofit;
    }

    /**
     * Obtiene la instancia única (Singleton) del servicio API.
     * Es la interfaz que define todos los endpoints disponibles.
     */
    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(ApiService.class);
        }
        return apiService;
    }
}
>>>>>>> Stashed changes
