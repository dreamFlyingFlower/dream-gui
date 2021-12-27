package com.wy.model;

public class EnemyTable {
	public int time;

	public float x;

	public float y;

	public int enemyKind;

	public int pattern;

	public EnemyTable(int enemyKind, int time, float x, float y, int pattern) {
		this.enemyKind = enemyKind;
		this.time = time;
		this.x = x;
		this.y = y;
		this.pattern = pattern;
	}
}