package edu.toronto.ece1778.urbaneyes.controller;

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

import edu.toronto.ece1778.urbaneyes.model.Member;

// The @Stateful annotation eliminates the need for manual transaction demarcation
@Stateful
@Model
public class MemberRegistration {

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Member> memberEventSrc;

	private Member newMember;

	@Produces
	@Named
	public Member getNewMember() {
		return newMember;
	}

	public void register() throws Exception {
		log.info("Registering " + newMember.getName());
		em.persist(newMember);
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Registered!",
				"Registration successful"));
		memberEventSrc.fire(newMember);
		initNewMember();
	}

	@PostConstruct
	public void initNewMember() {
		newMember = new Member();
	}
}
