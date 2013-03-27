package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum AnswerType implements Serializable {
	NUMBER, TEXT, YESNO, COUNT, NOISE;
}