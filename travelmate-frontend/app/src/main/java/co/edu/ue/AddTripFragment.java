package co.edu.ue;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTripFragment extends Fragment {

    private EditText etTripTitle, etTripDescription, etTripStartDate, etTripEndDate;
    private Button btnSaveTrip;

    public AddTripFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Vinculamos con el diseño XML del formulario de viajes que ya creaste
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);

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
                // Aquí Yudy conectará el guardado del viaje con la API de Spring Boot más adelante
                Toast.makeText(getContext(), "¡Viaje registrado con éxito!", Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed(); // Regresa a la lista de viajes
            }
        });

        return view;
    }
}