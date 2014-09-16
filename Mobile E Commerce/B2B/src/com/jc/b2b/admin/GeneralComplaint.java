package com.jc.b2b.admin;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jc.b2b.R;
import com.jc.b2b.utils.B2BUtils;

public class GeneralComplaint extends Fragment {
	ArrayList<String> email = new ArrayList<String>();
	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> desc = new ArrayList<String>();
	ArrayList<String> cid=new ArrayList<String>();
	MyAdapter ab;
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;

	public GeneralComplaint() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fm = getActivity().getSupportFragmentManager();
		fragmentTransaction = fm.beginTransaction();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		cid.clear();
		email.clear();
		title.clear();
		desc.clear();
		email.add("Complainer");
		title.add("Title");
		desc.add("Description");
		cid.add("-1");
		View view = inflater.inflate(R.layout.fragment_general_complaint,
				container, false);

		ListView lv = (ListView) view.findViewById(R.id.genComplaint);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ViewComplaintFragment ab=new ViewComplaintFragment();
				Bundle bundle=new Bundle();
				bundle.putString("id", cid.get(arg2));
				bundle.putString("email", email.get(arg2));
				bundle.putString("title", title.get(arg2));
				bundle.putString("desc", desc.get(arg2));
				ab.setArguments(bundle);
				fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.myfragment, ab);
				fragmentTransaction.commit();
				
			}
		});
		ab = new MyAdapter(getActivity());
		lv.setAdapter(ab);
		new FetchInfo().execute("");
		return view;
	}

	class MyAdapter extends BaseAdapter {
		Context context;

		public MyAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return email.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return email.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.general_complaint, arg2,
					false);
			TextView titletv = (TextView) view.findViewById(R.id.title);
			titletv.setText(title.get(arg0));

			if (arg0 == 0) {
				view.setBackgroundColor(Color.argb(254, 0, 25, 51));
				view.setPadding(5, 5, 5, 5);

			}
			return view;
		}

	}

	class FetchInfo extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			JSONObject ob = B2BUtils
					.getJSOn("http://10.0.2.2:8080/B2B/generalComplaint.jsp?opt=select");
			Iterator<?> it = ob.keys();
			while (it.hasNext()) {
				try {
					String ci=(String) it.next();
					JSONObject a = ob.getJSONObject(ci);
					cid.add(ci);
					email.add((String) a.get("email"));
					title.add((String) a.get("title"));
					desc.add((String) a.get("desc"));
				} catch (JSONException e) {
					Log.d(B2BUtils.LogString, e.toString());
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			ab.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}
}
