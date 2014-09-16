package com.jc.b2b.admin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jc.b2b.R;
import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.InsertForm;

public class AdvertisementPricing extends Fragment {
	ArrayList<String> categories=new ArrayList<String>();
	EditText desc,days,cost;
	Spinner category,type;
	ArrayAdapter<String> myAdapter;
	public AdvertisementPricing() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_advertisement_pricing,
				container, false);
		categories.clear();
		desc=(EditText)view.findViewById(R.id.desc);
		days=(EditText)view.findViewById(R.id.days);
		cost=(EditText)view.findViewById(R.id.cost);
		category=(Spinner)view.findViewById(R.id.category);
		type=(Spinner)view.findViewById(R.id.type);
		myAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,categories);
		myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		
		category.setAdapter(myAdapter);
		Button btn=(Button)view.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(desc.getText().toString().length()<1)
				{
					desc.setError("Description cannot be empty");
				}
				else if(days.getText().toString().length()<1)
				{
					days.setError("Days cannot be empty");
				}
				else if(cost.getText().toString().length()<1)
				{
					cost.setError("Cost cannot be empty");
				}
				else
				{
					List<NameValuePair> params=new LinkedList<NameValuePair>();
					params.add(new BasicNameValuePair("opt", "insert"));
					params.add(new BasicNameValuePair("t", "advertisement"));
					params.add(new BasicNameValuePair("cost", cost.getText().toString()));
					params.add(new BasicNameValuePair("days", days.getText().toString()));
					params.add(new BasicNameValuePair("desc", desc.getText().toString()));
					params.add(new  BasicNameValuePair("category", category.getSelectedItem().toString()));
					params.add(new BasicNameValuePair("type", type.getSelectedItem().toString()));
					new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/pricing.jsp");
				}
			}
		});
		new getCategories().execute("");
		return view;
	}

	class getCategories extends AsyncTask<String, String, String>
	{
		ProgressDialog progress;
		
		@Override
		protected String doInBackground(String... params) {
			//ArrayList<String> a=(ArrayList<String>) (B2BUtils.zerolevelcategories.values());
			categories.addAll(B2BUtils.zerolevelcategories.values());
			//Log.d("a", categories.size()+"");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (progress.isShowing()) {
				progress.dismiss();
			}
			myAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			progress=new ProgressDialog(getActivity());
			progress.setTitle("Fetching categories...");
			progress.show();
			super.onPreExecute();
		}
		
	}

	

}
