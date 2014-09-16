package com.jc.b2b.utils;

import java.io.Serializable;

public class AdvertisementPrice implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String category,cost,days,desc,type,id;

public AdvertisementPrice(String category, String cost, String days,
		String desc, String type,String id) {
	super();
	this.category = category;
	this.cost = cost;
	this.days = days;
	this.desc = desc;
	this.type = type;
	this.id=id;
}

public String getCategory() {
	return category;
}

public String getCost() {
	return cost;
}

public String getDays() {
	return days;
}

public String getDesc() {
	return desc;
}

public String getType() {
	return type;
}

public String getId() {
	return id;
}

}
