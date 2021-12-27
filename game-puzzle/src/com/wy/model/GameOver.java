package com.wy.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameOver {
	private boolean gameOver;

	private boolean stop;

	private int[][] matrix;

	private Puzzle puzzle;

	private int step;

	private long now;

	private long countTime;

	public GameOver(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public void start(int[][] m) {
		gameOver = false;
		matrix = m;
		step = 0;
		Date d = new Date();
		now = d.getTime();
		stop = false;
		countTime = 0;
	}

	public boolean judge() {
		if (gameOver) {
			return true;
		}
		step++;
		int c = 1;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != (c++))
					return false;
			}
		}
		Date d = new Date();
		countTime += d.getTime() - now;
		puzzle.endGame();
		return (gameOver = true);
	}

	public void pause() {
		Date d = new Date();
		if (stop) {
			now = d.getTime();
		}else {
			countTime += d.getTime() - now;
		}
		stop = !stop;
	}

	public long getTime() {
		return countTime / 1000;
	}
}