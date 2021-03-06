package com.jc.b2b;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.Category;
import com.jc.b2b.utils.CategoryAdapter;

public class SelectCategory extends DialogFragment {
ListView lv;

	public SelectCategory() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view= inflater.inflate(R.layout.fragment_select_category, container,
				false);
		lv=(ListView)view.findViewById(R.id.listView1);
		getDialog().setTitle("Select a Category");
		getDialog().setCancelable(false);
		ArrayList<String> parent=new ArrayList<String>();
		ArrayList<ArrayList<Category>> child=new ArrayList<ArrayList<Category>>();
		for(int i=0;i<B2BUtils.shop.getMycategories().size();i++)
		{
			parent.add(B2BUtils.shop.getMycategories().get(i).getName());
			
			child.add(B2BUtils.shop.getMycategories().get(i).getChildren());
		}
		//System.out.println(getActivity());
		lv.setAdapter(new CategoryAdapter(B2BUtils.shop.getMycategories(),getActivity()));
		
		return view;
	}
	
}
