package com.mywork.splash;


import com.mywork.ui.Login;
import com.mywork.ui.R;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainSplash extends Activity {

	private Thread mSplashThread; 
	 MediaPlayer oursong;

	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Splash screen view
	        setContentView(R.layout.splash);
	        final MainSplash sPlashScreen = this;
	        oursong = MediaPlayer.create(MainSplash.this, R.raw.music);
			oursong.start();
			
			
			final ImageView splashImageView =(ImageView) findViewById(R.id.SplashImageView);  
			 splashImageView.setBackgroundResource(R.drawable.flag);
			 final AnimationDrawable frameAnimation =(AnimationDrawable)splashImageView.getBackground();
			
	        
			 
	        // The thread to wait for splash screen events
	        mSplashThread =  new Thread(){
	            @Override
	            public void run(){
	                try {
	                    synchronized(this){
	                        // Wait given period of time or exit on touch
	                        wait(9000);
	                    }
	                }
	                catch(InterruptedException ex){ 
	                	ex.printStackTrace();
	                }
	                
	            
	                
	                
	                // Run next activity
	                Intent intent = new Intent();
	                intent.setClass(sPlashScreen, Login.class);
	                startActivity(intent);
	                                 
	            }
	        };
	        
	        mSplashThread.start(); 
	        
	        splashImageView.post(new Runnable(){  
               @Override  
               public void run() {  
                   frameAnimation.start();                  
               }              
           });  
	        
	        
	    }
	    
	    
	    
	    
	        
	    @Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			oursong.release();
			finish();
		}





		/**
	     * Processes splash screen touch events
	     */
	    @Override
	    public boolean onTouchEvent(MotionEvent evt)
	    {
	        if(evt.getAction() == MotionEvent.ACTION_DOWN)
	        {
	            synchronized(mSplashThread){
	                mSplashThread.notifyAll();
	            }
	        }
	        return true;
	    }    

}
