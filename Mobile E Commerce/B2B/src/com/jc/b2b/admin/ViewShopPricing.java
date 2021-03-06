package com.jc.b2b.admin;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
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
import com.jc.b2b.utils.ShopPrices;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ViewShopPricing extends Fragment {
	ExpandableListView lv;
	MyAadpter adapter;
	ArrayList<String> groupItems=new ArrayList<String>();
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;
	ArrayList<ArrayList<ShopPrices>>childItems=new ArrayList<ArrayList<ShopPrices>>();
	
	public ViewShopPricing() {
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
		View view= inflater.inflate(R.layout.fragment_view_shop_pricing, container,
				false);
		groupItems.clear();
		childItems.clear();
		adapter=new MyAadpter(getActivity());
		
		lv=(ExpandableListView)view.findViewById(R.id.list);
		lv.setAdapter(adapter);
		lv.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
					int arg3, long arg4) {
				ViewShopPricingDetails ab=new ViewShopPricingDetails();
				ShopPrices ap=childItems.get(arg2).get(arg3);
				Bundle bundle=new Bundle();
				bundle.putSerializable("ap",ap);
				
				ab.setArguments(bundle);
				fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.myfragment, ab);
				fragmentTransaction.commit();
				return true;
			}
		});
		new FetchData().execute("");
		return view;
	}

	class MyAadpter extends BaseExpandableListAdapter {
Activity activity;

		public MyAadpter(Activity activity) {
	super();
	this.activity = activity;
}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childItems.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater
						.inflate(R.layout.advertisement_list, null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.textView1);
			tv.setText(childItems.get(groupPosition).get(childPosition).getDescription());
			TextView tv2= (TextView) convertView.findViewById(R.id.textView2);
			tv2.setText("No of categories:"+childItems.get(groupPosition).get(childPosition).getCategories());
			TextView tv3 = (TextView) convertView.findViewById(R.id.textView3);
			tv3.setText("$"+childItems.get(groupPosition).get(childPosition).getCost()+Html.fromHtml("</br> No: of days"+childItems.get(groupPosition).get(childPosition).getTime()));
			return convertView;

		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return childItems.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return groupItems.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupItems.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater
						.inflate(R.layout.group_layout, null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.group);
			tv.setText(groupItems.get(groupPosition));
			
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}
class FetchData extends AsyncTask<String, String, String>
{

	@Override
	protected String doInBackground(String... params) {
		JSONObject ob=B2BUtils.getJSOn("http://10.0.2.2:8080/B2B/pricing.jsp?opt=select&t=shop");
		//Log.d("Object", ob.length()+"");
		Iterator<?> it=ob.keys();
		while(it.hasNext())
		{
			String an=(String)it.next();
			
			try
			{
			JSONObject t=ob.getJSONObject(an);
			if(t.length()>0)
			{
				groupItems.add(an);
				Iterator<?> tr=t.keys();
				ArrayList<ShopPrices> tem=new ArrayList<ShopPrices>();
				while(tr.hasNext())
				{
					String id=tr.next().toString();
					JSONObject temp=t.getJSONObject(id);
					tem.add(new ShopPrices(temp.getString("name"), temp.getString("time"), temp.getString("categories"), temp.getString("cost"), an, id));
				}
				childItems.add(tem);
			}
			}
			catch(Exception e)
			{
				Log.d("Error", e.toString());
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		adapter.notifyDataSetChanged();
		super.onPostExecute(result);
	}
	
	
}
}
