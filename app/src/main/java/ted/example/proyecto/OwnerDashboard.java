package ted.example.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

public class OwnerDashboard extends AppCompatActivity implements View.OnClickListener{

    User user;

    public void requestLoad(View view) {
        Intent intent = new Intent(this, Owner.class);
        intent.putExtra("user", user);
        user = (User) getIntent().getExtras().getSerializable("user");
        this.startActivity(intent);
    }

    public void getRequestLoadInProcess(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardowner);

    }

    public void getRequestLoadEnd(View view) {

    }

    @Override
    public void onClick(View v) {

    }

    public void associateVehicle(View view) {
        Intent intent = new Intent(this, AsociateVehicle.class);
        user = (User) getIntent().getExtras().getSerializable("user");
        intent.putExtra("user", user);
        this.startActivity(intent);

    }
}
