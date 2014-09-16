package com.jc.b2b.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jc.b2b.R;

public class TwoLineAdapter extends BaseAdapter {
ArrayList<String> itemone;
ArrayList<String> itemtwo;
Activity activity;

	public TwoLineAdapter(Activity activity,ArrayList<String> itemone, ArrayList<String> itemtwo) {
	super();
	this.activity=activity;
	this.itemone = itemone;
	this.itemtwo = itemtwo;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemone.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return itemone.get(arg0);
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
		View view = inflater.inflate(R.layout.twolist, null);
		TextView one=(TextView)view.findViewById(R.id.textView1);
		one.setText(itemone.get(arg0));
		TextView two=(TextView)view.findViewById(R.id.textView2);
		two.setText(itemtwo.get(arg0));
		return view;
	}

}
