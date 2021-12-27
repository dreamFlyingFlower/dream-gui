package com.wy.server;

import java.io.Serializable;

/**
 * @apiNote 用户信息
 * @author ParadiseWY
 * @date 2019年10月12日
 */
public class Customer implements Serializable {
	private static final long serialVersionUID = -9215977405584592618L;

	/**
	 * 用户名
	 */
	public String custName;

	/**
	 * 密码
	 */
	public String custPassword;

	/**
	 * 用户头像
	 */
	public String custHead;
}