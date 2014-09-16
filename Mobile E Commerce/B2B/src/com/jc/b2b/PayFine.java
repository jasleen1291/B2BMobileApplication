package com.jc.b2b;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.jc.b2b.utils.B2BUtils;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalPayment;

public class PayFine extends Activity implements OnClickListener {
	 CheckoutButton launchPayPalButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_fine);

        PayPal ppObj = PayPal.initWithAppID(this.getBaseContext(), "APP-80W284485P519543T", PayPal.ENV_NONE);

        launchPayPalButton= ppObj.getCheckoutButton(this, PayPal.BUTTON_152x33, CheckoutButton.TEXT_PAY);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.ALIGN_BOTTOM);

        launchPayPalButton.setLayoutParams(params);
        launchPayPalButton.setOnClickListener(this);

        ((RelativeLayout) findViewById(R.id.mRlayout1)).addView(launchPayPalButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_fine, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		 PayPalPayment newPayment = new PayPalPayment();
	        char val[] = { '5', '0' ,'.','7','5'};
	        BigDecimal obj_0 = new BigDecimal(val);
	        newPayment.setSubtotal(obj_0);
	        newPayment.setCurrencyType("USD");
	        newPayment.setRecipient("jas1291-facilitator@gmail.com");
	        newPayment.setMerchantName("My Company");
	        Intent paypalIntent = PayPal.getInstance().checkout(newPayment, this);
	        this.startActivityForResult(paypalIntent, 1);
		
	}
	@SuppressWarnings("unused")
	   @Override
	   public void onActivityResult(int requestCode, int resultCode, Intent data) {
	       switch (resultCode) {
	           case Activity.RESULT_OK:
	               // The payment succeeded
	               String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
	               Toast.makeText(getApplicationContext(), "Payment Confirmed", Toast.LENGTH_SHORT).show();
	               final List<NameValuePair> params = new LinkedList<NameValuePair>();
					params.add(new BasicNameValuePair("status", "activated"));
					params.add(new BasicNameValuePair("id", B2BUtils.getUser().getMasterid()));
					Thread ab=new Thread(new Runnable() {
						
						@Override
						public void run() {
							JSONObject ob=B2BUtils.submitSimpleForm("http://10.0.2.2:8080/B2B/setCustomerStatus.jsp", params);
							try
							{
							if(ob.getString("Status")!=null)
							{
								if(ob.getString("Status").equals("Values inserted"))
								{
									if(B2BUtils.getUser().getUsertype().equalsIgnoreCase("Supplier"))
									{
										startActivity(new Intent(PayFine.this,Supplier_area.class));
									}
									else
									{
										startActivity(new Intent(PayFine.this,Retailer_area.class));
									}
								}
								
							}
							}
							catch(Exception e)
							{
								Log.d(B2BUtils.LogString, e.toString());
								Toast.makeText(getApplicationContext(), "Check Network Connection",Toast.LENGTH_LONG).show();
							}
							
						}
					});
					ab.start();
	               break;
	           case Activity.RESULT_CANCELED:
	               // The payment was canceled
	               // Tell the user their payment was canceled
	        	   Toast.makeText(getApplicationContext(), "Payment Cancelled", Toast.LENGTH_SHORT).show();
		              
	               break;
	           case PayPalActivity.RESULT_FAILURE:
	               // The payment failed -- we get the error from the EXTRA_ERROR_ID
	               // and EXTRA_ERROR_MESSAGE
	               String errorID = data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
	               String errorMessage = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
	               Toast.makeText(getApplicationContext(), "Payment failed due to"+errorMessage, Toast.LENGTH_SHORT).show();
		              
	               break;
	       }
	   }

	@Override
	protected void onResume() {
		 if (launchPayPalButton != null && (launchPayPalButton instanceof CheckoutButton))
			 launchPayPalButton.updateButton();
		super.onResume();
	}
	
}
