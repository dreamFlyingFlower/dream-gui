package com.wy.screenshot;

import javax.swing.JButton;

public class CommandButton extends JButton {

	private static final long serialVersionUID = 1L;

	protected enum ButtonStyle {
		selectImage("选择图片"), selectSavepath("选择存放路径"), preview("预览效果"), batching("批量添加"), moreSetting("更多设置"),
		drirect("添加文字");
		private String name;

		ButtonStyle(String name) {
			this.name = name;
		}

		public String getButtonName() {
			return name;
		}
	}

	public CommandButton(ButtonStyle style) {
		super(style.getButtonName());
		addActionListener(new ButtonAction(style));
	}
}