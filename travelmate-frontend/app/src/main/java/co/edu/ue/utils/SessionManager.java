package co.edu.ue.utils;

import android.content.Context;
import android.content.SharedPreferences;
<<<<<<< Updated upstream
import co.edu.ue.dto.LoginResponse;

public class SessionManager {

    private static final String PREF_NAME = "TravelMateSession";
=======

/**
 * Maneja la sesión activa del usuario usando SharedPreferences.
 * Guarda los datos del usuario logueado para mantener la sesión
 * incluso si se cierra y se abre la aplicación.
 */
public class SessionManager {

    // Nombre del archivo SharedPreferences
    private static final String PREF_NAME = "TravelMateSession";

    // Claves para guardar los datos del usuario
>>>>>>> Stashed changes
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

<<<<<<< Updated upstream
=======
    /**
     * Constructor. Recibe el contexto de la aplicación.
     */
>>>>>>> Stashed changes
    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

<<<<<<< Updated upstream
    // Guardar sesión después de login exitoso
    public void saveSession(LoginResponse user) {
        editor.putLong(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getNombre());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    // Verificar si el usuario está logueado
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Obtener ID del usuario logueado
=======
    /**
     * Guarda los datos del usuario después de un login exitoso.
     */
    public void guardarSesion(Long userId, String nombre, String email) {
        editor.putLong(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, nombre);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    /**
     * Verifica si el usuario tiene una sesión activa.
     */
    public boolean estaLogueado() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Obtiene el ID del usuario logueado.
     */
>>>>>>> Stashed changes
    public Long getUserId() {
        return pref.getLong(KEY_USER_ID, -1);
    }

<<<<<<< Updated upstream
    // Obtener nombre del usuario
=======
    /**
     * Obtiene el nombre del usuario logueado.
     */
>>>>>>> Stashed changes
    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }

<<<<<<< Updated upstream
    // Obtener email del usuario
=======
    /**
     * Obtiene el email del usuario logueado.
     */
>>>>>>> Stashed changes
    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, "");
    }

<<<<<<< Updated upstream
    // Cerrar sesión
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
=======
    /**
     * Cierra la sesión del usuario (borra todos los datos guardados).
     */
    public void cerrarSesion() {
        editor.clear();
        editor.commit();
    }
}
>>>>>>> Stashed changes
