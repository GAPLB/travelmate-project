package co.edu.ue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "travelmate_local.db";
    private static final int DATABASE_VERSION = 2; // Subimos la versión por el cambio de tabla

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_STATUS = "status"; // 1 = Activo, 0 = Inactivo (Borrado lógico)

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_STATUS + " INTEGER DEFAULT 1)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    // Insertar nota con estatus inicial en 1 (activo)
    public boolean insertNote(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_STATUS, 1);

        long result = db.insert(TABLE_NOTES, null, values);
        db.close();
        return result != -1;
    }

    // Consultar ÚNICAMENTE las notas que tengan estatus 1 (activas)
    public Cursor getActiveNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_STATUS + " = 1", null);
    }

    // Actualizar una nota existente (Update del CRUD)
    public boolean updateNote(int noteId, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);

        int rowsAffected = db.update(TABLE_NOTES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
        return rowsAffected > 0;
    }

    // Borrado lógico: Actualiza el estatus de 1 a 0 en lugar de borrar la fila
    public boolean DeleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, 0);

        int rowsAffected = db.update(TABLE_NOTES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
        return rowsAffected > 0;
    }
}