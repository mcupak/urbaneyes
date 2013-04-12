package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Individual submission of answers to a survey. Multiple answers and a location
 * are associated with a submission.
 * 
 * @author mcupak
 * 
 */
@Entity
@XmlRootElement
public class Submission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private User user;

	@NotNull
	@Column
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date date;

	@OneToOne
	private Point point;

	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Answer> answers;

	@ManyToOne
	private Survey survey;

	public Submission() {
	}

	public Submission(User user, Date date, Point point, List<Answer> answers,
			Survey survey) {
		super();
		this.user = user;
		this.date = date;
		this.point = point;
		this.answers = answers;
		this.survey = survey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
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
		Submission other = (Submission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Submission [id=" + id + ", user=" + user + ", date=" + date
				+ ", point=" + point + ", answers=" + answers + ", survey="
				+ survey + "]";
	}

}