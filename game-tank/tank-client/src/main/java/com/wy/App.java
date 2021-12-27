package com.wy;

import java.awt.EventQueue;

import com.wy.client.StartFrame;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new StartFrame();
		});
	}
}