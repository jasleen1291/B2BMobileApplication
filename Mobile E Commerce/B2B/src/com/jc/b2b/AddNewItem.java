package com.jc.b2b;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.Uploader;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class AddNewItem extends Fragment {
	 private static ProgressDialog pd;
	 ArrayList<String> countryList;
	 ArrayAdapter<String> countryAdapter,availAdapter,batch;
	 ArrayList<String> mycountries,qtys,discounts;
	 ArrayList<String> shipment;
	 File file;
	 String url="http://10.0.2.2:8080/B2B/item.jsp";
	 MultipartEntity entity;
	 EditText itemname,longdesc,qty,unit,sp,cp,minorder,discount;
	 ImageView image;
	 Button btn,btn4,btn3;
	 static Activity activity=null;
	 private static int RESULT_LOAD_IMAGE = 1;
	 LayoutInflater inflater2;
	public AddNewItem() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		activity=getActivity();
		countryList=new ArrayList<String>();
		mycountries=new ArrayList<String>();
		shipment=new ArrayList<String>();
		qtys=new ArrayList<String>();
		discounts=new ArrayList<String>();
		availAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2,
	            android.R.id.text1,
	            mycountries) {
	 
	            @Override
	            public View getView(int position, View convertView, ViewGroup parent) {
	 
	               
	                View view = super.getView(position, convertView, parent);
	 
	                
	                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
	                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
	                text1.setText(mycountries.get(position));
	                text2.setText(shipment.get(position));
	                return view;
	            }
	        };
	        batch=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2,
		            android.R.id.text1,
		            qtys) {
		 
		            @Override
		            public View getView(int position, View convertView, ViewGroup parent) {
		 
		               
		                View view = super.getView(position, convertView, parent);
		 
		                
		                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
		                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
		                text1.setText(qtys.get(position));
		                text2.setText(discounts.get(position));
		                return view;
		            }
		        };
		countryAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,countryList);
		new FetchCountries(getActivity()).execute("");
		inflater2=inflater;
		View view= inflater.inflate(R.layout.fragment_add_new_item, container,
				false);
		sp=(EditText)view.findViewById(R.id.sp);
		cp=(EditText)view.findViewById(R.id.cp);
		minorder=(EditText)view.findViewById(R.id.minorder);
		discount=(EditText)view.findViewById(R.id.discount);
		itemname=(EditText)view.findViewById(R.id.itemname);
		longdesc=(EditText)view.findViewById(R.id.longdescription);
		qty=(EditText)view.findViewById(R.id.qtyperunit);
		unit=(EditText)view.findViewById(R.id.unit_of_measurement);
		image=(ImageView)view.findViewById(R.id.imageView1);
		entity=new MultipartEntity();
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent i = new Intent(
	                        Intent.ACTION_PICK,
	                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                 
	                startActivityForResult(i, RESULT_LOAD_IMAGE);
				
			}
		});
		Button btn2=(Button)view.findViewById(R.id.submit);
		btn=(Button)view.findViewById(R.id.button);
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				 if(mycountries.size()>0)
				 {
				 try
					{ 
					 
					 pd = ProgressDialog.show(getActivity(), "Working..", "Uploading Item", true,
	                         false);
					 List<NameValuePair> params=new LinkedList<NameValuePair>();
					 for(int i=0;i<mycountries.size();i++)
					 {
						 params.add(new BasicNameValuePair("country",B2BUtils.country.get(mycountries.get(i))));
						 // params.add("country", new StringBody(B2BUtils.country.get(mycountries.get(i))));
			             params.add(new BasicNameValuePair("shipment", shipment.get(i))) ;
						// entity.addPart("shipment", new StringBody(shipment.get(i)));  
					 }
					 for(int i=0;i<qtys.size();i++)
					 {
						 //String qty[]=request.getParameterValues("qty");
			               // String dis[]=request.getParameterValues("dis");
						 params.add(new BasicNameValuePair("qty",qtys.get(i)));
						// entity.addPart("qty", new StringBody(qtys.get(i)));
						 params.add(new BasicNameValuePair("dis", discounts.get(i)));
						 //entity.addPart("dis", new StringBody(discounts.get(i)));
					 }
					entity.addPart("itemname",new StringBody( itemname.getText().toString()));
					entity.addPart("longdescription", new StringBody( longdesc.getText().toString()));
					entity.addPart("categoryid", new StringBody(B2BUtils.categoryId.get(B2BUtils.categoryNames.indexOf(btn.getText().toString().trim().toUpperCase()))));
					entity.addPart("qtyperunit", new StringBody(qty.getText().toString()));
					entity.addPart("unit_of_measure", new StringBody(unit.getText().toString()));
					entity.addPart("ownerid", new StringBody(B2BUtils.getUser().getMasterid()));
					entity.addPart("sp", new StringBody(sp.getText().toString()));
					entity.addPart("cp", new StringBody(cp.getText().toString()));
					entity.addPart("minorder", new StringBody(minorder.getText().toString()));
					entity.addPart("discount", new StringBody(discount.getText().toString()));
					entity.addPart("shopid", new StringBody(B2BUtils.shop.getIdshop()));
					entity.addPart("sellertype", new StringBody(B2BUtils.user.getUsertype()));
					entity.addPart("supplier", new StringBody("-1"));
					Thread t=new Uploader(file, url, handler, entity,getActivity(),params);
					t.start();
					}
					catch(Exception e)
					{
						Log.d("Error", e.toString());
						
					}
				
				
			}
				 else
				 {
					 Toast.makeText(getActivity(), "No country added", Toast.LENGTH_SHORT).show();
				 }
				 }
		});
btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				showEditDialog();
			}
		});
			btn4=(Button)view.findViewById(R.id.button1);
			btn4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					final Dialog dialog=new Dialog(getActivity());
					View vie=inflater2.inflate(R.layout.simple2, null);
					vie.setMinimumWidth(600);
					vie.setMinimumHeight(600);
					dialog.show();
					dialog.setTitle("Add Country");
					dialog.setContentView(vie);
					final AutoCompleteTextView av=(AutoCompleteTextView)vie.findViewById(R.id.editText1);
					av.setAdapter(countryAdapter);
					//av.setThreshold(1);
					final EditText tv=(EditText)vie.findViewById(R.id.editText2);
					ListView lvs=(ListView)vie.findViewById(R.id.listView1);
					lvs.setAdapter(availAdapter);
					lvs.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							mycountries.remove(arg2);
							shipment.remove(arg2);
							
							availAdapter.notifyDataSetChanged();
							
						}
					});
					Button add=(Button)vie.findViewById(R.id.button1);
					add.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if(!mycountries.contains(av.getText().toString()))
							{mycountries.add(av.getText().toString());
							shipment.add(tv.getText().toString());
							
							availAdapter.notifyDataSetChanged();
							}
							else
							{
								Toast.makeText(getActivity(), "Country already added", Toast.LENGTH_SHORT).show();
							}
						}
					});
					Button update=(Button)vie.findViewById(R.id.button2);
					update.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
				}
			});
		btn3=(Button)view.findViewById(R.id.button2);
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final Dialog dialog=new Dialog(getActivity());
				View vie=inflater2.inflate(R.layout.simple, null);
				vie.setMinimumWidth(600);
				vie.setMinimumHeight(600);
				dialog.show();
				dialog.setTitle("Add Batch Order");
				dialog.setContentView(vie);
				final EditText av=(EditText)vie.findViewById(R.id.editText1);
				//av.setAdapter(countryAdapter);
				//av.setThreshold(1);
				final EditText tv=(EditText)vie.findViewById(R.id.editText2);
				ListView lvs=(ListView)vie.findViewById(R.id.listView1);
				lvs.setAdapter(batch);
				lvs.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						qtys.remove(arg2);
						discounts.remove(arg2);
						
						batch.notifyDataSetChanged();
						
					}
				});
				Button add=(Button)vie.findViewById(R.id.button1);
				add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(!qtys.contains(av.getText().toString()))
						{qtys.add(av.getText().toString());
						discounts.add(tv.getText().toString());
						
						batch.notifyDataSetChanged();
						}
						else
						{
							Toast.makeText(getActivity(), "Qty already added", Toast.LENGTH_SHORT).show();
						}
					}
				});
				Button update=(Button)vie.findViewById(R.id.button2);
				update.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
			}
		});
			return view;
	}
	private static Handler handler = new Handler() {
		
        @Override
        public void handleMessage(Message msg) {
                pd.dismiss();
                //tv.setText(pi_string);
             

        }	
};
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
             
            
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
         file=new File(picturePath);
        }
			super.onActivityResult(requestCode, resultCode, data);
	}
	protected void showEditDialog() {
		SelectCategory editNameDialog = new SelectCategory();
		editNameDialog.show(getFragmentManager(), "dialog");
		
	}
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			//intent.putExtra("Selction", inputText);
			//intent.putExtra("id", id);
			//id=intent.getStringExtra("id");
			btn.setText(intent.getStringExtra("Selction"));
			
		}
	};
	@Override
	public void onResume() {
	    super.onResume();
	    
	        IntentFilter filter = new IntentFilter(B2BUtils.CATEGORY_INTENT );
	        getActivity().getApplicationContext().registerReceiver(myReceiver, filter);
	    
	}
	class FetchCountries extends AsyncTask<String, String, String>
	{

		ProgressDialog progress;
		Context context;
		public FetchCountries(Activity activity) {
			super();
		}

		@Override
		protected void onPostExecute(String result) {
			if (progress.isShowing()) {
				progress.dismiss();
			}
			countryAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			progress=new ProgressDialog(getActivity());
			progress.setTitle("Fetching Countries Please wait...");
			progress.show();
			
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			
			try
			{
			 URL oracle = new URL("http://10.0.2.2:8080/B2B/getCountryList.jsp");
			 
		        BufferedReader in = new BufferedReader(
		        new InputStreamReader(oracle.openStream()));
		       
		        String inputLine,result="";
		        while ((inputLine = in.readLine()) != null)
		            result=result+inputLine;
		        in.close();
		        JSONObject countryJSON=new JSONObject(result);
		        Iterator<?> it=countryJSON.keys();
		       
				while(it.hasNext())
		        {
		        	String key=it.next().toString();
		        	String value=countryJSON.get(key).toString();
		        	B2BUtils.country.put(value,key);
		        	countryList.add(value);
		        	}
		       Collections.sort(countryList);
			}
			catch(Exception e)
			{
				Log.d(B2BUtils.LogString,e.toString());
			}
			return null;
		}

	}
	
}
