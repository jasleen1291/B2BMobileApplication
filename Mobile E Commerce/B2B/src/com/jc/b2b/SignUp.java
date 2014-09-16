package com.jc.b2b;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jc.b2b.utils.B2BUtils;

public class SignUp extends Activity {

	@Override
	protected void onStart() {
		
		super.onStart();
	}
	Spinner country,state,city;
	ArrayAdapter<String> countryAdapter,stateAdapter,cityAdpater,signupAsAdapter;
	EditText firstName,lastName,phone1,phone2,address,zipCode;
	Button signup;
	
	ArrayList<String> countryList=new ArrayList<String>();
	ArrayList<String> statesList=new ArrayList<String>();
	ArrayList<String> citesList=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		country=(Spinner)findViewById(R.id.country);
		state=(Spinner)findViewById(R.id.state);
		city=(Spinner)findViewById(R.id.city);
		signup=(Button)findViewById(R.id.signupBtn);
		
		//signupAs=(Spinner)findViewById(R.id.signup);
		countryAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item, countryList);
		//country.setBackgroundColor(Color.BLACK);
		stateAdapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, statesList);
		cityAdpater=new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,citesList);
		signupAsAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item);
		country.setAdapter(countryAdapter);
		state.setAdapter(stateAdapter);
		city.setAdapter(cityAdpater);
		//signupAs.setAdapter(signupAsAdapter);
		state.setEnabled(false);
		city.setEnabled(false);
		firstName=(EditText)findViewById(R.id.FirstName);
		lastName=(EditText)findViewById(R.id.LastName);
		phone1=(EditText)findViewById(R.id.phone1);
		phone2=(EditText)findViewById(R.id.phone2);
		address=(EditText)findViewById(R.id.address);
		zipCode=(EditText)findViewById(R.id.zipcode);
		new FetchCountries(SignUp.this).execute(new String[]{""});
		country.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				state.setEnabled(true);
				Log.d(countryList.get(arg2),B2BUtils.country.get(countryList.get(arg2)));
				new FetchStates().execute(new String[]{countryList.get(arg2)});
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		state.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				city.setEnabled(true);
				Log.d("lol", statesList.get(arg2));
				citesList.clear();
				citesList.addAll(B2BUtils.cities.get(statesList.get(arg2)));
				Log.d("lol", citesList.size()+"");
				cityAdpater.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final String p1=phone1.getText().toString();
				final String p2=phone2.getText().toString();
				if(firstName.getText().toString().length()<1)
				{
					firstName.setError("First Name cannot be empty");
				}
				else if(lastName.getText().toString().length()<1)
				{
					lastName.setError("Last Name cannot be empty");
				}
				else if(address.getText().toString().length()<1)
				{
					address.setError("Address canot be empty");
				}
				else if(zipCode.getText().toString().length()<1&&!TextUtils.isDigitsOnly(zipCode.getText().toString()))
				{
					zipCode.setError("Zipcode invalid");
				}
				else if(!TextUtils.isDigitsOnly(p1))
				{
					phone1.setError("Phone can only be in digits");
				}
				else if(!TextUtils.isDigitsOnly(p2))
				{
					phone2.setError("Phone can only be in digits");
					
				}
				else
				{
					Thread ab=new Thread(new Runnable() {
						
						@Override
						public void run() {
							Bundle bundle=getIntent().getExtras();
							List<NameValuePair> params=new LinkedList<NameValuePair>();
							params.add(new BasicNameValuePair("opt", "signup"));
							params.add(new BasicNameValuePair("type", bundle.getString("type")));
							params.add(new BasicNameValuePair("fname", firstName.getText().toString()));
							params.add(new BasicNameValuePair("lname", lastName.getText().toString()));
							params.add(new BasicNameValuePair("email", bundle.getString("email")));
							params.add(new BasicNameValuePair("phone1", p1));
							params.add(new BasicNameValuePair("phone2", p2));
							params.add(new BasicNameValuePair("address", address.getText().toString()));
							params.add(new BasicNameValuePair("city", city.getSelectedItem().toString()));
							params.add(new BasicNameValuePair("zip", zipCode.getText().toString()));
							params.add(new BasicNameValuePair("country", B2BUtils.country.get(country.getSelectedItem().toString())));
							params.add(new BasicNameValuePair("state", state.getSelectedItem().toString()));
							params.add(new BasicNameValuePair("bal", "0"));
							params.add(new BasicNameValuePair("username", bundle.getString("username")));
							params.add(new BasicNameValuePair("password", bundle.getString("pass")));
							JSONObject ob=B2BUtils.submitSimpleForm("http://10.0.2.2:8080/B2B/signup", params);
							try
							{
							if(ob.getString("Status").equalsIgnoreCase("Successful"))
							{
								startActivity(new Intent(SignUp.this,LoginActivity.class));
							}
							}
							catch(Exception e)
							{
								Log.d(B2BUtils.LogString, e.toString());
								Toast.makeText(getApplicationContext(), "Check Network Connection"+e.toString(),Toast.LENGTH_LONG).show();
							}// TODO Auto-generated method stub
							
						}
					});
					ab.start();
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}
class FetchCountries extends AsyncTask<String, String, String>
{

