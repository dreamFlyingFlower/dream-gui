package com.wy.server;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Base implements Actor {
	private Rectangle border;

	public Image base;

	public int xPos, yPos;

	public ServerModel gameModel;

	public int steelWallTime;

	public boolean baseKilled;

	public Base(ServerModel gameModel) {
		this.gameModel = gameModel;
		xPos = 260;
		yPos = 498;
		base = gameModel.textures[0];
		border = new Rectangle(xPos - 11, yPos - 11, 23, 23);

	}

	public Rectangle getBorder() {
		return border;
	}

	public void doom() {
		base = gameModel.textures[1];
		if (!baseKilled)
			gameModel.addActor(new Bomb(xPos, yPos, "big", gameModel));
		baseKilled = true;

		// 记录变化到输出行
		gameModel.outputLine += "b" + xPos + "," + yPos + "," + "1;";

	}

	public void move() {
		if (steelWallTime == 600) {
			Steelwall temp = new Steelwall(248, 498, 2, gameModel);
			gameModel.actors[0] = temp;
			writeToOutputLine("s", temp.shape, 248, 498);

			temp = new Steelwall(273, 498, 3, gameModel);
			gameModel.actors[1] = temp;
			writeToOutputLine("s", temp.shape, 273, 498);

			temp = new Steelwall(248, 473, 1, gameModel);
			gameModel.actors[2] = temp;
			writeToOutputLine("s", temp.shape, 248, 473);

			temp = new Steelwall(273, 473, 1, gameModel);
			gameModel.actors[3] = temp;
			writeToOutputLine("s", temp.shape, 273, 473);
		}
		if (steelWallTime > 0)
			steelWallTime--;
		if (steelWallTime == 1) {
			Wall temp = new Wall(248, 498, 2, gameModel);
			gameModel.actors[0] = temp;
			writeToOutputLine("w", temp.shape, 248, 498);

			temp = new Wall(273, 498, 3, gameModel);
			gameModel.actors[1] = temp;
			writeToOutputLine("w", temp.shape, 273, 498);

			temp = new Wall(248, 473, 1, gameModel);
			gameModel.actors[2] = temp;
			writeToOutputLine("w", temp.shape, 248, 473);

			temp = new Wall(273, 473, 1, gameModel);
			gameModel.actors[3] = temp;
			writeToOutputLine("w", temp.shape, 273, 473);
		}

	}

	public void writeToOutputLine(String type, boolean[] shape, int xPos, int yPos) {
		// 记录变化到输出行
		gameModel.outputLine += type + xPos + "," + yPos + ",";
		for (int i = 0; i < shape.length; i++) {
			if (shape[i])
				gameModel.outputLine += "1";
			else
				gameModel.outputLine += "0";
		}
		gameModel.outputLine += ";";
	}

	public String getType() {
		return "base";
	}

	public void draw(Graphics g) {
		g.drawImage(base, xPos - 12, yPos - 12, null);
	}

	// 未使用的方法
	public Rectangle[] getDetailedBorder() {
		return null;
	}

	public boolean walldestoried() {
		return false;
	}
}