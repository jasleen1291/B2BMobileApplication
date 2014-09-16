package com.jc.b2b.admin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jc.b2b.R;
import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.Category;
import com.jc.b2b.utils.InsertForm;

public class AddCategory extends Fragment {

	Button submit;
	EditText category;
	ArrayList<Category> cats=new ArrayList<Category>();
	public AddCategory() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_add_category, container,
				false);
		submit=(Button)view.findViewById(R.id.submit);
		category=(EditText)view.findViewById(R.id.catname);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(category.getText().toString().length()<1)
				{
					category.setError("Category name cannot be empty");
				}else if(B2BUtils.categoryNames.contains(category.getText().toString().toUpperCase()))
				{
					category.setError("Category name already exists");
				}
				else
				{
					List<NameValuePair> params=new LinkedList<NameValuePair>();
					params.add(new BasicNameValuePair("opt", "insert"));
					params.add(new BasicNameValuePair("name", category.getText().toString()));
					params.add(new BasicNameValuePair("parent", "0"));
					new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/category.jsp");
				}
				
			}
		});
		return view;
	}

	
	

}
