package ted.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DriverDashboard extends AppCompatActivity implements View.OnClickListener{

    User user;

    public void getRequestLoadInProcess(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardconductor);

    }

    public void getRequestLoadEnd(View view) {

    }

    @Override
    public void onClick(View v) {

    }

    public void requestLoadDriver(View view) {
        Intent intent = new Intent(this, Driver.class);
        user = (User) getIntent().getExtras().getSerializable("user");
        intent.putExtra("user", user);
        this.startActivity(intent);
    }

    public void reportLocation(View view) {
    }
}
