package com.mywork.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class MyGestureListener  extends GestureDetector.SimpleOnGestureListener{

	private static final int SWIPE_MIN_DISTANCE=150;
	private static final int SWIPE_MAX_OFF_PATH=100;
	private static final int SWIPE_THRESHOLD_VELOCITY=100;
	Context context;
	
	public MyGestureListener(Context c) {
		// TODO Auto-generated constructor stub
		this.context=c;
	}
	
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

		//Toast.makeText(getApplicationContext,"Right Swipe", Toast.LENGTH_SHORT).show();
			

		} else {

		//Toast.makeText(getApplicationContext(), “Left Swipe”, Toast.LENGTH_SHORT).show();

		}

		return true;
		}
		return false;
	}
	
}

