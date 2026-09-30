package co.edu.ue.utils;

import android.content.Context;
import android.content.SharedPreferences;
import co.edu.ue.dto.LoginResponse;

public class SessionManager {

    private static final String PREF_NAME = "TravelMateSession";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

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
    public Long getUserId() {
        return pref.getLong(KEY_USER_ID, -1);
    }

    // Obtener nombre del usuario
    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }

    // Obtener email del usuario
    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, "");
    }

    // Cerrar sesión
    public void logout() {
        editor.clear();
        editor.apply();
    }
}