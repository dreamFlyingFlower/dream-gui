package com.wy.view;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.wy.config.Props;
import com.wy.model.ArrayPoint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChessButton extends JButton {
	private static final long serialVersionUID = 1L;

	/**
	 * 按钮所对应的数组中的值和位置,用ArrayPoint结构来表示
	 */
	protected ArrayPoint point = null;

	/**
	 * 构造函数,指定按钮所代表的值和位置
	 * @param row 所在行数
	 * @param col 所在列数
	 * @param value 代表的值
	 */
	public ChessButton(int row, int col, int value) {
		this(new ArrayPoint(row, col, value));
	}

	public ChessButton(ArrayPoint point) {
		this.point = point;
		String name = "Resource/" + point.getValue() + Props.RELEX;
		URL url = ChessButton.class.getResource(name);
		// System.out.println(url);
		ImageIcon icon = new ImageIcon(url);
		this.setIcon(icon);
	}

	public ChessButton() {
		this(new ArrayPoint(0, 0, 0));
	}
}