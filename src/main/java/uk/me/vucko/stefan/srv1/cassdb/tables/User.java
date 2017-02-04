package uk.me.vucko.stefan.srv1.cassdb.tables;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class User {

	@PrimaryKey
	private String userId;

	private String displayName;
	private String passwordHash;
	private UUID roleId;
	private boolean blocked;
	private Date tsLogin;

	@Override
	public String toString() {
		return "User=[" + this.userId.toString() + ", displayName=" + displayName + "]";
	}

	public User(String userId, String displayName, String passwordHash, UUID roleId, boolean blocked) {
		super();
		this.userId = userId;
		this.displayName = displayName;
		this.passwordHash = passwordHash;
		this.roleId = roleId;
		this.blocked = blocked;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Date getTsLogin() {
		return tsLogin;
	}

	public void setTsLogin(Date tsLogin) {
		this.tsLogin = tsLogin;
	}
}
