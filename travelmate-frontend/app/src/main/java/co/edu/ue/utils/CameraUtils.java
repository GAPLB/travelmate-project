package co.edu.ue.utils;

<<<<<<< Updated upstream

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
=======
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

>>>>>>> Stashed changes
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
<<<<<<< Updated upstream

public class CameraUtils {

    public static final int REQUEST_CAMERA_PERMISSION = 100;
    public static final int REQUEST_IMAGE_CAPTURE = 101;

    /**
     * Verifica si el permiso de cámara está concedido
     */
    public static boolean hasCameraPermission(Fragment fragment) {
        return ContextCompat.checkSelfPermission(fragment.requireContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Solicita el permiso de cámara
     */
    public static void requestCameraPermission(Fragment fragment) {
        ActivityCompat.requestPermissions(fragment.requireActivity(),
                new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    /**
     * Crea un archivo temporal para guardar la foto capturada
     */
    public static File createImageFile(Fragment fragment) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TRAVELMATE_" + timeStamp + "_";
        File storageDir = fragment.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    /**
     * Obtiene la URI del archivo para compartir con la cámara
     */
    public static Uri getImageUri(Fragment fragment, File file) {
        return FileProvider.getUriForFile(fragment.requireContext(),
                fragment.requireContext().getPackageName() + ".fileprovider", file);
    }
}
=======
import java.util.Locale;

/**
 * Utilidad para manejar la cámara del dispositivo.
 * Provee métodos para verificar permisos y crear archivos temporales
 * donde se guardarán las fotografías capturadas.
 */
public class CameraUtils {

    /**
     * Verifica si la aplicación tiene permiso para usar la cámara.
     */
    public static boolean tienePermisoCamara(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Crea un archivo temporal en el almacenamiento externo para guardar una foto.
     * El archivo se crea en la carpeta de imágenes de la aplicación.
     * 
     * @return File apuntando al archivo creado (aún vacío)
     * @throws IOException si no se puede crear el archivo
     */
    public static File crearArchivoFoto(Context context) throws IOException {
        // Nombre único basado en la fecha y hora actual
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "TRAVELMATE_" + timeStamp + "_";

        // Directorio de imágenes de la aplicación
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Crear el archivo temporal
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);

        return imageFile;
    }

    /**
     * Obtiene la URI de un archivo usando FileProvider.
     * Esta URI es necesaria para pasarla a la intención de la cámara.
     */
    public static Uri obtenerUriFoto(Context context, File file) {
        return FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".fileprovider",
                file
        );
    }
}
>>>>>>> Stashed changes
