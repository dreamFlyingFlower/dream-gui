package com.wy.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wy.config.Props;
import com.wy.controller.Controller;
import com.wy.listener.GameListener;
import com.wy.model.Food;
import com.wy.model.Ground;
import com.wy.model.Snake;
import com.wy.view.GamePanel;

import lombok.Getter;
import lombok.Setter;

/**
 * 主界面
 * @author ParadiseWY
 * @date 2019年10月2日 下午9:30:42
 */
@Getter
@Setter
public class MainFrame extends JFrame implements GameListener {

	private static final long serialVersionUID = 1L;

	private final GameOptionPanel optionPanel;

	private final GamePanel gamePanel;

	private final Snake snake;

	private final Ground ground;

	private final Food food;

	private final JLabel infoLabel;

	private final Controller controller;

	public MainFrame(Controller c) {
		super();
		this.controller = c;

		this.setTitle("贪吃蛇");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);

		int left = 10;
		optionPanel = new GameOptionPanel();

		gamePanel = c.getGamePanel();
		snake = c.getSnake();
		ground = c.getGround();
		food = c.getFood();
		infoLabel = c.getGameInfoLabel() == null ? new JLabel() : c.getGameInfoLabel();
		c.setGameInfoLabel(infoLabel);

		optionPanel.getButton_griddingColor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color griddingColor = JColorChooser.showDialog(MainFrame.this, "请选择网格颜色", Color.LIGHT_GRAY);
				if (griddingColor != null)
					ground.setGriddingColor(griddingColor);
			}
		});
		optionPanel.getButton_backgroundColor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color backgroundColor = JColorChooser.showDialog(MainFrame.this, "请选择背景颜色", new Color(0xcfcfcf));
				if (backgroundColor != null)
					gamePanel.setBackgroundColor(backgroundColor);
			}
		});
		optionPanel.getButton_foodColor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color foodColor = JColorChooser.showDialog(MainFrame.this, "请选择食物颜色", Color.DARK_GRAY);
				if (foodColor != null)
					food.setColor(foodColor);
			}
		});
		optionPanel.getButton_headColor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color headColor = JColorChooser.showDialog(MainFrame.this, "请选择蛇头颜色", new Color(0xFF4500));
				if (headColor != null)
					snake.setHeadColor(headColor);
			}
		});
		optionPanel.getButton_bodyColor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color bodyColor = JColorChooser.showDialog(MainFrame.this, "请选择蛇身体颜色", Color.DARK_GRAY);
				if (bodyColor != null)
					snake.setBodyColor(bodyColor);
			}
		});

		this.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent arg0) {
				controller.pauseGame();
				if (optionPanel.getPauseButton().isEnabled())
					optionPanel.getPauseButton().setText("继续游戏");
			}
		});
		gamePanel.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
				// controller.continueGame();
			}

			public void focusLost(FocusEvent arg0) {
				controller.pauseGame();
				if (optionPanel.getPauseButton().isEnabled())
					optionPanel.getPauseButton().setText("继续游戏");
			}
		});

		optionPanel.getRadioButton_map2().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				controller.setMap(optionPanel.getRadioButton_map2().isSelected() ? 2 : 1);
			}
		});

		optionPanel.getNewGameButton().addActionListener(new ActionListener() {
			// 开始游戏按钮
			public void actionPerformed(ActionEvent e) {
				if (controller.isPlaying()) {
					return;
				}
				controller.newGame();
			}
		});
		optionPanel.getStopGameButton().addActionListener(new ActionListener() {
			// 停止游戏按钮
			public void actionPerformed(ActionEvent e) {

				controller.stopGame();
			}
		});
		optionPanel.getPauseButton().setEnabled(false);
		optionPanel.getStopGameButton().setEnabled(false);

		optionPanel.getPauseButton().addActionListener(new ActionListener() {
			// 暂停/继续游戏的按钮
			public void actionPerformed(ActionEvent e) {
				if (controller.isPausingGame()) {
					controller.continueGame();

				} else {
					controller.pauseGame();
				}
				if (controller.isPausingGame())
					optionPanel.getPauseButton().setText("继续游戏");
				else
					optionPanel.getPauseButton().setText("暂停游戏");
			}
		});
		optionPanel.getCheckBox_drawGridding().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				optionPanel.getButton_griddingColor().setVisible(optionPanel.getCheckBox_drawGridding().isSelected());
				ground.setDrawGridding(optionPanel.getCheckBox_drawGridding().isSelected());
			}
		});

		optionPanel.getButton_default().addActionListener(new ActionListener() {
			// 恢复默认设置按钮
			public void actionPerformed(ActionEvent e) {

				gamePanel.setBackgroundColor(GamePanel.DEFAULT_BACKGROUND_COLOR);
				optionPanel.getCheckBox_drawGridding().setSelected(false);
				ground.setGriddingColor(Ground.DEFAULT_GRIDDING_COLOR);
				snake.setHeadColor(Snake.DEFAULT_HEAD_COLOR);
				snake.setBodyColor(Snake.DEFAULT_BODY_COLOR);
				optionPanel.getRadioButton_map1().setSelected(true);

			}
		});

		infoLabel.setBounds(10, 0, infoLabel.getSize().width - 10, infoLabel.getSize().height);
		gamePanel.setBounds(0, infoLabel.getSize().height, gamePanel.getSize().width, gamePanel.getSize().height);

		JPanel subPanel = new JPanel();
		subPanel.setLayout(null);
		subPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		subPanel.setFocusable(false);

		subPanel.setSize(gamePanel.getSize().width + 1, infoLabel.getSize().height + gamePanel.getSize().height + 1);
		subPanel.setBounds(left, 5, subPanel.getSize().width, subPanel.getSize().height);

		subPanel.add(infoLabel);
		subPanel.add(gamePanel);

		optionPanel.setBounds(left, subPanel.getSize().height + 10, optionPanel.getSize().width,
				optionPanel.getSize().height);

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		infoPanel.setLayout(null);
		infoPanel.setBounds(left + optionPanel.getSize().width + 5, subPanel.getSize().height + 10,
				gamePanel.getSize().width - optionPanel.getSize().width - 5 + 1, optionPanel.getSize().height);

		final JLabel infoTitleLable = new JLabel();
		infoTitleLable.setFont(new Font("宋体", Font.PLAIN, 12));
		infoTitleLable.setText(Props.TITLE_LABEL_TEXT);
		infoTitleLable.setBounds(10, 5, infoPanel.getSize().width - 10, 20);

		final JTextArea infoTextArea = new JTextArea();
		infoTextArea.setFont(new Font("宋体", Font.PLAIN, 12));
		infoTextArea.setText(Props.INFO_LABEL_TEXT);
		infoTextArea.setFocusable(false);
		infoTextArea.setBackground(this.getBackground());
		infoTextArea.setBounds(10, 25, infoPanel.getSize().width - 20, infoPanel.getSize().height - 50);

		infoPanel.add(infoTitleLable);
		infoPanel.add(infoTextArea);

		optionPanel.getCheckBox_drawGridding().setFocusable(false);
		optionPanel.getRadioButton_map1().setFocusable(false);
		optionPanel.getRadioButton_map2().setFocusable(false);

		this.setSize(
				subPanel.getSize().width > optionPanel.getSize().width ? gamePanel.getSize().width + 2 * left + 8
						: optionPanel.getSize().width + 2 * left + 8,
				subPanel.getSize().height + 20 + optionPanel.getSize().height + 30);
		// 窗口居中
		this.setLocation(this.getToolkit().getScreenSize().width / 2 - this.getWidth() / 2,
				this.getToolkit().getScreenSize().height / 2 - this.getHeight() / 2);
		// 添加监听器
		gamePanel.addKeyListener(controller);
		this.addKeyListener(controller);
		controller.addGameListener(this);

		this.getContentPane().add(subPanel);
		this.getContentPane().add(optionPanel);
		this.getContentPane().add(infoPanel);
	}

	public void gameOver() {
		optionPanel.getPauseButton().setEnabled(false);
		optionPanel.getStopGameButton().setEnabled(false);
		optionPanel.getNewGameButton().setEnabled(true);
		optionPanel.getPauseButton().setText("暂停/继续");
	}

	public void gameStart() {
		optionPanel.getPauseButton().setEnabled(true);
		optionPanel.getNewGameButton().setEnabled(false);
		optionPanel.getStopGameButton().setEnabled(true);
	}

	public void gameContinue() {
		optionPanel.getPauseButton().setText("暂停游戏");
	}

	public void gamePause() {
		optionPanel.getPauseButton().setText("继续游戏");
	}
}