package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleResponse extends BaseOutput {

	public List<Role> roles;
	public Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getRoles() {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "RoleResponse [roles=" + roles + ", role=" + role + "]";
	}
}
