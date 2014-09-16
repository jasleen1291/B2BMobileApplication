package com.jc.b2b.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;

public class OrganizeCategories {
JSONArray ab;
ArrayList<String> cat;
ArrayList<ArrayList<Category>> item;
public OrganizeCategories(JSONArray ab,Handler h) {
	super();
	this.ab = ab;
	cat=new ArrayList<String>();
	item=new ArrayList<ArrayList<Category>>();
}

public ArrayList<String> getCat() {
	return cat;
}

public ArrayList<ArrayList<Category>> getItem() {
	return item;
}

public JSONArray getAb() {
	return ab;
}
public void organize()
{
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
}
