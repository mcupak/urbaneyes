package edu.toronto.ece1778.urbaneyes.view;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.richfaces.model.Filter;

import edu.toronto.ece1778.urbaneyes.model.Submission;

/**
 * Bean responsible for filtering of submissions when displayed in a table with
 * filtering capabilities in JSF.
 * 
 * @author mcupak
 * 
 */
@Named
@ViewScoped
public class SubmissionFilteringBean implements Serializable {

	private static final long serialVersionUID = -5680001353441022183L;
	private String userFilter;
	private String dateFilter;
	private String addressFilter;

	public Filter<?> getDateFilterImpl() {
		return new Filter<Submission>() {
			public boolean accept(Submission item) {
				String date = getDateFilter();
				return (date == null || date.length() == 0 || item.getDate()
						.toString().contains(date));
			}
		};
	}

	public Filter<?> getUserFilterImpl() {
		return new Filter<Submission>() {
			public boolean accept(Submission t) {
				String vendor = getUserFilter();
				return (vendor == null || vendor.length() == 0 || t.getUser()
						.getName().contains(vendor));
			}
		};
	}

	public Filter<?> getAddressFilterImpl() {
		return new Filter<Submission>() {
			public boolean accept(Submission t) {
				String vendor = getAddressFilter();
				return (vendor == null || vendor.length() == 0 || t.getPoint()
						.getAddress().contains(vendor));
			}
		};
	}

	public String getUserFilter() {
		return userFilter;
	}

	public void setUserFilter(String userFilter) {
		this.userFilter = userFilter;
	}

	public String getDateFilter() {
		return dateFilter;
	}

	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}

	public String getAddressFilter() {
		return addressFilter;
	}

	public void setAddressFilter(String addressFilter) {
		this.addressFilter = addressFilter;
	}

}
