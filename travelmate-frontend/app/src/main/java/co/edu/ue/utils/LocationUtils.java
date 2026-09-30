package co.edu.ue.utils;

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
<<<<<<< Updated upstream
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

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {

            listener.onError("Permiso de ubicación no concedido");
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        listener.onResult(
                                location.getLatitude(),
                                location.getLongitude()
                        );
                    } else {
                        listener.onError("No se pudo obtener la ubicación");
                    }
                })
                .addOnFailureListener(e ->
                        listener.onError(e.getMessage())
                );
    }

    /**
     * Solicita actualizaciones de ubicación en tiempo real
     */
    public void requestLocationUpdates(LocationCallback callback) {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);

        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                callback,
                Looper.getMainLooper()
        );
    }

    public interface OnLocationResultListener {
        void onResult(double latitude, double longitude);
        void onError(String error);
    }
}
=======
import android.location.LocationManager;

import androidx.core.content.ContextCompat;

/**
 * Utilidad para manejar la ubicación del dispositivo.
 * Provee métodos para verificar permisos y obtener la última ubicación conocida.
 */
public class LocationUtils {

    /**
     * Verifica si la aplicación tiene permisos de ubicación.
     */
    public static boolean tienePermisoUbicacion(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Verifica si el GPS está activado en el dispositivo.
     */
    public static boolean gpsActivado(Context context) {
        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.G_PROVIDER);
    }

    /**
     * Obtiene la última ubicación conocida del dispositivo.
     * Retorna null si no hay ubicación disponible o no hay permisos.
     */
    public static Location obtenerUltimaUbicacion(Context context) {
        if (!tienePermisoUbicacion(context)) {
            return null;
        }

        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager == null) {
            return null;
        }

        // Intentar obtener la última ubicación conocida de diferentes proveedores
        Location bestLocation = null;

        try {
            Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            // Elegir la más reciente entre GPS y red
            if (gpsLocation != null && networkLocation != null) {
                bestLocation = gpsLocation.getTime() > networkLocation.getTime() ? gpsLocation : networkLocation;
            } else if (gpsLocation != null) {
                bestLocation = gpsLocation;
            } else {
                bestLocation = networkLocation;
            }
        } catch (SecurityException e) {
            // No hay permisos suficientes
            return null;
        }

        return bestLocation;
    }

    /**
     * Obtiene la latitud de la última ubicación conocida.
     * Retorna null si no hay ubicación disponible.
     */
    public static Double obtenerLatitud(Context context) {
        Location location = obtenerUltimaUbicacion(context);
        return location != null ? location.getLatitude() : null;
    }

    /**
     * Obtiene la longitud de la última ubicación conocida.
     * Retorna null si no hay ubicación disponible.
     */
    public static Double obtenerLongitud(Context context) {
        Location location = obtenerUltimaUbicacion(context);
        return location != null ? location.getLongitude() : null;
    }
}
>>>>>>> Stashed changes
