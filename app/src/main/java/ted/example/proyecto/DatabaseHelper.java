package ted.example.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "proyecto";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "User" ;

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DatabaseHelper() {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL ("CREATE TABLE " + TABLE_NAME + "("

                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "

                + "NAME TEXT, "

                + "LASTNAME TEXT,"

                + "EMAIL TEXT,"

                + "GENDER TEXT,"

                + "PHONE INT,"

                + "BIRTHDAY DATE,"

                +"DOCUMENT INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }

    void insert(SQLiteDatabase db, String name, String lastname, String email,
                String gender, int phone, Date birthday, long document){
        ContentValues drinkValues= new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("LASTNAME", lastname);
        drinkValues.put("EMAIL", email);
        drinkValues.put("GENDER", gender);
        drinkValues.put("PHONE", phone);
        drinkValues.put("BIRTHDAY", String.valueOf(birthday));
        drinkValues.put("DOCUMENT", document);


        db.insert(TABLE_NAME, null, drinkValues);

    }
}
