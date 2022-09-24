package com.example.gpsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    final int PERMISSION_REQUEST_CODE=0;

    Location Maudes;
    Location Opus;
    Location Pascals;
    Location Cym;
    boolean maudes=false;
    boolean opus=false;
    boolean pascals=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Maudes=new Location(LocationManager.GPS_PROVIDER);
        Maudes.setLongitude(-82.3237);
        Maudes.setLatitude(29.6496);

        Opus=new Location(LocationManager.GPS_PROVIDER);
        Opus.setLongitude(-82.3334);
        Opus.setLatitude(29.6505);

        Pascals=new Location(LocationManager.GPS_PROVIDER);
        Pascals.setLongitude(-82.3435);
        Pascals.setLatitude(29.6532);

        Cym=new Location(LocationManager.GPS_PROVIDER);
        Cym.setLatitude(29.6598);
        Cym.setLongitude(-82.4007);

        Intent my_intent = new Intent(getBaseContext(), InfoActivity.class);
        my_intent.putExtra("pictureID", R.drawable.application__coffee_welcome);
        startActivity(my_intent);


        boolean permissionGranted=
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted){

            //start Location Services
            Log.d("Example","User granted permissions before. Start GPS now");
            startGPS();
        }
        else{

            //We need to request permissions
            Log.d("Example","User never granted permissions before. Request now");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);

        }
    }

    public void startGPS(){

        boolean permissionGranted=
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted) {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                //The user clicked on the DENY button
                Log.d("Example", "User denied permissions just now. Exit");
                finish();
            } else {
                //The user clicked on the ALLOW button
                Log.d("Example", "User granted permissions right now. Start GPS now");
                startGPS();
            }

        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Example","New location received. Long:"+location.getLongitude()+" Lat:"+location.getLatitude());

        if(location.distanceTo(Opus)<100)//within 100 meters
        {
            Intent my_intent = new Intent(getBaseContext(), InfoActivity.class);
            if(maudes){
                opus = true;
                my_intent.putExtra("pictureID", R.drawable.opuscoffee);
            }
            else {
                my_intent.putExtra("pictureID", R.drawable.lostmaudes);
            }
            startActivity(my_intent);
        }
        else if(location.distanceTo(Maudes)<100)
        {
            maudes=true;
            Intent my_intent = new Intent(getBaseContext(), InfoActivity.class);
            my_intent.putExtra("pictureID", R.drawable.maudescafe);
            startActivity(my_intent);

        }
        else if(location.distanceTo(Pascals)<100)
        {
            Intent my_intent = new Intent(getBaseContext(), InfoActivity.class);
            if(opus && maudes){
                pascals = true;
                my_intent.putExtra("pictureID", R.drawable.pascalscoffeehouse);

            }
            else if (maudes){
                my_intent.putExtra("pictureID", R.drawable.lostopus);
            }
            else {
                my_intent.putExtra("pictureID", R.drawable.lostmaudes);
            }
            startActivity(my_intent);
        }
        else if(location.distanceTo(Cym)<100)
        {
            Intent my_intent = new Intent(getBaseContext(), InfoActivity.class);
            if(pascals && opus && maudes){
                my_intent.putExtra("pictureID", R.drawable.cymcoffeeco);
            }
            else if (opus && maudes){
                my_intent.putExtra("pictureID", R.drawable.lostpascals);
            }
            else if (maudes){
                my_intent.putExtra("pictureID", R.drawable.lostopus);
            }
            else {
                my_intent.putExtra("pictureID", R.drawable.lostmaudes);
            }
            startActivity(my_intent);

        }


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}