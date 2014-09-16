package com.jc.b2b.admin;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jc.b2b.R;
import com.jc.b2b.utils.InsertForm;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ShopPricing extends Fragment {
EditText desc,cost,days,category;
Spinner type;
Button btn;
android.support.v4.app.FragmentTransaction fragmentTransaction;
android.support.v4.app.FragmentManager fm;
	public ShopPricing() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		fm = getActivity().getSupportFragmentManager();
		fragmentTransaction = fm.beginTransaction();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_shop_pricing, container,
				false);
		desc=(EditText)view.findViewById(R.id.desc);
		desc.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1)
				{
					if(desc.getText().toString().length()<1)
					{
						desc.setText("Description cannot be empty");
					}
				}
				
			}
		});
		cost=(EditText)view.findViewById(R.id.cost);
		cost.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1)
				{

					if(isNumber(cost.getText().toString()))
					{}
					else
					{
						cost.setError("Invalid cost");
					}
				}
				
			}
		});
		category=(EditText)view.findViewById(R.id.category);
		category.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus)
				{
					if(isNumber(category.getText().toString() ))
					{}
					else
					{
						category.setError("Invalid number of categories");
					}
				}
				
			}
		});
		days=(EditText)view.findViewById(R.id.days);
		days.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(isNumber(days.getText().toString() ))
				{}
				else
				{
					days.setError("Invalid number of days");
				}
			}
		});
		type=(Spinner)view.findViewById(R.id.type);
		btn=(Button)view.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(desc.getText().toString().length()>1 &isNumber(cost.getText().toString()))
				{
				ViewShopPricing ab=new ViewShopPricing();
				List<NameValuePair> params=new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("opt", "insert"));
				params.add(new BasicNameValuePair("t", "shop"));
				params.add(new BasicNameValuePair("time", days.getText().toString()));
				params.add(new BasicNameValuePair("cat", category.getText().toString()));	
				params.add(new BasicNameValuePair("cost", cost.getText().toString()));
				params.add(new BasicNameValuePair("name", desc.getText().toString()));
				params.add(new BasicNameValuePair("type", type.getSelectedItem().toString()));
				new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/pricing.jsp");
				cost.setText("");
				desc.setText("");
				days.setText("");
				category.setText("");
				fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.myfragment, ab);
				fragmentTransaction.commit();
				}
				else
				{
					Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		return view;
	}
	public boolean  isNumber(String v) {
		  
		  return v.trim().length() > 0 && v.trim().matches("^[1-9]\\d*(\\.\\d+)?$");
		}
}
