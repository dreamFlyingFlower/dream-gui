package com.wy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrayPoint {
	// 行
	protected int i;

	// 列
	protected int j;

	// 值
	protected int value;

	public ArrayPoint(int i, int j, int value) {
		this.i = i;
		this.j = j;
		this.value = value;
	}

	public String toString() {
		return i + "," + j + "," + value;
	}
}