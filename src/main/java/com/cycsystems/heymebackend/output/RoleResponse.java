package com.cycsystems.heymebackend.output;

import java.util.ArrayList;
import java.util.List;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Role;

public class RoleResponse extends BaseOutput {

	public List<Role> roles;

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
		return "RoleResponse [roles=" + roles + ", toString()=" + super.toString() + "]";
	}
}
