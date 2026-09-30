package co.edu.ue;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import co.edu.ue.model.ActividadGasto;
import co.edu.ue.model.Viaje;
import co.edu.ue.network.RetrofitClient;
import co.edu.ue.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseFragment extends Fragment {

    private Spinner spinnerCategory;
    private EditText etExpenseConcept, etExpenseAmount, etExpenseDate;
    private Button btnSaveExpense;
    private String selectedCategory = "";

    public ExpenseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        etExpenseConcept = view.findViewById(R.id.etExpenseConcept);
        etExpenseAmount = view.findViewById(R.id.etExpenseAmount);
        etExpenseDate = view.findViewById(R.id.etExpenseDate);
        btnSaveExpense = view.findViewById(R.id.btnSaveExpense);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), R.array.expense_categories,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = "";
            }
        });

        btnSaveExpense.setOnClickListener(v -> {
            String concept = etExpenseConcept.getText().toString().trim();
            String amount = etExpenseAmount.getText().toString().trim();
            String date = etExpenseDate.getText().toString().trim();

            if (selectedCategory.equals("Seleccione una categoría...") || concept.isEmpty() || amount.isEmpty()) {
                Toast.makeText(getContext(), "Por favor selecciona una categoría válida y llena los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Verificar conexión
                if (!NetworkUtils.isNetworkAvailable(requireContext())) {
                    NetworkUtils.showNoConnectionToast(requireContext());
                    return;
                }

                // Crear objeto ActividadGasto
                ActividadGasto gasto = new ActividadGasto();
                gasto.setConcepto(concept);
                gasto.setMonto(new BigDecimal(amount));
                gasto.setCategoria(selectedCategory);
                if (!date.isEmpty()) {
                    gasto.setFecha(LocalDate.parse(date));
                }

                // Aquí necesitarías asociar el viaje seleccionado
                // gasto.setViaje(viajeSeleccionado);

                // Guardar en la API
                RetrofitClient.getApiService().crearGasto(gasto).enqueue(new Callback<ActividadGasto>() {
                    @Override
                    public void onResponse(Call<ActividadGasto> call, Response<ActividadGasto> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Gasto registrado en: " + selectedCategory, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error al guardar el gasto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ActividadGasto> call, Throwable t) {
                        Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}