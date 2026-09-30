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
    private boolean isLoginMode = true;
    private SessionManager sessionManager;

    public AccountFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        sessionManager = new SessionManager(requireContext());

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        tvToggleMode = view.findViewById(R.id.tvToggleMode);
        tvAppTitle = view.findViewById(R.id.tvAppTitle);

        // Si ya está logueado, mostrar mensaje de bienvenida
        if (sessionManager.isLoggedIn()) {
            tvAppTitle.setText("Hola, " + sessionManager.getUserName());
        }

        tvToggleMode.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            if (isLoginMode) {
                etName.setVisibility(View.GONE);
                btnSubmit.setText("Iniciar Sesión");
                tvToggleMode.setText("¿No tienes cuenta? Regístrate aquí");
                tvAppTitle.setText("TravelMate - Login");
            } else {
                etName.setVisibility(View.VISIBLE);
                btnSubmit.setText("Registrarse");
                tvToggleMode.setText("¿Ya tienes cuenta? Inicia sesión");
                tvAppTitle.setText("TravelMate - Registro");
            }
        });

        btnSubmit.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String name = etName.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || (!isLoginMode && name.isEmpty())) {
                Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar conexión a Internet
            if (!NetworkUtils.isNetworkAvailable(requireContext())) {
                NetworkUtils.showNoConnectionToast(requireContext());
                return;
            }

            if (isLoginMode) {
                // LÓGICA DE LOGIN CON API
                LoginDTO loginDTO = new LoginDTO(email, password);
                RetrofitClient.getApiService().login(loginDTO).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            LoginResponse user = response.body();
                            sessionManager.saveSession(user);
                            Toast.makeText(getContext(), "¡Bienvenido, " + user.getNombre() + "!", Toast.LENGTH_SHORT).show();
                            tvAppTitle.setText("Hola, " + user.getNombre());
                        } else {
                            Toast.makeText(getContext(), "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // LÓGICA DE REGISTRO CON API
                UsuarioDTO usuarioDTO = new UsuarioDTO(name, email, password);
                RetrofitClient.getApiService().registrarUsuario(usuarioDTO).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "¡Registro exitoso! Ahora inicia sesión.", Toast.LENGTH_SHORT).show();
                            // Cambiar a modo login
                            isLoginMode = true;
                            etName.setVisibility(View.GONE);
                            btnSubmit.setText("Iniciar Sesión");
                            tvToggleMode.setText("¿No tienes cuenta? Regístrate aquí");
                        } else if (response.code() == 409) {
                            Toast.makeText(getContext(), "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error en el registro", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}