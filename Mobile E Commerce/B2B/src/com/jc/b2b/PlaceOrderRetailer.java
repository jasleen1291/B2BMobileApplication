package com.jc.b2b;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.test.PerformanceTestCase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.ShoppingcartItem;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalPayment;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class PlaceOrderRetailer extends Fragment {
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	android.support.v4.app.FragmentManager fm;
	static FragmentActivity activity;
	private static ProgressDialog pd;
	float qtyf = 0.0f, totalf = 0.0f;
	protected static final int INITIALIZE_SUCCESS = 0;
	protected static final int INITIALIZE_FAILURE = 1;
	Spinner warehouse;
	String id = "", email = "";
	TextView totalitems, total, cptv, mintv;
	EditText qty;
	AutoCompleteTextView itemname;
	Button add, placeorder, batchbtn;
	ListView twoline;
	ArrayList<String> names, ids, cp, qtyonhand, pathname, itemshopid,
			warehousname, warehousedialog, warehouseid, minorder, dicount;
	ArrayList<ArrayList<Double>> orderbatch, disbatch;
	Orderadapter od;
	CheckoutButton launchSimplePayment;
	RelativeLayout rl;
	ArrayList<ShoppingcartItem> items = new ArrayList<ShoppingcartItem>();
	ArrayAdapter<String> warehouseadapter, autoadapter;
	private static final int server = PayPal.ENV_NONE;
	// The ID of your application that you received from PayPal
	private static final String appID = "APP-80W284485P519543T";
	Handler hRefresh = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case INITIALIZE_SUCCESS: {
				setupButtons();
			}
				break;
			case INITIALIZE_FAILURE:
				System.out.println("make pa2");
				break;
			}
		}

	};

	private void setupButtons() {
		// TODO Auto-generated method stub
		System.out.println("make pa");
		PayPal pp = PayPal.getInstance();
		launchSimplePayment = pp.getCheckoutButton(getActivity(),
				PayPal.BUTTON_194x37, CheckoutButton.TEXT_PAY);
		// You'll need to have an OnClickListener for the CheckoutButton. For
		// this application, MPL_Example implements OnClickListener and we
		// have the onClick() method below.
		launchSimplePayment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				PayPalPayment payment = new PayPalPayment();
				  // Sets the currency type for this payment.
				     payment.setCurrencyType("USD");
				     // Sets the recipient for the payment. This can also be a phone number.
				     payment.setRecipient("example-merchant-1@paypal.com");
				     // Sets the amount of the payment, not including tax and shipping amounts.
				     payment.setSubtotal(new BigDecimal("8.25"));
				     // Sets the payment type. This can be PAYMENT_TYPE_GOODS, PAYMENT_TYPE_SERVICE, PAYMENT_TYPE_PERSONAL, or PAYMENT_TYPE_NONE.
				     payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);
				     
				     // PayPalInvoiceData can contain tax and shipping amounts. It also contains an ArrayList of PayPalInvoiceItem which can
				     // be filled out. These are not required for any transaction.
				     PayPalInvoiceData invoice = new PayPalInvoiceData();
				     // Sets the tax amount.
				     invoice.setTax(new BigDecimal("1.25"));
				     // Sets the shipping amount.
				     invoice.setShipping(new BigDecimal("4.50"));
				     
				     // PayPalInvoiceItem has several parameters available to it. None of these parameters is required.
				     PayPalInvoiceItem item1 = new PayPalInvoiceItem();
				     // Sets the name of the item.
				     item1.setName("Pink Stuffed Bunny");
				     // Sets the ID. This is any ID that you would like to have associated with the item.
				     item1.setID("87239");
				     // Sets the total price which should be (quantity * unit price). The total prices of all PayPalInvoiceItem should add up
				     // to less than or equal the subtotal of the payment.
				     item1.setTotalPrice(new BigDecimal("6.00"));
				     // Sets the unit price.
				     item1.setUnitPrice(new BigDecimal("2.00"));
				     // Sets the quantity.
				     item1.setQuantity(3);
				     // Add the PayPalInvoiceItem to the PayPalInvoiceData. Alternatively, you can create an ArrayList
				     // and pass it to the PayPalInvoiceData function setInvoiceItems().
				     invoice.getInvoiceItems().add(item1);
				     
				     // Create and add another PayPalInvoiceItem to add to the PayPalInvoiceData.
				     PayPalInvoiceItem item2 = new PayPalInvoiceItem();
				     item2.setName("Well Wishes");
				     item2.setID("56691");
				     item2.setTotalPrice(new BigDecimal("2.25"));
				     item2.setUnitPrice(new BigDecimal("0.25"));
				     item2.setQuantity(9);
				     invoice.getInvoiceItems().add(item2);
				     
				     // Sets the PayPalPayment invoice data.
				     payment.setInvoiceData(invoice);
				     // Sets the merchant name. This is the name of your Application or Company.
				     payment.setMerchantName("The Gift Store");
				     // Sets the description of the payment.
				     payment.setDescription("Quite a simple payment");
				     // Sets the Custom ID. This is any ID that you would like to have associated with the payment.
				     payment.setCustomID("8873482296");
				     // Sets the Instant Payment Notification url. This url will be hit by the PayPal server upon completion of the payment.
				     payment.setIpnUrl("http://www.exampleapp.com/ipn");
				     // Sets the memo. This memo will be part of the notification sent by PayPal to the necessary parties.
				     payment.setMemo("Hi! I'm making a memo for a simple payment.");
					// Use checkout to create our Intent.
					Intent checkoutIntent = PayPal.getInstance().checkout(
							payment, getActivity());
					// Use the android's startActivityForResult() and pass in
					// our Intent. This will start the library.
					startActivityForResult(checkoutIntent, 1);
				
			}
		});
		// The CheckoutButton is an android LinearLayout so we can add it to our
		// display like any other View.
		rl.addView(launchSimplePayment);
	}

	private void initLibrary() {
		PayPal pp = PayPal.getInstance();
		// If the library is already initialized, then we don't need to
		// initialize it again.
		if (pp == null) {
			// This is the main initialization call that takes in your Context,
			// the Application ID, and the server you would like to connect to.
			pp = PayPal.initWithAppID(getActivity(), appID, server);

			// -- These are required settings.
			pp.setLanguage("en_US"); // Sets the language for the library.
			// --

			// -- These are a few of the optional settings.
			// Sets the fees payer. If there are fees for the transaction, this
			// person will pay for them. Possible values are FEEPAYER_SENDER,
			// FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and
			// FEEPAYER_SECONDARYONLY.
			//pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
			// Set to true if the transaction will require shipping.
			//pp.setShippingEnabled(true);
			// Dynamic Amount Calculation allows you to set tax and shipping
			// amounts based on the user's shipping address. Shipping must be
			// enabled for Dynamic Amount Calculation. This also requires you to
			// create a class that implements PaymentAdjuster and Serializable.
			pp.setDynamicAmountCalculationEnabled(false);
			// --
		}
	}

	public PlaceOrderRetailer() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		activity = getActivity();
		fm = getActivity().getSupportFragmentManager();
		fragmentTransaction = fm.beginTransaction();

		Thread libraryInitializationThread = new Thread() {
			public void run() {
				initLibrary();

				// The library is initialized so let's create our CheckoutButton
				// and update the UI.
				if (PayPal.getInstance().isLibraryInitialized()) {
					hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
				} else {
					hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
				}
			}
		};
		libraryInitializationThread.start();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		id = bundle.getString("ap");
		items.clear();
		View view = inflater.inflate(R.layout.fragment_place_order_retailer,
				container, false);
		od = new Orderadapter();
		orderbatch = new ArrayList<ArrayList<Double>>();
		dicount = new ArrayList<String>();
		disbatch = new ArrayList<ArrayList<Double>>();

		qtyonhand = new ArrayList<String>();
		itemshopid = new ArrayList<String>();
		minorder = new ArrayList<String>();
		mintv = (TextView) view.findViewById(R.id.minorder);
		warehousname = new ArrayList<String>();
		warehousedialog = new ArrayList<String>();
		warehouse = (Spinner) view.findViewById(R.id.spinner2);
		batchbtn = (Button) view.findViewById(R.id.button1);
		batchbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final Dialog dialog = new Dialog(getActivity());
				LayoutInflater inflator = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflator.inflate(R.layout.batch_discount, null);
				view.setMinimumHeight(300);
				view.setMinimumWidth(300);
				TableLayout tb = (TableLayout) view.findViewById(R.id.tb);
				int index = names.indexOf(itemname.getText().toString());
				for (int i = 0; i < orderbatch.get(index).size(); i++) {
					TableRow tr = new TableRow(getActivity());
					TextView qty = new TextView(getActivity());
					qty.setText(orderbatch.get(index).get(i).toString());
					TextView dis = new TextView(getActivity());
					dis.setText(disbatch.get(index).get(i).toString());
					tr.addView(qty);
					tr.addView(dis);
					tb.addView(tr);
				}
				dialog.setTitle("Batch Discounts");
				dialog.show();
				dialog.setContentView(view);

			}
		});
		warehouseid = new ArrayList<String>();
		warehouseadapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, warehousname);
		warehouseadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		warehouse.setAdapter(warehouseadapter);
		total = (TextView) view.findViewById(R.id.total);
		total.setText("0");
		totalitems = (TextView) view.findViewById(R.id.totalitems);
		totalitems.setText("0");
		qty = (EditText) view.findViewById(R.id.qty);
		cptv = (TextView) view.findViewById(R.id.cp);
		rl = (RelativeLayout) view.findViewById(R.id.rl);
		// rl.setVisibility(View.GONE);
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
				} else if (Double.parseDouble(qty.getText().toString()) < Double
						.parseDouble(mintv.getText().toString())) {
					Toast.makeText(getActivity(),
							"Qty cannot be less than minimum limit",
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
							Double dis = (double) Float.parseFloat(dicount
									.get(index));
							for (int i = 0; i < orderbatch.get(index).size(); i++) {
								if (qtya > orderbatch.get(index).get(i)) {
									dis = disbatch.get(index).get(i);
								}
							}
							dis = 100 - dis;
							dis = dis / 100;
							float totals = (float) (qtya * cpa * dis);
							ShoppingcartItem temp = (new ShoppingcartItem(
									itemname.getText().toString(), pathname
											.get(index), qtya, cpa, totals));
							temp.setDis(dis);
							items.add(temp);
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
						Double dis = (double) Float.parseFloat(dicount
								.get(index));
						for (int i = 0; i < orderbatch.get(index).size(); i++) {
							if (qtya > orderbatch.get(index).get(i)) {
								dis = disbatch.get(index).get(i);
							}
						}
						dis = 100 - dis;
						dis = dis / 100;
						float totals = (float) (qtya * cpa * dis);
						ShoppingcartItem item = items.get(ind);
						item.setDis(dis);
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
				if (items.size() == 0) {
					Toast.makeText(getActivity(), "No item selected",
							Toast.LENGTH_SHORT).show();
				} else {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							getActivity());

					// set title
					alertDialogBuilder.setTitle("Are you sure");

					// set dialog message
					alertDialogBuilder
							.setMessage(
									"Changes once made cannot be undone. Are you sure you want to proceeed? ")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {

											placeOrder();
										}
									})
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {

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

		autoadapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, names);
		itemname.setAdapter(autoadapter);
		itemname.setValidator(new Validator());
		itemname.setOnFocusChangeListener(new FocusListener());
		new FetchItems().execute("");
		return view;

	}

	class Validator implements AutoCompleteTextView.Validator {

		@Override
		public boolean isValid(CharSequence text) {
			if (names.contains(text.toString())) {
				cptv.setText(cp.get(names.indexOf(text.toString())));
				mintv.setText(minorder.get(names.indexOf(text.toString())));
				if (orderbatch.get(names.indexOf(text.toString())).size() < 1) {
					batchbtn.setEnabled(false);
				}
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

	void populatelists() {
		for (int i = 0; i < B2BUtils.shop.getChildren().size(); i++) {
			if (B2BUtils.shop.getChildren().get(i).getItem_shopid().equals(id)) {
				names.add(B2BUtils.shop.getChildren().get(i).getItemname());
				ids.add(B2BUtils.shop.getChildren().get(i).getIditem());
				cp.add(B2BUtils.shop.getChildren().get(i).getUnitcost());
				pathname.add(B2BUtils.shop.getChildren().get(i).getImagepath());
				qtyonhand.add(B2BUtils.shop.getChildren().get(i)
						.getQtyonorder());
				itemshopid.add(B2BUtils.shop.getChildren().get(i)
						.getItem_shopid());
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
					TextView itemname = (TextView) v
							.findViewById(R.id.itemname);
					int index = AlreadyIn(itemname.getText().toString());
					ShoppingcartItem cart = items.get(index);
					float qtya = (cart.getQty());
					float cpa = cart.getCp();
					float totals = qtya * cpa;
					items.remove(index);
					qtyf = qtyf - qtya;
					totalf = totalf - totals;
					totalitems.setText(qtyf + "");
					total.setText(totals + "");
					od.notifyDataSetChanged();
					Toast.makeText(getActivity(), itemname.getText(),
							Toast.LENGTH_SHORT).show();

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
			if (warehouseid.size() < 1) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());

				// set title
				alertDialogBuilder.setTitle("No warehouse found");

				// set dialog message
				alertDialogBuilder
						.setMessage(
								"You will now be redirected to add warehouse ")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										AddWarehouse ab = new AddWarehouse();
										fragmentTransaction = fm
												.beginTransaction();
										fragmentTransaction.replace(
												R.id.myfragment, ab);
										fragmentTransaction.commit();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			} else {
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
								+ ob.getString("state") + "</br>"
								+ ob.getString("country") + "</br>" + "Zip : "
								+ ob.getString("zip")

						);
					}
				} catch (Exception e) {
					Log.d(B2BUtils.LogString, e.toString());
				}
			}
			return null;
		}

	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			//launchSimplePayment.performClick();

		}
	};

	public void performclick() {
		launchSimplePayment.performClick();
	}

	public void placeOrder() {
		pd = ProgressDialog.show(getActivity(), "Working..",
				"Placing your order", true, false);
		Thread ab = new Thread(new Runnable() {

			@Override
			public void run() {
				List<NameValuePair> param = new LinkedList<NameValuePair>();
				List<NameValuePair> params = new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("opt", "retailorder"));
				for (int i = 0; i < items.size(); i++) {
					ShoppingcartItem item = items.get(i);
					int index = names.indexOf(item.getName());
					String itemshopids = itemshopid.get(index);
					params.add(new BasicNameValuePair("itemshopid", itemshopids));
					param.add(new BasicNameValuePair("itemid", itemshopids));
					param.add(new BasicNameValuePair("qty", item.getQty() + ""));
					param.add(new BasicNameValuePair("cost", item.getCp() + ""));
					param.add(new BasicNameValuePair("discount", item.getDis()
							+ ""));
					param.add(new BasicNameValuePair("afterdiscount", item
							.getTotal() + ""));
					param.add(new BasicNameValuePair("shipmentcost", "0"));
					float qtyonorder = Float.parseFloat(qtyonhand.get(index))
							+ item.getQty();
					params.add(new BasicNameValuePair("qtyonorder", qtyonorder
							+ ""));
				}
				JSONObject ob = B2BUtils.submitSimpleForm(
						"http://10.0.2.2:8080/B2B/order.jsp", params);
				try {
					if (ob.getString("Status").equals("Values inserted")) {
						/**/

						param.add(new BasicNameValuePair("opt", "insert"));
						param.add(new BasicNameValuePair("pby", B2BUtils.shop
								.getIdshop()));
						param.add(new BasicNameValuePair("pto", id));
						param.add(new BasicNameValuePair("total", total
								.getText().toString()));
						param.add(new BasicNameValuePair("status", "pending"));
						param.add(new BasicNameValuePair("whouse", warehouseid
								.get(warehousname.indexOf(warehouse
										.getSelectedItem()))));
						JSONObject ob1 = B2BUtils.submitSimpleForm(
								"http://10.0.2.2:8080/B2B/order.jsp", param);
						if (ob1.getString("Status").equals("Values inserted")) {
							Message message = handler.obtainMessage();
							message.obj = "Values inserted";
							handler.sendMessage(message);
						}

					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
		});
		ab.start();
	}

	

	class FetchItems extends AsyncTask<String, String, String> {

		@Override
		protected void onPostExecute(String result) {
			autoadapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			JSONArray ae = B2BUtils
					.getJSOnArr("http://10.0.2.2:8080/B2B/shop.jsp?shopid="
							+ id);
			try {
				email = ae.getString(0);
				System.out.println(email);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				System.out.print(e1.toString());
			}
			JSONArray ar = B2BUtils
					.getJSOnArr("http://10.0.2.2:8080/B2B/my_supplier.jsp?opt=mysubscriptions&shopid="
							+ B2BUtils.shop.getIdshop() + "&suppid=" + id);
			for (int i = 0; i < ar.length(); i++) {
				try {
					JSONObject ob = ar.getJSONObject(i);

					names.add(ob.getString("itemname"));
					ids.add(ob.getString("item_shopid"));
					cp.add(ob.getString("sp"));
					dicount.add(ob.getString("discount"));
					minorder.add(ob.getString("min_order"));
					pathname.add((ob.getString("imagepath")));
					itemshopid.add((ob.getString("item_shopid")));
					qtyonhand.add((ob.getString("qtyonhand")));
					ArrayList<Double> qtyy = new ArrayList<Double>();
					ArrayList<Double> diss = new ArrayList<Double>();
					if (ob.getJSONArray("batch").length() > 0) {
						for (int j = 0; j < ob.getJSONArray("batch").length(); j++) {
							JSONObject temp = ob.getJSONArray("batch")
									.getJSONObject(j);
							qtyy.add(temp.getDouble("qty"));
							diss.add(temp.getDouble("discount"));
						}

					}
					orderbatch.add(qtyy);
					disbatch.add(diss);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
				}

			}

			return null;
		}

	}

	@SuppressWarnings("unused")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			// The payment succeeded
			String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
			Toast.makeText(getActivity(), "Payment Confirmed",
					Toast.LENGTH_SHORT).show();

			break;
		case Activity.RESULT_CANCELED:
			// The payment was canceled
			// Tell the user their payment was canceled
			Toast.makeText(getActivity(), "Payment Cancelled",
					Toast.LENGTH_SHORT).show();

			break;
		case PayPalActivity.RESULT_FAILURE:
			// The payment failed -- we get the error from the EXTRA_ERROR_ID
			// and EXTRA_ERROR_MESSAGE
			String errorID = data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
			
			System.out.println(errorID);
			String errorMessage = data
					.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
			Toast.makeText(getActivity(),
					"Payment failed due to" + errorMessage, Toast.LENGTH_SHORT)
					.show();

			break;
		}
	}

	@Override
	public void onResume() {
		if (launchSimplePayment != null
				&& (launchSimplePayment instanceof CheckoutButton))
			launchSimplePayment.updateButton();
		System.out.println("reumes");
		super.onResume();
	}
}
