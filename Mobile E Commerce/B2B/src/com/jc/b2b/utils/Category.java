package com.jc.b2b.utils;

import java.util.ArrayList;

public class Category {
String id,name,parentname;
ArrayList<Category> children;
ArrayList<Items> it;
public ArrayList<Items> getIt() {
	return it;
}

public Category(String id, String name, 
		ArrayList<Items> it) {
	super();
	this.id = id;
	this.name = name;
	
	
	this.it = it;
}

public Category(String id, String name, String parentname) {
	super();
	this.id = id;
	this.name = name;
	this.parentname = parentname;
	children=new ArrayList<Category>();
}

public ArrayList<Category> getChildren() {
	return children;
}

public void setChildren(ArrayList<Category> children) {
	this.children = children;
}

public String getId() {
	return id;
}

public String getName() {
	return name;
}

public String getParentname() {
	return parentname;
}
}
