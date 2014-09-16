package com.jc.b2b.admin;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.b2b.R;
import com.jc.b2b.utils.B2BUtils;


public class ViewComplaintFragment extends Fragment {
	String sm="";
	public ViewComplaintFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle=getArguments();
		List<NameValuePair> params = new LinkedList<NameValuePair>();

	    params.add(new BasicNameValuePair("id",bundle.getString("id")));
	    new updateServer().execute(params);
		View v=inflater.inflate(R.layout.layout_complaint, container,
				false);
		TextView complainer=(TextView)v.findViewById(R.id.complainer);
		complainer.setText(bundle.getString("email"));
		TextView title=(TextView)v.findViewById(R.id.title);
		title.setText(bundle.getString("title"));
		TextView desc=(TextView)v.findViewById(R.id.desc);
		desc.setText(bundle.getString("desc"));
		sm=bundle.getString("email");
		if(bundle.getString("shopid")!=null)
		{
			TextView complainabout=(TextView)v.findViewById(R.id.complainAbout);
			complainabout.setVisibility(View.VISIBLE);
			TextView shop=(TextView)v.findViewById(R.id.shop);
			shop.setText(bundle.getString("shopname")+"(Shop id:"+bundle.getString("shopid")+")");
			shop.setVisibility(View.VISIBLE);
			
		}
		if(bundle.getString("itemid")!=null)
		{
			TextView item=(TextView)v.findViewById(R.id.item);
			item.setText(bundle.getString("itemname")+"(Item id: "+bundle.getString("itemid")+")");
			item.setVisibility(View.VISIBLE);
		}
		Button btn=(Button)v.findViewById(R.id.respond);
		btn.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View arg) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/html");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{sm});
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return v;
	}
class updateServer extends AsyncTask<List<NameValuePair>,String, String>
{

	@Override
	protected String doInBackground(List<NameValuePair>... params) {
		B2BUtils.submitSimpleForm("http://10.0.2.2:8080/B2B/setComplaintStatus.jsp", params[0]);
		
		return null;
	}

	
	
}
}
