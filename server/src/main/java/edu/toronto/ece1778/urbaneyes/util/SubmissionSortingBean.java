package edu.toronto.ece1778.urbaneyes.util;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.richfaces.component.SortOrder;

@Named
@ViewScoped
public class SubmissionSortingBean implements Serializable {
	private static final long serialVersionUID = -5156711102367948040L;
	private SortOrder dateOrder = SortOrder.descending;
	private SortOrder userOrder = SortOrder.unsorted;

	public void sortByDate() {
		userOrder = SortOrder.unsorted;
		if (dateOrder.equals(SortOrder.ascending)) {
			setDateOrder(SortOrder.descending);
		} else {
			setDateOrder(SortOrder.ascending);
		}
	}

	public void sortByUser() {
		dateOrder = SortOrder.unsorted;
		if (userOrder.equals(SortOrder.ascending)) {
			setUserOrder(SortOrder.descending);
		} else {
			setUserOrder(SortOrder.ascending);
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

}