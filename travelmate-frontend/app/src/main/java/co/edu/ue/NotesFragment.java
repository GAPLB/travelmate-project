package co.edu.ue;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    private EditText etSearchNote;
    private ListView listViewNotes;
    private FloatingActionButton fabAddNote;
    private DatabaseHelper databaseHelper;

    private ArrayList<String> notesList;
    private ArrayList<Integer> notesIds;
    private ArrayAdapter<String> adapter;

    public NotesFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        etSearchNote = view.findViewById(R.id.etSearchNote);
        listViewNotes = view.findViewById(R.id.listViewNotes);
        fabAddNote = view.findViewById(R.id.fabAddNote);

        // Cargamos todas las notas activas al iniciar el fragmento
        loadNotesIntoList("");

        // Buscador en tiempo real
        etSearchNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadNotesIntoList(s.toString()); // Filtra según lo que el usuario escriba
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Botón flotante (+) para abrir el formulario de creación de una nueva nota
        fabAddNote.setOnClickListener(v -> {
            // Aquí navegamos al Fragment del formulario de notas (AddEditNoteFragment)
            AddEditNoteFragment formFragment = new AddEditNoteFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, formFragment);
            transaction.addToBackStack(null); // Permite volver atrás con el botón del celular
            transaction.commit();
        });

        // Tocar una nota brevemente para EDITARLA enviando su ID al formulario
        listViewNotes.setOnItemClickListener((parent, view1, position, id) -> {
            int selectedId = notesIds.get(position);

            Bundle bundle = new Bundle();
            bundle.putInt("NOTE_ID", selectedId); // Pasamos el ID para saber que vamos a actualizar

            AddEditNoteFragment formFragment = new AddEditNoteFragment();
            formFragment.setArguments(bundle);

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, formFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Mantener presionado una nota (Long Click) para hacer el Borrado con status (1 a 0)
        listViewNotes.setOnItemLongClickListener((parent, view12, position, id) -> {
            int noteIdToDelete = notesIds.get(position);
            boolean deleted = databaseHelper.DeleteNote(noteIdToDelete);

            if (deleted) {
                Toast.makeText(getContext(), "Nota eliminada correctamente", Toast.LENGTH_SHORT).show();
                loadNotesIntoList("");
            } else {
                Toast.makeText(getContext(), "No se pudo eliminar la nota", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        return view;
    }

    // Método para consultar SQLite con o sin filtro de búsqueda y pintar las notas
    private void loadNotesIntoList(String query) {
        notesList = new ArrayList<>();
        notesIds = new ArrayList<>();

        Cursor cursor;
        if (query.isEmpty()) {
            cursor = databaseHelper.getActiveNotes();
        } else {
            cursor = databaseHelper.searchActiveNotes(query);
        }

        if (cursor != null && cursor.moveToFirst()) {
            int contador = 1;
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION));

                notesIds.add(id);
                notesList.add(contador + ". " + title + "\n   " + description);
                contador++;
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);
    }
}