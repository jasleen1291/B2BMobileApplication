package com.jc.b2b.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.jc.b2b.FetchImage;


public class FetchShopInfo extends AsyncTask<String, String, String> {
	int mStackLevel = 0;
	List<String> groupItems;
	List<List<ChildItem>> childItems;
	
	HashMap<String, Bitmap> items;
	Activity activity;
	Shop shop;
	public FetchShopInfo(Activity activity) {
		super();
		this.activity=activity;
	}

	

	@Override
	protected String doInBackground(String... params) {
		JSONArray array = B2BUtils
				.getJSOnArr("http://10.0.2.2:8080/B2B/shop.jsp?opt=myshops&id="
						+ B2BUtils.getUser().getMasterid());
		
			try {
				JSONObject ob = array.getJSONObject(0);
				items=new HashMap<String, Bitmap>();
				ArrayList<Category> mycategories=new ArrayList<Category>();
				JSONArray categories=ob.getJSONArray("categories");
				{
					if(categories.length()<1)
					{
						
					}
					else
					{
						for(int i=0;i<categories.length();i++)
						{
							JSONObject ac=categories.getJSONObject(i);
				            //System.out.println(ac.getString("name")+10);
							Category cat=new Category(ac.getString("id"), ac.getString("name"),new ArrayList<Items>());
				            JSONArray db=ac.getJSONArray("children");
				            if(db.length()>0)
				            {
				            	cat.setChildren(myChildren(db));
				            }
				            mycategories.add(cat);
				            
						}
						Log.d("Length", mycategories.size()+"");
					}
				}
				Log.d("Size", mycategories.size()+"");
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
					Intent ic = new Intent(activity.getApplicationContext(),
							FetchImage.class);
					ic.putExtra("url", "http://10.0.2.2:8080/B2B/images/"
							+ child.getString("imagepath"));
					ic.putExtra("imagename", child.getString("itemname"));
					activity.startService(ic);
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
					
					if(getCategory(mycategories,child.getString("categoryid"))!=null)
					{
						getCategory(mycategories,child.getString("categoryid")).add(current);
					}
					children.add(current);

				}
				ArrayList<Items> available=new ArrayList<Items>();
				JSONArray ard2 = ob.getJSONArray("available");
				for(int i2=0;i2<ard2.length();i2++)
				{
					JSONObject child=ard2.getJSONObject(i2);
					Items current=(new Items(child.getString("categoryid"),
							child.getString("status"), child
							.getString("imagepath"), child
							.getString("itemname"), child
							.getString("unit_of_measurement"),
					child.getString("qtyperunit"), child
							.getString("owner"), child
							.getString("longdesciption"), child
							.getString("iditem")));
					available.add(current);
					Intent ic = new Intent(activity.getApplicationContext(),
							FetchImage.class);
					ic.putExtra("url", "http://10.0.2.2:8080/B2B/images/"
							+ child.getString("imagepath"));
					ic.putExtra("imagename", child.getString("itemname"));
					activity.startService(ic);
					if(getCategory(mycategories,child.getString("categoryid"))!=null)
					{
						getCategory(mycategories,child.getString("categoryid")).add(current);
					}
				}
				
				shop=new Shop(ob.getString("status"),ob.getString("description"), ob.getString("categoriesallowed"), ob.getString("ShopName"), ob.getString("expirydate"), ob.getString("idshop"), ob.getString("paymentemail"), children, available, items, mycategories);
				B2BUtils.shop=shop;
				Log.d("d", B2BUtils.shop.getImgs().size()+"");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		return null;
	}



public ArrayList<Items> getCategory(ArrayList<Category> cat,String catid)
{
	if(cat.size()==0)
	{
		return null;
	}
	else
	{
		for(int i=0;i<cat.size();i++)
		{
			if(cat.get(i).getId().equals(catid))
			{
				return cat.get(i).getIt();
			}
			else
			{
				ArrayList<Category> d=cat.get(i).getChildren();
				if(d.size()>0)
				{
					if(getCategory(d, catid)!=null)
					{
						return getCategory(d, catid);
					}
				}
			}
		}
	}
	return null;
}
ArrayList<Category> myChildren(JSONArray ab)
{
	try
	{
	ArrayList<Category> a=new ArrayList<Category>();
	 for(int i=0;i<ab.length();i++)
     {
         JSONObject ac=ab.getJSONObject(i);
         
         JSONArray db=ac.getJSONArray("children");
         Category aq=new Category(ac.getString("id"), ac.getString("name"), new ArrayList<Items>());
         if(db.length()>0)
         {
            
        	 aq.setChildren((myChildren(db))) ;
        	
         }
         else
         {
        	 aq.setChildren(new ArrayList<Category>());
         }
         a.add(aq);
         //item.add(new ArrayList<Category>());
         
     }
	 return a;
	}
	 catch(Exception e)
	 {
		 
	 }
	return null;
	
	
}

}
