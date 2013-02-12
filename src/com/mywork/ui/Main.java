package com.mywork.ui;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Toast;

public class Main extends MapActivity implements LocationListener,OnCheckedChangeListener,OnDrawerOpenListener{

	MapView map;
	long start,stop;
	MyLocationOverlay compass;
	MapController controller;
	int x,y;
	GeoPoint touchedPoint,point;
	List<Overlay> overlayList;
	Drawable d;
	LocationManager lm;
	String towers;
	int lat=0;
    int longi=0;
    final static long MIN_DISTANCE_CHANGE_FOR_UPDATES=05;
	final static long MIN_TIME_BW_UPDATES=1000*60*1;
	LocationManager locationmanager;
	Location location;
	boolean isGPSEnabled=false;
	boolean isNetworkEnabled=false;
	Context context=getBaseContext();
	TextView tv;
	SlidingDrawer sd;
    
    int lat0=(int)(6.916712*1E6);
	int longi0=(int)(79.843312*1E6);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_main);
        
        d=getResources().getDrawable(R.drawable.pinpoint);
        
        CheckBox chkbx=(CheckBox) findViewById(R.id.checkBox1);
        sd=(SlidingDrawer) findViewById(R.id.slidingD);
        
        chkbx.setOnCheckedChangeListener(this);
        sd.setOnDrawerOpenListener(this);
        
        map=(MapView) findViewById(R.id.mvMain);
        map.setBuiltInZoomControls(true);
        Touchy t=new Touchy();
        overlayList=map.getOverlays();
        overlayList.add(t);
        //compass=new MyLocationOverlay(Main.this, map);
       // overlayList.add(compass);
        /*controller=map.getController();
        GeoPoint point=new GeoPoint(6916712,79843312);
        controller.animateTo(point);
        controller.setZoom(6);*/
        
       controller=map.getController();
        point=new GeoPoint(lat0,longi0);
        controller.animateTo(point);
        controller.setZoom(10);
       
        
        //placing pinpoint to our GPS location
       lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria crit=new Criteria();
        
        towers=lm.getBestProvider(crit, false);
        Location location=lm.getLastKnownLocation(towers);
        
        if (location != null){
        	lat=(int) (location.getLatitude()*1E6);
        	longi=(int) (location.getLongitude()*1E6);
     
        GeoPoint mylocation=new GeoPoint(lat, longi);
        
        OverlayItem overlayItem = new OverlayItem(mylocation, "What's up?", "2nd string'");
		CustomPinpoint custom=new CustomPinpoint(d, Main.this);
		custom.insertPinpoint(overlayItem);
		overlayList.add(custom);
        }
        else{
        	Toast.makeText(Main.this,"Couldn't find user location",Toast.LENGTH_SHORT).show();
        	
        }
        try{
        locationmanager=(LocationManager) context.getSystemService(LOCATION_SERVICE);
        isGPSEnabled=locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled=locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
        if(!isGPSEnabled&&!isNetworkEnabled){
			//no GPS or Network -> no coverage
        	Toast.makeText(Main.this, "Gps or Network Service not available", Toast.LENGTH_SHORT).show();
		}
        else{
        	
        	if(isNetworkEnabled){
				
				locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
				Log.d("Network","Network");
				
				if(locationmanager!=null){
					//get location from locationmanager
					location=locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					
					if(location!=null){
						//get lat and long if a location is there
						Toast.makeText(Main.this, "NETWORK Location Found", Toast.LENGTH_SHORT).show();
						lat=(int)(location.getLatitude()*1E6);
						longi=(int)(location.getLongitude()*1E6);
						
						
						GeoPoint mylocation=new GeoPoint(lat, longi);
				        
        				OverlayItem overlayItem = new OverlayItem(mylocation, "What's up?", "I live here'");
        				CustomPinpoint custom=new CustomPinpoint(d, context);
        				custom.insertPinpoint(overlayItem);
        				overlayList.add(custom);
					}
					
				}
			}
        	
        	
        	if(isGPSEnabled){
        		locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        		Log.d("GPS Enabled","GPS Enabled");
	
        		if(locationmanager!=null){
        			//get location
        			location=locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			
        			if(location!=null){
        				Toast.makeText(Main.this, "GPS Location Found", Toast.LENGTH_SHORT).show();
        				lat=(int) (location.getLatitude()*1E6);
        				longi=(int) (location.getLongitude()*1E6);
				
        				GeoPoint mylocation=new GeoPoint(lat, longi);
		        
        				OverlayItem overlayItem = new OverlayItem(mylocation, "What's up?", "I live here'");
        				CustomPinpoint custom=new CustomPinpoint(d, context);
        				custom.insertPinpoint(overlayItem);
        				overlayList.add(custom);
        			}
        		}
        	}
        }
        }catch(Exception e){
        	e.printStackTrace();
        	//Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        	
        }
        
        
        
    }
  

    
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		//compass.disableCompass();
		super.onPause();
		//lm.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		//compass.enableCompass();
		// TODO Auto-generated method stub
		super.onResume();
		//lm.requestLocationUpdates(towers, 500, 1, this);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
	class Touchy extends Overlay{
		
		public boolean onTouchEvent(MotionEvent e,MapView m){
			
			if(e.getAction()== MotionEvent.ACTION_DOWN){
				start=e.getEventTime();
				x=(int)e.getX();
				y=(int) e.getY();
				
			touchedPoint=map.getProjection().fromPixels(x, y);
			}
			if(e.getAction()==MotionEvent.ACTION_UP){
				stop=e.getEventTime();
				
			}
			if(stop-start>1500){
				AlertDialog alert=new AlertDialog.Builder(Main.this).create();
				alert.setTitle("Pick an Option");
				alert.setMessage("I told you to pick an Option man....");
				alert.setButton("place a Pinpoint", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						OverlayItem overlayItem = new OverlayItem(touchedPoint, "What's up?", "2nd string'");
						CustomPinpoint custom=new CustomPinpoint(d, context);
						custom.insertPinpoint(overlayItem);
						overlayList.add(custom);
						
					}
				});
				
				alert.setButton2("Get Address", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Geocoder geocoder=new Geocoder(getBaseContext(), Locale.getDefault());
						
						try{
							List<Address> address= geocoder.getFromLocation(touchedPoint.getLatitudeE6()/1E6, touchedPoint.getLongitudeE6()/1E6, 1);
							
							if(address.size()>0){
								String display="";
								for(int i=0;i<address.get(0).getMaxAddressLineIndex();i++){
									
									display+=address.get(0).getAddressLine(i)+"\n";
									
								}	
								Toast t=Toast.makeText(getBaseContext(), display, Toast.LENGTH_LONG);
								t.show();
							}
							
						}catch(IOException e){
							e.printStackTrace();
							
						}
					}
				});
				alert.setButton3("Toggle View", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(map.isSatellite()){
							
							map.setSatellite(false);
							map.setStreetView(true);
						}
						else{
							map.setStreetView(false);
							map.setSatellite(true);
							
						}
					}
				});
				
				alert.show();
				return true;
			}
			return false;
		}
	}

	@Override
	public void onLocationChanged(Location l) {
		// TODO Auto-generated method stub
		lat=(int)(l.getLatitude()*1E6);
		longi=(int)(l.getLongitude()*1E6);
		
		GeoPoint mylocation=new GeoPoint(lat, longi);
        
        OverlayItem overlayItem = new OverlayItem(mylocation, "What's up?", "2nd string'");
		CustomPinpoint custom=new CustomPinpoint(d, context);
		custom.insertPinpoint(overlayItem);
		overlayList.add(custom);
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
	public void onDrawerOpened() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if(arg0.isChecked()){
			
			sd.lock();
		}
		else{
			
			sd.unlock();
		}
	}
}
