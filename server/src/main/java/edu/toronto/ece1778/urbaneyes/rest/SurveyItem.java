package edu.toronto.ece1778.urbaneyes.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import edu.toronto.ece1778.urbaneyes.model.Survey;

/**
 * Short proxy representation of a survey for the purpose of a survey list.
 * 
 * @author mcupak
 * 
 */
@XmlRootElement(name = "survey")
public class SurveyItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Boolean priv;

	private String description;

	public SurveyItem() {
	}

	public SurveyItem(Survey s) {
		super();
		this.id = s.getId();
		this.name = s.getName();
		this.priv = s.getPriv();
		this.description = s.getDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getPriv() {
		return priv;
	}

	public void setPriv(Boolean priv) {
		this.priv = priv;
	}

	@Override
	public String toString() {
		return "Survey [id=" + id + ", name=" + name + ", priv=" + priv
				+ ", description=" + description + "]";
	}

}