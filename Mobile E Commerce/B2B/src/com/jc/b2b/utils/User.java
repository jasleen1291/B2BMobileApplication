package com.jc.b2b.utils;

public class User {
String status,zipcode,state,usertype,phone2,LastName,password,phone1,country,city,masterid,username,email,address,firstname;

public String getStatus() {
	return status;
}

public String getZipcode() {
	return zipcode;
}

public String getState() {
	return state;
}

public String getUsertype() {
	return usertype;
}

public String getPhone2() {
	return phone2;
}

public String getLastName() {
	return LastName;
}

public String getPassword() {
	return password;
}

public String getPhone1() {
	return phone1;
}

public String getCountry() {
	return country;
}

public String getCity() {
	return city;
}

public String getMasterid() {
	return masterid;
}

public String getUsername() {
	return username;
}

public String getEmail() {
	return email;
}

public String getAddress() {
	return address;
}

public String getFirstname() {
	return firstname;
}

public User(String status, String zipcode, String state, String usertype,
		String phone2, String lastName, String password, String phone1,
		String country, String city, String masterid, String username,
		String email, String address, String firstname) {
	super();
	this.status = status;
	this.zipcode = zipcode;
	this.state = state;
	this.usertype = usertype;
	this.phone2 = phone2;
	LastName = lastName;
	this.password = password;
	this.phone1 = phone1;
	this.country = country;
	this.city = city;
	this.masterid = masterid;
	this.username = username;
	this.email = email;
	this.address = address;
	this.firstname = firstname;
}

}
