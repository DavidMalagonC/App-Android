package ted.example.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Transport";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "User";
    private SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("

                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "

                + "NAME TEXT, "

                + "LASTNAME TEXT,"

                + "EMAIL TEXT,"

                + "PASSWORD TEXT,"

                + "GENDER TEXT,"

                + "PHONE INT,"

                + "BIRTHDAY DATE,"

                + "ROL INTEGER,"

                + "DOCUMENT INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }

    boolean insert(String name, String lastname, String email, String gender,
                   String phone, Date birthday, long document, String password, int rol) {
        Cursor c = getUser(email);
        if(c.getCount() > 0){
            return false;
        }
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("LASTNAME", lastname);
        drinkValues.put("EMAIL", email);
        drinkValues.put("GENDER", gender);
        drinkValues.put("PHONE", phone);
        drinkValues.put("BIRTHDAY", birthday.toString());
        drinkValues.put("DOCUMENT", document);
        drinkValues.put("PASSWORD", password);
        drinkValues.put("ROL", rol);


        db.insert(TABLE_NAME, null, drinkValues);
        return true;

    }

    public Boolean login(String email, String password) {
        Boolean login = false;
        Cursor c = getUser(email);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String user = c.getString(c.getColumnIndex("EMAIL"));
                String passwordDB = c.getString(c.getColumnIndex("PASSWORD"));
                int id = c.getInt(c.getColumnIndex("id"));
                if (password.equals(passwordDB))
                    login = true;
            } while (c.moveToNext());
        }

        //Cerramos el cursor
        c.close();
        return login;
    }

    public Cursor getUser(String email) {
        return db.rawQuery("select * from User where EMAIL = '" + email + "'", null);
    }
}

