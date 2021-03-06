package com.jc.b2b.utils;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ColorPicker extends BaseAdapter {
	 private Context mContext;
	 Set<Integer> set = new HashSet<Integer>(Gradients.getColors());
	public ColorPicker(Context mContext) {
		super();
		this.mContext = mContext;
		
	}

	@Override
	public int getCount() {
		
		return set.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		TextView b;
        if (convertView == null) {
            b = new TextView(mContext);
            b.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT, 50));            
            b.setPadding(8, 8, 8, 8);
            b.setTextSize(18f);      
        } else {
            b = (TextView) convertView;
        }
        b.setText("Color");
        b.setBackgroundColor(Gradients.getColors().get(arg0));
		return b;
	}

}
