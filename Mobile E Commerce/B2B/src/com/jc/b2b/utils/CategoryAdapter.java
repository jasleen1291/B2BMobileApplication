package com.jc.b2b.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.b2b.R;


public class CategoryAdapter extends BaseAdapter{
	
	ArrayList<Category> child;
	FragmentActivity activity;
	int i=0,j=0;
	String name,id;	

public CategoryAdapter(ArrayList<Category> child, FragmentActivity activity) {
		super();
		this.child = child;
		this.activity = activity;
	}



public void ab(String inputText)
{
	
	Intent intent=new Intent(B2BUtils.CATEGORY_INTENT);
	intent.putExtra("Selction", inputText);
	//intent.putExtra("id", id);
	DialogFragment f=(DialogFragment)activity.getSupportFragmentManager().findFragmentByTag("dialog");
	if(f!=null)
	f.dismiss();
	
	activity.getApplicationContext().sendBroadcast(intent);
	
}



@Override
public int getCount() {
	// TODO Auto-generated method stub
	return child.size();
}



@Override
public Object getItem(int arg0) {
	// TODO Auto-generated method stub
	return child.get(arg0);
}



@Override
public long getItemId(int arg0) {
	// TODO Auto-generated method stub
	return arg0;
}



@Override
public View getView(int arg0, View arg1, ViewGroup arg2) {
	LayoutInflater inflater = (LayoutInflater) activity
    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	try
	{
	LinearLayout view=(LinearLayout)inflater.inflate(R.layout.nlevel, arg2,false);
	final TextView tv=(TextView)view.findViewById(R.id.tv);
	tv.setText(child.get(arg0).getName());
	final int j=arg0;
	tv.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ab(child.get(j).getName());
			
		}
	});
	if(child.get(arg0).getChildren().size()>0)
	view.addView(getLL(activity, child.get(arg0).getChildren(),0));
	return view;
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return null;
}
public LinearLayout getLL(Activity activity,ArrayList<Category> cat,int a)
{
	LinearLayout ll=new LinearLayout(activity);
	ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	ll.setOrientation(LinearLayout.VERTICAL);
	ll.setBackgroundResource(R.drawable.listlevel0);
	LayoutInflater inflater = (LayoutInflater) activity
    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	for( int i=0;i<cat.size();i++)
	{
		
		final TextView tv=(TextView)inflater.inflate(R.layout.texts, null);
		
		tv.setText(Html.fromHtml("&#187;")+" "+cat.get(i).getName());
		tv.setTextSize(24.0f);
		tv.setTextColor(Color.WHITE);
		tv.setPadding(20*a, 0, 0, 0);
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//System.out.println(tv.getText().toString());
				ab(tv.getText().toString().replace(Html.fromHtml("&#187;"), ""));
				
			}
		});
		//System.out.println(cat.get(i).getName());
		ll.addView(tv);
		
		if(cat.get(i).getChildren().size()>0)
		{
			ll.addView(getLL(activity, cat.get(i).getChildren(),(a+1)));
		}
	}
	return ll;
	
}

}