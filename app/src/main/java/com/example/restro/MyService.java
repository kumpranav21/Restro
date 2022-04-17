package com.example.restro;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RequestHelp();

    }

    @RequiresApi(Build.VERSION_CODES.O)


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RequestHelp();
        return super.onStartCommand(intent, flags, startId);
    }


    private void RequestHelp() {
        //******************************* GPS Location ***********************************
        GPSTracker gps = new GPSTracker(getApplicationContext());
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }

            //************************************* SMS *******************************

            String address = "Address: " + addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            String Message = msg + "\n  TrackAddress: \n http://maps.google.com/?q=" + latitude + "," + longitude + "\n Via Yodhya";

            Log.e("TAG", "Address: " + address);





        } else {
            gps.showSettingsAlert();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
