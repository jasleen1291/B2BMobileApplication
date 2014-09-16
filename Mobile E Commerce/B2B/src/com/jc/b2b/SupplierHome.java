package com.jc.b2b;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.InsertForm;

public class SupplierHome extends Fragment {
	EditText shopname,desc,paymentEmail;
	TextView noofcategories,itemsSelling,itemsAvailable,expirydate;
	Button update;
	public SupplierHome() {
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
		View view= inflater.inflate(R.layout.fragment_supplier_home, container,
				false);
		shopname=(EditText)view.findViewById(R.id.shopname);
		desc=(EditText)view.findViewById(R.id.desc);
		noofcategories=(TextView)view.findViewById(R.id.noofcategories);
		paymentEmail=(EditText)view.findViewById(R.id.paymentemail);
		itemsSelling=(TextView)view.findViewById(R.id.itemsSelling);
		itemsAvailable=(TextView)view.findViewById(R.id.itemsAvailable);
		expirydate=(TextView)view.findViewById(R.id.expirydate);
		update=(Button)view.findViewById(R.id.update);
		if(B2BUtils.shop!=null)
		{
			shopname.setText(B2BUtils.shop.getShopname());
			desc.setText(B2BUtils.shop.getDescription());
			noofcategories.setText(B2BUtils.shop.getCategoriesallowed());
			itemsSelling.setText(B2BUtils.shop.getChildren().size()+"");
			itemsAvailable.setText(B2BUtils.shop.getAvailable().size()+"");
			expirydate.setText(B2BUtils.shop.getExpirydate());
			paymentEmail.setText(B2BUtils.shop.getPaymentemail());
			update.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					List<NameValuePair> params=new LinkedList<NameValuePair>();
					params.add(new BasicNameValuePair("opt", "update"));
					params.add(new BasicNameValuePair("name", shopname.getText().toString()));
					params.add(new BasicNameValuePair("desc", desc.getText().toString()));
					params.add(new BasicNameValuePair("paymentemail", paymentEmail.getText().toString()));
					params.add(new BasicNameValuePair("noofcategories", noofcategories.getText().toString()));
					params.add(new BasicNameValuePair("id", B2BUtils.shop.getIdshop()));
					new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/shop.jsp");
					getActivity().finish();
					startActivity(new Intent(getActivity().getBaseContext(),Supplier_area.class));
					
				}
			});
		}
		return view;
	}



}
