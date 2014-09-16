package com.jc.b2b.utils;

import java.io.Serializable;

public class FinePrices implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String desc, cost, type,id;

public FinePrices(String desc, String cost, String type,String id) {
	super();
	this.desc = desc;
	this.cost = cost;
	this.type = type;
	this.id=id;
}

public String getDesc() {
	return desc;
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
