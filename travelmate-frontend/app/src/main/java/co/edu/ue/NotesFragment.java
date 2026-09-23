package co.edu.ue;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    private EditText etNoteTitle, etNoteDescription;
    private Button btnSaveNote;
    private ListView listViewNotes;
    private DatabaseHelper databaseHelper;

    private ArrayList<String> notesList;
    private ArrayList<Integer> notesIds;
    private ArrayAdapter<String> adapter;

    private int selectedNoteId = -1; // Variable para saber si estamos editando una nota existente

    public NotesFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        etNoteTitle = view.findViewById(R.id.etNoteTitle);
        etNoteDescription = view.findViewById(R.id.etNoteDescription);
        btnSaveNote = view.findViewById(R.id.btnSaveNote);
        listViewNotes = view.findViewById(R.id.listViewNotes);

        loadNotesIntoList();

        // Botón para Guardar o Actualizar
        btnSaveNote.setOnClickListener(v -> {
            String title = etNoteTitle.getText().toString().trim();
            String description = etNoteDescription.getText().toString().trim();

            if (!title.isEmpty() && !description.isEmpty()) {
                if (selectedNoteId == -1) {
                    // Si no hay ID seleccionado, es una inserción nueva (CREATE)
                    boolean isInserted = databaseHelper.insertNote(title, description);
                    if (isInserted) {
                        Toast.makeText(getContext(), "¡Nota guardada offline con éxito!", Toast.LENGTH_SHORT).show();
                        clearForm();
                        loadNotesIntoList();
                    } else {
                        Toast.makeText(getContext(), "Error al guardar la nota", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si hay un ID seleccionado, actualizamos la nota (UPDATE)
                    boolean isUpdated = databaseHelper.updateNote(selectedNoteId, title, description);
                    if (isUpdated) {
                        Toast.makeText(getContext(), "¡Nota actualizada con éxito!", Toast.LENGTH_SHORT).show();
                        clearForm();
                        loadNotesIntoList();
                    } else {
                        Toast.makeText(getContext(), "Error al actualizar la nota", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        // Tocar una nota brevemente para CARGARLA en el formulario y poder EDITARLA
        listViewNotes.setOnItemClickListener((parent, view1, position, id) -> {
            selectedNoteId = notesIds.get(position);

            // Extraemos los textos actuales para ponerlos en los EditText
            Cursor cursor = databaseHelper.getActiveNotes();
            if (cursor != null) {
                int index = 0;
                while (cursor.moveToNext()) {
                    if (index == position) {
                        etNoteTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)));
                        etNoteDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)));
                        btnSaveNote.setText("Actualizar Nota");
                        break;
                    }
                    index++;
                }
                cursor.close();
            }
            Toast.makeText(getContext(), "Modo edición activado", Toast.LENGTH_SHORT).show();
        });

        // Mantener presionado una nota (Long Click) para hacer el Borrado Lógico (1 a 0)
        listViewNotes.setOnItemLongClickListener((parent, view12, position, id) -> {
            int noteIdToDelete = notesIds.get(position);
            boolean deleted = databaseHelper.DeleteNote(noteIdToDelete);

            if (deleted) {
                Toast.makeText(getContext(), "Nota eliminada (Borrado lógico)", Toast.LENGTH_SHORT).show();
                clearForm();
                loadNotesIntoList();
            } else {
                Toast.makeText(getContext(), "No se pudo eliminar la nota", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        return view;
    }

    // Método para consultar SQLite y pintar las notas numeradas (1., 2., 3...)
    private void loadNotesIntoList() {
        notesList = new ArrayList<>();
        notesIds = new ArrayList<>();

        Cursor cursor = databaseHelper.getActiveNotes();

        if (cursor != null && cursor.moveToFirst()) {
            int contador = 1;
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION));

                notesIds.add(id);
                // Aquí aplicamos el formato numerado 1., 2., 3... para que parezca checklist
                notesList.add(contador + ". " + title + "\n   " + description);
                contador++;
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);
    }

    // Limpiar formulario y restablecer el botón
    private void clearForm() {
        etNoteTitle.setText("");
        etNoteDescription.setText("");
        btnSaveNote.setText("Guardar Nota Offline");
        selectedNoteId = -1;
    }
}