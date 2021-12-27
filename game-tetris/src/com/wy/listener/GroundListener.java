package com.wy.listener;

import com.wy.model.Ground;

/**
 * Ground监听器
 * @author ParadiseWY
 * @date 2019年10月1日 下午4:07:30
 */
public interface GroundListener {

	/**
	 * 将要消行事件
	 * @param ground
	 * @param lineNum 将要消除的行号
	 */
	void beforeDeleteFullLine(Ground ground, int lineNum);

	/**
	 * 消除满行事件
	 * @param ground
	 * @param deletedLineCount 本次消除的行数
	 */
	void fullLineDeleted(Ground ground, int deletedLineCount);

	void groundIsFull(Ground ground);
}