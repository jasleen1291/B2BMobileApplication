package com.jc.b2b.utils;

public class ComplaintItem {
String shopid,title,desc,itemname,email,shopname,id,itemid;


public ComplaintItem(String shopid, String title, String desc, String itemname,
		String email, String shopname, String id, String itemid) {
	super();
	this.shopid = shopid;
	this.title = title;
	this.desc = desc;
	this.itemname = itemname;
	this.email = email;
	this.shopname = shopname;
	this.id = id;
	this.itemid = itemid;
}


public ComplaintItem(String shopid, String title, String desc, String email,
		String shopname, String id) {
	super();
	this.shopid = shopid;
	this.title = title;
	this.desc = desc;
	this.email = email;
	this.shopname = shopname;
	this.id = id;
}


public String getShopid() {
	return shopid;
}


public String getTitle() {
	return title;
}


public String getDesc() {
	return desc;
}


public String getItemname() {
	return itemname;
}


public String getEmail() {
	return email;
}


public String getShopname() {
	return shopname;
}


public String getId() {
	return id;
}


public String getItemid() {
	return itemid;
}

}
