package com.jc.b2b;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.Items;
import com.jc.b2b.utils.Order;
import com.jc.b2b.utils.OrderDetails;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ViewOrder extends Fragment {
	ArrayList<String> warehousname, warehousedialog, warehouseid;
	ArrayList<Order> orderlist;
	ListView listview, list;
	LinearLayout zoomview;
	Spinner spinner;
	Button update;
	OrdersAdpater adapter;
	int current = 0;

	public ViewOrder() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		warehousname = new ArrayList<String>();
		warehousedialog = new ArrayList<String>();
		warehouseid = new ArrayList<String>();

		orderlist = new ArrayList<Order>();
		adapter = new OrdersAdpater();
		new FetchInfo().execute("");
		View view = inflater.inflate(R.layout.fragment_view_order, container,
				false);
		list = (ListView) view.findViewById(R.id.list);
		listview = (ListView) view.findViewById(R.id.listView1);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), orderlist.get(arg2).getTotal(),
						Toast.LENGTH_SHORT).show();
				zoomview.setVisibility(View.VISIBLE);
				list.setAdapter(new OrderDetailsAdapter(orderlist.get(arg2)
						.getDetails()));
				current = arg2;

			}
		});

		zoomview = (LinearLayout) view.findViewById(R.id.zoomview);
		zoomview.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				zoomview.setVisibility(View.GONE);
				return false;
			}
		});
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		spinner.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, new String[] {
						"Cancelled", "Received" }));
		update = (Button) view.findViewById(R.id.update);
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (spinner.getSelectedItem().equals("Cancelled")) {
					List<NameValuePair> params = new LinkedList<NameValuePair>();
					params.add(new BasicNameValuePair("opt", "cancel"));
					params.add(new BasicNameValuePair("id", orderlist.get(
							current).getId()));
					ArrayList<OrderDetails> ab = orderlist.get(current)
							.getDetails();
					for (int i = 0; i < ab.size(); i++) {
						params.add(new BasicNameValuePair("itemshopid", ab.get(
								i).getItemid()));
						params.add(new BasicNameValuePair("qtyonorder", ab.get(
								i).getQty()));
					}
					B2BUtils.submitSimpleForm(
							"http://10.0.2.2:8080/B2B/order.jsp", params);

					zoomview.setVisibility(View.GONE);
					new FetchInfo().execute("");

				}else
				{
					List<NameValuePair> params = new LinkedList<NameValuePair>();
					params.add(new BasicNameValuePair("opt", "Received"));
					params.add(new BasicNameValuePair("id", orderlist.get(
							current).getId()));
					ArrayList<OrderDetails> ab = orderlist.get(current)
							.getDetails();
					for (int i = 0; i < ab.size(); i++) {
						params.add(new BasicNameValuePair("itemshopid", ab.get(
								i).getItemid()));
						params.add(new BasicNameValuePair("qtyonorder", ab.get(
								i).getQty()));
					}
					B2BUtils.submitSimpleForm(
							"http://10.0.2.2:8080/B2B/order.jsp", params);

					zoomview.setVisibility(View.GONE);
					new FetchInfo().execute("");
				}
			}
		});
		return view;
	}

	class OrdersAdpater extends BaseAdapter {
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderlist.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return orderlist.get(arg0);
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
			arg1 = infalInflater.inflate(R.layout.order_item_list_view, null);
			Order order = orderlist.get(arg0);
			TextView date = (TextView) arg1.findViewById(R.id.date);
			date.setText(order.getDate());
			TextView placed_by = (TextView) arg1.findViewById(R.id.placed_by);
			placed_by.setText(B2BUtils.shops.get(order.getPlacedby()));
			TextView placed_to = (TextView) arg1.findViewById(R.id.placed_to);
			placed_to.setText(B2BUtils.shops.get(order.getPlacedto()));
			TextView Shipment = (TextView) arg1
					.findViewById(R.id.shipment_address);
			Shipment.setText(Html.fromHtml(warehousedialog.get(warehouseid
					.indexOf(order.getWarehouse()))));
			TextView totalnoofitems = (TextView) arg1
					.findViewById(R.id.totalitems);
			totalnoofitems.setText(order.getTotal());
			return arg1;
		}

	}

	public class FetchInfo extends AsyncTask<String, String, String> {
		ProgressDialog progress;

		public FetchInfo() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPreExecute() {
			progress = new ProgressDialog(getActivity());
			progress.setTitle("Fetching Shop Info Please wait...");
			progress.show();
			orderlist.clear();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (progress.isShowing()) {
				progress.dismiss();
			}
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			JSONObject ob = B2BUtils
					.getJSOn("http://10.0.2.2:8080/B2B/order.jsp?opt=select&shopid="
							+ B2BUtils.shop.getIdshop()
							+ "&wid="
							+ B2BUtils.getUser().getMasterid());
			System.out.println("http://10.0.2.2:8080/B2B/order.jsp?opt=select&shopid="
					+ B2BUtils.shop.getIdshop()
					+ "&wid="
					+ B2BUtils.getUser().getMasterid());
			try {

				ArrayList<Items> children = new ArrayList<Items>();
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
					Intent ic = new Intent(getActivity(), FetchImage.class);
					ic.putExtra("url", "http://10.0.2.2:8080/B2B/images/"
							+ child.getString("imagepath"));
					ic.putExtra("imagename", child.getString("itemname"));
					getActivity().startService(ic);
					Items current = (new Items(child.getString("categoryid"),
							child.getString("status"),
							child.getString("imagepath"),
							child.getString("itemname"),
							child.getString("unit_of_measurement"),
							child.getString("qtyperunit"),
							child.getString("owner"),
							child.getString("longdesciption"),
							child.getString("iditem"), child.getString("sp"),
							child.getString("unitcost"),
							child.getString("min_order"),
							child.getString("item_shopid"),
							child.getString("discount"),
							child.getString("qtyonorder"), qty, discounts, ids,
							child.getString("qtyperunit")));

					children.add(current);
					B2BUtils.shop.setChildren(children);
				}
				JSONArray arr = ob.getJSONArray("warehouses");
				for (int j = 0; j < arr.length(); j++) {
					try {
						JSONObject ob1 = arr.getJSONObject(j);
						if (ob1.getString("idwarehouse").equals("-1")) {

						} else {
							warehouseid.add(ob1.getString("idwarehouse"));
							warehousedialog.add(ob1.getString("address")
									+ "<br>" + ob1.getString("city") + " <br>"
									+ ob1.getString("state") + " <br>"
									+ ob1.getString("country") + "<br>"
									+ "Zip : " + ob1.getString("zip")

							);
						}
					} catch (Exception e) {
						Log.d(B2BUtils.LogString, e.toString());
					}
					JSONArray order = ob.getJSONArray("orders");

					for (int k = 0; k < order.length(); k++) {
						JSONObject ord = order.getJSONObject(k);
						if (ord.getString("state").equalsIgnoreCase("pending")) {
							JSONArray details = ord.getJSONArray("details");
							ArrayList<OrderDetails> od = new ArrayList<OrderDetails>();
							for (int r = 0; r < details.length(); r++) {
								JSONObject id = details.getJSONObject(r);
								od.add(new OrderDetails(id.getString("qty"), id
										.getString("cost"), id
										.getString("discount"), id
										.getString("afterdiscount"), id
										.getString("itemid"), id
										.getString("shipmentcost")));

							}
							orderlist.add(new Order(ord.getString("total"), ord
									.getString("placedto"), ord
									.getString("idorder"), ord
									.getString("state"), ord
									.getString("placedby"), ord
									.getString("warehouse"), ord
									.getString("date"), od));
						}
					}
				}

			} catch (Exception e) {
				Log.d(B2BUtils.LogString, e.toString());
			}

			return null;
		}

	}

	class OrderDetailsAdapter extends BaseAdapter {

		ArrayList<OrderDetails> orderl;

		public OrderDetailsAdapter(ArrayList<OrderDetails> orderlist) {
			super();
			this.orderl = orderlist;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderl.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return orderl.get(arg0);
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
			arg1 = infalInflater.inflate(R.layout.order_detail_list_view, null);
			OrderDetails obj = orderl.get(arg0);
			TextView qty = (TextView) arg1.findViewById(R.id.qty);
			qty.setText(obj.getQty());
			TextView cost = (TextView) arg1.findViewById(R.id.cost);
			cost.setText(obj.getCost());
			TextView discount = (TextView) arg1.findViewById(R.id.discount);
			discount.setText(obj.getDiscount());
			TextView shipping = (TextView) arg1.findViewById(R.id.shipping);
			shipping.setText(obj.getShipmentcost());
			TextView sp = (TextView) arg1.findViewById(R.id.sp);
			sp.setText(obj.getAfterdiscount());
			return arg1;
		}
	}
}
