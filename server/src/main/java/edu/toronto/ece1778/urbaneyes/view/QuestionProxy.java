package edu.toronto.ece1778.urbaneyes.view;

import java.io.Serializable;

import edu.toronto.ece1778.urbaneyes.model.AnswerType;
import edu.toronto.ece1778.urbaneyes.model.Question;

/**
 * Proxy for an in-memory representation of a question.
 * 
 * @author mcupak
 * 
 */
public class QuestionProxy implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	private AnswerType answerType;

	public QuestionProxy() {
		id = Long.valueOf(-1);
		answerType = AnswerType.YESNO;
	}

	public QuestionProxy(Question q) {
		name = q.getName();
		answerType = q.getAnswerType();
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

	public AnswerType getAnswerType() {
		return answerType;
	}

	public void setAnswerType(AnswerType answerType) {
		this.answerType = answerType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((answerType == null) ? 0 : answerType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		QuestionProxy other = (QuestionProxy) obj;
		if (answerType != other.answerType)
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
		return true;
	}

	@Override
	public String toString() {
		return "QuestionProxy [id=" + id + ", name=" + name + ", answerType="
				+ answerType + "]";
	}

}