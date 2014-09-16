package com.jc.b2b.utils;

import java.util.ArrayList;

public class Order {
String total,placedto,id,state,placedby,warehouse,date;
ArrayList<OrderDetails> details;

public Order(String total, String placedto, String id, String state,
		String placedby, String warehouse, String date,
		ArrayList<OrderDetails> details) {
	super();
	this.total = total;
	this.placedto = placedto;
	this.id = id;
	this.state = state;
	this.placedby = placedby;
	this.warehouse = warehouse;
	this.date = date;
	this.details = details;
}
public String getTotal() {
	return total;
}
public void setTotal(String total) {
	this.total = total;
}
public String getPlacedto() {
	return placedto;
}
public void setPlacedto(String placedto) {
	this.placedto = placedto;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getPlacedby() {
	return placedby;
}
public void setPlacedby(String placedby) {
	this.placedby = placedby;
}
public String getWarehouse() {
	return warehouse;
}
public void setWarehouse(String warehouse) {
	this.warehouse = warehouse;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public ArrayList<OrderDetails> getDetails() {
	return details;
}
public void setDetails(ArrayList<OrderDetails> details) {
	this.details = details;
}

}
