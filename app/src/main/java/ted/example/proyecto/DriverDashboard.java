package ted.example.proyecto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DriverDashboard extends AppCompatActivity implements View.OnClickListener{

    User user;
    UbicacionGPS gps;
    FusedLocationProviderClient fusedLocationPorviderClient;
    String locationTravel;
    DatabaseHelper db;

    public void getRequestLoadInProcess(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardconductor);
        fusedLocationPorviderClient = LocationServices.getFusedLocationProviderClient(this);
        db = new DatabaseHelper(this, null, 1, null);


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
        getLocation();
    }

    public void getLocation() {
        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            fusedLocationPorviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if (location != null) {
                        Geocoder geocoder = new Geocoder(DriverDashboard.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            locationTravel = "latitud: " + (double) addresses.get(0).getLatitude();
                            locationTravel += "longitud: " + (double) addresses.get(0).getLongitude();
                            db.updateRequestLocation(user.getId(), locationTravel);
                            /*text_View1.setText("latitud: " + (double) addresses.get(0).getLatitude());
                            text_View2.setText("longitud: " + (double) addresses.get(0).getLongitude());
                            text_View3.setText(addresses.get(0).getCountryName());
                            text_View4.setText(addresses.get(0).getLocality());
                            text_View5.setText(addresses.get(0).getAddressLine(0));*/


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        if (ActivityCompat.checkSelfPermission(DriverDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                            getLocation();
                    }

                }
            });
        }
    }
}
