package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

/**
 * Urban Planning project. A survey entity representing a set of questions.
 * 
 * @author mcupak
 * 
 */
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
	@Size(min = 1, max = 30, message = "name must be 1-30 characters long")
	@Pattern(regexp = "[A-Za-z ]*", message = "name must contain only letters and spaces")
	private String name;

	@NotNull
	private Boolean priv;

	@ManyToOne
	private User owner;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<User> contributors;

	@NotNull(message = "description cannot be empty")
	private String description;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Question> questions;

	public Survey() {
	}

	public Survey(String name, Boolean priv, User owner,
			Set<User> contributors, String description, Set<Question> questions) {
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

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
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

	public Set<User> getContributors() {
		return contributors;
	}

	public void setContributors(Set<User> contributors) {
		this.contributors = contributors;
	}

	public void addContributor(User user) {
		if (contributors == null) {
			contributors = new HashSet<User>();
		}
		if (!contributors.contains(user)) {
			contributors.add(user);
		}
	}

	public void removeContributor(User user) {
		if (contributors != null) {
			contributors.remove(user);
		}
	}

	public void addQuestion(Question q) {
		if (questions == null) {
			questions = new HashSet<Question>();
		}
		if (!questions.contains(q)) {
			questions.add(q);
		}
	}

	public void removeQuestion(Question q) {
		if (questions != null) {
			questions.remove(q);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Survey other = (Survey) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Survey [id=" + id + ", name=" + name + ", priv=" + priv
				+ ", description=" + description + "]";
	}

}