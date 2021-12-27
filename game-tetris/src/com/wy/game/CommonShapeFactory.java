package com.wy.game;

import java.awt.Color;

import com.wy.config.Props;
import com.wy.listener.ShapeListener;
import com.wy.model.Shape;
import com.wy.model.ShapeFactory;

/**
 * 为了扩展一些功能,可用配置文件配置这个工厂用的颜色
 * @author ParadiseWY
 * @date 2019年10月1日 下午3:00:56
 */
public class CommonShapeFactory extends ShapeFactory {

	@Override
	public Shape getShape(ShapeListener shapeListener) {
		int type = random.nextInt(shapes.length);
		int status = random.nextInt(shapes[type].length);
		Shape shape = new Shape(shapes[type], status);
		shape.setColor(isColorfulShape() ? getColorByType(type) : getDefaultShapeColor());
		shape.addShapeListener(shapeListener);
		return shape;
	}

	private Color getColorByType(int type) {
		if (type < 0 || type >= Props.COMMON_COLORS.size())
			return getDefaultShapeColor();
		return Props.COMMON_COLORS.get(type);
	}
}