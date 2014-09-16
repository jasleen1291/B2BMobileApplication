package com.jc.b2b;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.InsertForm;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class EditMyCategories extends Fragment {

	public EditMyCategories() {
		// Required empty public constructor
	}
ListView lv;
Button button1;
ArrayList<String>id,category,myids;
android.support.v4.app.FragmentTransaction fragmentTransaction;
android.support.v4.app.FragmentManager fm;
ArrayList<Boolean> check;
ArrayAdapter<String> myadapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		id=new ArrayList<String>();
		category=new ArrayList<String>();
		myids=new ArrayList<String>();
		check=new ArrayList<Boolean>();
		for(int i=0;i<B2BUtils.shop.getMycategories().size();i++)
		{
			myids.add(B2BUtils.shop.getMycategories().get(i).getId());
		}
		Enumeration<String> keys=B2BUtils.zerolevelcategories.keys();
		while(keys.hasMoreElements())
		{
			String ids=keys.nextElement();
			if(myids.contains(ids))
			{
				check.add(true);
				
			}
			else
			{
				check.add(false);
				
			}
			id.add(ids);
			category.add(B2BUtils.zerolevelcategories.get(ids));
		}
		View view= inflater.inflate(R.layout.fragment_edit_my_categories,
				container, false);
		lv=(ListView)view.findViewById(R.id.list);
		myadapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice,category);
		lv.setAdapter(myadapter);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 
				lv.getChildAt(arg2).setSelected(!lv.getChildAt(arg2).isSelected());
			}
		});
		for(int i=0;i<check.size();i++)
		{
			
				lv.setItemChecked(i, check.get(i));
			
		}
		button1=(Button)view.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean status=false;
				List<NameValuePair> params = new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("opt", "editcategories"));
				params.add(new BasicNameValuePair("id", B2BUtils.getUser().getMasterid()));
				int j=0;
				for(int i=0;i<lv.getChildCount();i++)
				{
					if(lv.isItemChecked(i))
					{
						j++;
						status=true;
						params.add(new BasicNameValuePair("catid",id.get(i)));
					}
				}
				if(!status)
				{
					Toast.makeText(getActivity(), "No item selected", Toast.LENGTH_LONG)
					.show();
				}
				else
				{
					if(B2BUtils.shop.getCategoriesallowed()!="-1")
						if(j>Integer.parseInt(B2BUtils.shop.getCategoriesallowed()))
						{
							Toast.makeText(getActivity(), "You have exceeded your no of subscribed categories", Toast.LENGTH_SHORT).show();
						}
						else
						{
					new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/category.jsp");
					startActivity(new Intent(getActivity().getBaseContext(),Supplier_area.class));
						}	
				}
			}
		});
		return view;
	}

}
