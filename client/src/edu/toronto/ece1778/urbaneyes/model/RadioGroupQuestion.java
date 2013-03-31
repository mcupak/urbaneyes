package edu.toronto.ece1778.urbaneyes.model;

import java.util.ArrayList;

public final class RadioGroupQuestion extends Question {

	public class Option {
		private int id;
		private String desc;
		
		public Option() {
			
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public int getId() {
			return id;
		}
		
		public String getDesc() {
			return desc;
		}
	}
	
	private ArrayList<Option> options = new ArrayList<Option>();
	
	public RadioGroupQuestion() {
		super();
	}
	
	public void addOption(Option o) {
		options.add(o);
	}
	
	public ArrayList<Option> getOptions() {
		return options;
	}
}
