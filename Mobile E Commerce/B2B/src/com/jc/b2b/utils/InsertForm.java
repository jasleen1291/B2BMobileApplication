package com.jc.b2b.utils;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class InsertForm extends AsyncTask<String, String, String> {
Activity act;
List<NameValuePair> params;
ProgressDialog progress;
JSONObject ob;
	public InsertForm(Activity act,List<NameValuePair> params) {
		this.act=act;
		this.params=params;
	}

	@Override
	protected void onPostExecute(String result) {
		if (progress.isShowing()) {
			progress.dismiss();

			
			
		}
		try
		{
		if(ob.getString("Status")!=null)
		{
			if(ob.getString("Status").equals("Values inserted"))
			{
				Toast.makeText(act, "Values inserted",Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(act, "Values not inserted",Toast.LENGTH_LONG).show();
			}
		}
		}
		catch(Exception e)
		{
			Log.d(B2BUtils.LogString, e.toString());
			Toast.makeText(act, "Check Network Connection",Toast.LENGTH_LONG).show();
		}
		
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		progress=new ProgressDialog(act);
		progress.setTitle("Submitting form...");
		progress.show();
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) {
		ob=B2BUtils.submitSimpleForm(arg0[0], params);
		
		return null;
	}

}
