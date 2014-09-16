package com.jc.b2b.utils;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Warehouse implements Serializable{
String idWarehouse,warehousename,address,city,state,country,zip,ownerid;

public Warehouse(String idWarehouse, String warehousename, String address,
		String city, String state, String country, String zip, String ownerid) {
	super();
	this.idWarehouse = idWarehouse;
	this.warehousename = warehousename;
	this.address = address;
	this.city = city;
	this.state = state;
	this.country = country;
	this.zip = zip;
	this.ownerid = ownerid;
}

public String getIdWarehouse() {
	return idWarehouse;
}

public void setIdWarehouse(String idWarehouse) {
	this.idWarehouse = idWarehouse;
}

public String getWarehousename() {
	return warehousename;
}

public void setWarehousename(String warehousename) {
	this.warehousename = warehousename;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getZip() {
	return zip;
}

public void setZip(String zip) {
	this.zip = zip;
}

public String getOwnerid() {
	return ownerid;
}

public void setOwnerid(String ownerid) {
	this.ownerid = ownerid;
}


}
