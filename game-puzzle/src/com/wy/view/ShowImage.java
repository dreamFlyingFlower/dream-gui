package com.wy.view;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.wy.model.Arg;

public class ShowImage extends JFrame
{
	private static final long serialVersionUID = 1L;

	public ShowImage(String fn)
	{
		super("Picture");

		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		String path = Arg.path + "/" + fn;
		ImageIcon image = new ImageIcon(path);
		c.add(new JLabel(image));

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(image.getIconWidth()+10, image.getIconHeight()+30);
		setVisible(true);
	}
}