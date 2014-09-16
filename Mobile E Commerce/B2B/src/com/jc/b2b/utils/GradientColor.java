package com.jc.b2b.utils;

public class GradientColor {
	
	private String Name;
	private int ColorA;
	private int ColorB;
	private int ColorC;
	private int ColorD;
	private int ColorE;
	private int TextColor;
	
	public GradientColor(String name, int colorA, int colorB, int colorC, int colorD, int colorE, int textColor){
		Name = name;
		ColorA = colorA;
		ColorB = colorB;
		ColorC = colorC;
		ColorD = colorD;
		ColorE = colorE;
		setTextColor(textColor);
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getColorA() {
		return ColorA;
	}

	public void setColorA(int colorA) {
		ColorA = colorA;
	}

	public int getColorB() {
		return ColorB;
	}

	public void setColorB(int colorB) {
		ColorB = colorB;
	}

	public int getColorC() {
		return ColorC;
	}

	public void setColorC(int colorC) {
		ColorC = colorC;
	}

	public int getColorD() {
		return ColorD;
	}

	public void setColorD(int colorD) {
		ColorD = colorD;
	}

	public int getColorE() {
		return ColorE;
	}

	public void setColorE(int colorE) {
		ColorE = colorE;
	}

	public int getTextColor() {
		return TextColor;
	}

	public void setTextColor(int textColor) {
		TextColor = textColor;
	}

	
}
