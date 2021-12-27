package com.wy.game;

import com.wy.config.Props;
import com.wy.listener.GroundAdapter;
import com.wy.model.Ground;

/**
 * 自己用的一个GroundListener,处理不可消的障碍物,增加趣味性
 * @author ParadiseWY
 * @date 2019年10月1日 下午3:01:28
 */
public class MyGroundListener extends GroundAdapter {

	int deletedLineCount = 0;

	@Override
	public void fullLineDeleted(Ground ground, int deletedLineCount) {
		this.deletedLineCount += deletedLineCount;
		if ((deletedLineCount %= 10) == 9 || deletedLineCount > 2)
			for (int y = 0; y < Props.HEIGHT; y++)
				for (int x = 0; x < Props.WIDTH; x++)
					if (ground.isStubbornObstacle(x, y))
						ground.addObstacle(x, y);
	}
}