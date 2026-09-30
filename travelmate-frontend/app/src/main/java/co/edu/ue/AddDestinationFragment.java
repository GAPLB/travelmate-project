package co.edu.ue;


import java.time.LocalDate;

import co.edu.ue.model.Destino;
import co.edu.ue.model.Viaje;
import co.edu.ue.network.RetrofitClient;
import co.edu.ue.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import co.edu.ue.utils.LocationUtils;

public class AddDestinationFragment extends Fragment {

    private EditText etDestinationName;
    private EditText etVisitDate;
    private EditText etLatitude;
    private EditText etLongitude;

    private Button btnGetLocation;
    private Button btnSaveDestination;

    private LocationUtils locationUtils;
    private Long viajeId;

    public AddDestinationFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_add_destination,
                container,
                false

        );
        if (getArguments() != null) {
            viajeId = getArguments().getLong("viajeId", -1L);
        }

        // Conectar Java con los elementos del XML
        etDestinationName = view.findViewById(R.id.etDestinationName);
        etVisitDate = view.findViewById(R.id.etVisitDate);
        etLatitude = view.findViewById(R.id.etLatitude);
        etLongitude = view.findViewById(R.id.etLongitude);

        btnGetLocation = view.findViewById(R.id.btnGetLocation);
        btnSaveDestination = view.findViewById(R.id.btnSaveDestination);

        // Utilidad para obtener la ubicación
        locationUtils = new LocationUtils(requireContext());

        // Obtener ubicación del dispositivo
        btnGetLocation.setOnClickListener(v -> {

            if (!LocationUtils.hasLocationPermission(requireContext())) {

                ActivityCompat.requestPermissions(
                        requireActivity(),
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        LocationUtils.REQUEST_LOCATION_PERMISSION
                );

                return;
            }

            locationUtils.getLastLocation(
                    new LocationUtils.OnLocationResultListener() {

                        @Override
                        public void onResult(double latitude, double longitude) {

                            etLatitude.setText(String.valueOf(latitude));
                            etLongitude.setText(String.valueOf(longitude));

                            Toast.makeText(
                                    getContext(),
                                    "Ubicación capturada",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                        @Override
                        public void onError(String error) {

                            Toast.makeText(
                                    getContext(),
                                    "Error: " + error,
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );
        });

        // Guardar destino
        btnSaveDestination.setOnClickListener(v -> {

            String nombre = etDestinationName.getText().toString().trim();
            String fecha = etVisitDate.getText().toString().trim();
            String latitudTexto = etLatitude.getText().toString().trim();
            String longitudTexto = etLongitude.getText().toString().trim();

            // Validar campos
            if (nombre.isEmpty()
                    || fecha.isEmpty()
                    || latitudTexto.isEmpty()
                    || longitudTexto.isEmpty()) {

                Toast.makeText(
                        getContext(),
                        "Por favor completa todos los campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Verificar que recibimos el viaje
            if (viajeId == null || viajeId == -1L) {

                Toast.makeText(
                        getContext(),
                        "No se encontró el viaje asociado",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Verificar conexión
            if (!NetworkUtils.isNetworkAvailable(requireContext())) {
                NetworkUtils.showNoConnectionToast(requireContext());
                return;
            }

            try {

                double latitud = Double.parseDouble(latitudTexto);
                double longitud = Double.parseDouble(longitudTexto);
                if (latitud < -90 || latitud > 90 ||
                        longitud < -180 || longitud > 180) {

                    Toast.makeText(
                            getContext(),
                            "Las coordenadas no son válidas",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                // Crear destino
                Destino destino = new Destino();

                destino.setNombre(nombre);
                destino.setFechaVisita(LocalDate.parse(fecha));
                destino.setLatitud(latitud);
                destino.setLongitud(longitud);

                // Asociar el viaje
                Viaje viaje = new Viaje();
                viaje.setId(viajeId);

                destino.setViaje(viaje);

                // Enviar al backend
                RetrofitClient.getApiService()
                        .crearDestino(destino)
                        .enqueue(new Callback<Destino>() {

                            @Override
                            public void onResponse(
                                    Call<Destino> call,
                                    Response<Destino> response) {

                                if (response.isSuccessful()) {

                                    Toast.makeText(
                                            getContext(),
                                            "¡Destino guardado con éxito!",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    requireActivity().onBackPressed();

                                } else {

                                    Toast.makeText(
                                            getContext(),
                                            "Error al guardar el destino",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }

                            @Override
                            public void onFailure(
                                    Call<Destino> call,
                                    Throwable t) {

                                Toast.makeText(
                                        getContext(),
                                        "Error de conexión: " + t.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });

            } catch (Exception e) {

                Toast.makeText(
                        getContext(),
                        "Verifica la fecha y las coordenadas",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        return view;


    }
}