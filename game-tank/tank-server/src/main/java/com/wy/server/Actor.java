package com.wy.server;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Actor {
	public void draw(Graphics g);

	public void move();

	public String getType();

	public Rectangle getBorder();

	public Rectangle[] getDetailedBorder();

	public boolean walldestoried();
}