package com.wy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Point {
	private int row;

	private int cal;

	public boolean neighbor(int r, int c) {
		int n = Math.abs(row - r) + Math.abs(cal - c);
		return n == 1;
	}
}