	ProgressDialog progress;
	Context context;
	public FetchCountries(Activity activity) {
		super();
	}

	@Override
	protected void onPostExecute(String result) {
		if (progress.isShowing()) {
			progress.dismiss();
		}
		countryAdapter.notifyDataSetChanged();
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		progress=new ProgressDialog(SignUp.this);
		progress.setTitle("Fetching Countries Please wait...");
		progress.show();
		
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		
		try
		{
		 URL oracle = new URL("http://10.0.2.2:8080/B2B/getCountryList.jsp");
		 
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
	       
	        String inputLine,result="";
	        while ((inputLine = in.readLine()) != null)
	            result=result+inputLine;
	        in.close();
	        JSONObject countryJSON=new JSONObject(result);
	        Iterator<?> it=countryJSON.keys();
	        while(it.hasNext())
	        {
	        	String key=it.next().toString();
	        	String value=countryJSON.get(key).toString();
	        	B2BUtils.country.put(value,key);
	        	countryList.add(value);
	        	}
	       Collections.sort(countryList);
		}
		catch(Exception e)
		{
			Log.d(B2BUtils.LogString,e.toString());
		}
		return null;
	}

}
class FetchStates extends AsyncTask<String, String, String>
{
ProgressDialog progress;
	@Override
	protected void onPostExecute(String result) {
		if (progress.isShowing()) {
			
			progress.dismiss();
		}
		stateAdapter.notifyDataSetChanged();
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		progress=new ProgressDialog(SignUp.this);
		progress.setTitle("Fetching States Please wait...");
		progress.show();
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try
		{
			statesList.clear();
		 URL oracle = new URL("http://10.0.2.2:8080/B2B/getCityList.jsp?cc="+B2BUtils.country.get(params[0]));
		 
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
	       
	        String inputLine,result="";
	        while ((inputLine = in.readLine()) != null)
	            result=result+inputLine;
	        in.close();
	        JSONObject countryJSON=new JSONObject(result);
	        Iterator<?> it=countryJSON.keys();
	        while(it.hasNext())
	        {
	        	String key=it.next().toString();
	        	JSONArray value=(JSONArray)countryJSON.get(key);
	        	ArrayList<String> obj=new ArrayList<String>();
	        	for(int i=0;i<value.length();i++)
	        	{
	        		obj.add((String) value.get(i));
	        		//Log.d(key, (String)value.get(i));
	        	}
	        	B2BUtils.cities.put(key, obj);
	        	statesList.add(key);
	        	}
	       Collections.sort(statesList);
		}
		catch(Exception e)
		{
			Log.d(B2BUtils.LogString,e.toString());
		}
		return null;
	}

}

}
