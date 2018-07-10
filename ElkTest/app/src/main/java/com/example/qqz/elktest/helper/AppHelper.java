package com.example.qqz.elktest.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

public class AppHelper{

        public static Drawable fetchDrawableByResId(Context context, String resourceName){
            int flagId=context.getResources().getIdentifier(resourceName , "drawable", context.getPackageName());
            Drawable drawable=null;
            try {
                drawable=context.getResources().getDrawable(flagId);
            }catch (Resources.NotFoundException e){

            }

            return drawable;
        }

    public static final boolean isLocationOpen(final Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return false;
        }
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = false;
        try {
            gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){

        }
        boolean network = false;
        try {
            network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){

        }
        if (gps || network) {
            return true;
        }
        return false;
    }

}