package co.edu.ue;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TripsFragment extends Fragment {

    private ListView listViewTrips;
    private FloatingActionButton fabAddTrip;

    public TripsFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Vinculamos este Fragment con el diseño XML que creamos
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        listViewTrips = view.findViewById(R.id.listViewTrips);
        fabAddTrip = view.findViewById(R.id.fabAddTrip);

        // Botón flotante (+) para abrir el formulario de creación de un nuevo viaje
        fabAddTrip.setOnClickListener(v -> {
            // Navegamos al fragmento del formulario de viajes
            AddTripFragment formFragment = new AddTripFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, formFragment);
            transaction.addToBackStack(null); // Permite volver atrás con el botón del celular
            transaction.commit();
        });

        // Aquí Yudy programará la carga de los viajes de la API más adelante

        return view;
    }
}