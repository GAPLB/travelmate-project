package co.edu.ue.utils;


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