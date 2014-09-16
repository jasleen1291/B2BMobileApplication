package com.jc.b2b.utils;

public class OrderDetails {
String qty,cost,discount,afterdiscount,itemid,shipmentcost;

public OrderDetails(String qty, String cost, String discount,
		String afterdiscount, String itemid, String shipmentcost) {
	super();
	this.qty = qty;
	this.cost = cost;
	this.discount = discount;
	this.afterdiscount = afterdiscount;
	this.itemid = itemid;
	this.shipmentcost = shipmentcost;
}

public String getQty() {
	return qty;
}

public void setQty(String qty) {
	this.qty = qty;
}

public String getCost() {
	return cost;
}

public void setCost(String cost) {
	this.cost = cost;
}

public String getDiscount() {
	return discount;
}

public void setDiscount(String discount) {
	this.discount = discount;
}

public String getAfterdiscount() {
	return afterdiscount;
}

public void setAfterdiscount(String afterdiscount) {
	this.afterdiscount = afterdiscount;
}

public String getItemid() {
	return itemid;
}

public void setItemid(String itemid) {
	this.itemid = itemid;
}

public String getShipmentcost() {
	return shipmentcost;
}

public void setShipmentcost(String shipmentcost) {
	this.shipmentcost = shipmentcost;
}


}
