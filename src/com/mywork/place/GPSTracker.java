package com.mywork.place;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;

public class GPSTracker extends Service implements LocationListener {

	Context context;
	boolean isGPSEnabled=false;
	boolean isNetworkEnabled=false;
	boolean canGetLocation=false;
	Location location;
	double latitude,longitude;
	final static long MIN_DISTANCE_CHANGE_FOR_UPDATES=10;
	final static long MIN_TIME_BW_UPDATES=2000*60*1;
	LocationManager locationmanager;

	public GPSTracker(Context c){
		this.context=c;
		getLocation();
		
	}
	
	public void getLocation(){
		
		try{
			//set location
			locationmanager=(LocationManager) context.getSystemService(LOCATION_SERVICE);
			
			//getting GPS status
			isGPSEnabled=locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			//getting Network status
			isNetworkEnabled=locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if(!isGPSEnabled&&!isNetworkEnabled){
				//no GPS or Network -> no coverage
				
			}
			else{//at least one coverage
				
				this.canGetLocation=true;
				
				//get location by Network
				if(isNetworkEnabled){
					
					locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network","Network");
					
					if(locationmanager!=null){
						//get location from locationmanager
						location=locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						
						if(location!=null){
							//get lat and long if a location is there
							latitude=location.getLatitude();
							longitude=location.getLongitude();
							
						}
						
					}
				}
				//get lat/long using GPS if available
				if(isGPSEnabled){
					if(location==null){
						locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled","GPS Enabled");
					
						if(locationmanager!=null){
						//get location
							location=locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							
							if(location!=null){
								latitude=location.getLatitude();
								longitude=location.getLongitude();
							}
						}
						
						
					}
					
				}
				
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
	}
	
	public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
 
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean canGetLocation() {
        return this.canGetLocation;
    }
	
	public double getLatitude(){
		
		return this.latitude;
	}
	
	public double getLongitude(){
		
		return this.longitude;
	}
    
}
