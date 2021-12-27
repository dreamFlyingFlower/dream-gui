package com.wy.server;

import java.io.Serializable;

/**
 * @apiNote
 * @author ParadiseWY
 * @date 2019年10月12日
 */
public class Chat implements Serializable {
	private static final long serialVersionUID = 4058485121419391969L;

	/**
	 * 发言人用户名
	 */
	public String chatUser;

	/**
	 * 聊天内容
	 */
	public String chatMessage;

	/**
	 * 接受对象用户名
	 */
	public String chatToUser;

	/**
	 * 是否私聊
	 */
	public boolean whisper;
}