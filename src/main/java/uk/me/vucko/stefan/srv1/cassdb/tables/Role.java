package uk.me.vucko.stefan.srv1.cassdb.tables;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Role {

	@PrimaryKey
	private UUID roleId;

	private String description;
	private List<String> permissions;

	@Override
	public String toString() {
		return "Role=[" + this.roleId.toString() + ", description='" + description + "', permissions=" + permissions.toString() + "]";
	}

	public Role(UUID roleId, String description, List<String> permissions) {
		super();
		this.roleId = roleId;
		this.permissions = permissions;
		this.description = description;
	}

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
