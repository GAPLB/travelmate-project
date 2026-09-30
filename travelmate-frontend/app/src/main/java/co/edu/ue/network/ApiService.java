package co.edu.ue.network;


import co.edu.ue.dto.LoginDTO;
import co.edu.ue.dto.LoginResponse;
import co.edu.ue.dto.UsuarioDTO;
import co.edu.ue.model.ActividadGasto;
import co.edu.ue.model.Destino;
import co.edu.ue.model.Viaje;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ApiService {

    // ========== USUARIOS ==========
    @POST("/api/usuarios/registro")
    Call<String> registrarUsuario(@Body UsuarioDTO usuario);

    @POST("/api/usuarios/login")
    Call<LoginResponse> login(@Body LoginDTO loginDTO);

    // ========== VIAJES ==========
    @GET("/api/viaje/usuario/{usuarioId}")
    Call<List<Viaje>> listarViajesPorUsuario(@Path("usuarioId") Long usuarioId);

    @GET("/api/viaje/{id}")
    Call<Viaje> obtenerViaje(@Path("id") Long id);

    @POST("/api/viaje")
    Call<Viaje> crearViaje(@Body Viaje viaje);

    @PUT("/api/viaje/{id}")
    Call<Viaje> actualizarViaje(@Path("id") Long id, @Body Viaje viaje);

    @DELETE("/api/viaje/{id}")
    Call<Void> eliminarViaje(@Path("id") Long id);

    // ========== DESTINOS ==========
    @GET("/api/destinos/viaje/{viajeId}")
    Call<List<Destino>> listarDestinosPorViaje(@Path("viajeId") Long viajeId);

    @GET("/api/destinos/{id}")
    Call<Destino> obtenerDestino(@Path("id") Long id);

    @POST("/api/destinos")
    Call<Destino> crearDestino(@Body Destino destino);

    @PUT("/api/destinos/{id}")
    Call<Destino> actualizarDestino(@Path("id") Long id, @Body Destino destino);

    @DELETE("/api/destinos/{id}")
    Call<Void> eliminarDestino(@Path("id") Long id);

    // ========== ACTIVIDADES/GASTOS ==========
    @GET("/api/actividades-gastos/viaje/{viajeId}")
    Call<List<ActividadGasto>> listarGastosPorViaje(@Path("viajeId") Long viajeId);

    @GET("/api/actividades-gastos/{id}")
    Call<ActividadGasto> obtenerGasto(@Path("id") Long id);

    @POST("/api/actividades-gastos")
    Call<ActividadGasto> crearGasto(@Body ActividadGasto gasto);

    @PUT("/api/actividades-gastos/{id}")
    Call<ActividadGasto> actualizarGasto(@Path("id") Long id, @Body ActividadGasto gasto);

    @DELETE("/api/actividades-gastos/{id}")
    Call<Void> eliminarGasto(@Path("id") Long id);
}