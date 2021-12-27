package com.wy.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class CardFrame extends MenuFrame {
	private static final long serialVersionUID = 1L;

	private CardLayout card;

	private Container con;

	protected JPanel[] panel = { new JPanel(), new JPanel() };

	public CardFrame() {
		con = this.getContentPane();
		card = new CardLayout();
		con.setLayout(card);

		panel[0].setLayout(null);
		JLabel label;
		label = new JLabel("Please put you image to the");
		label.setBounds(10, 15, 190, 30);
		panel[0].add(label);
		label = new JLabel("\"background\"directory.\n");
		label.setBounds(10, 30, 190, 45);
		panel[0].add(label);
		label = new JLabel("Click \"Option->Background\"");
		label.setBounds(10, 50, 190, 65);
		panel[0].add(label);
		label = new JLabel("to select the picture.\n");
		label.setBounds(10, 65, 190, 80);
		panel[0].add(label);
		label = new JLabel("Last, click \"Begin->New\" to");
		label.setBounds(10, 85, 190, 100);
		panel[0].add(label);
		label = new JLabel("start the game.");
		label.setBounds(10, 100, 190, 115);
		panel[0].add(label);

		panel[1].setLayout(new BorderLayout());
		JButton button = new JButton("Continue");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameGetFocus();
			}
		});
		panel[1].add(button, BorderLayout.CENTER);

		con.add(panel[0], "Game");
		con.add(panel[1], "Pause");
		card.show(con, "Game");

		this.addWindowListener(new WindowAdapter() {
			public void windowDeactivated(WindowEvent e) {
				FrameLostFocus();
			}
		});
	}

	protected void nextCard() {
		card.next(con);
	}

	protected abstract void FrameLostFocus();

	protected abstract void FrameGetFocus();
}
