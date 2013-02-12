package com.mywork.ui;





import com.mywork.place.PlacesMain;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Html;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainInterface extends Activity implements OnClickListener{

	//MapView mv;
	ImageButton b1,b2,b3,b4;
	TextView tv1,tv2,tv3,tv4;
	//MapController controller;
	//GeoPoint point;
	
	private GestureDetector gd=null;
	RelativeLayout layout;
	
	int lat=(int)(6.9167*1E6);
	int longi=(int)(79.8433*1E6);
	ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_main);
		
		layout=(RelativeLayout) findViewById(R.id.rl1);
		
		//mv=(MapView) findViewById(R.id.mvMain);
		
		b1=(ImageButton) findViewById(R.id.newtour);
		b2=(ImageButton) findViewById(R.id.intelligence);
		b3=(ImageButton) findViewById(R.id.favourits);
		b4=(ImageButton) findViewById(R.id.communityhelp);
		
		tv1=(TextView) findViewById(R.id.newtourtv);
		tv2=(TextView) findViewById(R.id.intelligenttourtv);
		tv3=(TextView) findViewById(R.id.favouritstv);
		tv4=(TextView) findViewById(R.id.communitytv);
		
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		
		//controller=mv.getController();
		//point=new GeoPoint(lat,longi);
		//controller.animateTo(point);
		//controller.setZoom(14);
		
		//mv.setBuiltInZoomControls(true);
		
		gd= new GestureDetector(new MyGestureListener());
		layout.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				gd.onTouchEvent(event);
				return true;
			}
			
		});
	}
	
	public boolean dispatchTouchEvent(MotionEvent ev){

		super.dispatchTouchEvent(ev);

		 

		return gd.onTouchEvent(ev);

		 

		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ui_main, menu);
		return true;
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String btn="";
		
		switch(arg0.getId()){
		
		case R.id.newtour:
			btn="New Tour";
			//new NewTour().execute();
			Intent i0=new Intent(MainInterface.this,PlacesMain.class);
			startActivity(i0);
			break;
			
		case R.id.intelligence:
			//btn="Intelligence Tour";
			Intent i1=new Intent(MainInterface.this, Intelligence.class);
			startActivity(i1);
			break;
			
		case R.id.favourits:
			//btn="Favourits";
			Intent i2=new Intent(MainInterface.this, BarChart.class);
			startActivity(i2);
			break;
			
		case R.id.communityhelp:
			//btn="Community";
			Intent i3=new Intent(MainInterface.this, Community.class);
			startActivity(i3);
			break;
		
		}
		
		
		//Toast.makeText(getApplicationContext(), "Button"+btn+" is pressed", Toast.LENGTH_SHORT).show();
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}




	public class MyGestureListener  extends GestureDetector.SimpleOnGestureListener{

		private static final int SWIPE_MIN_DISTANCE=100;
		private static final int SWIPE_MAX_OFF_PATH=100;
		private static final int SWIPE_THRESHOLD_VELOCITY=50;
		
			
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			float dX = e2.getX()-e1.getX();

			float dY = e1.getY()-e2.getY();

			if (Math.abs(dY)<SWIPE_MAX_OFF_PATH &&

			Math.abs(velocityX)>=SWIPE_THRESHOLD_VELOCITY &&

			Math.abs(dX)>=SWIPE_MIN_DISTANCE ) {

				if (dX>0) {

					Toast.makeText(getApplicationContext(),"Right Swipe", Toast.LENGTH_SHORT).show();
					
				

				} else {

					//Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
					new MyTask().execute();
				}

				return true;
			}
			return false;
		}
		
	}
	
	class MyTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(MainInterface.this,Profile.class);
			startActivity(i);
			return null;
		}
		
		
	}
	
	class NewTour extends AsyncTask<String, String, String>{
		
		  @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(MainInterface.this);
	            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(MainInterface.this, Main.class);
			startActivity(i);
			return null;
		}
		
	
		protected void onPostExecute() {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            
		}
		
		
	}

	

}
