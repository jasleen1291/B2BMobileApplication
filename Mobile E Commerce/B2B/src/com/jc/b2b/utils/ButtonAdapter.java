package com.jc.b2b.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ButtonAdapter extends BaseAdapter {
    private Context mContext;

    public ButtonAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return Gradients.AllGradients().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
        TextView b;
        if (convertView == null) {
            b = new TextView(mContext);
            b.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT, 50));            
            b.setPadding(8, 8, 8, 8);
            b.setTextSize(18f);      
        } else {
            b = (TextView) convertView;
        }
        
        final GradientColor gd = Gradients.AllGradients().get(position);
        b.setBackgroundDrawable(Gradients.NewGradient(gd));
        b.setText(gd.getName());
        b.setClickable(false);
        b.setTextColor(gd.getTextColor());    
        b.setShadowLayer(1f, 1f, 1f, gd.getColorE());
        b.setTag(Integer.valueOf(position));
        return b;
    }    
}