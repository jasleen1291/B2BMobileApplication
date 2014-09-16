package com.jc.b2b.admin;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jc.b2b.R;
import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.Category;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class NLevel extends DialogFragment {
	public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }
	ArrayList<String> cat=new ArrayList<String>();
	ArrayList<ArrayList<Category>> item=new ArrayList<ArrayList<Category>>();
	CategoryAdapter adapter;
	ListView lv;
	public NLevel() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle("Hello");
		cat.clear();
		item.clear();
		View view=inflater.inflate(R.layout.nlevel, container,false);
		TextView tv=(TextView)view.findViewById(R.id.tv);
		tv.setVisibility(View.GONE);
		adapter=new CategoryAdapter(cat, item,0);
		lv=(ListView)view.findViewById(R.id.list);
		lv.setVisibility(View.VISIBLE);
		lv.setAdapter(adapter);
		
		new FetchCategories().execute("");
		
		return view;
	}
	class FetchCategories extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			JSONArray ab=B2BUtils.getJSOnArr("http://10.0.2.2:8080/B2B/category.jsp?opt=select");
			try
			{
			for(int i=0;i<ab.length();i++)
			{
				JSONObject ac=ab.getJSONObject(i);
	            //System.out.println(ac.getString("name")+10);
	            JSONArray db=ac.getJSONArray("children");
	            cat.add(ac.getString("name"));
	           
	            //Category aq=new Category(ac.getString("id"), ac.getString("name"), "Main");
	            if(db.length()>0)
	            {
	               
	           	 item.add(getChildren(db,ac.getString("name")));
	           	
	            }else
	            {
	            	item.add(new ArrayList<Category>());
	            }
	            
	           
			}
			
			}
			catch(Exception e)
			{
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			adapter.notifyDataSetChanged();
			
			super.onPostExecute(result);
		}
		
	}
	public ArrayList<Category> getChildren(JSONArray ab, String parentname)
	{
		try
		{
		ArrayList<Category> a=new ArrayList<Category>();
		 for(int i=0;i<ab.length();i++)
	     {
	         JSONObject ac=ab.getJSONObject(i);
	         
	         JSONArray db=ac.getJSONArray("children");
	         Category aq=new Category(ac.getString("id"), ac.getString("name"), parentname);
	         if(db.length()>0)
	         {
	            
	        	 aq.setChildren((getChildren(db,ac.getString("name")))) ;
	        	
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
	class CategoryAdapter extends BaseAdapter{
		ArrayList<String> parent;
		ArrayList<ArrayList<Category>>child;
		
		int level;
		public CategoryAdapter(ArrayList<String> parent,
				ArrayList<ArrayList<Category>> child ,int level) {
			super();
			this.parent = parent;
			this.child = child;
			this.level=level;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return parent.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return parent.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			 LayoutInflater inflater = (LayoutInflater) getActivity()
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg1=inflater.inflate(R.layout.nlevel, arg2, false);
			TextView tv=(TextView)arg1.findViewById(R.id.tv);
			tv.setText(parent.get(arg0));
			tv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					ab(((TextView)arg0).getText().toString());
					
				}
			});
			if(level==0)
				tv.setBackgroundResource(R.drawable.listlevel0);
			if(child.get(arg0).size()>0)
			{
				if(level>0)
				{
					tv.setBackgroundResource(R.drawable.listlevel1);
				}
				
				ListView ls=(ListView)arg1.findViewById(R.id.list);
				ls.setVisibility(View.VISIBLE);
				
				ArrayList<String> par=new ArrayList<String>();
				ArrayList<ArrayList<Category>>ch=new ArrayList<ArrayList<Category>>();
				for(int i=0;i<child.get(arg0).size();i++)
				{
					par.add(child.get(arg0).get(i).getName());
					ch.add(child.get(arg0).get(i).getChildren());
				}
				ls.setAdapter(new CategoryAdapter(par, ch,level+1));
			}
			else
			{
				if(level>0)
				tv.setBackgroundResource(android.R.color.transparent);
				 
			}
			return arg1;
		}
		
	}
	public void ab(String inputText)
	{
		this.dismiss();
		EditNameDialogListener activity=(EditNameDialogListener)getActivity();
		activity.onFinishEditDialog(inputText);
		
	}
}
