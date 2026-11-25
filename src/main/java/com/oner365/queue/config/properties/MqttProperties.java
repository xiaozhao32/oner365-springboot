package com.oner365.queue.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MQTT properties
 *
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 地址
	 */
	private String host;

	/**
	 * 端口
	 */
	private int port;

	/**
	 * client
	 */
	private String clientId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
