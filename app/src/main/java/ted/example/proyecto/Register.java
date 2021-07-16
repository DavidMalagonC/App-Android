package ted.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;

public class Register extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    DatabaseHelper db;

    private EditText name;
    private EditText lastname;
    private EditText document;
    private EditText email;
    private EditText gender;
    private EditText phone;
    private EditText birthday;
    private EditText password;
    private Button buttonRegister;
    private int rol;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText) findViewById(R.id.name);
        lastname=(EditText) findViewById(R.id.lastname);
        document=(EditText)findViewById(R.id.document);
        gender=(EditText)findViewById(R.id.gender);
        birthday=(EditText)findViewById(R.id.birthday);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);

        db=new DatabaseHelper(this,null,1,null);
        buttonRegister=(Button)findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.rol);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rols, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void onClick(View v) {
        try{
        switch (v.getId()) {
            case R.id.buttonRegister:
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                boolean result = db.insert(name.getText().toString(), lastname.getText().toString(), email.getText().toString(),
                        gender.getText().toString(),  phone.getText().toString(),
                        format.parse(birthday.getText().toString()), Long.parseLong(document.getText().toString()),
                        password.getText().toString(), rol);
                if(result)
                    alert("¡Registro exitoso!");
                else
                    alert("Ya existe una cuenta con este correo");
        }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
         String rolSelected = parent.getItemAtPosition(pos).toString();
        if(rolSelected.equals("Comerciante"))
            rol = 1;
         if(rolSelected.equals("Conductor"))
            rol = 2;
         if(rolSelected.equals("Propietario"))
            rol = 3;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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