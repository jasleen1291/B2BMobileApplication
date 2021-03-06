package com.jc.b2b.admin;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jc.b2b.R;
import com.jc.b2b.utils.B2BUtils;


public class GeneralNotification extends Fragment {
	EditText email,title,message;
	String userid="-1";
	public GeneralNotification() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v= inflater.inflate(R.layout.fragment_general_notification,
				container, false);
		email=(EditText)v.findViewById(R.id.email);
		email.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1)
				 if(email.getText().toString().length()<1)
				{
					email.setError("Email cannot be empty");
				}
				else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
				{
					email.setError("Invalid Email address");
				}
				else
				{
					new userdetails().execute("");
				}
				
			}
		});
		title=(EditText)v.findViewById(R.id.title);
		title.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1)
				if(title.getText().toString().length()<1)
				{
					title.setError("Title cannot be empty");
				}
				
			}
		});
		message=(EditText)v.findViewById(R.id.message);
		message.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1)
				if(message.getText().toString().length()<1)
				{
					message.setError("Message cannot be empty");
				}
			}
		});
		Button btn=(Button)v.findViewById(R.id.submit);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				attempyLogin();
			}
		});
		return v;
	}

	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
	}

	@Override
	public void onDetach() {
		super.onDetach();
		}

	class userdetails extends AsyncTask<String, String, String>
	{
		ProgressDialog progress;
		JSONObject result;
		String emailadd="";
		@Override
		protected void onPostExecute(String r) {
			if (progress.isShowing()) {
				progress.dismiss();
			}
			try
			{
		if(result.getString("User")!=null)
		{
			if(result.getString("User")!="-1")
				
			{
				
				userid=(String) result.getString("User");
			}
			else
			{
				email.setError("Email address not registered");
			}
		}
			}
			catch(Exception e)
			{
				Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(r);
		}

		@Override
		protected void onPreExecute() {
			progress=new ProgressDialog(getActivity());
			progress.setTitle("Fetching UserInfo...");
			progress.show();
			emailadd=email.getText().toString();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			result=B2BUtils.getJSOn("http://10.0.2.2:8080/B2B/userInfo.jsp?email="+emailadd);
			return null;
		}
		
	}
	public void attempyLogin()
	{
		
		Intent em= new Intent(Intent.ACTION_SEND);
		em.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});		  
		em.putExtra(Intent.EXTRA_SUBJECT, "General Notification from admin: "+ title.getText().toString());
		em.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
		em.setType("message/rfc822");
		startActivity(em);
		
	}
	
}
