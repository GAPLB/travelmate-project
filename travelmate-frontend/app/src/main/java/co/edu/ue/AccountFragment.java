package co.edu.ue;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AccountFragment extends Fragment {

    private EditText etName, etEmail, etPassword;
    private Button btnSubmit;
    private TextView tvToggleMode, tvAppTitle;

    private boolean isLoginMode = true; // Por defecto arranca en modo Iniciar Sesión

    public AccountFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        tvToggleMode = view.findViewById(R.id.tvToggleMode);
        tvAppTitle = view.findViewById(R.id.tvAppTitle);

        // Controlar el botón de alternar entre Login y Registro
        tvToggleMode.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            if (isLoginMode) {
                etName.setVisibility(View.GONE);
                btnSubmit.setText("Iniciar Sesión");
                tvToggleMode.setText("¿No tienes cuenta? Regístrate aquí");
                tvAppTitle.setText("TravelMate 🌍 - Login");
            } else {
                etName.setVisibility(View.VISIBLE);
                btnSubmit.setText("Registrarse");
                tvToggleMode.setText("¿Ya tienes cuenta? Inicia sesión");
                tvAppTitle.setText("TravelMate 🌍 - Registro");
            }
        });

        // Acción al presionar el botón principal
        btnSubmit.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String name = etName.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || (!isLoginMode && name.isEmpty())) {
                Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isLoginMode) {
                // LÓGICA DE LOGIN (Aquí se conectará con el endpoint de Spring Boot más adelante)
                Toast.makeText(getContext(), "Iniciando sesión con: " + email, Toast.LENGTH_SHORT).show();
            } else {
                // LÓGICA DE REGISTRO (Aquí se conectará con POST /api/usuarios/registro creado por Gabriela)
                Toast.makeText(getContext(), "Registrando usuario: " + name, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}