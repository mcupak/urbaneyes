package edu.toronto.ece1778.urbaneyes.view;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.richfaces.model.Filter;

import edu.toronto.ece1778.urbaneyes.model.Survey;

/**
 * Bean responsible for filtering of surveys when displayed in a table with
 * filtering capabilities in JSF.
 * 
 * @author mcupak
 * 
 */
@Named
@ViewScoped
public class SurveyFilteringBean implements Serializable {

	private static final long serialVersionUID = -5680001353441022183L;
	private String nameFilter;
	private String descriptionFilter;
	private String privateFilter;

	public Filter<?> getNameFilterImpl() {
		return new Filter<Survey>() {
			public boolean accept(Survey item) {
				String date = getNameFilter();
				return (date == null || date.length() == 0 || item.getName()
						.contains(date));
			}
		};
	}

	public Filter<?> getDescriptionFilterImpl() {
		return new Filter<Survey>() {
			public boolean accept(Survey item) {
				String date = getDescriptionFilter();
				return (date == null || date.length() == 0 || item
						.getDescription().contains(date));
			}
		};
	}

	public Filter<?> getPrivateFilterImpl() {
		return new Filter<Survey>() {
			public boolean accept(Survey item) {
				String date = getPrivateFilter();
				return (date == null || date.length() == 0 || item.getPriv()
						.toString().contains(date));
			}
		};
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public String getDescriptionFilter() {
		return descriptionFilter;
	}

	public void setDescriptionFilter(String descriptionFilter) {
		this.descriptionFilter = descriptionFilter;
	}

	public String getPrivateFilter() {
		return privateFilter;
	}

	public void setPrivateFilter(String privateFilter) {
		this.privateFilter = privateFilter;
	}

}
