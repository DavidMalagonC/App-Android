package ted.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Registro extends AppCompatActivity implements View.OnClickListener {
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
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

    }

    public void onClick(View v) {
        try{
        switch (v.getId()) {
            case R.id.buttonRegister:
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                db.insert(name.getText().toString(), lastname.getText().toString(), email.getText().toString(),
                        gender.getText().toString(),  phone.getText().toString(),
                        format.parse(birthday.getText().toString()), Long.parseLong(document.getText().toString()),
                        password.getText().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setTitle("Titulo");
                builder.setMessage("¡Registro exitoso!");
                builder.setPositiveButton("Aceptar", null);

                AlertDialog dialog = builder.create();
                dialog.show();

        }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}