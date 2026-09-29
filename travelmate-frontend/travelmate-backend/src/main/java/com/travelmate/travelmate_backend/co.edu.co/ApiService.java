package co.edu.ue;

import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // ---------- Usuarios ----------
    @POST("api/usuarios/registro")
    Call<ResponseBody> registrar(@Body RegistroRequest request);

    @POST("api/usuarios/login")
    Call<UsuarioResponse> login(@Body LoginRequest request);

    // ---------- Viajes ----------
    @POST("api/viajes")
    Call<Viaje> crearViaje(@Body Viaje viaje);

    @GET("api/viajes/usuario/{usuarioId}")
    Call<List<Viaje>> listarViajes(@Path("usuarioId") long usuarioId);

    @GET("api/viajes/{id}")
    Call<Viaje> obtenerViaje(@Path("id") long id);

    @PUT("api/viajes/{id}")
    Call<Viaje> actualizarViaje(@Path("id") long id, @Body Viaje viaje);

    @DELETE("api/viajes/{id}")
    Call<ResponseBody> eliminarViaje(@Path("id") long id);

    // ---------- Destinos ----------
    @POST("api/destinos")
    Call<Destino> crearDestino(@Body Destino destino);

    @GET("api/destinos/viaje/{viajeId}")
    Call<List<Destino>> listarDestinos(@Path("viajeId") long viajeId);

    @GET("api/destinos/{id}")
    Call<Destino> obtenerDestino(@Path("id") long id);

    @PUT("api/destinos/{id}")
    Call<Destino> actualizarDestino(@Path("id") long id, @Body Destino destino);

    @DELETE("api/destinos/{id}")
    Call<ResponseBody> eliminarDestino(@Path("id") long id);

    // ---------- Actividades / Gastos ----------
    @POST("api/actividades-gastos")
    Call<ActividadGasto> crearGasto(@Body ActividadGasto gasto);

    @GET("api/actividades-gastos/viaje/{viajeId}")
    Call<List<ActividadGasto>> listarGastos(@Path("viajeId") long viajeId);

    @GET("api/actividades-gastos/{id}")
    Call<ActividadGasto> obtenerGasto(@Path("id") long id);

    @PUT("api/actividades-gastos/{id}")
    Call<ActividadGasto> actualizarGasto(@Path("id") long id, @Body ActividadGasto gasto);

    @DELETE("api/actividades-gastos/{id}")
    Call<ResponseBody> eliminarGasto(@Path("id") long id);
}