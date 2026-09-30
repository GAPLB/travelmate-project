package co.edu.ue;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import co.edu.ue.model.Viaje;
import co.edu.ue.network.RetrofitClient;
import co.edu.ue.utils.NetworkUtils;
import co.edu.ue.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class TripsFragment extends Fragment {

    private ListView listViewTrips;
    private FloatingActionButton fabAddTrip;
    private SessionManager sessionManager;
    private List<Viaje> viajesList = new ArrayList<>();

    public TripsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        sessionManager = new SessionManager(requireContext());
        listViewTrips = view.findViewById(R.id.listViewTrips);
        fabAddTrip = view.findViewById(R.id.fabAddTrip);

        fabAddTrip.setOnClickListener(v -> {
            AddTripFragment formFragment = new AddTripFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, formFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Cargar viajes desde la API
        loadTripsFromApi();

        return view;
    }

    private void loadTripsFromApi() {
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(getContext(), "Inicia sesión para ver tus viajes", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            NetworkUtils.showNoConnectionMessage(getView(), requireContext());
            // Aquí podrías cargar datos cacheados desde SQLite
            return;
        }

        Long userId = sessionManager.getUserId();
        RetrofitClient.getApiService().listarViajesPorUsuario(userId).enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    viajesList = response.body();
                    List<String> tripNames = new ArrayList<>();
                    for (Viaje v : viajesList) {
                        tripNames.add(v.getTitulo());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            requireContext(), android.R.layout.simple_list_item_1, tripNames);
                    listViewTrips.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Toast.makeText(getContext(), "Error al cargar viajes: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}