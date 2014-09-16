package com.jc.b2b.admin;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.jc.b2b.R;
import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.ComplaintItem;


public class ShopComplaint extends Fragment {
	
	ArrayList<String> group=new ArrayList<String>();
	ArrayList<ArrayList<ComplaintItem>> child=new ArrayList<ArrayList<ComplaintItem>> ();
	MyAdapter ab;
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;
	ExpandableListView lv;
	public ShopComplaint() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fm = getActivity().getSupportFragmentManager();
		fragmentTransaction = fm.beginTransaction();
		group.clear();
		child.clear();
		/*
		 * 
		 * */
		View view = inflater.inflate(R.layout.fragment_shop_complaint,
				container, false);

		lv = (ExpandableListView) view.findViewById(R.id.complaint);
		
		lv.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
					int arg3, long arg4) {
				ViewComplaintFragment ab=new ViewComplaintFragment();
				Bundle bundle=new Bundle();
				bundle.putString("id", child.get(arg2).get(arg3).getId());
				bundle.putString("email", child.get(arg2).get(arg3).getEmail());
				bundle.putString("title", child.get(arg2).get(arg3).getTitle());
				bundle.putString("desc", child.get(arg2).get(arg3).getDesc());
				//bundle.putString("itemname", child.get(arg2).get(arg3).getItemname());
				//bundle.putString("itemid", child.get(arg2).get(arg3).getItemid());
				bundle.putString("shopname", child.get(arg2).get(arg3).getShopname());
				bundle.putString("shopid", child.get(arg2).get(arg3).getShopid());
				ab.setArguments(bundle);
				fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.myfragment, ab);
				fragmentTransaction.commit();
				return false;
			}
		});
		ab = new MyAdapter(getActivity());
		lv.setAdapter(ab);
		
		new FetchInfo().execute("");
		return view;

	}
	class MyAdapter extends BaseExpandableListAdapter {
		Context context;

		public MyAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public Object getChild(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return child.get(arg0).get(arg1);
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return arg1;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.general_complaint, null);
			TextView titletv = (TextView) view.findViewById(R.id.title);
			titletv.setText(child.get(arg0).get(arg1).getTitle());
			return view;
		}

		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			return child.get(arg0).size();
		}

		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			return group.get(arg0);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return group.size();
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View arg2,
				ViewGroup arg3) {
			LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			TextView tv=(TextView) inflater.inflate(R.layout.group_item, null);
			tv.setText(group.get(arg0));
			return tv;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return true;
		}


	}
	class FetchInfo extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			JSONObject ob = B2BUtils
					.getJSOn("http://10.0.2.2:8080/B2B/shopComplaint.jsp?opt=select");
			Iterator<?> it = ob.keys();
			while (it.hasNext()) {
				try {
					String ci=(String) it.next();
					group.add(ci);
					JSONArray a = ob.getJSONArray(ci);
					ArrayList<ComplaintItem> citem=new ArrayList<ComplaintItem>();
					for(int i=0;i<a.length();i++)
					{
						JSONObject ab=a.getJSONObject(i);
						citem.add(new ComplaintItem(ab.getString("shopid"), ab.getString("title"), ab.getString("desc"), ab.getString("email"), ci, ab.getString("id")));
						//citem.add(new ComplaintItem(ab.getString("shopid"), ab.getString("title"), ab.getString("desc"), ab.getString("itemname"), ab.getString("email"), ci, ab.getString("idComplaint"), ab.getString("itemid")));
					}
					child.add(citem);
				} catch (JSONException e) {
					Log.d(B2BUtils.LogString, e.toString());
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			ab.notifyDataSetChanged();
			for(int i=0;i<group.size();i++)
			{
				lv.expandGroup(i);
			}
			super.onPostExecute(result);
		}

	}
	
}