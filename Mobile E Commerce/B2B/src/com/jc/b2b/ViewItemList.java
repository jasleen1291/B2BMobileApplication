package com.jc.b2b;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.Items;


public class ViewItemList extends Fragment {
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;
	Activity activity;
	ArrayList<Items> items=new ArrayList<Items>();
	GridAdapter a;
	public ViewItemList() {
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
		items.clear();
		a=new GridAdapter();
		activity=getActivity();
		View view=inflater.inflate(R.layout.fragment_view_item_list, container,
				false);
		ListView grd=(ListView)view.findViewById(R.id.grid1);
		grd.setAdapter(a);
		grd.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ViewItem ab=new ViewItem();
				Items ap= items.get(arg2);
				Bundle bundle=new Bundle();
				bundle.putSerializable("ap",ap);
				
				ab.setArguments(bundle);
				fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.myfragment, ab);
				fragmentTransaction.commit();
				
				
			}
		});
		new FetchShopInfo().execute("");
		return view;
	}
class GridAdapter extends BaseAdapter
{

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		LayoutInflater infalInflater = (LayoutInflater)getActivity(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arg1=infalInflater.inflate(R.layout.grid_item, arg2, false);
		ImageView iv=(ImageView)arg1.findViewById(R.id.imageView1);
		
		if(B2BUtils.shop.getImgs().containsKey(items.get(arg0).getImagepath()))
		{
			iv.setImageBitmap(B2BUtils.shop.getImgs().get(items.get(arg0).getImagepath()));
		}
		else
		{
			//Log.d("wtf", B2BUtils.shop.getChildren().get(arg0).getImagepath());
		}
		TextView tv1=(TextView)arg1.findViewById(R.id.name);
		tv1.setText(items.get(arg0).getItemname());
		TextView tv2=(TextView)arg1.findViewById(R.id.sp);
		if(null!=items.get(arg0).getSp())
		{
			tv2.setText(items.get(arg0).getSp()+"");
		}
		else
		{
			tv2.setText("");
		}
		TextView tv3=(TextView)arg1.findViewById(R.id.cp);
		if(null!=items.get(arg0).getUnitcost())
		{
			tv3.setText(items.get(arg0).getUnitcost()+"");
		}
		else
		{
			tv3.setText("");
		}
		TextView tv4=(TextView)arg1.findViewById(R.id.discount);
		if(null!=items.get(arg0).getDiscount())
		{
			tv4.setText(items.get(arg0).getDiscount()+"");
		}
		else
		{
			tv4.setText("");
		}
		TextView tv5=(TextView)arg1.findViewById(R.id.minorder);
		if(null!=items.get(arg0).getMin_order())
		{
			tv5.setText(items.get(arg0).getMin_order()+"");
		}
		else
		{
			tv5.setText("");
		}
		return arg1;
	}
	
}
public class FetchShopInfo extends AsyncTask<String, Items, String>
{

	@Override
	protected void onProgressUpdate(Items... values) {
		// TODO Auto-generated method stub
		items.add(values[0]);
		a.notifyDataSetChanged();
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		B2BUtils.shop.setChildren(items);
		super.onPostExecute(result);
	}

	@Override
	protected String doInBackground(String... params) {
		JSONArray array = B2BUtils
		.getJSOnArr("http://10.0.2.2:8080/B2B/shop.jsp?opt=myshops&id="
				+ B2BUtils.getUser().getMasterid());
		try
		{
			JSONObject ob = array.getJSONObject(0);
		
			
			JSONArray ard = ob.getJSONArray("children");
			for (int i = 0; i < ard.length(); i++) {
				JSONObject child = ard.getJSONObject(i);
				JSONArray batch = child.getJSONArray("batch");
				ArrayList<String> qty, discounts, ids;
				qty = new ArrayList<String>();
				discounts = new ArrayList<String>();
					ids = new ArrayList<String>();
					if (batch.length() > 0) {
						for (int i1 = 0; i1 < batch.length(); i1++) {
							try {
								JSONObject btch = batch.getJSONObject(i1);
								qty.add(btch.getString("qty"));
								discounts.add(btch.getString("discount"));
								ids.add(btch.getString("id"));
							} catch (Exception e) {
								Log.d("Erroe", e.toString());
							}
						}
					}
					if(B2BUtils.shop.getImgs().containsKey(child
							.getString("imagepath")))
							{
						
							}
					else
					{
					Intent ic = new Intent(activity.getApplicationContext(),
							FetchImage.class);
					ic.putExtra("url", "http://10.0.2.2:8080/B2B/images/"
							+ child.getString("imagepath"));
					ic.putExtra("imagename", child.getString("itemname"));
					activity.startService(ic);
					}
					Items current=(new Items(child.getString("categoryid"),
							child.getString("status"), child
									.getString("imagepath"), child
									.getString("itemname"), child
									.getString("unit_of_measurement"),
							child.getString("qtyperunit"), child
									.getString("owner"), child
									.getString("longdesciption"), child
									.getString("iditem"), child
									.getString("sp"), child
									.getString("unitcost"), child
									.getString("min_order"), child
									.getString("item_shopid"), child
									.getString("discount"), child
									.getString("qtyonorder"), qty,
							discounts, ids, child.getString("qtyperunit")));
					publishProgress(current);
					

		}
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

	
	
}
private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
	@Override
	public void onReceive(Context context, Intent intent) {
		a.notifyDataSetChanged();
	}
};

@Override
public void onResume() {
	super.onResume();

	// Register mMessageReceiver to receive messages.
	activity.registerReceiver(
			mMessageReceiver, new IntentFilter(B2BUtils.IMAGE_INTENT));
}


@Override
public void onPause() {
	activity.unregisterReceiver(mMessageReceiver);
	super.onPause();
}

}
