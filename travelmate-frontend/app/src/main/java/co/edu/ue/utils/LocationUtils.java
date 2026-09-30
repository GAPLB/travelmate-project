package co.edu.ue.utils;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.*;

public class LocationUtils {

    public static final int REQUEST_LOCATION_PERMISSION = 200;

    private FusedLocationProviderClient fusedLocationClient;
    private Context context;

    public LocationUtils(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    /**
     * Verifica si los permisos de ubicación están concedidos
     */
    public static boolean hasLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Obtiene la última ubicación conocida del dispositivo
     */
    public void getLastLocation(OnLocationResultListener listener) {
        if (!hasLocationPermission(context)) {
            listener.onError("Permiso de ubicación no concedido");
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        listener.onResult(location.getLatitude(), location.getLongitude());
                    } else {
                        listener.onError("No se pudo obtener la ubicación");
                    }
                })
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    /**
     * Solicita actualizaciones de ubicación en tiempo real
     */
    public void requestLocationUpdates(LocationCallback callback) {
        if (!hasLocationPermission(context)) return;

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)  // 10 segundos
                .setFastestInterval(5000);  // 5 segundos

        fusedLocationClient.requestLocationUpdates(locationRequest,
                callback, Looper.getMainLooper());
    }

    public interface OnLocationResultListener {
        void onResult(double latitude, double longitude);
        void onError(String error);
    }
}