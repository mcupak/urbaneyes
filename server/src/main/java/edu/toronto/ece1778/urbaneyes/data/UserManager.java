package edu.toronto.ece1778.urbaneyes.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Managing class for users. Performs operations on user entities.
 * 
 * @author mcupak
 * 
 */
@Stateless
@Named
public class UserManager {

	@Inject
	private EntityManager em;

	/**
	 * Finds user by his id.
	 * 
	 * @param id
	 *            userid
	 * @return user with the given id
	 */
	public User getUser(Long id) {
		return em.find(User.class, id);
	}

	/**
	 * Retrieves all the user accounts in the database.
	 * 
	 * @return list of all the users
	 */
	@Produces
	@Model
	public List<User> getUsers() {
		TypedQuery<User> query = em.createQuery(
				"SELECT m FROM User m ORDER BY m.name", User.class);
		return query.getResultList();
	}

	/**
	 * Finds a user by his credentials.
	 * 
	 * @param email
	 *            userid
	 * @param password
	 *            password
	 * @return user with the specified email and password (unique)
	 */
	public User getUser(String email, String password) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(User.class);
		Root<User> userRoot = cq.from(User.class);
		cq = cq.where(cb.equal(userRoot.get("email"), email));
		TypedQuery<User> q = em.createQuery(cq);
		User user = q.getSingleResult();
		if ((user != null) && (user.getPassword().equals(password))) {
			return user;
		} else {
			return null;
		}
	}

	/**
	 * Adds a new user to a database.
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		if (user != null) {
			em.persist(user);
		}
	}

	/**
	 * Removes a user from a database.
	 * 
	 * @param id
	 *            userid
	 * @return true if remove was performed, false otherwise
	 */
	public boolean deleteUser(Long id) {
		User user = getUser(id);
		if (user == null) {
			return false;
		} else {
			em.remove(user);
			return true;
		}
	}

	/**
	 * Edits an existing persisted user.
	 * 
	 * @param user
	 */
	public void editUser(User user) {
		if (getUser(user.getId()) != null) {
			em.merge(user);
		}
	}
}
