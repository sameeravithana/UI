package com.mywork.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity implements OnClickListener{

	Button bsubmit;
	TextView tv1,tv2,tv3,tv4;
	EditText username,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		tv1=(TextView) findViewById(R.id.topictv);
		tv2=(TextView) findViewById(R.id.unametv);
		tv3=(TextView) findViewById(R.id.pastv);
		tv4=(TextView) findViewById(R.id.resulttv);
		
		username=(EditText) findViewById(R.id.usernameet);
		password=(EditText) findViewById(R.id.paswrdet);
		
		bsubmit=(Button) findViewById(R.id.bsubmit);
		bsubmit.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String uname=username.getText().toString();
		String pass=password.getText().toString();
		
		if(uname.equals("admin") && pass.equals("123")){
			
			tv4.setText("Valid");
			Intent i=new Intent(Login.this,MainInterface.class);
			startActivity(i);
			finish();
		}
		else{
			
			tv4.setText("Invalid");
		}
	}
	
	
	
}
