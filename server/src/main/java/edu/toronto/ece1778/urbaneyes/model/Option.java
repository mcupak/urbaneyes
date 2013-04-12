package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * An option as a possible answer for a question. Used when a question specifies
 * a type of answer allowing a selection from a custom fixed set of options on
 * the user side.
 * 
 * @author mcupak
 * 
 */
@Entity
@XmlRootElement
public class Option implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@NotEmpty
	private String name;

	public Option() {
	}

	public Option(String name) {
		super();
		this.name = name;
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

	@Override
	public String toString() {
		return "Option [id=" + id + ", name=" + name + "]";
	}

}