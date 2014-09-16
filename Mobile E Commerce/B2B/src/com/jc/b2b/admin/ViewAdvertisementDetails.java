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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.jc.b2b.R;
import com.jc.b2b.utils.AdvertisementPrice;
import com.jc.b2b.utils.InsertForm;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ViewAdvertisementDetails extends Fragment {
	
	EditText desc,days,cost;
	ArrayAdapter<String> myAdapter;
	AdvertisementPrice ap;
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;

	public ViewAdvertisementDetails() {
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
		Bundle bundle=getArguments();
		View view=inflater.inflate(R.layout.view_advertisement_detail, container,false);
		ap=(AdvertisementPrice) bundle.getSerializable("ap");
		
		desc=(EditText)view.findViewById(R.id.desc);
		desc.setText(ap.getDesc());
		days=(EditText)view.findViewById(R.id.days);
		days.setText(ap.getDays());
		cost=(EditText)view.findViewById(R.id.cost);
		cost.setText(ap.getCost());
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
					ViewAdvertisements ab=new ViewAdvertisements();
					List<NameValuePair> params=new LinkedList<NameValuePair>();
					params.add(new BasicNameValuePair("opt", "update"));
					params.add(new BasicNameValuePair("t", "advertisement"));
					params.add(new BasicNameValuePair("id", ap.getId()));
					params.add(new BasicNameValuePair("cost", cost.getText().toString()));
					params.add(new BasicNameValuePair("days", days.getText().toString()));
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
				ViewAdvertisements ab=new ViewAdvertisements();
				List<NameValuePair> params=new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("opt", "delete"));
				params.add(new BasicNameValuePair("t", "advertisement"));
				params.add(new BasicNameValuePair("id", ap.getId()));
				new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/pricing.jsp");
				fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.myfragment, ab);
				fragmentTransaction.commit();
			}
		});
		return view;
	}



	
}
