package com.wy.model;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Key extends KeyAdapter {

	public static boolean left;

	public static boolean right;

	public static boolean up;

	public static boolean down;

	public static boolean zkey;

	public static boolean xkey;

	public static boolean escape;

	public static boolean enter;

	public static boolean space;

	public Key() {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case 37:
				left = true;
				break;

			case 39:
				right = true;
				break;

			case 38:
				up = true;
				break;

			case 40:
				down = true;
				break;

			case 90:
				zkey = true;
				break;

			case 88:
				xkey = true;
				break;

			case 27:
				escape = true;
				break;

			case 10:
				enter = true;
				break;
			case 32:
				space = true;
				break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case 37:
				left = false;
				break;

			case 39:
				right = false;
				break;

			case 38:
				up = false;
				break;

			case 40:
				down = false;
				break;

			case 90:
				zkey = false;
				break;

			case 88:
				xkey = false;
				break;

			case 10:
				enter = false;
				break;
			case 32:
				space = false;
				break;
		}
	}
}