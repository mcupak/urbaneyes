package edu.toronto.ece1778.urbaneyes.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.jboss.solder.logging.Logger;

import edu.toronto.ece1778.urbaneyes.model.Option;

@Stateless
@Named
public class OptionManager {

	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	public Option getOption(Long id) {
		return em.find(Option.class, id);
	}

	@Produces
	@Model
	public List<Option> getOptions() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Option.class));
		return em.createQuery(cq).getResultList();
	}

	public void addOption(Option option) {
		if (option != null) {
			em.persist(option);
		}
	}

	public boolean deleteOption(Long id) {
		Option option = getOption(id);
		if (option == null) {
			return false;
		} else {
			em.remove(option);
			return true;
		}
	}

	public void editOption(Option option) {
		if (getOption(option.getId()) != null) {
			em.merge(option);
		}
	}
}
