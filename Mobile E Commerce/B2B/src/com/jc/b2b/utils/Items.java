package com.jc.b2b.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class Items implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String categoryid="", status="", imagepath="", itemname="", unitofmeasurement="",
			qtyperunit="", owner="", longdescription="", iditem="", sp="", unitcost="",
			min_order="", item_shopid="", discount="", qtyonorder="", qtyonhand="";
	ArrayList<String> qty, discounts, ids;

	public Items(String categoryid, String status, String imagepath,
			String itemname, String unitofmeasurement, String qtyperunit,
			String owner, String longdescription, String iditem) {
		super();
		this.categoryid = categoryid;
		this.status = status;
		this.imagepath = imagepath;
		this.itemname = itemname;
		this.unitofmeasurement = unitofmeasurement;
		this.qtyperunit = qtyperunit;
		this.owner = owner;
		this.longdescription = longdescription;
		this.iditem = iditem;
	}

	public void setQty(ArrayList<String> qty) {
		this.qty = qty;
	}

	public void setDiscounts(ArrayList<String> discounts) {
		this.discounts = discounts;
	}

	public Items(String categoryid, String status, String imagepath,
			String itemname, String unitofmeasurement, String qtyperunit,
			String owner, String longdescription, String iditem, String sp,
			String unitcost, String min_order, String item_shopid,
			String discount, String qtyonorder, ArrayList<String> qty,
			ArrayList<String> discounts, ArrayList<String> ids, String qtyonhand) {
		super();
		this.categoryid = categoryid;
		this.status = status;
		this.imagepath = imagepath;
		this.itemname = itemname;
		this.unitofmeasurement = unitofmeasurement;
		this.qtyperunit = qtyperunit;
		this.owner = owner;
		this.longdescription = longdescription;
		this.iditem = iditem;
		this.sp = sp;
		this.unitcost = unitcost;
		this.min_order = min_order;
		this.item_shopid = item_shopid;
		this.discount = discount;
		this.qtyonorder = qtyonorder;
		this.qty = qty;
		this.discounts = discounts;
		this.ids = ids;
		this.qtyonhand = qtyonhand;
	}

	public ArrayList<String> getQty() {
		return qty;
	}

	public ArrayList<String> getDiscounts() {
		return discounts;
	}

	public String getSp() {
		return sp;
	}

	public String getUnitcost() {
		return unitcost;
	}

	public String getMin_order() {
		return min_order;
	}

	public String getItem_shopid() {
		return item_shopid;
	}

	public String getDiscount() {
		return discount;
	}

	public String getQtyonorder() {
		return qtyonorder;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public String getStatus() {
		return status;
	}

	public String getImagepath() {
		return imagepath;
	}

	public String getItemname() {
		return itemname;
	}

	public String getUnitofmeasurement() {
		return unitofmeasurement;
	}

	public String getQtyperunit() {
		return qtyperunit;
	}

	public String getOwner() {
		return owner;
	}

	public String getLongdescription() {
		return longdescription;
	}

	public String getIditem() {
		return iditem;
	}

}
