package co.edu.ue;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import co.edu.ue.model.Usuario;
import co.edu.ue.model.Viaje;
import co.edu.ue.network.RetrofitClient;
import co.edu.ue.utils.NetworkUtils;
import co.edu.ue.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.time.LocalDate;

public class AddTripFragment extends Fragment {

    private EditText etTripTitle, etTripDescription, etTripStartDate, etTripEndDate;
    private Button btnSaveTrip;
    private SessionManager sessionManager;

    public AddTripFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);

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
                // Verificar conexión
                if (!NetworkUtils.isNetworkAvailable(requireContext())) {
                    NetworkUtils.showNoConnectionToast(requireContext());
                    return;
                }

                // Crear objeto Viaje
                Viaje viaje = new Viaje();
                viaje.setTitulo(title);
                viaje.setDescripcion(description);
                viaje.setFechaInicio(LocalDate.parse(startDate));
                viaje.setFechaFin(LocalDate.parse(endDate));

                // Asociar el usuario logueado
                Usuario usuario = new Usuario();
                usuario.setId(sessionManager.getUserId());
                viaje.setUsuario(usuario);

                // Guardar en la API
                RetrofitClient.getApiService().crearViaje(viaje).enqueue(new Callback<Viaje>() {
                    @Override
                    public void onResponse(Call<Viaje> call, Response<Viaje> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "¡Viaje registrado con éxito!", Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed();
                        } else {
                            Toast.makeText(getContext(), "Error al guardar el viaje", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Viaje> call, Throwable t) {
                        Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}