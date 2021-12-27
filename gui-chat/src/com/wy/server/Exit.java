package com.wy.server;

import java.io.Serializable;

/**
 * @apiNote 用户退出
 * @author ParadiseWY
 * @date 2019年10月12日
 */
public class Exit implements Serializable {
	private static final long serialVersionUID = -5267537916643834426L;

	/**
	 * 退出者用户名
	 */
	public String exitname;
}