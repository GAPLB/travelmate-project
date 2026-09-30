package co.edu.ue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.math.BigDecimal;

import co.edu.ue.model.ActividadGasto;
import co.edu.ue.network.RetrofitClient;
import co.edu.ue.utils.NetworkUtils;
import co.edu.ue.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseFragment extends Fragment {

    // Declaración de los componentes de la vista
    private Spinner spinnerCategory;
    private EditText etExpenseConcept, etExpenseAmount, etExpenseDate;
    private Button btnSaveExpense;

    // Variable para almacenar la opción seleccionada en el menú desplegable
    private String selectedCategory = "";

    // Manejador de sesión para obtener el ID del usuario logueado
    private SessionManager sessionManager;

    public ExpenseFragment() {
        // Constructor vacío requerido por los Fragments
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el diseño visual del fragmento
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        // Inicializar el manejador de sesión
        sessionManager = new SessionManager(requireContext());

        // Enlazamos los componentes del XML con las variables Java
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        etExpenseConcept = view.findViewById(R.id.etExpenseConcept);
        etExpenseAmount = view.findViewById(R.id.etExpenseAmount);
        etExpenseDate = view.findViewById(R.id.etExpenseDate);
        btnSaveExpense = view.findViewById(R.id.btnSaveExpense);

        // 1. Configurar el adaptador del Spinner vinculándolo con las opciones del strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.expense_categories,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // 2. Programar la lógica dinámica de selección para que el formulario se adapte solo
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();

                // Cambiamos el texto de ayuda (hint) del concepto dependiendo de la categoría elegida
                if (selectedCategory.equals("TRANSPORTE")) {
                    etExpenseConcept.setHint("Ej: Tiquete aéreo, Taxi, Bus...");
                } else if (selectedCategory.equals("COMIDA")) {
                    etExpenseConcept.setHint("Ej: Almuerzo típico, Cena, Snacks...");
                } else if (selectedCategory.equals("HOSPEDAJE")) {
                    etExpenseConcept.setHint("Ej: Hotel, Hostal, Airbnb...");
                } else if (selectedCategory.equals("ACTIVIDAD")) {
                    etExpenseConcept.setHint("Ej: Tour guiado, Entrada a museo...");
                } else {
                    etExpenseConcept.setHint("Concepto del gasto o actividad");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = "";
            }
        });

        // 3. Programar la acción del botón Guardar
        btnSaveExpense.setOnClickListener(v -> {
            String concept = etExpenseConcept.getText().toString().trim();
            String amount = etExpenseAmount.getText().toString().trim();
            String date = etExpenseDate.getText().toString().trim();

            // Validamos que se haya seleccionado una categoría real y llenado los campos obligatorios
            if (selectedCategory.equals("Seleccione una categoría...") || concept.isEmpty() || amount.isEmpty()) {
                Toast.makeText(getContext(), "Por favor selecciona una categoría válida y llena los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Verificar conexión a Internet
                if (!NetworkUtils.hayConexionInternet(requireContext())) {
                    Toast.makeText(getContext(), "No hay conexión a Internet. No se puede guardar el gasto.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Verificar que el usuario esté logueado
                if (!sessionManager.estaLogueado()) {
                    Toast.makeText(getContext(), "Debes iniciar sesión para registrar un gasto.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Crear el objeto ActividadGasto y enviarlo al servidor
                guardarGasto(concept, amount, date);
            }
        });

        return view;
    }

    /**
     * Crea un objeto ActividadGasto y lo envía al servidor Spring Boot.
     * URL: POST /api/actividades-gastos
     */
    private void guardarGasto(String concepto, String montoStr, String fecha) {
        try {
            // Convertir el monto a BigDecimal
            BigDecimal monto = new BigDecimal(montoStr);

            // Crear el objeto ActividadGasto
            ActividadGasto gasto = new ActividadGasto();
            gasto.setConcepto(concepto);
            gasto.setMonto(monto);
            gasto.setCategoria(selectedCategory);
            gasto.setFecha(fecha);
            // Nota: El viajeId debería obtenerse del viaje seleccionado. Por ahora se deja null.
            // Esto se puede mejorar después para asociar el gasto a un viaje específico.

            // Enviar la petición POST al servidor
            RetrofitClient.getApiService().crearGasto(gasto).enqueue(new Callback<ActividadGasto>() {
                @Override
                public void onResponse(Call<ActividadGasto> call, Response<ActividadGasto> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ActividadGasto gastoCreado = response.body();
                        Toast.makeText(getContext(), "Gasto '" + gastoCreado.getConcepto() + "' registrado con éxito! (ID: " + gastoCreado.getId() + ")", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Error al guardar el gasto: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ActividadGasto> call, Throwable t) {
                    Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "El monto debe ser un número válido", Toast.LENGTH_SHORT).show();
        }
    }
}
