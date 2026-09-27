package co.edu.ue;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ExpenseFragment extends Fragment {

    // Declaración de los componentes de la vista
    private Spinner spinnerCategory;
    private EditText etExpenseConcept, etExpenseAmount, etExpenseDate;
    private Button btnSaveExpense;

    // Variable para almacenar la opción seleccionada en el menú desplegable
    private String selectedCategory = "";

    public ExpenseFragment() {
        // Constructor vacío requerido por los Fragments
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el diseño visual del fragmento
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

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
                // Éxito: Aquí es donde Yudy más adelante conectará este objeto con la API de Spring Boot
                Toast.makeText(getContext(), "Gasto registrado en: " + selectedCategory, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}