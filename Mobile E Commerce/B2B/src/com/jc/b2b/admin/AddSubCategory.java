package com.jc.b2b.admin;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jc.b2b.R;
import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.InsertForm;


public class AddSubCategory extends Fragment {
	
	EditText et;
	Button btn;
	public AddSubCategory() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view= inflater.inflate(R.layout.fragment_add_sub_category, container,
				false);
		btn=(Button)view.findViewById(R.id.button);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				showEditDialog();
			}
		});
		et=(EditText)view.findViewById(R.id.editText1);
		et.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1)
				{
					if(((EditText)arg0).getText().toString().length()>0)
					{
						((EditText)arg0).setError("Category Name cannot be empty");
					}
					else if(B2BUtils.categoryNames.contains(((EditText)arg0).getText().toString().toUpperCase()))
					{
						((EditText)arg0).setError("Category Name already exists");
					}
				}
				
			}
		});
		Button btn2=(Button)view.findViewById(R.id.submit);
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(et.getText().toString().length()>0&& !btn.getText().toString().equalsIgnoreCase("Parent Category"))
				{
					String id=B2BUtils.categoryId.get(B2BUtils.categoryNames.indexOf(btn.getText().toString().toUpperCase()));
					List<NameValuePair> params=new LinkedList<NameValuePair>();
					params.add(new BasicNameValuePair("opt", "insert"));
					params.add(new BasicNameValuePair("name", et.getText().toString()));
					params.add(new BasicNameValuePair("parent", id));
					new InsertForm(getActivity(), params).execute("http://10.0.2.2:8080/B2B/category.jsp");
			
				}
				
			}
		});
			return view;
	}
	public  void showEditDialog() {
        FragmentManager fm = getChildFragmentManager();
        NLevel editNameDialog = new NLevel();
        editNameDialog.show(fm, "fragment_edit_name");
    }


	public void updateCategory(String inputText)
{
		btn.setText(inputText);
	}
}
