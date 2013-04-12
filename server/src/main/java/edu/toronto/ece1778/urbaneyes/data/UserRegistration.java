package edu.toronto.ece1778.urbaneyes.data;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Bean responsible for user registration.
 * 
 * @author mcupak
 * 
 */
@Stateful
@Model
public class UserRegistration {

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private EntityManager em;

	@Inject
	private Event<User> memberEventSrc;

	private User newMember;

	@Produces
	@Named
	public User getNewMember() {
		return newMember;
	}

	/**
	 * Registers the user and stores his credentials.
	 * 
	 * @throws Exception
	 */
	public void register() throws Exception {
		log.info("Registering " + newMember.getName());
		em.persist(newMember);
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Registered!",
				"Registration successful"));
		memberEventSrc.fire(newMember);
		initNewMember();
	}

	/**
	 * Creates a new user.
	 */
	@PostConstruct
	public void initNewMember() {
		newMember = new User();
	}
}
