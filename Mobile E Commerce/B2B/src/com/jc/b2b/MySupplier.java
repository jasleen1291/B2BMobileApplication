package com.jc.b2b;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jc.b2b.utils.B2BUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class MySupplier extends Fragment {
	ArrayList<String> id=new ArrayList<String>();
	ArrayList<String> names=new ArrayList<String>();
	ArrayAdapter<String> adapter;
	ListView lv;
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		fm = getActivity().getSupportFragmentManager();
		fragmentTransaction = fm.beginTransaction();
		super.onCreate(savedInstanceState);
	}
	public MySupplier() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		id.clear();
		names.clear();
		adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,id);
		View view= inflater
				.inflate(R.layout.fragment_my_supplier, container, false);
		lv=(ListView)view.findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String ap=names.get(arg2);
				Bundle bundle=new Bundle();
				bundle.putString("ap",ap);
				PlaceOrderRetailer ab=new PlaceOrderRetailer();
				ab.setArguments(bundle);
				fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.myfragment, ab);
				fragmentTransaction.commit();
				
			}
		});
		lv.setAdapter(adapter);
		new FetchMySupplier().execute("");
		return view;
	}
	class FetchMySupplier extends AsyncTask<String, String, String>
	{

		@Override
		protected void onPostExecute(String result) {
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			JSONArray ar=B2BUtils.getJSOnArr("http://10.0.2.2:8080/B2B/my_supplier.jsp?opt=mysupplier&shopid="+B2BUtils.shop.getIdshop());
			System.out.println("http://10.0.2.2:8080/B2B/my_supplier.jsp?opt=mysupplier&shopid="+B2BUtils.shop.getIdshop());
			for(int i=0;i<ar.length();i++)
			{
				try {
					names.add((ar.getString(i)));
					id.add(B2BUtils.shops.get(ar.getString(i)));
				} catch (JSONException e) {
					Log.d(B2BUtils.LogString, e.toString());
				}
			}
			return null;
		}
		
	}
}
