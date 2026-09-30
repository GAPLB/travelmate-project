package co.edu.ue.utils;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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