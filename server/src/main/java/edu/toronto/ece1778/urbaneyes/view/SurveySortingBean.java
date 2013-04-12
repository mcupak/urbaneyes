package edu.toronto.ece1778.urbaneyes.view;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.richfaces.component.SortOrder;

/**
 * Bean responsible for sorting of surveys when displayed in a table.
 * 
 * @author mcupak
 * 
 */
@Named
@ViewScoped
public class SurveySortingBean implements Serializable {
	private static final long serialVersionUID = -5156711102367948040L;
	private SortOrder nameOrder = SortOrder.ascending;
	private SortOrder privOrder = SortOrder.unsorted;
	private SortOrder descriptionOrder = SortOrder.unsorted;

	/**
	 * Sets the ordering to name.
	 */
	public void sortByName() {
		privOrder = SortOrder.unsorted;
		descriptionOrder = SortOrder.unsorted;
		if (nameOrder.equals(SortOrder.ascending)) {
			setNameOrder(SortOrder.descending);
		} else {
			setNameOrder(SortOrder.ascending);
		}
	}

	/**
	 * Sets the ordering to privacy status.
	 */
	public void sortByPrivate() {
		nameOrder = SortOrder.unsorted;
		descriptionOrder = SortOrder.unsorted;
		if (privOrder.equals(SortOrder.ascending)) {
			setPrivOrder(SortOrder.descending);
		} else {
			setPrivOrder(SortOrder.ascending);
		}
	}

	/**
	 * Sets the ordering to description.
	 */
	public void sortByDescription() {
		nameOrder = SortOrder.unsorted;
		privOrder = SortOrder.unsorted;
		if (descriptionOrder.equals(SortOrder.ascending)) {
			setDescriptionOrder(SortOrder.descending);
		} else {
			setDescriptionOrder(SortOrder.ascending);
		}
	}

	public SortOrder getNameOrder() {
		return nameOrder;
	}

	public void setNameOrder(SortOrder nameOrder) {
		this.nameOrder = nameOrder;
	}

	public SortOrder getPrivOrder() {
		return privOrder;
	}

	public void setPrivOrder(SortOrder privOrder) {
		this.privOrder = privOrder;
	}

	public SortOrder getDescriptionOrder() {
		return descriptionOrder;
	}

	public void setDescriptionOrder(SortOrder descriptionOrder) {
		this.descriptionOrder = descriptionOrder;
	}

}