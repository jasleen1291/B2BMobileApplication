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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.InsertForm;
import com.jc.b2b.utils.Items;
import com.jc.b2b.utils.Uploader;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ViewItem extends Fragment {
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;
	ArrayAdapter<String> countryAdapter;
	protected static final int RESULT_LOAD_IMAGE = 0;
	ListView lv;
	private static ProgressDialog pd;
	EditText itemname,desc,sp,discount,minorder,qtyperunit,unit;
	ArrayList<String> countryList=new ArrayList<String>();
	TextView cp;
	ImageView imageview1,expandedImage;
	File file;
	String url="http://10.0.2.2:8080/B2B/updateItem.jsp";
	private String CACHE_FOLDER="cache";
	Button button1,button2,button3;
	public ViewItem() {
		// Required empty public constructor
	}
	Items items;
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
		items=(Items) bundle.getSerializable("ap");
		
		String tmpLocation = 
	    	Environment.getExternalStoragePublicDirectory(
	    	        Environment.DIRECTORY_PICTURES
	    	    ).getPath() + File.separator +CACHE_FOLDER ;
		file=new File(tmpLocation+ File.separator +items.getImagepath());
		View view = inflater.inflate(R.layout.fragment_view_item, container, false);
		itemname=(EditText)view.findViewById(R.id.name);
		itemname.setText(items.getItemname());
		desc=(EditText)view.findViewById(R.id.desc);
		desc.setText(items.getLongdescription());
		sp=(EditText)view.findViewById(R.id.sp);
		sp.setText(items.getSp());
		discount=(EditText)view.findViewById(R.id.discount);
		discount.setText(items.getDiscount());
		minorder=(EditText)view.findViewById(R.id.minorder);
		minorder.setText(items.getMin_order());
		qtyperunit=(EditText)view.findViewById(R.id.qtyperunit);
		qtyperunit.setText(items.getQtyperunit());
		button1=(Button)view.findViewById(R.id.button1);
		button2=(Button)view.findViewById(R.id.add);
		lv=(ListView)view.findViewById(R.id.list);
		countryAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice,countryList );
		lv.setAdapter(countryAdapter);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 
				lv.getChildAt(arg2).setSelected(!lv.getChildAt(arg2).isSelected());
			}
		});
		new FetchCountries(getActivity()).execute("");
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<NameValuePair> params = new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("opt", "deleteitem"));
				params.add(new BasicNameValuePair("id", items.getIditem()));

			new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/shop.jsp");
			ViewItemList ab=new ViewItemList();
			fragmentTransaction = fm.beginTransaction();
			fragmentTransaction.replace(R.id.myfragment, ab);
			fragmentTransaction.commit();
				
			}
		});
		unit=(EditText)view.findViewById(R.id.unit_of_measurement);
		unit.setText(items.getUnitofmeasurement());
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 pd = ProgressDialog.show(getActivity(), "Working..", "Uploading Item", true,
                         false);
				try
				{
				MultipartEntity entity=new MultipartEntity();
				entity.addPart("itemname", new StringBody(itemname.getText().toString()));
				entity.addPart("longdescription", new StringBody(desc.getText().toString()));
				entity.addPart("shopid", new StringBody(B2BUtils.shop.getIdshop()));
				entity.addPart("qtyperunit", new StringBody(qtyperunit.getText().toString()));
				entity.addPart("unit_of_measure", new StringBody(unit.getText().toString()));
				entity.addPart("id",new StringBody(items.getIditem()) );
				entity.addPart("itemshopid", new StringBody(items.getItem_shopid()));
				entity.addPart("min_order", new StringBody(minorder.getText().toString()));
				entity.addPart("sp", new StringBody(sp.getText().toString()));
				entity.addPart("discount", new StringBody(discount.getText().toString()));
				Thread t=new Uploader(file, url, handler, entity);
				t.start();
				}
				catch(Exception e)
				{
					Log.d(B2BUtils.LogString, e.toString());
				}
			}
		});
		button3=(Button)view.findViewById(R.id.button);
		cp=(TextView)view.findViewById(R.id.cp);
		cp.setText(items.getUnitcost());
		imageview1=(ImageView)view.findViewById(R.id.imageView1);
		imageview1.setImageBitmap(BitmapFactory.decodeFile(tmpLocation+ File.separator +items.getImagepath()));
		expandedImage=(ImageView)view.findViewById(R.id.expanded_image);
		expandedImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			expandedImage.setVisibility(View.GONE);
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent i = new Intent(
	                        Intent.ACTION_PICK,
	                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                 
	                startActivityForResult(i, RESULT_LOAD_IMAGE);
				
			}
		});
		imageview1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				expandedImage.setVisibility(View.VISIBLE);
				Bitmap bitmap = ((BitmapDrawable)imageview1.getDrawable()).getBitmap();
				expandedImage.setImageBitmap(bitmap);
				
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
             
            
            imageview1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
         file=new File(picturePath);
        }
			super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onDetach() {
		items=null;
		super.onDetach();
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
