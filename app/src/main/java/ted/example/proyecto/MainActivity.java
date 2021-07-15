package ted.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_registrar;
    DatabaseHelper db;
    private EditText email;
    private EditText password;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);

        db = new DatabaseHelper(this, null, 1, null);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);

        tv_registrar = findViewById(R.id.btn_reg);

        tv_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(intentReg);

            }
        });

    }

    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.buttonLogin:

                    User user = db.login(email.getText().toString(), password.getText().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    //builder.setTitle("Titulo");
                    String message;
                    if (user != null) {
                        Intent intent = null;
                        if (user.getRol() == 1)
                            intent = new Intent(MainActivity.this, Businessman.class);
                        if (user.getRol() == 2)
                            intent = new Intent(MainActivity.this, OwnerDashboard.class);
                        if (user.getRol() == 3)
                            intent = new Intent(MainActivity.this, Businessman.class);
                        intent.putExtra("user", user);
                        MainActivity.this.startActivity(intent);

                    } else {
                        message = " ¡El correo o contraseña son incorrectos!";
                        builder.setMessage(message);

                        builder.setPositiveButton("Aceptar", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}