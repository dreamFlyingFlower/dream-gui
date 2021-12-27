package com.wy.server;

import java.io.Serializable;
import java.util.Vector;

/**
 * @apiNote
 * @author ParadiseWY
 * @date 2019年10月12日
 */
public class Message implements Serializable {
	private static final long serialVersionUID = -3831507106408529855L;

	/**
	 * 用户在线对象集
	 */
	public Vector<Customer> userOnLine;

	/**
	 * 聊天信息集
	 */
	public Vector<Chat> chat;

	/**
	 * 公告
	 */
	public String serverMessage;
}