package co.edu.ue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import co.edu.ue.dto.LoginDTO;
import co.edu.ue.dto.LoginResponse;
import co.edu.ue.dto.UsuarioDTO;
import co.edu.ue.network.RetrofitClient;
import co.edu.ue.utils.NetworkUtils;
import co.edu.ue.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private EditText etName, etEmail, etPassword;
    private Button btnSubmit;
    private TextView tvToggleMode, tvAppTitle;

    private boolean isLoginMode = true; // Por defecto arranca en modo Iniciar Sesión

    // Manejador de sesión para guardar los datos del usuario logueado
    private SessionManager sessionManager;

    public AccountFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Inicializar el manejador de sesión
        sessionManager = new SessionManager(requireContext());

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

            // Verificar conexión a Internet antes de hacer la petición
            if (!NetworkUtils.hayConexionInternet(requireContext())) {
                Toast.makeText(getContext(), "No hay conexión a Internet. Verifica tu red.", Toast.LENGTH_LONG).show();
                return;
            }

            if (isLoginMode) {
                // LÓGICA DE LOGIN: conectar con el endpoint de Spring Boot
                iniciarSesion(email, password);
            } else {
                // LÓGICA DE REGISTRO: conectar con POST /api/usuarios/registro
                registrarUsuario(name, email, password);
            }
        });

        return view;
    }

    /**
     * Realiza la petición de login al servidor Spring Boot.
     * URL: POST /api/usuarios/login
     */
    private void iniciarSesion(String email, String password) {
        LoginDTO loginDTO = new LoginDTO(email, password);

        RetrofitClient.getApiService().login(loginDTO).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    // Guardar la sesión del usuario
                    sessionManager.guardarSesion(loginResponse.getId(), loginResponse.getNombre(), loginResponse.getEmail());

                    Toast.makeText(getContext(), "¡Bienvenido, " + loginResponse.getNombre() + "!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Credenciales inválidas. Verifica tu email y contraseña.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Realiza la petición de registro al servidor Spring Boot.
     * URL: POST /api/usuarios/registro
     */
    private void registrarUsuario(String nombre, String email, String password) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(nombre, email, password);

        RetrofitClient.getApiService().registrarUsuario(usuarioDTO).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "¡Usuario registrado con éxito! Ahora inicia sesión.", Toast.LENGTH_LONG).show();
                    // Cambiar automáticamente al modo login
                    isLoginMode = true;
                    etName.setVisibility(View.GONE);
                    btnSubmit.setText("Iniciar Sesión");
                    tvToggleMode.setText("¿No tienes cuenta? Regístrate aquí");
                    tvAppTitle.setText("TravelMate 🌍 - Login");
                } else if (response.code() == 409) {
                    Toast.makeText(getContext(), "El correo ya está registrado. Intenta con otro.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Error al registrar: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
