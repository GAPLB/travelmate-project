package co.edu.ue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import co.edu.ue.model.Viaje;
import co.edu.ue.network.RetrofitClient;
import co.edu.ue.utils.NetworkUtils;
import co.edu.ue.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTripFragment extends Fragment {

    private EditText etTripTitle, etTripDescription, etTripStartDate, etTripEndDate;
    private Button btnSaveTrip;

    // Manejador de sesión para obtener el ID del usuario logueado
    private SessionManager sessionManager;

    public AddTripFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);

        // Inicializar el manejador de sesión
        sessionManager = new SessionManager(requireContext());

        etTripTitle = view.findViewById(R.id.etTripTitle);
        etTripDescription = view.findViewById(R.id.etTripDescription);
        etTripStartDate = view.findViewById(R.id.etTripStartDate);
        etTripEndDate = view.findViewById(R.id.etTripEndDate);
        btnSaveTrip = view.findViewById(R.id.btnSaveTrip);

        btnSaveTrip.setOnClickListener(v -> {
            String title = etTripTitle.getText().toString().trim();
            String description = etTripDescription.getText().toString().trim();
            String startDate = etTripStartDate.getText().toString().trim();
            String endDate = etTripEndDate.getText().toString().trim();

            if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(getContext(), "Por favor completa los campos obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                // Verificar conexión a Internet
                if (!NetworkUtils.hayConexionInternet(requireContext())) {
                    Toast.makeText(getContext(), "No hay conexión a Internet. No se puede guardar el viaje.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Verificar que el usuario esté logueado
                if (!sessionManager.estaLogueado()) {
                    Toast.makeText(getContext(), "Debes iniciar sesión para registrar un viaje.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Crear el objeto Viaje y enviarlo al servidor
                guardarViaje(title, description, startDate, endDate);
            }
        });

        return view;
    }

    /**
     * Crea un objeto Viaje y lo envía al servidor Spring Boot.
     * URL: POST /api/viaje
     */
    private void guardarViaje(String titulo, String descripcion, String fechaInicio, String fechaFin) {
        // Crear el objeto Viaje con los datos del formulario
        Viaje viaje = new Viaje();
        viaje.setTitulo(titulo);
        viaje.setDescripcion(descripcion);
        viaje.setFechaInicio(fechaInicio);
        viaje.setFechaFin(fechaFin);
        viaje.setUsuarioId(sessionManager.getUserId());

        // Enviar la petición POST al servidor
        RetrofitClient.getApiService().crearViaje(viaje).enqueue(new Callback<Viaje>() {
            @Override
            public void onResponse(Call<Viaje> call, Response<Viaje> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Viaje viajeCreado = response.body();
                    Toast.makeText(getContext(), "¡Viaje '" + viajeCreado.getTitulo() + "' registrado con éxito! (ID: " + viajeCreado.getId() + ")", Toast.LENGTH_LONG).show();
                    requireActivity().onBackPressed(); // Regresa a la lista de viajes
                } else {
                    Toast.makeText(getContext(), "Error al guardar el viaje: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Viaje> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
