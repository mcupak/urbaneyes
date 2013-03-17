package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	private AnswerType answerType;

	@OneToMany
	private List<Option> options;

	public Question() {
	}

	public Question(String name, AnswerType answerType, List<Option> options,
			Survey survey) {
		super();
		this.name = name;
		this.answerType = answerType;
		this.options = options;
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

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", name=" + name + ", answerType="
				+ answerType + ", options=" + options + "]";
	}
}