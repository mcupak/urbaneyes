package edu.toronto.ece1778.urbaneyes.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import edu.toronto.ece1778.urbaneyes.model.Question;
import edu.toronto.ece1778.urbaneyes.model.Survey;
import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Proxy object representing an in-memory version of a survey.
 * 
 * @author mcupak
 * 
 */
public class SurveyProxy implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	@Size(min = 1, max = 30, message = "name must be 1-30 characters long")
	@Pattern(regexp = "[A-Za-z ]*", message = "name must contain only letters and spaces")
	private String name;

	@NotNull
	private Boolean priv;

	private User owner;

	private List<User> contributors;

	@NotNull(message = "description cannot be empty")
	private String description;

	private List<QuestionProxy> questions;

	public SurveyProxy() {
		id = Long.valueOf(-1);
	}

	public SurveyProxy(Long id, String name, Boolean priv, User owner,
			Set<User> contributors, String description,
			List<QuestionProxy> questions) {
		super();
		this.id = id;
		this.name = name;
		this.priv = priv;
		this.owner = owner;
		this.contributors = new ArrayList<User>();
		this.contributors.addAll(contributors);
		this.description = description;
		this.questions = questions;
	}

	public SurveyProxy(Survey s) {
		id = s.getId();
		name = s.getName();
		priv = s.getPriv();
		owner = s.getOwner();
		contributors = new ArrayList<User>();
		contributors.addAll(s.getContributors());
		description = s.getDescription();
		questions = new ArrayList<QuestionProxy>();
		for (Question q : s.getQuestions()) {
			questions.add(new QuestionProxy(q));
		}
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

	public List<QuestionProxy> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionProxy> questions) {
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

	public void addContributor(User user) {
		if (contributors == null) {
			contributors = new ArrayList<User>();
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

	public void addQuestion(QuestionProxy q) {
		if (questions == null) {
			questions = new ArrayList<QuestionProxy>();
		}
		if (!questions.contains(q)) {
			questions.add(q);
		}
	}

	public void removeQuestion(QuestionProxy q) {
		if (questions != null) {
			questions.remove(q);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((priv == null) ? 0 : priv.hashCode());
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
		SurveyProxy other = (SurveyProxy) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (priv == null) {
			if (other.priv != null)
				return false;
		} else if (!priv.equals(other.priv))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SurveyProxy [id=" + id + ", name=" + name + ", priv=" + priv
				+ ", owner=" + owner + ", contributors=" + contributors
				+ ", description=" + description + ", questions=" + questions
				+ "]";
	}

}