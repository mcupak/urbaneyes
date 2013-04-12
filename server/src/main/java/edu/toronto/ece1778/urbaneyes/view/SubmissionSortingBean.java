package edu.toronto.ece1778.urbaneyes.view;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.richfaces.component.SortOrder;

/**
 * Bean responsible for sorting of submissions when displayed in a table with
 * sorting capabilities in JSF.
 * 
 * @author mcupak
 * 
 */
@Named
@ViewScoped
public class SubmissionSortingBean implements Serializable {
	private static final long serialVersionUID = -5156711102367948040L;
	private SortOrder dateOrder = SortOrder.descending;
	private SortOrder userOrder = SortOrder.unsorted;
	private SortOrder pointOrder = SortOrder.unsorted;

	/**
	 * Set ordering to date.
	 */
	public void sortByDate() {
		userOrder = SortOrder.unsorted;
		pointOrder = SortOrder.unsorted;
		if (dateOrder.equals(SortOrder.ascending)) {
			setDateOrder(SortOrder.descending);
		} else {
			setDateOrder(SortOrder.ascending);
		}
	}

	/**
	 * Set ordering to user.
	 */
	public void sortByUser() {
		dateOrder = SortOrder.unsorted;
		pointOrder = SortOrder.unsorted;
		if (userOrder.equals(SortOrder.ascending)) {
			setUserOrder(SortOrder.descending);
		} else {
			setUserOrder(SortOrder.ascending);
		}
	}

	/**
	 * Set ordering to point.
	 */
	public void sortByPoint() {
		dateOrder = SortOrder.unsorted;
		userOrder = SortOrder.unsorted;
		if (pointOrder.equals(SortOrder.ascending)) {
			setPointOrder(SortOrder.descending);
		} else {
			setPointOrder(SortOrder.ascending);
		}
	}

	public SortOrder getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(SortOrder dateOrder) {
		this.dateOrder = dateOrder;
	}

	public SortOrder getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(SortOrder userOrder) {
		this.userOrder = userOrder;
	}

	public SortOrder getPointOrder() {
		return pointOrder;
	}

	public void setPointOrder(SortOrder pointOrder) {
		this.pointOrder = pointOrder;
	}

}