package br.com.metronic.daos;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import br.com.metronic.models.Notification;
import br.com.metronic.models.NotificationType;

@Repository
public class NotificationDAO {

	@PersistenceContext
	private EntityManager manager;
	
	public void save(Notification notification) {
		manager.persist(notification);
	}
	
	public List<Notification> list(boolean userHasAdminRole){
		
		// Define Notification Type
		List<NotificationType> listTypes = null;
		if (userHasAdminRole) {
			listTypes = Arrays.asList(NotificationType.values());
		} else {
			listTypes = Lists.newArrayList();
			listTypes.add(NotificationType.INFO);
		}
		
		// DB Query
		String jpql = " select distinct(n) from Notification n"
				    + " where n.type in (:types)"
				    + " and n.read = 0"
				    + " order by n.createdDate desc";
		
		// Get ResultSet
		return manager
				.createQuery(jpql, Notification.class).setParameter("types", listTypes)
				.getResultList();
	}

}
