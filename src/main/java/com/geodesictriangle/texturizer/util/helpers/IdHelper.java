package com.geodesictriangle.texturizer.util.helpers;

public class IdHelper {
	int id;
	public IdHelper() {
		this.id = 0;
	}
	public int getnextid() {
		this.id += 1;
		return id;
	}
	public void setcurrentid(int id) {
		this.id = id;
	}
	public int getcurrentid() {
		return this.id;
	}
}
