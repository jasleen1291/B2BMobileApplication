package com.jc.b2b;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.CategoryAdapter;
import com.jc.b2b.utils.InsertForm;
import com.jc.b2b.utils.Items;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class Subscribe_new_items extends Fragment {
ListView lv,lv2;
ViewFlipper v1;
EditText search;
//HashMap<String, Bitmap> item;
private File cacheDir;
private String CACHE_FOLDER="cache";
ArrayList<Items> items,original;
String categoryid="0";
ImageAdapter adapter;
String tmpLocation = 
	Environment.getExternalStoragePublicDirectory(
	        Environment.DIRECTORY_PICTURES
	    ).getPath() + File.separator +CACHE_FOLDER ;
	public Subscribe_new_items() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		View view= inflater.inflate(R.layout.fragment_subscribe_new_items,
				container, false);
		lv=(ListView)view.findViewById(R.id.listView1);
		lv.setAdapter(new CategoryAdapter(B2BUtils.shop.getMycategories(),getActivity()));
		search=(EditText)view.findViewById(R.id.search);
		search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				adapter.getFilter().filter(s.toString());
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
				
			}
		});
		v1=(ViewFlipper)view.findViewById(R.id.viewFlipper1);
		original=new ArrayList<Items>();
		
		items=new ArrayList<Items>();
		adapter=new ImageAdapter(getActivity(), items);
		lv2=(ListView)view.findViewById(R.id.listView2);
		lv2.setAdapter(adapter);
		new FetchAdvertisement().execute("");
		return view;
	}
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Extract data included in the Intent
			adapter.notifyDataSetChanged();
		}
	};

	private BroadcastReceiver myRecei = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			//intent.putExtra("Selction", inputText);
			//intent.putExtra("id", id);
			//id=intent.getStringExtra("id");
			//(intent.getStringExtra("Selction"));
			System.out.println("d"+intent.getStringExtra("Selction").toUpperCase().trim()+"d");
			search.setText("");
			categoryid=B2BUtils.categoryId.get(
			B2BUtils.categoryNames.indexOf(intent.getStringExtra("Selction").toUpperCase().trim()));
			new FilterByCategory().execute("");
		}
	};
	@Override
	public void onResume() {
	    super.onResume();
	    
	        IntentFilter filter = new IntentFilter(B2BUtils.IMAGE_INTENT );
	        getActivity().getApplicationContext().registerReceiver(mMessageReceiver, filter);
	        IntentFilter filter2 = new IntentFilter(B2BUtils.CATEGORY_INTENT );
	        getActivity().getApplicationContext().registerReceiver(myRecei, filter2);
	    
	}
	class ImageAdapter extends ArrayAdapter<Items>
	{
		 private Filter filter;
	public ImageAdapter(Context context, ArrayList<Items> items) {
		super(context, items.size(), items);
	}
	@Override
	public void add(Items object) {
		// TODO Auto-generated method stub
		super.add(object);
	}
	@Override
	public Filter getFilter() {
	    if (filter == null)
            filter = new NameFilter();

        return filter;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int r=position;
		if(convertView==null)
		{
			LayoutInflater inflator=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflator.inflate(R.layout.subscribe_item, parent, false);
		}
		else
		{
			
		}
		ImageView view1=(ImageView)convertView.findViewById(R.id.imageView1);
		view1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final Dialog dialog=new Dialog(getActivity());
				dialog.setTitle("Preview");
				dialog.show();
				ImageView view=new ImageView(getActivity());
				
				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
					dialog.dismiss();
						
					}
				});
				view.setImageBitmap(B2BUtils.shop.getImgs().get(items.get(r).getImagepath()));
				view.setMinimumHeight(300);
				view.setMinimumWidth(300);
				dialog.setCancelable(false);
				dialog.setContentView(view);
				
			}
		});
		if(B2BUtils.shop.getImgs().containsKey(items.get(position).getImagepath()))
		view1.setImageBitmap(B2BUtils.shop.getImgs().get(items.get(position).getImagepath()));
		
		TextView tv1=(TextView)convertView.findViewById(R.id.textView1);
		tv1.setText(items.get(position).getItemname());
		TextView tv2=(TextView)convertView.findViewById(R.id.textView2);
		tv2.setText(items.get(position).getLongdescription());
		TextView tv3=(TextView)convertView.findViewById(R.id.textView3);
		tv3.setText("SP : "+items.get(position).getSp());
		TextView tv4=(TextView)convertView.findViewById(R.id.textView4);
		tv4.setText("Min Order: "+items.get(position).getSp());
		Button button=(Button)convertView.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final Dialog dialog=new Dialog(getActivity());
				dialog.setTitle("Set your selling price");
				dialog.show();
				LayoutInflater inflator=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view=inflator.inflate(R.layout.setprice, null, false);
				view.setMinimumHeight(300);
				view.setMinimumWidth(300);
				dialog.setContentView(view);
				final EditText sp=(EditText)view.findViewById(R.id.editText1);
				final EditText discount=(EditText)view.findViewById(R.id.editText2);
				Button submit=(Button)view.findViewById(R.id.button1);
				submit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						List<NameValuePair> params=new LinkedList<NameValuePair>();
						params.add(new BasicNameValuePair("opt", "additem"));
						params.add(new BasicNameValuePair("itemid", items.get(r).getIditem()));
						params.add(new BasicNameValuePair("shopid", B2BUtils.shop.getIdshop()));
						params.add(new BasicNameValuePair("sellertype", B2BUtils.user.getUsertype()));
						params.add(new BasicNameValuePair("unitcost", "-1"));
						params.add(new BasicNameValuePair("sp", sp.getText().toString()));
						params.add(new BasicNameValuePair("qtyonhand", "0"));
						params.add(new BasicNameValuePair("qtyonorder", "0"));
						params.add(new BasicNameValuePair("discount", discount.getText().toString()));
						params.add(new BasicNameValuePair("item_supplier", items.get(r).getItem_shopid()));
						params.add(new BasicNameValuePair("min_order", "0"));
						new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/shop.jsp");
						dialog.dismiss();
					}
				});
				Button cancel=(Button)view.findViewById(R.id.button2);
				cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
						
					}
				});
			}
		});
		return convertView;
	}
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}
		
	}
	 private class NameFilter extends Filter
	    {
		 FilterResults results = new FilterResults();
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			 String prefix = constraint.toString().toLowerCase();
			 if (prefix == null || prefix.length() == 0)
             {
                
                 items.clear();
                 items.addAll(original);
             }
			 else
			 {
				 items.clear();
				 for(int i=0;i<original.size();i++)
				 {
					if(original.get(i).getItemname().toLowerCase().contains(prefix))
					{
						items.add(original.get(i));
					}
					
					 
				 }
			 }
			 results.values=items;
			 results.count=items.size();
			return results;
		}

		@Override
		protected void publishResults(CharSequence arg0, FilterResults arg1) {
			adapter.notifyDataSetChanged();
			
		}
		 
	    }
	 
	class FilterByCategory extends AsyncTask<String, String, String>
	{

		ProgressDialog progress;
		@Override
		protected void onPreExecute() {
			items.clear();
			original.clear();
			progress=new ProgressDialog(getActivity());
			progress.setTitle("Fetching Shop Info Please wait...");
			progress.show();
			super.onPreExecute();
		}
		//ArrayList<Bitmap> bmp=new ArrayList<Bitmap>();
		ArrayList<Items> available=new ArrayList<Items>();
		
		public FilterByCategory() {
			super();
			cacheDir = new File(tmpLocation);
		    if(!cacheDir.exists()){
		        cacheDir.mkdirs();
		    }
		}

		@Override
		protected void onPostExecute(String result) {
			if (progress.isShowing()) {
				progress.dismiss();
			}
			
			items.addAll(available);
			original.addAll(available);
			adapter.notifyDataSetChanged();
			//B2BUtils.shop.setAvailable(items);
			super.onPostExecute(result);
			
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			JSONArray ard2 = B2BUtils
			.getJSOnArr("http://10.0.2.2:8080/B2B/category_item.jsp?shopid="
					+ B2BUtils.getUser().getMasterid()+"&category="+categoryid);
			try {
				
				
				
				for (int i = 0; i < ard2.length(); i++) {
					JSONObject child = ard2.getJSONObject(i);
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
					Intent ic = new Intent(getActivity(),
							FetchImage.class);
					ic.putExtra("url", "http://10.0.2.2:8080/B2B/images/"
							+ child.getString("imagepath"));
					ic.putExtra("imagename", child.getString("itemname"));
					getActivity().startService(ic);
					available.add(current);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			}


			return null;
		}
		
	}
	 
	class FetchAdvertisement extends AsyncTask<String, String, String>
	{
		ProgressDialog progress;
		@Override
		protected void onPreExecute() {
			progress=new ProgressDialog(getActivity());
			progress.setTitle("Fetching Shop Info Please wait...");
			progress.show();
			super.onPreExecute();
		}
		ArrayList<Bitmap> bmp=new ArrayList<Bitmap>();
		ArrayList<Items> available=new ArrayList<Items>();
		public FetchAdvertisement() {
			super();
			cacheDir = new File(tmpLocation);
		    if(!cacheDir.exists()){
		        cacheDir.mkdirs();
		    }
		}

		@Override
		protected void onPostExecute(String result) {
			if (progress.isShowing()) {
				progress.dismiss();
			}for(int i=0;i<bmp.size();i++)
			{
				ImageView view=new ImageView(getActivity());
				
				view.setImageBitmap(bmp.get(i));
				view.setMinimumHeight(100);
				view.setMinimumWidth(100);
				
				v1.addView(view);
			}
			v1.startFlipping();
			items.addAll(available);
			original.addAll(available);
			adapter.notifyDataSetChanged();
			B2BUtils.shop.setAvailable(items);
			super.onPostExecute(result);
			
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONArray array = B2BUtils
			.getJSOnArr("http://10.0.2.2:8080/B2B/shop.jsp?opt=myshops&id="
					+ B2BUtils.getUser().getMasterid());
			try {
				JSONObject ob = array.getJSONObject(0);
				
				
				JSONArray ard2 = ob.getJSONArray("available");
				for (int i = 0; i < ard2.length(); i++) {
					JSONObject child = ard2.getJSONObject(i);
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
					Intent ic = new Intent(getActivity(),
							FetchImage.class);
					ic.putExtra("url", "http://10.0.2.2:8080/B2B/images/"
							+ child.getString("imagepath"));
					ic.putExtra("imagename", child.getString("itemname"));
					getActivity().startService(ic);
					available.add(current);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			}

			JSONArray ar=B2BUtils.getJSOnArr("http://10.0.2.2:8080/B2B/getAdvetisement.jsp?type=Supplier");
			
			
			for(int i=0;i<ar.length();i++)
			{
				try
				{
				JSONObject ob=ar.getJSONObject(i);
				
				String remoteUrl ="http://10.0.2.2:8080/B2B/images/advertisement/"+ob.getString("bannerPath");
			    String location;
			    String filename = 
			        remoteUrl.substring(
			        remoteUrl.lastIndexOf(File.separator)+1);
			    System.out.println(filename);
			    File tmp = new File(cacheDir.getPath() 
			            + File.separator +filename);
			   
			    {
			    try{
			        URL url = new URL(remoteUrl);
			        HttpURLConnection httpCon = 
			            (HttpURLConnection)url.openConnection();
			        if(httpCon.getResponseCode() != 200)
			            throw new Exception("Failed to connect");
			        InputStream is = httpCon.getInputStream();
			        FileOutputStream fos = new FileOutputStream(tmp);
			        writeStream(is, fos);
			        fos.flush(); fos.close();
			        is.close();
			        location = tmp.getAbsolutePath();
			        bmp.add(BitmapFactory.decodeFile(location));
			    }catch(Exception e){
			        System.out.println(e.toString());
			    }
			    }
				}
				catch(Exception e)
				{
					System.out.println(e.toString());
				}
				}
			return null;	
			}
	}
			
			
			

	private void writeStream(InputStream is, FileOutputStream fos) {
		byte buf[]=new byte[1024];
		  int len;
		  try {
			while((len=is.read(buf))>0)
			  fos.write(buf,0,len);
		
		  fos.close();
		  is.close();
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
