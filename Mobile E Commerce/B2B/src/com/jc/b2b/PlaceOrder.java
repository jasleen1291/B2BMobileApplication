package com.jc.b2b;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.ShoppingcartItem;

public class PlaceOrder extends Fragment {
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;
	static FragmentActivity activity;
	private static ProgressDialog pd;
	public PlaceOrder() {
		// Required empty public constructor
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		activity=getActivity();
		fm = getActivity().getSupportFragmentManager();
		fragmentTransaction = fm.beginTransaction();
		super.onCreate(savedInstanceState);
	}
	float qtyf = 0.0f, totalf = 0.0f;
	Spinner method, warehouse;

	TextView contact, totalitems, total, cptv;
	EditText contactet, qty;
	AutoCompleteTextView itemname;
	Button add, placeorder;
	ListView twoline;
	ArrayList<String> names, ids, cp,qtyonhand, pathname,itemshopid, warehousname, warehousedialog,
			warehouseid;
	Orderadapter od;
	ArrayList<ShoppingcartItem> items = new ArrayList<ShoppingcartItem>();
	ArrayAdapter<String> warehouseadapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		items.clear();
		View view = inflater.inflate(R.layout.place_order, container, false);
		od = new Orderadapter();
		method = (Spinner) view.findViewById(R.id.spinner1);
		qtyonhand=new ArrayList<String>();
		itemshopid=new ArrayList<String>();
		method.setOnItemSelectedListener(new  OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(method.getSelectedItem().toString().equals("Email"))
				{
					contactet.setHint("Email");
					contactet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
					contact.setText("Email");
				}
				else
				{
					contactet.setHint("Phone");
					contactet.setInputType(InputType.TYPE_CLASS_PHONE);
					contact.setText("Phone");
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		warehousname = new ArrayList<String>();
		warehousedialog = new ArrayList<String>();
		warehouse = (Spinner) view.findViewById(R.id.spinner2);
		warehouseid = new ArrayList<String>();
		warehouseadapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, warehousname);
		warehouseadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		warehouse.setAdapter(warehouseadapter);
		contact = (TextView) view.findViewById(R.id.contact);
		total = (TextView) view.findViewById(R.id.total);
		total.setText("0");
		totalitems = (TextView) view.findViewById(R.id.totalitems);
		totalitems.setText("0");
		contactet = (EditText) view.findViewById(R.id.editText1);
		qty = (EditText) view.findViewById(R.id.qty);
		cptv = (TextView) view.findViewById(R.id.cp);
		itemname = (AutoCompleteTextView) view.findViewById(R.id.itemname);
		twoline = (ListView) view.findViewById(R.id.twoline);
		twoline.setAdapter(od);
		
		pathname = new ArrayList<String>();
		new FetchWarehouse().execute("");
		add = (Button) view.findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				itemname.performValidation();
				if (qty.getText().toString().length() < 0) {
					Toast.makeText(getActivity(), "Qty cannot be empty",
							Toast.LENGTH_SHORT).show();
				} else if (itemname.getText().toString().length() < 0) {
					Toast.makeText(getActivity(), "No item selected",
							Toast.LENGTH_SHORT).show();
				} else {
					if (AlreadyIn(itemname.getText().toString()) == -1) {
						try {
							int index = names.indexOf(itemname.getText()
									.toString());
							float qtya = Float.parseFloat(qty.getText()
									.toString());
							float cpa = Float.parseFloat(cp.get(index));
							float totals = qtya * cpa;
							items.add(new ShoppingcartItem(itemname.getText()
									.toString(), pathname.get(index), qtya,
									cpa, totals));
							qtyf = qtyf + qtya;
							totalf = totalf + totals;
							totalitems.setText(qtyf + "");
							total.setText(totals + "");
							od.notifyDataSetChanged();
						} catch (Exception e) {
							Toast.makeText(getActivity(), "Qty invalid",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						int ind = AlreadyIn(itemname.getText().toString());
						int index = names
								.indexOf(itemname.getText().toString());
						float qtya = Float.parseFloat(qty.getText().toString());
						float cpa = Float.parseFloat(cp.get(index));
						float totals = qtya * cpa;
						ShoppingcartItem item = items.get(ind);
						item.setQty(item.getQty() + qtya);
						item.setTotal(item.getTotal() + totals);
						qtyf = qtyf + qtya;
						totalf = totalf + totals;
						totalitems.setText(qtyf + "");
						total.setText(totals + "");
						od.notifyDataSetChanged();
						Toast.makeText(getActivity(),
								"Qty already in list. Qty added in existing",
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});
		placeorder = (Button) view.findViewById(R.id.button2);
		placeorder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(items.size()==0)
				{
					Toast.makeText(getActivity(), "No item selected", Toast.LENGTH_SHORT).show();
				}
				else
				{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							getActivity());
			 
						// set title
						alertDialogBuilder.setTitle("Are you sure");
			 
						// set dialog message
						alertDialogBuilder
							.setMessage("Changes once made cannot be undone. Are you sure you want to proceeed? ")
							.setCancelable(false)
							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									int position = method.getSelectedItemPosition();
								    Intent intent = null;
								    switch (position) {
								    case 0:
								    	if(contactet.getText().length()>0)
								    	{
								      sendEmail();
								      placeOrder();
								    	}
								    	else
								    	{
								    		contactet.setError("Phone Number Empty");
								    	}
								      break;
								    
								    case 1:
								    	if(contactet.getText().length()>0)
								    	{
								      intent = new Intent(Intent.ACTION_DIAL,
								          Uri.parse("tel:"+contactet.getText().toString()));
								      startActivity(intent);
								      placeOrder();
								    	}
								    	else
								    	{
								    		contactet.setError("Phone Number Empty");
								    	}
								      break;
								  

								    }
								    if (intent != null) {
								      startActivity(intent);
								    }
								}
							  }).setNegativeButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										
										dialog.cancel();
									}
								});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
					/**/
				}
				
			}
		});
		names = new ArrayList<String>();
		ids = new ArrayList<String>();
		cp = new ArrayList<String>();

		populatelists();
		itemname.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, names));
		itemname.setValidator(new Validator());
		itemname.setOnFocusChangeListener(new FocusListener());

		return view;
	}

	void populatelists() {
		for (int i = 0; i < B2BUtils.shop.getChildren().size(); i++) {
			names.add(B2BUtils.shop.getChildren().get(i).getItemname());
			ids.add(B2BUtils.shop.getChildren().get(i).getIditem());
			cp.add(B2BUtils.shop.getChildren().get(i).getUnitcost());
			pathname.add(B2BUtils.shop.getChildren().get(i).getImagepath());
			qtyonhand.add(B2BUtils.shop.getChildren().get(i).getQtyonorder());
			itemshopid.add(B2BUtils.shop.getChildren().get(i).getItem_shopid());
		}
	}

	class Validator implements AutoCompleteTextView.Validator {

		@Override
		public boolean isValid(CharSequence text) {
			if (names.contains(text.toString())) {
				cptv.setText(cp.get(names.indexOf(text.toString())));
				return true;
			}

			return false;
		}

		@Override
		public CharSequence fixText(CharSequence invalidText) {
			Toast.makeText(getActivity(), "Invalid Item name",
					Toast.LENGTH_LONG).show();

			return "";
		}
	}

	class FocusListener implements View.OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {

			if (!hasFocus) {
				((AutoCompleteTextView) v).performValidation();
			}
		}
	}

	class Orderadapter extends BaseAdapter {

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
			LayoutInflater infalInflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg1 = infalInflater.inflate(R.layout.order_item, null);
			arg1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TextView itemname = (TextView) v.findViewById(R.id.itemname);
					int index = AlreadyIn(itemname.getText().toString());
					ShoppingcartItem cart=items.get(index);
					float qtya = (cart.getQty());
					float cpa = cart.getCp();
					float totals = qtya * cpa;
					items.remove(index);
					qtyf = qtyf - qtya;
					totalf = totalf - totals;
					totalitems.setText(qtyf + "");
					total.setText(totals + "");
					od.notifyDataSetChanged();
					Toast.makeText(getActivity(), itemname.getText() , Toast.LENGTH_SHORT).show();
					
				}
			});
			ImageView iv = (ImageView) arg1.findViewById(R.id.imageView1);
			iv.setImageBitmap(B2BUtils.shop.getImgs().get(
					items.get(arg0).getPathname()));
			TextView qty = (TextView) arg1.findViewById(R.id.totalitems);
			qty.setText(items.get(arg0).getQty() + "");
			TextView itemname = (TextView) arg1.findViewById(R.id.itemname);
			itemname.setText(items.get(arg0).getName());
			TextView cp = (TextView) arg1.findViewById(R.id.cp);
			cp.setText(items.get(arg0).getCp() + "");
			TextView total = (TextView) arg1.findViewById(R.id.total);
			total.setText(items.get(arg0).getTotal() + "");
			return arg1;
		}

	}

	int AlreadyIn(String name) {
		if (items.size() == 0)
			return -1;
		else {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getName().equals(name)) {
					return i;
				}
			}
		}
		return -1;
	}

	class FetchWarehouse extends AsyncTask<String, String, String> {

		@Override
		protected void onPostExecute(String result) {
			if(warehouseid.size()<1)
			{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
		 
					// set title
					alertDialogBuilder.setTitle("No warehouse found");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("You will now be redirected to add warehouse ")
						.setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								AddWarehouse ab=new AddWarehouse();
								fragmentTransaction = fm.beginTransaction();
								fragmentTransaction.replace(R.id.myfragment, ab);
								fragmentTransaction.commit();
							}
						  });
						
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
			}
			else
			{
				warehouseadapter.notifyDataSetChanged();
			}
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			JSONArray arr = B2BUtils
					.getJSOnArr("http://10.0.2.2:8080/B2B/warehouse.jsp?opt=selectw&wid="
							+ B2BUtils.getUser().getMasterid());
			
			for (int i = 0; i < arr.length(); i++) {
				try {
					JSONObject ob = arr.getJSONObject(i);
					if (ob.getString("idwarehouse").equals("-1")) {

					} else {
						warehouseid.add(ob.getString("idwarehouse"));
						warehousname.add(ob.getString("warehousename"));
						warehousedialog.add(ob.getString("address") + "</br>"
								+ ob.getString("city") + "</br>"
								+ ob.getString("state")+"</br>"
								+ ob.getString("country")+"</br>"+
								"Zip : "+ob.getString("zip")
									
						);
					}
				} catch (Exception e) {
					Log.d(B2BUtils.LogString, e.toString());
				}
			}
			return null;
		}

	}
