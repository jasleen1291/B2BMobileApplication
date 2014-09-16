package com.jc.b2b.admin;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.jc.b2b.R;
import com.jc.b2b.utils.InsertForm;
import com.jc.b2b.utils.ShopPrices;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ViewShopPricingDetails extends Fragment {
	EditText desc,cost,days,category;
	
	ArrayAdapter<String> myAdapter;
	ShopPrices ap;
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;public ViewShopPricingDetails() {
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
		View view=inflater.inflate(R.layout.fragment_view_shop_pricing_details,
				container, false);
		Bundle bundle=getArguments();
		ap=(ShopPrices) bundle.getSerializable("ap");
		
		desc=(EditText)view.findViewById(R.id.desc);
		desc.setText(ap.getDescription());		
		cost=(EditText)view.findViewById(R.id.cost);
		cost.setText(ap.getCost());
		days=(EditText)view.findViewById(R.id.days);
		days.setText(ap.getTime());
		category=(EditText)view.findViewById(R.id.category);
		category.setText(ap.getCategories());
				Button btn=(Button)view.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(desc.getText().toString().length()<1)
				{
					desc.setError("Description cannot be empty");
				}
				
				else if(!isNumber(cost.getText().toString()))
				{
					cost.setError("Invalid Cost");
				}
				else if(!isNumber(days.getText().toString()))
				{
					days.setError("Invalid Days");
				}
				else if(!isNumber(category.getText().toString()))
				{
					category.setError("Invalid No of categories");
				}
					else
				{
					ViewShopPricing ab=new ViewShopPricing();
					List<NameValuePair> params=new LinkedList<NameValuePair>();
					params.add(new BasicNameValuePair("opt", "update"));
					params.add(new BasicNameValuePair("t", "fine"));
					params.add(new BasicNameValuePair("id", ap.getId()));
					params.add(new BasicNameValuePair("cost", cost.getText().toString()));
					params.add(new BasicNameValuePair("desc", desc.getText().toString()));
					new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/pricing.jsp");
					fragmentTransaction = fm.beginTransaction();
					fragmentTransaction.replace(R.id.myfragment, ab);
					fragmentTransaction.commit();
				}
			}
		});
		Button btn2=(Button)view.findViewById(R.id.add);
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ViewShopPricing ab=new ViewShopPricing();
				List<NameValuePair> params=new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("opt", "delete"));
				params.add(new BasicNameValuePair("t", "fine"));
				params.add(new BasicNameValuePair("id", ap.getId()));
				new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/pricing.jsp");
				fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.myfragment, ab);
				fragmentTransaction.commit();
			}
		});

				return view;
	}
	public boolean  isNumber(String v) {
		  try
		  {
			  Log.d("hello",  Float.parseFloat(v)+"");
			  Float.parseFloat(v);
			  return true;
		  }
		  catch(Exception e)
		  {
			  Log.d("hello", e.toString());
			  return false;
		  }
		}
}
