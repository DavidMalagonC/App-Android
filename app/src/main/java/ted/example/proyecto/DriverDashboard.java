package ted.example.proyecto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    LocationManager ubicacion;

    public void getRequestLoadInProcess(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardconductor);
        user = (User) getIntent().getExtras().getSerializable("user");
        fusedLocationPorviderClient = LocationServices.getFusedLocationProviderClient(this);
        db = new DatabaseHelper(this, null, 1, null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        lastLocation();
    }

    public void lastLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER) ;

            if (ubicacion != null) {
                String locationTravel = String.valueOf(loc.getLatitude());
                locationTravel +=  " " + String.valueOf(loc.getLongitude());
                try {
                    db.updateRequestLocation(user.getId(), locationTravel);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationPorviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    task.isSuccessful();

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
                        if (ActivityCompat.checkSelfPermission(DriverDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//                            locationTravel = "latitud: " + (double) ;
//                            locationTravel += "longitud: " + (double) addresses.get(0).getLongitude();
//                            db.updateRequestLocation(user.getId(), locationTravel);
//                            getLocation();
                    }
                }}
            });
        }
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

    public void onSendMaps(View view) {
        ArrayList<ArrayList<String>> request = db.getRequestLoadDriver(user.getId());

        String origin = request.get(0).get(0);
        String destiny = request.get(0).get(1);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("www.google.com").appendPath("maps").
                appendPath("dir").appendPath("").appendQueryParameter("api", "1").
                appendQueryParameter("origin", origin).
                appendQueryParameter("destination", destiny);
        String url = builder.build().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
