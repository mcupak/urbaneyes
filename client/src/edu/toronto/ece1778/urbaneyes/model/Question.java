package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;

public class Question implements Serializable {

	private int id;
	private String desc;
	private AnswerType ansType;
	
	public Question() {
		ansType = AnswerType.NUMBER;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public AnswerType getAnsType() {
		return ansType;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void setAnsType(AnswerType ansType) {
		this.ansType = ansType;
	}
}
