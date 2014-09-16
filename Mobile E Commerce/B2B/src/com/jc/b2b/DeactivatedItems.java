package com.jc.b2b;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.b2b.utils.B2BUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class DeactivatedItems extends Fragment {

	public DeactivatedItems() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view= inflater.inflate(R.layout.fragment_deactivated_items, container,
				false);
		ListView grd=(ListView)view.findViewById(R.id.grid1);
		grd.setAdapter(new GridAdapter());
		grd.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), B2BUtils.shop.getAvailable().get(arg2).getIditem(), Toast.LENGTH_SHORT).show();
				
			}
		});
		return view;
	}
	class GridAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return B2BUtils.shop.getAvailable().size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return B2BUtils.shop.getAvailable().get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LayoutInflater infalInflater = (LayoutInflater)getActivity(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg1=infalInflater.inflate(R.layout.grid_item2, arg2, false);
			ImageView iv=(ImageView)arg1.findViewById(R.id.imageView1);
			
			if(B2BUtils.shop.getImgs().containsKey(B2BUtils.shop.getAvailable().get(arg0).getImagepath()))
			{
				iv.setImageBitmap(B2BUtils.shop.getImgs().get(B2BUtils.shop.getAvailable().get(arg0).getImagepath()));
			}
			
			TextView tv1=(TextView)arg1.findViewById(R.id.name);
			tv1.setText(B2BUtils.shop.getAvailable().get(arg0).getItemname());
			/*TextView tv2=(TextView)arg1.findViewById(R.id.sp);
			if(null!=B2BUtils.shop.getChildren().get(arg0).getSp())
			{
				tv2.setText(B2BUtils.shop.getChildren().get(arg0).getSp()+"");
			}
			else
			{
				tv2.setText("");
			}
			TextView tv3=(TextView)arg1.findViewById(R.id.cp);
			if(null!=B2BUtils.shop.getChildren().get(arg0).getUnitcost())
			{
				tv3.setText(B2BUtils.shop.getChildren().get(arg0).getUnitcost()+"");
			}
			else
			{
				tv3.setText("");
			}
			TextView tv4=(TextView)arg1.findViewById(R.id.discount);
			if(null!=B2BUtils.shop.getChildren().get(arg0).getDiscount())
			{
				tv4.setText(B2BUtils.shop.getChildren().get(arg0).getDiscount()+"");
			}
			else
			{
				tv4.setText("");
			}
			TextView tv5=(TextView)arg1.findViewById(R.id.minorder);
			if(null!=B2BUtils.shop.getChildren().get(arg0).getMin_order())
			{
				tv5.setText(B2BUtils.shop.getChildren().get(arg0).getMin_order()+"");
			}
			else
			{
				tv5.setText("");
			}
			*/
						return arg1;
		}
		
	}

}
