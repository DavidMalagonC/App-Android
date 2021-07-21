package ted.example.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class OwnerDashboard extends AppCompatActivity implements View.OnClickListener {

    User user;

    public void requestLoad(View view) {
        Intent intent = new Intent(this, Owner.class);
        user = (User) getIntent().getExtras().getSerializable("user");
        intent.putExtra("user", user);
        this.startActivity(intent);
    }

    public void getRequestLoadInProcess(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardowner);
        Resources res = getResources();
        String[] list = res.getStringArray(R.array.dashboardOwner);


        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, list

        );

        ListView listOptions = (ListView) findViewById(R.id.dashboardOwner);
        listOptions.setAdapter(listAdapter);


        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listDrinks,
                                    View itemView,
                                    int position,
                                    long id) {
                if (position == 0) {
                    Intent intent = new Intent(OwnerDashboard.this, Owner.class);
                    user = (User) getIntent().getExtras().getSerializable("user");
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OwnerDashboard.this, AsociateVehicle.class);
                    user = (User) getIntent().getExtras().getSerializable("user");
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }

        };

        listOptions.setOnItemClickListener(itemClickListener);

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
