package co.edu.ue;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditNoteFragment extends Fragment {

    private EditText etNoteTitle, etNoteDescription;
    private Button btnSaveNote;
    private TextView tvFormTitle;
    private DatabaseHelper databaseHelper;

    private int noteId = -1; // -1 indica que es una inserción nueva, si trae otro número es modo edición

    public AddEditNoteFragment() {
        // Constructor vacío requerido por los Fragments
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Vinculamos este Java con el diseño XML que acabamos de crear
        View view = inflater.inflate(R.layout.fragment_add_edit_note, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        etNoteTitle = view.findViewById(R.id.etNoteTitle);
        etNoteDescription = view.findViewById(R.id.etNoteDescription);
        btnSaveNote = view.findViewById(R.id.btnSaveNote);
        tvFormTitle = view.findViewById(R.id.tvFormTitle);

        // Verificamos si la vista recibió un ID de nota (Significa que el usuario tocó una nota para editarla)
        if (getArguments() != null) {
            noteId = getArguments().getInt("NOTE_ID", -1);
            if (noteId != -1) {
                tvFormTitle.setText("Editar Nota Offline");
                btnSaveNote.setText("Actualizar Nota");
                loadNoteData(noteId); // Cargamos los datos actuales en los EditText
            }
        }

        // Programamos el evento del botón Guardar / Actualizar
        btnSaveNote.setOnClickListener(v -> {
            String title = etNoteTitle.getText().toString().trim();
            String description = etNoteDescription.getText().toString().trim();

            if (!title.isEmpty() && !description.isEmpty()) {
                if (noteId == -1) {
                    // CREAR: Si no hay ID, insertamos un registro nuevo en SQLite
                    boolean isInserted = databaseHelper.insertNote(title, description);
                    if (isInserted) {
                        Toast.makeText(getContext(), "¡Nota guardada offline con éxito!", Toast.LENGTH_SHORT).show();
                        requireActivity().onBackPressed(); // Regresa automáticamente a la lista de notas
                    } else {
                        Toast.makeText(getContext(), "Error al guardar la nota", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // ACTUALIZAR: Si hay ID, modificamos el registro existente en SQLite
                    boolean isUpdated = databaseHelper.updateNote(noteId, title, description);
                    if (isUpdated) {
                        Toast.makeText(getContext(), "¡Nota actualizada con éxito!", Toast.LENGTH_SHORT).show();
                        requireActivity().onBackPressed(); // Regresa a la lista de notas
                    } else {
                        Toast.makeText(getContext(), "Error al actualizar la nota", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Método auxiliar para buscar los datos de la nota seleccionada y rellenar el formulario
    private void loadNoteData(int id) {
        Cursor cursor = databaseHelper.getActiveNotes();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                if (currentId == id) {
                    etNoteTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)));
                    etNoteDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)));
                    break;
                }
            }
            cursor.close();
        }
    }
}