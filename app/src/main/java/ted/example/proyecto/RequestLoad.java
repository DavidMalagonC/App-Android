package ted.example.proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RequestLoad extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db;

    private Table table; // Layout donde se pintará la tabla

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        db = new DatabaseHelper(this, null, 1, null);
        user = (User) getIntent().getExtras().getSerializable("user");
        table = new Table(this, (TableLayout) findViewById(R.id.table));
        table.agregarCabecera(R.array.cabecera_tabla);
        ArrayList<ArrayList<String>> elements = db.getRequestLoadBusinessMan(user.getId());
        for(int i = 0; i< elements.size(); i++) {
            table.agregarFilaTabla(elements.get(i), this, user.getId());
        }


    }

    public void onClick(View v) {
        try {
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