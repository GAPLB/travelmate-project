package co.edu.ue;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Declaramos la variable para la barra de navegación inferior
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Vinculamos esta clase Java con el diseño visual de la actividad principal (activity_main.xml)
        setContentView(R.layout.activity_main);

        // Encontramos la barra de navegación en el XML usando su ID
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Al abrir la aplicación por primera vez, cargamos la pantalla de inicio (HomeFragment) por defecto
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Programamos el evento que detecta cuando el usuario presiona cualquier opción del menú inferior
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            // Evaluamos cuál opción del menú fue presionada y asignamos su respectivo Fragment (pantalla)
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment(); // Pantalla de Inicio
            } else if (itemId == R.id.nav_favorite) {
                selectedFragment = new NotesFragment(); // Pantalla de Notas Offline
            } else if (itemId == R.id.nav_info) {
                selectedFragment = new InfoFragment(); // Pantalla de Información
            }

            // Si se seleccionó un fragmento válido, lo mostramos en pantalla
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true; // Indicamos que el clic fue manejado con éxito
            }
            return false;
        });
    }

    // Método auxiliar encargado de reemplazar el contenido actual del contenedor por el nuevo Fragment seleccionado
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment); // Reemplaza la vista del contenedor principal
        transaction.commit(); // Ejecuta el cambio de pantalla
    }
}