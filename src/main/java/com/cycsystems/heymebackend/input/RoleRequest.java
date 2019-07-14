package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Role;

public class RoleRequest extends BaseInput{

	public Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "RoleRequest [role=" + role + ", toString()=" + super.toString() + "]";
	}
}
