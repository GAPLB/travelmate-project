package co.edu.ue.utils;

<<<<<<< Updated upstream

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

public class NetworkUtils {

    /**
     * Verifica si el dispositivo tiene conexión a Internet
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(
                    connectivityManager.getActiveNetwork());
            return capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }

    /**
     * Muestra un Snackbar de "Sin conexión" si no hay Internet
     */
    public static void showNoConnectionMessage(View view, Context context) {
        if (!isNetworkAvailable(context)) {
            Snackbar.make(view, "Sin conexión a Internet. Mostrando datos cacheados.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Muestra un Toast de "Sin conexión"
     */
    public static void showNoConnectionToast(Context context) {
        Toast.makeText(context, "Sin conexión a Internet", Toast.LENGTH_SHORT).show();
    }
}
=======
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utilidad para detectar el estado de conexión a Internet.
 * Usa ConnectivityManager para verificar si el dispositivo tiene
 * una red activa y si esa red tiene conexión a Internet.
 */
public class NetworkUtils {

    /**
     * Verifica si el dispositivo tiene conexión a Internet.
     * 
     * @param context Contexto de la aplicación
     * @return true si hay conexión, false si no hay conexión
     */
    public static boolean hayConexionInternet(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    /**
     * Verifica si el dispositivo está conectado a WiFi.
     */
    public static boolean esWifi(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiInfo != null && wifiInfo.isConnected();
        }
        return false;
    }

    /**
     * Verifica si el dispositivo está conectado a datos móviles.
     */
    public static boolean esDatosMoviles(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return mobileInfo != null && mobileInfo.isConnected();
        }
        return false;
    }
}
>>>>>>> Stashed changes
