package ted.example.proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class Businessman extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper db;

    private EditText origin;
    private EditText destiny;
    private EditText product;
    private EditText description;
    private EditText weigth;
    private EditText conditions;
    private Button buttonRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_request);
        origin=(EditText) findViewById(R.id.origin);
        destiny=(EditText) findViewById(R.id.destiny);
        product=(EditText)findViewById(R.id.product);
        description=(EditText)findViewById(R.id.description);
        weigth=(EditText)findViewById(R.id.weigth);
        conditions=(EditText)findViewById(R.id.conditions);

        db=new DatabaseHelper(this,null,1,null);
        buttonRequest = (Button)findViewById(R.id.buttonRequest);
        buttonRequest.setOnClickListener(this);

    }

    public void onClick(View v) {
        try{
        switch (v.getId()) {
            case R.id.buttonRequest:
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                boolean result = db.insertRequest(origin.getText().toString(), destiny.getText().toString(),
                        product.getText().toString(), description.getText().toString(),
                        weigth.getText().toString(), conditions.getText().toString(), 1);
                if(result)
                    alert("¡Solicitud enviada exitosamente!");
                else
                    alert("Ocurrio un error al crear la solictud");
        }
        } catch (Exception e){
            System.out.println(e.getMessage());
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