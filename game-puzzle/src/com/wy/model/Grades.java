package com.wy.model;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JOptionPane;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Grades {
	private Puzzle puzzle;

	private Data[] data = { new Data(), new Data(), new Data() };

	public Grades(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	private void creatData() {
		try {
			File file = new File(Arg.rc);
			if (file.exists())
				return;
			file.createNewFile();

			PrintStream fout = new PrintStream(Arg.rc);
			fout.printf("%s %d %d\n", "name", 999999, 999999);
			fout.printf("%s %d %d\n", "name", 999999, 999999);
			fout.printf("%s %d %d\n", "name", 999999, 999999);
			fout.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Arg.rc + " has broken!");
			return;
		}
	}

	private void readData() {
		try {
			File file = new File(Arg.rc);
			@SuppressWarnings("resource")
			Scanner cin = new Scanner(file);

			data[0].setName(cin.next());
			data[0].setTime(cin.nextInt());
			data[0].setStep(cin.nextInt());

			data[1].setName(cin.next());
			data[1].setTime(cin.nextInt());
			data[1].setStep(cin.nextInt());

			data[2].setName(cin.next());
			data[2].setTime(cin.nextInt());
			data[2].setStep(cin.nextInt());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Arg.rc + " can't read!");
		}
	}

	public void set(int t, int s) {
		int type = puzzle.getLevel();
		creatData();
		readData();
		if (t > data[type].getTime() || (t == data[type].getTime() && s >= data[type].getStep()))
			return;
		data[type].setName(JOptionPane.showInputDialog(null, "You broke the record!\nPlease input your name."));
		data[type].setTime(t);
		data[type].setStep(s);

		try {
			PrintStream f = new PrintStream(Arg.rc);
			f.printf("%s %d %d\n", data[0].getName(), data[0].getTime(), data[0].getStep());
			f.printf("%s %d %d\n", data[1].getName(), data[1].getTime(), data[1].getStep());
			f.printf("%s %d %d\n", data[2].getName(), data[2].getTime(), data[2].getStep());
			f.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Arg.rc + " has broken!");
		}

		show();
	}

	public void show() {
		try {
			creatData();
			readData();

			String title = String.format("%8s%15s%8s%8s\n", "Level", "Name", "Time", "Step");
			String h = String.format("%8s%15s%8d%8d\n", "Hard", data[0].getName(), data[0].getTime(),
					data[0].getStep());
			String n = String.format("%8s%15s%8d%8d\n", "Normal", data[1].getName(), data[1].getTime(),
					data[1].getStep());
			String e = String.format("%8s%15s%8d%8d\n", "Easy", data[2].getName(), data[2].getTime(),
					data[2].getStep());

			JOptionPane.showMessageDialog(null, title + h + n + e);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Arg.rc + " has broken!");
		}
	}
}

@Getter
@Setter
class Data {
	private String name;

	private int time;

	private int step;
}