package edu.toronto.ece1778.urbaneyes.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import edu.toronto.ece1778.urbaneyes.model.Option;

/**
 * Managing bean for options. Performes operations on options in a database.
 * 
 * @author mcupak
 * 
 */
@Stateless
@Named
public class OptionManager {

	@Inject
	private EntityManager em;

	/**
	 * Finds an option by its ID.
	 * 
	 * @param id
	 *            ID of an option to find
	 * @return option with a given ID
	 */
	public Option getOption(Long id) {
		return em.find(Option.class, id);
	}

	/**
	 * Retrieves all the options in a database.
	 * 
	 * @return list of all the options
	 */
	@Produces
	@Model
	public List<Option> getOptions() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Option.class));
		return em.createQuery(cq).getResultList();
	}

	/**
	 * Saves a new option to a database.
	 * 
	 * @param option
	 *            option to save
	 */
	public void addOption(Option option) {
		if (option != null) {
			em.persist(option);
		}
	}

	/**
	 * Removes an existing option from a database.
	 * 
	 * @param id
	 *            ID of an option to remove
	 * @return true if the remove was performed, false otherwise
	 */
	public boolean deleteOption(Long id) {
		Option option = getOption(id);
		if (option == null) {
			return false;
		} else {
			em.remove(option);
			return true;
		}
	}

	/**
	 * Saves changes to an existing persisted option.
	 * 
	 * @param option
	 *            option to save
	 */
	public void editOption(Option option) {
		if (getOption(option.getId()) != null) {
			em.merge(option);
		}
	}
}
