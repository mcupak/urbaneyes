package edu.toronto.ece1778.urbaneyes.view;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.richfaces.component.SortOrder;

import com.google.common.collect.Maps;

import edu.toronto.ece1778.urbaneyes.model.Submission;
import edu.toronto.ece1778.urbaneyes.util.JPADataModel;

/**
 * Bean taking care of submission when displayed using JPADataModel. Not used
 * ATM.
 * 
 * @author mcupak
 * 
 */
@Named
@SessionScoped
public class SubmissionBean implements Serializable {
	private static final long serialVersionUID = -5156711102367948040L;
	@Inject
	private EntityManager em;

	private static final class SubmissionDataModel extends
			JPADataModel<Submission> {
		private SubmissionDataModel(EntityManager entityManager) {
			super(entityManager, Submission.class);
		}

		@Override
		protected Object getId(Submission t) {
			return t.getId();
		}
	}

	private Map<String, SortOrder> sortOrders = Maps
			.newHashMapWithExpectedSize(1);
	private Map<String, String> filterValues = Maps.newHashMap();
	private String sortProperty;

	public SubmissionBean() {
		sortOrders.put("date", SortOrder.descending);
		sortOrders.put("user", SortOrder.unsorted);
	}

	private EntityManager lookupEntityManager() {
		return em;
	}

	public Map<String, SortOrder> getSortOrders() {
		return sortOrders;
	}

	public Map<String, String> getFilterValues() {
		return filterValues;
	}

	public String getSortProperty() {
		return sortProperty;
	}

	public void setSortProperty(String sortPropety) {
		this.sortProperty = sortPropety;
	}

	public void toggleSort() {
		for (Entry<String, SortOrder> entry : sortOrders.entrySet()) {
			SortOrder newOrder;

			if (entry.getKey().equals(sortProperty)) {
				if (entry.getValue() == SortOrder.ascending) {
					newOrder = SortOrder.descending;
				} else {
					newOrder = SortOrder.ascending;
				}
			} else {
				newOrder = SortOrder.unsorted;
			}

			entry.setValue(newOrder);
		}
	}

	public Object getDataModel() {
		return new SubmissionDataModel(lookupEntityManager());
	}
}