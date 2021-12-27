package com.wy.view;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Version extends JDialog {
	private static final long serialVersionUID = 1L;

	JLabel jl1 = new JLabel("自己玩");

	JLabel jl2 = new JLabel("paradiseWy");

	JLabel jl3 = new JLabel("俄罗斯方块1.0");

	JPanel jp = new JPanel();

	// JTextField jt=new JTextField("111");
	public Version(JFrame j, String s, boolean b) {
		super(j, s, b);
		// this.setLayout(null);
		// this.setLayout();
		this.setBounds(400, 120, 200, 200);
		this.setVisible(true);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		jp.setLayout(new GridLayout(3, 1));
		// jt.setBounds(50, 50, 30, 30);
		// jl.setBounds(50, 50, 30, 30);
		// jp.setBounds(0, 0, 200, 300);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jl3);
		this.add(jp);
	}
}