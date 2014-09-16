package com.jc.b2b.utils;

import java.io.Serializable;

public class ShopPrices implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String description,time,categories,cost,type,id;

public ShopPrices(String description, String time, String categories,
		String cost, String type, String id) {
	super();
	this.description = description;
	this.time = time;
	this.categories = categories;
	this.cost = cost;
	this.type = type;
	this.id = id;
}

public String getDescription() {
	return description;
}

public String getTime() {
	return time;
}

public String getCategories() {
	return categories;
}

public String getCost() {
	return cost;
}

public String getType() {
	return type;
}

public String getId() {
	return id;
}

}
