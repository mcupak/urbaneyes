package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;

public enum AnswerType implements Serializable {
	NUMBER, TEXT, LONGITUDE, LATITUDE, ALTITUDE, RADIOGROUP, COUNT;
}
