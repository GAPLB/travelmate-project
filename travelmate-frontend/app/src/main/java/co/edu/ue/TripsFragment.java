package co.edu.ue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import co.edu.ue.model.Viaje;
import co.edu.ue.network.RetrofitClient;
import co.edu.ue.utils.NetworkUtils;
import co.edu.ue.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripsFragment extends Fragment {

    private ListView listViewTrips;
    private FloatingActionButton fabAddTrip;

    // Lista de viajes y adaptador para mostrarlos en el ListView
    private List<Viaje> listaViajes;
    private ArrayAdapter<String> adaptador;

    // Manejador de sesión para obtener el ID del usuario logueado
    private SessionManager sessionManager;

    public TripsFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        // Inicializar el manejador de sesión
        sessionManager = new SessionManager(requireContext());

        listViewTrips = view.findViewById(R.id.listViewTrips);
        fabAddTrip = view.findViewById(R.id.fabAddTrip);

        // Inicializar la lista y el adaptador
        listaViajes = new ArrayList<>();
        adaptador = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        listViewTrips.setAdapter(adaptador);

        // Botón flotante (+) para abrir el formulario de creación de un nuevo viaje
        fabAddTrip.setOnClickListener(v -> {
            AddTripFragment formFragment = new AddTripFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, formFragment);
            transaction.addToBackStack(null); // Permite volver atrás con el botón del celular
            transaction.commit();
        });

        // Cargar los viajes del usuario desde la API
        cargarViajes();

        return view;
    }

    /**
     * Carga los viajes del usuario logueado desde el servidor Spring Boot.
     * URL: GET /api/viaje/usuario/{usuarioId}
     */
    private void cargarViajes() {
        // Verificar conexión a Internet
        if (!NetworkUtils.hayConexionInternet(requireContext())) {
            Toast.makeText(getContext(), "No hay conexión a Internet. No se pueden cargar los viajes.", Toast.LENGTH_LONG).show();
            return;
        }

        // Verificar que el usuario esté logueado
        if (!sessionManager.estaLogueado()) {
            Toast.makeText(getContext(), "Debes iniciar sesión para ver tus viajes.", Toast.LENGTH_LONG).show();
            return;
        }

        Long usuarioId = sessionManager.getUserId();

        // Realizar la petición GET al servidor
        RetrofitClient.getApiService().obtenerViajesPorUsuario(usuarioId).enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaViajes = response.body();
                    mostrarViajes();
                } else {
                    Toast.makeText(getContext(), "Error al cargar los viajes: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Muestra los viajes cargados en el ListView.
     */
    private void mostrarViajes() {
        List<String> textosViajes = new ArrayList<>();

        for (Viaje viaje : listaViajes) {
            String texto = viaje.getTitulo() + "\n" +
                    "Del " + viaje.getFechaInicio() + " al " + viaje.getFechaFin();
            if (viaje.getDescripcion() != null && !viaje.getDescripcion().isEmpty()) {
                texto += "\n" + viaje.getDescripcion();
            }
            textosViajes.add(texto);
        }

        adaptador.clear();
        adaptador.addAll(textosViajes);
        adaptador.notifyDataSetChanged();
    }
}
