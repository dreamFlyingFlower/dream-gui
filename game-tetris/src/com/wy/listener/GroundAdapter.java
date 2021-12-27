package com.wy.listener;

import com.wy.model.Ground;

/**
 * Ground适配器
 * @author ParadiseWY
 * @date 2019年10月1日 下午4:06:46
 */
public class GroundAdapter implements GroundListener {

	public void beforeDeleteFullLine(Ground ground, int lineNum) {
	}

	public void fullLineDeleted(Ground ground, int deletedLineCount) {
	}

	public void groundIsFull(Ground ground) {
	}
}