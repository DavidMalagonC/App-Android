package ted.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BusinessmanDashboard extends AppCompatActivity implements View.OnClickListener{

    User user;

    public void requestLoad(View view) {
        Intent intent = new Intent(this, Businessman.class);
        user = (User) getIntent().getExtras().getSerializable("user");
        intent.putExtra("user", user);
        this.startActivity(intent);
    }

    public void getRequestLoad(View view) {
        Intent intent = new Intent(this, RequestLoad.class);
        user = (User) getIntent().getExtras().getSerializable("user");
        intent.putExtra("user", user);
        this.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardcomerciante);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onClick(View v) {

    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; /** true -> el menú ya está visible */
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            return true;
        }
        if (id == R.id.acercaDe) {
            // lanzarAcercaDe(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
