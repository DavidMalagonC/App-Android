package ted.example.proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Driver extends AppCompatActivity implements View.OnClickListener{

    private EditText emailDriver;
    private EditText plate;
    DatabaseHelper db;
    User user;
    private Table table; // Layout donde se pintará la tabla

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_driver);

        emailDriver = (EditText) findViewById(R.id.emailDriver);
        plate = (EditText) findViewById(R.id.plate);

        db = new DatabaseHelper(this, null, 1, null);
        user = (User) getIntent().getExtras().getSerializable("user");

        table = new Table(this, (TableLayout) findViewById(R.id.table));
        table.agregarCabecera(R.array.cabecera_tabla);
        ArrayList<ArrayList<String>> elements = db.getRequestLoadDriver(user.getId());
        if(null == elements){
            alert("No se encontraron viajes pendientes");
            return;
        }
        for(int i = 0; i< elements.size(); i++) {
            table.agregarFilaTablaDriver(elements.get(i), this, user.getId());
        }
    }

    public void Asociate(View view) {

        boolean res = db.insertVehicle(plate.getText().toString(), emailDriver.getText().toString(), user.getId() + "" );
        if(res){
            alert("Vehiculo registrado exitosamente");
        }
        else{
            alert("No se encontro un conductor con el correo ingresado");
        }
    }

    public void alert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Titulo");
        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
