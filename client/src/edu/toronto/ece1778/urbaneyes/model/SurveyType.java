package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;
import java.util.ArrayList;

public final class SurveyType implements Serializable {

	private int id;
	private String name;
	private SurveyKind kind = SurveyKind.POINT;
	private ArrayList<Question> questions;
	
	public SurveyType() {
		questions = new ArrayList<Question>();
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public SurveyKind getKind() {
		return kind;
	}
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public void addQuestion(Question q) {
		questions.add(q);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setKind(SurveyKind kind) {
		this.kind = kind;
	}

}
