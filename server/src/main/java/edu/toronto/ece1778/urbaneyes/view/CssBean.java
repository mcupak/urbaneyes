package edu.toronto.ece1778.urbaneyes.view;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 * Bean responsible for dynamic CSS effects, e.g. TAB highlighting in the menu.
 * 
 * @author mcupak
 * 
 */
@Named
@ViewScoped
public class CssBean implements Serializable {
	private static final long serialVersionUID = -5156711102367948040L;

	private long tab = 0;

	public long getTab() {
		return tab;
	}

	public void setTab(Long tab) {
		this.tab = tab;
	}

	public void loadTab(Long t) {
		setTab(t);
	}

	public boolean checkTab(Long index) {
		return tab == index;
	}

}