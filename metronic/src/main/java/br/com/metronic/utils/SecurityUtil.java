package br.com.metronic.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.metronic.models.Role;
import br.com.metronic.models.User;

@Component
public class SecurityUtil {

	public boolean userHasAdminRole(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.getRoles().contains(new Role("ROLE_ADMIN"))) { return true; }
		return false;
	}

}
