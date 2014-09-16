package com.jc.b2b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.jc.b2b.utils.B2BUtils;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		try
		{
			Thread initBkgdThread = new Thread(new Runnable(){

				@Override
				public void run() {
					B2BUtils.getCategories();
					B2BUtils.getShops();
					startActivity(new Intent(SplashActivity.this,RegisterationActivity.class));
					finish();
				}});
		initBkgdThread.start();
		}
		catch(Exception e)
		{
			Log.d("error", e.toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
