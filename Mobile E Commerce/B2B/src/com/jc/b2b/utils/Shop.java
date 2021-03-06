package com.jc.b2b.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;

public class Shop {
String status,description,categoriesallowed,shopname,expirydate,idshop,paymentemail;
ArrayList<Items> children,available;
HashMap <String,Bitmap> imgs;
ArrayList<Category> mycategories;
public Shop(String status, String description, String categoriesallowed,
		String shopname, String expirydate, String idshop, String paymentemail,
		ArrayList<Items> children, ArrayList<Items> available,
		HashMap<String, Bitmap> imgs, ArrayList<Category> mycategories) {
	super();
	this.status = status;
	this.description = description;
	this.categoriesallowed = categoriesallowed;
	this.shopname = shopname;
	this.expirydate = expirydate;
	this.idshop = idshop;
	this.paymentemail = paymentemail;
	this.children = children;
	this.available = available;
	this.imgs = imgs;
	this.mycategories = mycategories;
}
public void setAvailable(ArrayList<Items> available) {
	this.available = available;
}
public void setChildren(ArrayList<Items> children) {
	this.children = children;
}
public String getStatus() {
	return status;
}
public String getDescription() {
	return description;
}
public String getCategoriesallowed() {
	return categoriesallowed;
}
public String getShopname() {
	return shopname;
}
public String getExpirydate() {
	return expirydate;
}
public String getIdshop() {
	return idshop;
}
public String getPaymentemail() {
	return paymentemail;
}
public ArrayList<Items> getChildren() {
	return children;
}
public ArrayList<Items> getAvailable() {
	return available;
}
public HashMap<String, Bitmap> getImgs() {
	return imgs;
}
public ArrayList<Category> getMycategories() {
	return mycategories;
}

}