private static Handler handler = new Handler() {
		
        @Override
        public void handleMessage(Message msg) {
                pd.dismiss();
                ViewOrder ab=new ViewOrder();
                android.support.v4.app.FragmentTransaction fragmentTransaction;
            	android.support.v4.app.FragmentManager fm;
            	fm = activity.getSupportFragmentManager();
        		fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.myfragment, ab);
				fragmentTransaction.commit();
             

        }	
};
	 public void sendEmail()
	 {
		 String content="Dear Sir<br>"+
		 "Following is the list of items I wish to order from you<br>"+
		 "<table>"+
		 "<tr>"+
		 "<td>Item Id</td>"+
		 "<td>Qty</td>"+
		 "<td>Cost</td>"+
		 "<td>Discount</td>"+
		 "<td>Shipment Cost</td>"+
		 "<td>S.P</td>"+
		 "</tr>"
		 ;
		 for(int i=0;i<items.size();i++)
			{
				ShoppingcartItem item=items.get(i);
				int index =names.indexOf(item.getName());
				String itemshopids=itemshopid.get(index);
				content=content+"<div>"+
				 "<td>"+itemshopids+"</td>"+
				 "<td>"+item.getQty()+"</td>"+
				 "<td>"+item.getCp()+"</td>"+
				 "<td>0</td>"+
				 "<td>0</td>"+
				 "<td>"+item.getTotal()+"</div></tr>";
				
				
			}
		 content=content+"</table>";
		 content=content+"Please Ship it to following address:</br>"+warehousedialog.get(warehousname.indexOf(warehouse.getSelectedItem()));
		 Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[]{contactet.getText().toString()});		  
			email.putExtra(Intent.EXTRA_SUBJECT, "Order Details");
			email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(content));
			email.setType("text/html");
			startActivity(Intent.createChooser(email, "Choose an Email client :"));
	 }
	 public void placeOrder()
	 {
		 pd = ProgressDialog.show(getActivity(), "Working..", "Placing your order", true,
                 false);
		 Thread ab=new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<NameValuePair> param = new LinkedList<NameValuePair>();
				List<NameValuePair> params = new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("opt", "updateitemsorder"));
				for(int i=0;i<items.size();i++)
				{
					ShoppingcartItem item=items.get(i);
					int index =names.indexOf(item.getName());
					String itemshopids=itemshopid.get(index);
					params.add(new BasicNameValuePair("itemshopid", itemshopids));
					param.add(new BasicNameValuePair("itemid", itemshopids));
					param.add(new BasicNameValuePair("qty", item.getQty()+""));
					param.add(new BasicNameValuePair("cost", item.getCp()+""));
					param.add(new BasicNameValuePair("discount", "0"));
					param.add(new BasicNameValuePair("afterdiscount",item.getTotal()+""));
					param.add(new BasicNameValuePair("shipmentcost", "0"));
					float qtyonorder=Float.parseFloat(qtyonhand.get(index))+item.getQty();
					params.add(new BasicNameValuePair("qtyonorder", qtyonorder+""));
				}
				JSONObject ob=B2BUtils.submitSimpleForm("http://10.0.2.2:8080/B2B/order.jsp", params);
				try
				{
				if(ob.getString("Status").equals("Values inserted"))
				{
					/**/
					
					param.add(new BasicNameValuePair("opt", "insert"));
					param.add(new BasicNameValuePair("pby", B2BUtils.shop.getIdshop()));
					param.add(new BasicNameValuePair("pto","-1"));
					param.add(new BasicNameValuePair("total", total.getText().toString()));
					param.add(new BasicNameValuePair("status","pending"));
					param.add(new BasicNameValuePair("whouse", warehouseid.get(warehousname.indexOf(warehouse.getSelectedItem()))));
					JSONObject ob1=B2BUtils.submitSimpleForm("http://10.0.2.2:8080/B2B/order.jsp", param);
					if(ob1.getString("Status").equals("Values inserted"))
					{
						Message message=handler.obtainMessage();
			            message.obj="Values inserted";
			            handler.sendMessage(message);
					}
						
				}
				}
				catch(Exception e)
				{
					System.out.println(e.toString());
				}
			}
		});
		 ab.start();
	 }
}

