package br.com.metronic.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import br.com.metronic.models.Role;
import br.com.metronic.models.User;

@Repository
public class UserDAO implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String jpql = "select u from User u where u.login = :login";
		List<User> users = manager.createQuery(jpql, User.class).setParameter("login", username).getResultList();
		if (users.isEmpty()) {
			throw new UsernameNotFoundException("O usuario " + username + " não existe");
		}
		return users.get(0);
	}
	
	public void saveUser(User user) {
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		manager.persist(user);
	}
	
	public void saveRole(Role role) {
		manager.persist(role);
	}
	
	public List<User> listUsers(){
		return manager
				.createQuery("select distinct(u) from User u", User.class)
				.getResultList();
	}
	
	public List<Role> listRoles(){
		return manager
				.createQuery("select distinct(r) from Role r", Role.class)
				.getResultList();
	}

}
