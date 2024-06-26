package cn.manager.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

	/**
	 * 发件人邮箱
	 */
	private String senderMail;

	/**
	 * 发件邮箱主机
	 */
	private String host;

	/**
	 * 发件邮箱密钥授权码
	 */
	private String password;

	public MailProperties() {
	}

	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
