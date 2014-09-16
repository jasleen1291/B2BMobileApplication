package com.jc.b2b;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterationActivity extends Activity {

	EditText username,pass,email;
	Spinner signUpAs;
	TextView alreadyRegis;
	Button signUp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registeration);
		username=(EditText)findViewById(R.id.username);
		username.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus)
				{
					if(username.getText().toString().length()<1)
					{
						username.setError("Username cannot be empty");
					}
					else
					{
						new checkAvail().execute("");
					}
				}
			}
		});
		pass=(EditText)findViewById(R.id.pass);
		email=(EditText)findViewById(R.id.email1);
		signUpAs=(Spinner)findViewById(R.id.signUp);
		signUp=(Button)findViewById(R.id.signupBtn);
		alreadyRegis=(TextView)findViewById(R.id.clickToLogin);
		alreadyRegis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(RegisterationActivity.this,LoginActivity.class));
				
			}
		});
		signUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				attemptSignUp();
			}
		});
		
	}
private void attemptSignUp()
{
	if(username.getText().toString().length()<1)
	{
		username.setError("Username cannot be empty");
	}
	else if(username.getText().toString().length()<3)
	{
		username.setError("Username cannot be less than 3 characters");
	}
	else if(pass.getText().toString().length()<1)
	{
		pass.setError("Password cannot be empty");
	}
	else if(pass.getText().toString().length()<4)
	{
		pass.setError("Password is too short");
	}
	else if(email.getText().toString().length()<1)
	{
		email.setError("Email cannot be empty");
	}
	else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
	{
		email.setError("Invalid Email address");
	}
	else
	{
		if(signUpAs.getSelectedItem().equals("Customer"))
		{
			new signUpAsCustomer().execute("");
		}
		else
		{
			Intent intent=new Intent(RegisterationActivity.this, SignUp.class);
			intent.putExtra("type", signUpAs.getSelectedItem().toString());
			intent.putExtra("email", email.getText().toString());
			intent.putExtra("pass", pass.getText().toString());
			intent.putExtra("username", username.getText().toString());
			startActivity(intent);
		}
	}
}
	
class signUpAsCustomer extends AsyncTask<String, String, String>

{
	String status="";

	@Override
	protected String doInBackground(String... param) {
		String url="http://10.0.2.2:8080/B2B/signup?opt=signup&type=customer&";
		

	    List<NameValuePair> params = new LinkedList<NameValuePair>();

	    params.add(new BasicNameValuePair("username", username.getText().toString()));
	    params.add(new BasicNameValuePair("email", email.getText().toString()));
	    params.add(new BasicNameValuePair("password", pass.getText().toString()));


	    String paramString = URLEncodedUtils.format(params, "utf-8");

	    url += paramString;
	    Log.d("url", url);
	    InputStream content = null;
	    String result="";
	    try {
	      HttpClient httpclient = new DefaultHttpClient();
	      HttpResponse response = httpclient.execute(new HttpGet(url));
	      content = response.getEntity().getContent();
	    } catch (Exception e) {
	      Log.d("[GET REQUEST]", "Network exception"+ e);
	    }
	    try
	    {
	    BufferedReader in = new BufferedReader(new InputStreamReader(content));

	            String inputLine;
	            while ((inputLine = in.readLine()) != null)
	                result=result+(inputLine);
	            in.close();
	            JSONObject ob=new JSONObject(result);
	            status=ob.getString("Status");
	    }
	    catch(Exception e)
	    {
	    	 Log.d("[GET REQUEST]", "Network exception"+ e);
	    }
	    
		return status;
	}

	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
		if(status.equals("Successful"))
		{
			Intent intent=new Intent(RegisterationActivity.this,LoginActivity.class);
			startActivity(intent);
		}
		super.onPostExecute(result);
	}
	
}
class checkAvail extends AsyncTask<String, String, String>
{
	String result="",status="";
	@Override
	protected String doInBackground(String... params) {
		try
		{
		URL oracle = new URL("http://10.0.2.2:8080/B2B/signup?opt=avail&username="+username.getText().toString());
        Log.d("url", "http://10.0.2.2:8080/B2B/signup?opt=avail&username="+username.getText().toString());
		BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
           result=result+(inputLine);
        in.close();
        JSONObject ob=new JSONObject(result);
        status=ob.getString("Status");
        
		}
		catch(Exception e)
		{
			Log.d("Error", e.toString());
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if(!status.equals("Available"))
        {
        	username.setError("Username not available");
        }
		super.onPostExecute(result);
	}
	
}
}
