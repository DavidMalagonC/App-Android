package ted.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BusinessmanDashboard extends AppCompatActivity implements View.OnClickListener{

    User user;

    public void requestLoad(View view) {
        Intent intent = new Intent(this, Businessman.class);
        user = (User) getIntent().getExtras().getSerializable("user");
        intent.putExtra("user", user);
        this.startActivity(intent);
    }

    public void getRequestLoad(View view) {
        Intent intent = new Intent(this, Businessman.class);
        user = (User) getIntent().getExtras().getSerializable("user");
        intent.putExtra("user", user);
        this.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardcomerciante);

    }

    @Override
    public void onClick(View v) {

    }
}
