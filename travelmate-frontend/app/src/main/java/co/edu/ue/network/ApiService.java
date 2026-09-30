package co.edu.ue.network;

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
import co.edu.ue.dto.LoginDTO;
import co.edu.ue.dto.LoginResponse;
import co.edu.ue.dto.UsuarioDTO;
import co.edu.ue.model.ActividadGasto;
import co.edu.ue.model.Destino;
import co.edu.ue.model.Viaje;
import retrofit2.Call;
<<<<<<< Updated upstream
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
=======
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

/**
 * Interfaz que define todos los endpoints de la API REST de Spring Boot.
 * Retrofit usa esta interfaz para generar las peticiones HTTP automáticamente.
 */
public interface ApiService {

    // ==================== USUARIOS ====================

    /**
     * Registra un nuevo usuario en el servidor.
     * URL: POST /api/usuarios/registro
     */
    @POST("/api/usuarios/registro")
    Call<String> registrarUsuario(@Body UsuarioDTO usuarioDTO);

    /**
     * Inicia sesión con email y contraseña.
     * URL: POST /api/usuarios/login
     */
    @POST("/api/usuarios/login")
    Call<LoginResponse> login(@Body LoginDTO loginDTO);

    // ==================== VIAJES ====================

    /**
     * Obtiene todos los viajes de un usuario específico.
     * URL: GET /api/viaje/usuario/{usuarioId}
     */
    @GET("/api/viaje/usuario/{usuarioId}")
    Call<List<Viaje>> obtenerViajesPorUsuario(@Path("usuarioId") Long usuarioId);

    /**
     * Obtiene un viaje específico por su ID.
     * URL: GET /api/viaje/{id}
     */
    @GET("/api/viaje/{id}")
    Call<Viaje> obtenerViajePorId(@Path("id") Long id);

    /**
     * Crea un nuevo viaje en el servidor.
     * URL: POST /api/viaje
     */
    @POST("/api/viaje")
    Call<Viaje> crearViaje(@Body Viaje viaje);

    /**
     * Actualiza un viaje existente.
     * URL: PUT /api/viaje/{id}
     */
    @PUT("/api/viaje/{id}")
    Call<Viaje> actualizarViaje(@Path("id") Long id, @Body Viaje viaje);

    /**
     * Elimina un viaje del servidor.
     * URL: DELETE /api/viaje/{id}
     */
    @DELETE("/api/viaje/{id}")
    Call<Void> eliminarViaje(@Path("id") Long id);

    // ==================== DESTINOS ====================

    /**
     * Obtiene todos los destinos de un viaje específico.
     * URL: GET /api/destinos/viaje/{viajeId}
     */
    @GET("/api/destinos/viaje/{viajeId}")
    Call<List<Destino>> obtenerDestinosPorViaje(@Path("viajeId") Long viajeId);

    /**
     * Obtiene un destino específico por su ID.
     * URL: GET /api/destinos/{id}
     */
    @GET("/api/destinos/{id}")
    Call<Destino> obtenerDestinoPorId(@Path("id") Long id);

    /**
     * Crea un nuevo destino en el servidor.
     * URL: POST /api/destinos
     */
    @POST("/api/destinos")
    Call<Destino> crearDestino(@Body Destino destino);

    /**
     * Actualiza un destino existente.
     * URL: PUT /api/destinos/{id}
     */
    @PUT("/api/destinos/{id}")
    Call<Destino> actualizarDestino(@Path("id") Long id, @Body Destino destino);

    /**
     * Elimina un destino del servidor.
     * URL: DELETE /api/destinos/{id}
     */
    @DELETE("/api/destinos/{id}")
    Call<Void> eliminarDestino(@Path("id") Long id);

    // ==================== ACTIVIDADES/GASTOS ====================

    /**
     * Obtiene todas las actividades/gastos de un viaje específico.
     * URL: GET /api/actividades-gastos/viaje/{viajeId}
     */
    @GET("/api/actividades-gastos/viaje/{viajeId}")
    Call<List<ActividadGasto>> obtenerGastosPorViaje(@Path("viajeId") Long viajeId);

    /**
     * Obtiene una actividad/gasto específico por su ID.
     * URL: GET /api/actividades-gastos/{id}
     */
    @GET("/api/actividades-gastos/{id}")
    Call<ActividadGasto> obtenerGastoPorId(@Path("id") Long id);

    /**
     * Crea una nueva actividad/gasto en el servidor.
     * URL: POST /api/actividades-gastos
     */
    @POST("/api/actividades-gastos")
    Call<ActividadGasto> crearGasto(@Body ActividadGasto gasto);

    /**
     * Actualiza una actividad/gasto existente.
     * URL: PUT /api/actividades-gastos/{id}
     */
    @PUT("/api/actividades-gastos/{id}")
    Call<ActividadGasto> actualizarGasto(@Path("id") Long id, @Body ActividadGasto gasto);

    /**
     * Elimina una actividad/gasto del servidor.
     * URL: DELETE /api/actividades-gastos/{id}
     */
    @DELETE("/api/actividades-gastos/{id}")
    Call<Void> eliminarGasto(@Path("id") Long id);
}
>>>>>>> Stashed changes
