package com.jc.b2b.utils;

import android.support.v4.app.Fragment;

public class ChildItem {
public ChildItem(Fragment a, String b) {
		super();
		this.a = a;
		this.b = b;
	}
Fragment a;
String b;
public Fragment getA() {
	return a;
}
public String getB() {
	return b;
}
}
