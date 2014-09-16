package com.jc.b2b;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.InsertForm;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalPayment;

public class FirstTimeLogin extends Activity implements OnClickListener{
EditText name,desc,paymentemail;
String type,id;
Spinner shoptype;
ArrayList<String> groupItems=new ArrayList<String>();
ArrayList<String> days=new ArrayList<String>();
ArrayList<String> price=new ArrayList<String>();
ArrayList<String> cats=new ArrayList<String>();
ArrayAdapter<String> adapter;
CheckoutButton launchPayPalButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_time_login);
		try
		{
		name=(EditText)findViewById(R.id.editText1);
		desc=(EditText)findViewById(R.id.editText2);
		paymentemail=(EditText)findViewById(R.id.editText3);
		shoptype=(Spinner)findViewById(R.id.shoptype);
		
		adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,groupItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shoptype.setAdapter(adapter);
        PayPal ppObj = PayPal.initWithAppID(this.getBaseContext(), "APP-80W284485P519543T", PayPal.ENV_NONE);

        launchPayPalButton= ppObj.getCheckoutButton(this, PayPal.BUTTON_152x33, CheckoutButton.TEXT_PAY);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.ALIGN_BOTTOM);

        launchPayPalButton.setLayoutParams(params);
        launchPayPalButton.setOnClickListener(this);

        ((RelativeLayout) findViewById(R.id.mRlayout1)).addView(launchPayPalButton);
        new FetchData().execute("");
}
catch(Exception e)
{
Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();	
}
	}

	@Override
	protected void onResume() {
		 if (launchPayPalButton != null && (launchPayPalButton instanceof CheckoutButton))
			 launchPayPalButton.updateButton();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_time_login, menu);
		 		return true;
	}
	class FetchData extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			try
			{
			JSONObject ob=B2BUtils.getJSOn("http://10.0.2.2:8080/B2B/pricing.jsp?opt=select&t=shop");
			JSONObject as=ob.getJSONObject(B2BUtils.getUser().getUsertype());
			Iterator<?> it=as.keys();
			while(it.hasNext())
			{id=it.next().toString();
				JSONObject obs=as.getJSONObject(id);
				type=(obs.getString("time").equalsIgnoreCase("-1"))?"unlimited":obs.getString("time");
				groupItems.add(obs.getString("name")+" ("+obs.getString("categories")+" for $"+obs.getString("cost")+" and "+type+" days)");
				days.add(obs.getString("time"));
				price.add(obs.getString("cost"));
				cats.add(obs.getString("categories"));
			}
			}
			catch(Exception e)
			{
				Log.d(B2BUtils.LogString, e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
		
		
	}
	@Override
	public void onClick(View arg0) {
		 PayPalPayment newPayment = new PayPalPayment();
		 int index=shoptype.getSelectedItemPosition();
		 
	        char val[] = price.get(index).toCharArray();
	        BigDecimal obj_0 = new BigDecimal(val);
	        newPayment.setSubtotal(obj_0);
	        newPayment.setCurrencyType("USD");
	        newPayment.setRecipient("jas1291-facilitator@gmail.com");
	        newPayment.setMerchantName("Mobile Ecommerce");
	        Intent paypalIntent = PayPal.getInstance().checkout(newPayment, this);
	        this.startActivityForResult(paypalIntent, 1);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	       switch (resultCode) {
           case Activity.RESULT_OK:
           { 
        	   int index=shoptype.getSelectedItemPosition();
        	   List<NameValuePair> params = new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("opt", "addshop"));
				params.add(new BasicNameValuePair("name", name.getText().toString()));
				params.add(new BasicNameValuePair("type", B2BUtils.getUser().getUsertype()));
				params.add(new BasicNameValuePair("id", B2BUtils.getUser().getMasterid()));
				params.add(new BasicNameValuePair("desc", desc.getText().toString()));
				params.add(new BasicNameValuePair("days", days.get(index)));
				params.add(new BasicNameValuePair("email", paymentemail.getText().toString()));
				params.add(new BasicNameValuePair("no", cats.get(index)))	;
        	   new InsertForm(FirstTimeLogin.this, params).execute("http://10.0.2.2:8080/B2B/shop.jsp");
        	   finish();
              break;
           }
           case Activity.RESULT_CANCELED:
               // The payment was canceled
               // Tell the user their payment was canceled
        	   Toast.makeText(getApplicationContext(), "Payment Cancelled", Toast.LENGTH_SHORT).show();
	              
               break;
           case PayPalActivity.RESULT_FAILURE:
               // The payment failed -- we get the error from the EXTRA_ERROR_ID
               // and EXTRA_ERROR_MESSAGE
               //String errorID = data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
               String errorMessage = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
               Toast.makeText(getApplicationContext(), "Payment failed due to"+errorMessage, Toast.LENGTH_SHORT).show();
	              
               break;
       }
	}
	
}
