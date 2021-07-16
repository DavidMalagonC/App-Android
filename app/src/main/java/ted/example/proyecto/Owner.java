package ted.example.proyecto;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Owner extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db;

    private Table table; // Layout donde se pintará la tabla
    private ArrayList<TableRow> filas; // Array de las filas de la tabla
    private Activity actividad;
    private Resources rs;
    private int FILAS, COLUMNAS; // Filas y columnas de nuestra tabla

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        db = new DatabaseHelper(this, null, 1, null);
        user = (User) getIntent().getExtras().getSerializable("user");
        table = new Table(this, (TableLayout) findViewById(R.id.table));
        table.agregarCabecera(R.array.cabecera_tabla);
        ArrayList<ArrayList<String>> elements = db.getRequestLoad();
        for(int i = 0; i< elements.size(); i++) {
            table.agregarFilaTabla(elements.get(i), this, user.getId());
        }


    }

    public void onClick(View v) {
        try {
            db.updateRequest(v.getId()+ "", "ACCEPTED", user.getId()+"");
            GMailSender em = new GMailSender("juventusdebogota@gmail.com", "Juventus12345");
            // em.sendMail("Confirmacion de registro", "Su usuario es: " + correo + " su contraseña es: " + contraseña, "123123123123123", "manuelcastrog9@gmail.com");
                /*boolean result = db.insertRequest(origin.getText().toString(), destiny.getText().toString(),
                        product.getText().toString(), description.getText().toString(),
                        weigth.getText().toString(), conditions.getText().toString(), user.getId());
                if(result)
                    alert("¡Solicitud enviada exitosamente!");
                else
                    alert("Ocurrio un error al crear la solictud");*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void alert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Titulo");
        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}