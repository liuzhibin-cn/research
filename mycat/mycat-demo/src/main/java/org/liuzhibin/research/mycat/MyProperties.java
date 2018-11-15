package org.liuzhibin.research.mycat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="my")
public class MyProperties {
	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String value) {
		this.dbUrl = value;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String value) {
		this.dbUser = value;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String value) {
		this.dbPassword = value;
	}
}