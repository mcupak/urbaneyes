package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Survey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 30)
	@Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
	private String name;

	@NotNull
	private Boolean priv;

	@ManyToOne
	private User owner;

	@ManyToMany
	private List<User> contributors;

	@NotNull
	@NotEmpty
	private String description;

	@OneToMany
	private List<Question> questions;

	public Survey() {
	}

	public Survey(String name, Boolean priv, User owner,
			List<User> contributors, String description,
			List<Question> questions) {
		super();
		this.name = name;
		this.priv = priv;
		this.owner = owner;
		this.contributors = contributors;
		this.description = description;
		this.questions = questions;
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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Boolean getPriv() {
		return priv;
	}

	public void setPriv(Boolean priv) {
		this.priv = priv;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getContributors() {
		return contributors;
	}

	public void setContributors(List<User> contributors) {
		this.contributors = contributors;
	}

	@Override
	public String toString() {
		return "Survey [id=" + id + ", name=" + name + ", priv=" + priv
				+ ", description=" + description + "]";
	}

}