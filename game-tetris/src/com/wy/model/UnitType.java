package com.wy.model;

import java.awt.Color;

import lombok.Getter;
import lombok.Setter;

/**
 * @apiNote 可以表示地面里面的障碍物, 不可消除的障碍物, 空白. 外部不可以直接new 一个实例, 而应该用clone()方法
 *          产生实例(防止产生不能识别的类型),如UnitType.OBSTACLE.clone()
 * @author ParadiseWY
 * @date 2019年10月1日 下午4:17:04
 */
@Getter
@Setter
public class UnitType implements Cloneable {

	/**
	 * 空白类型的值
	 */
	private static final int BLANK_VALUE = 0;

	/**
	 * 边框类型的值
	 */
	private static final int STUBBORN_OBSTACLE_VALUE = 1;

	/**
	 * 障碍物类型的值
	 */
	private static final int OBSTACLE_VALUE = 2;

	/**
	 * 空白类型
	 */
	public static final UnitType BLANK = new UnitType(BLANK_VALUE, Color.WHITE);

	/**
	 * 边框类类型
	 */
	public static final UnitType STUBBORN_OBSTACLE = new UnitType(STUBBORN_OBSTACLE_VALUE, new Color(0x808000));

	/**
	 * 障碍物类型
	 */
	public static final UnitType OBSTACLE = new UnitType(OBSTACLE_VALUE, Color.DARK_GRAY);

	/**
	 * 类型值
	 */
	private int value;

	/**
	 * 颜色
	 */
	private Color color;

	private UnitType(int value) {
		super();
		this.value = value;
	}

	private UnitType(int value, Color color) {
		super();
		this.value = value;
		this.color = color;
	}

	/**
	 * 返回一个新的,和自己有一样的颜色并且类型相同的UnitType
	 */
	@Override
	public UnitType clone() {
		return new UnitType(this.value, this.color);
	}

	/**
	 * 把自己克隆成和指定的UnitType 相同的 UnitType, value 和 Color 都会被克隆
	 * @param ut
	 */
	public void cloneProperties(UnitType ut) {
		this.color = ut.color;
		this.value = ut.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	/**
	 * 相同的类型就是相等, 不比较颜色
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UnitType other = (UnitType) obj;
		if (value != other.value)
			return false;
		return true;
	}
}