package edu.toronto.ece1778.urbaneyes.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Producer for users for use in JSF.
 * 
 * @author mcupak
 * 
 */
@RequestScoped
public class MemberListProducer {
	@Inject
	private EntityManager em;

	private List<User> members;

	/**
	 * Retrieves the list of users.
	 * 
	 * @return list of users
	 */
	@Produces
	@Named
	public List<User> getMembers() {
		return members;
	}

	/**
	 * Retrieves users on change of the list.
	 * 
	 * @param member
	 */
	public void onMemberListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final User member) {
		retrieveAllMembersOrderedByName();
	}

	/**
	 * Retrieves users ordered by name.
	 */
	@PostConstruct
	public void retrieveAllMembersOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> member = criteria.from(User.class);
		criteria.select(member).orderBy(cb.asc(member.get("name")));
		members = em.createQuery(criteria).getResultList();
	}
}
