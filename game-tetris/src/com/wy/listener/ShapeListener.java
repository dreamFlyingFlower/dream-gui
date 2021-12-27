package com.wy.listener;

import com.wy.model.Shape;

/**
 * 图形监听器
 * @author ParadiseWY
 * @date 2019年10月1日 下午4:08:52
 */
public interface ShapeListener {

	/**
	 * 图形询问是否可以下落
	 * @param shape 图形
	 * @return true是
	 */
	boolean isShapeMoveDownable(Shape shape);

	/**
	 * 图形下落事件
	 * @param shape 图形
	 */
	void shapeMovedDown(Shape shape);
}
