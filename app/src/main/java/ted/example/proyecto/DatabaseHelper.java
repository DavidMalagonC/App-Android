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
import androidx.core.database.CursorWindowCompat;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Transport";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String TABLE_NAME_REQUEST = "request_load";
    private static final String TABLE_NAME_VEHICLE = "vehicle";
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

        db.execSQL("CREATE TABLE " + TABLE_NAME_REQUEST + "("

                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "

                + "origin TEXT, "

                + "destiny TEXT,"

                + "product TEXT,"

                + "description TEXT,"

                + "weigth INT,"

                + "conditions TEXT,"

                + "id_vehicle INT,"

                + "location TEXT,"

                + "id_user INT)");

        db.execSQL("CREATE TABLE " + TABLE_NAME_VEHICLE + "("

                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "

                + "plates TEXT, "

                + "id_driver INT,"

                + "id_owner INT)");

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

    boolean insertRequest(String origin, String destiny, String product, String description,
                          String weigth, String conditions, int id_user) {

        ContentValues content = new ContentValues();
        content.put("origin", origin);
        content.put("destiny", destiny);
        content.put("product", product);
        content.put("description", description);
        content.put("weigth", weigth);
        content.put("conditions", "NEW");
        content.put("id_vehicle", "SA");
        content.put("id_user", id_user);

        db.insert(TABLE_NAME_REQUEST, null, content);
        return true;

    }

    boolean insertVehicle(String plate, String emailDriver, String idOwner) {
        Cursor c = getUser(emailDriver);
        if(c == null || c.getCount() < 1 ){
            return false;
        }
        c.moveToFirst();
        ContentValues content = new ContentValues();
        content.put("plates", plate);
        content.put("id_owner", idOwner);
        content.put("id_driver",  c.getInt(c.getColumnIndex("id")));

        db.insert(TABLE_NAME_VEHICLE, null, content);
        return true;

    }

    boolean updateRequest(String id, String conditions, String id_owner, String message) throws Exception {
        Cursor c = getVehicle(id_owner);
        c.moveToFirst();
        if(c == null || c.getCount() < 1 ){
            return false;
        }
        int idVehicle =  c.getInt(c.getColumnIndex("id"));
        db.execSQL("UPDATE "+ TABLE_NAME_REQUEST +" SET conditions='"+conditions+ "', id_vehicle='"+idVehicle+ "' WHERE id='"+id+"'");
        GMailSender em = new GMailSender("juventusdebogota@gmail.com", "Juventus12345");
        em.sendMail("Notificaci??n solicitud de carga de transporte", message, "Juventus", "davidmalagonc@gmail.com");
        return true;

    }

    boolean updateRequestDriver(String id, String conditions, String id_owner, String message) throws Exception {
        db.execSQL("UPDATE "+ TABLE_NAME_REQUEST +" SET conditions='"+conditions+ "' WHERE id='"+id+"'");
        GMailSender em = new GMailSender("juventusdebogota@gmail.com", "Juventus12345");
        em.sendMail("Notificaci??n solicitud de carga de transporte", message, "Juventus", "davidmalagonc@gmail.com");
        return true;

    }

    boolean updateRequestLocation(int id, String location) throws Exception {
        Cursor c = getVehicleByDriver(id);
        c.moveToFirst();
        int idVehicle =  c.getInt(c.getColumnIndex("id"));
        db.execSQL("UPDATE "+ TABLE_NAME_REQUEST +" SET location='"+location+ "' WHERE id_vehicle='"+idVehicle+"' ");
        GMailSender em = new GMailSender("juventusdebogota@gmail.com", "Juventus12345");
        em.sendMail("Notificaci??n solicitud de carga de transporte", location, "Juventus", "davidmalagonc@gmail.com");
        return true;

    }

    public User login(String email, String password) {
        Boolean login = false;
        Cursor c = getUser(email);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

            int rol = c.getInt(c.getColumnIndex("ROL"));
            String passwordDB = c.getString(c.getColumnIndex("PASSWORD"));
            int id = c.getInt(c.getColumnIndex("id"));
            if (password.equals(passwordDB)) {
                c.close();
                return new User(id, rol);
            }
        }

        //Cerramos el cursor
        c.close();
        return null;
    }

    public Cursor getUser(String email) {
        return db.rawQuery("select * from user where EMAIL = '" + email + "'", null);
    }

    public Cursor getVehicle(String idOwner) {
        return db.rawQuery("select * from vehicle where id_owner = '" + idOwner + "'", null);
    }

    public ArrayList<ArrayList<String>> getRequestLoad() {
        ArrayList<ArrayList<String>> requestsList = new ArrayList<ArrayList<String>>();
        Cursor c = db.rawQuery("select * from request_load", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                ArrayList<String> requests = new ArrayList<String>();
                requests.add(c.getString(c.getColumnIndex("origin")));
                requests.add(c.getString(c.getColumnIndex("destiny")));
                requests.add(c.getString(c.getColumnIndex("product")));
                requests.add(c.getString(c.getColumnIndex("description")));
                requests.add(c.getInt(c.getColumnIndex("weigth"))+"");
                requests.add(c.getString(c.getColumnIndex("id_vehicle"))+"");
                requests.add(c.getString(c.getColumnIndex("conditions"))+"");
                requests.add(c.getString(c.getColumnIndex("location"))+"");
                requests.add(c.getString(c.getColumnIndex("id")));
                requestsList.add(requests);
                c.moveToNext();
            }

        }
        c.close();
        return requestsList;

    }

    public ArrayList<ArrayList<String>> getRequestLoadBusinessMan(int id) {
        ArrayList<ArrayList<String>> requestsList = new ArrayList<ArrayList<String>>();
        Cursor c = db.rawQuery("select * from request_load where id_user="+id, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                ArrayList<String> requests = new ArrayList<String>();
                requests.add(c.getString(c.getColumnIndex("origin")));
                requests.add(c.getString(c.getColumnIndex("destiny")));
                requests.add(c.getString(c.getColumnIndex("product")));
                requests.add(c.getString(c.getColumnIndex("description")));
                requests.add(c.getInt(c.getColumnIndex("weigth"))+"");
                requests.add(c.getString(c.getColumnIndex("id_vehicle"))+"");
                requests.add(c.getString(c.getColumnIndex("conditions"))+"");
                requests.add(c.getString(c.getColumnIndex("location"))+"");
                requests.add(c.getString(c.getColumnIndex("id")));
                requestsList.add(requests);
                c.moveToNext();
            }

        }
        c.close();
        return requestsList;

    }

    public ArrayList<ArrayList<String>> getRequestLoadDriver(int id) {

        Cursor vehicle = getVehicleByDriver(id);
        if(vehicle == null || vehicle.getCount()  < 1){
            return null;
        }
        vehicle.moveToFirst();
        int idVehicle = vehicle.getInt(vehicle.getColumnIndex("id"));

        ArrayList<ArrayList<String>> requestsList = new ArrayList<ArrayList<String>>();
        Cursor c = db.rawQuery("select * from request_load where conditions = 'ACCEPTED' OR conditions = 'TRAVELING' OR conditions = 'FINALIZED' and  id_vehicle = '"+ idVehicle +"'", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                ArrayList<String> requests = new ArrayList<String>();
                requests.add(c.getString(c.getColumnIndex("origin")));
                requests.add(c.getString(c.getColumnIndex("destiny")));
                requests.add(c.getString(c.getColumnIndex("product")));
                requests.add(c.getString(c.getColumnIndex("description")));
                requests.add(c.getInt(c.getColumnIndex("weigth"))+"");
                requests.add(c.getInt(c.getColumnIndex("id_vehicle"))+"");
                requests.add(c.getString(c.getColumnIndex("conditions"))+"");
                requests.add(c.getString(c.getColumnIndex("location"))+"");
                requests.add(c.getString(c.getColumnIndex("id")));
                requestsList.add(requests);
                c.moveToNext();
            }

        }
        else {
            return null;
        }
        c.close();
        return requestsList;

    }

    public Cursor getVehicleByDriver(int idDriver) {
        return db.rawQuery("select * from vehicle where id_driver = " + idDriver + "", null);
    }
}

