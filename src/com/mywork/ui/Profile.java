package com.mywork.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity implements OnClickListener{

	ImageView iv;
	ImageButton ib1,ib2,ib3;
	TextView tv1,tv2,tv3,tv4,tv5,tv6;
	RelativeLayout rl;
	GestureDetector gd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		rl=(RelativeLayout) findViewById(R.id.rl);
		
		iv=(ImageView) findViewById(R.id.imageView1);
		
		tv1=(TextView) findViewById(R.id.actnametv);
		tv2=(TextView) findViewById(R.id.detailtv);
		tv3=(TextView) findViewById(R.id.actdetailtv);
		tv4=(TextView) findViewById(R.id.mytourstv);
		tv5=(TextView) findViewById(R.id.settingstv);
		tv6=(TextView) findViewById(R.id.touranalyzetv);
		
		ib1=(ImageButton)findViewById(R.id.pasttourib);
		ib2=(ImageButton)findViewById(R.id.changepassib);
		ib3=(ImageButton)findViewById(R.id.analyzeib);
		
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		ib3.setOnClickListener(this);
		
		gd= new GestureDetector(new MyGestureListener());
		rl.setOnTouchListener(new OnTouchListener(){

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
	
	
	public class MyGestureListener  extends GestureDetector.SimpleOnGestureListener{

		private static final int SWIPE_MIN_DISTANCE=150;
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

					//Toast.makeText(getApplicationContext(),"Right Swipe", Toast.LENGTH_SHORT).show();
					//Intent i=new Intent(Profile.this,MainInterface.class);
					//startActivity(i);
					finish();

				} else {

					Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();

				}

				return true;
			}
			return false;
		}
		
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.pasttourib:
			
			Intent i0=new Intent(Profile.this, MyTours.class);
			startActivity(i0);
			break;
			
		case R.id.analyzeib:
			Intent i1=new Intent(Profile.this, TourAnalyzer.class);
			startActivity(i1);
			break;
		case R.id.changepassib:
			Intent i2=new Intent(Profile.this, Settings.class);
			startActivity(i2);
			break;
		
		}
		
	}

	
}
