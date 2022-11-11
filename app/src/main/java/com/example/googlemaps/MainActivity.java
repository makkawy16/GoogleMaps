package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.googlemaps.utils.MyLocation;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_REQUEST_CODE = 100;
    MyLocation myLocation;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocation = new MyLocation(this);

        if (isLocationPersmisssionAllowed()) {
            location = myLocation.getCurrentLocation(null);
            if (location != null)
                Log.d("ddddddddddd", "onCreate: " + location.toString());
        } else {
            requestLocationPermission();
        }

    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            alertDialog("Alert", "need your location");
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location = myLocation.getCurrentLocation(null);
                    if (location != null)
                        Log.d("ddddddddddd", "onCreate: " + location.toString());
                } else {
                    Toast.makeText(this, "location not available", Toast.LENGTH_SHORT).show();
                }

                return; // mlhash lazma bs 3shan lw kaza permission

        }
    }

    private boolean isLocationPersmisssionAllowed() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }


    private void alertDialog(String title, String msg) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(msg);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        });
        dialog.show();


    }

}