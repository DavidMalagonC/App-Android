package ted.example.proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AsociateVehicle extends AppCompatActivity implements View.OnClickListener{

    private EditText emailDriver;
    private EditText plate;
    DatabaseHelper db;
    User user;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        emailDriver = (EditText) findViewById(R.id.emailDriver);
        plate = (EditText) findViewById(R.id.plate);

        db = new DatabaseHelper(this, null, 1, null);
        user = (User) getIntent().getExtras().getSerializable("user");
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

    private void alert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Titulo");
        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
