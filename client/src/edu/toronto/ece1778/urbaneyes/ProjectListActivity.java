package edu.toronto.ece1778.urbaneyes;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;

import edu.toronto.ece1778.urbaneyes.common.AnswerType;
import edu.toronto.ece1778.urbaneyes.common.Question;
import edu.toronto.ece1778.urbaneyes.common.RadioGroupQuestion;
import edu.toronto.ece1778.urbaneyes.common.SurveyType;

/**
 * Screen for the choice of projects.
 * 
 * @author mcupak
 * 
 */
public class ProjectListActivity extends SherlockListActivity {
	private List<String> projects = new ArrayList<String>();
	private Integer selectedProject = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);
		// prepare list of files
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, loadProjects()));
	}

	private List<String> loadProjects() {
		// TODO add surveytypes received from server
		addSurveyTypeObjects();
		projects = SurveyStateHolder.getSurveyNames();
		return projects;
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		selectedProject = position;
		Intent myIntent = new Intent(getBaseContext(), MapActivity.class);
		myIntent.putExtra("selectedProject", selectedProject);
		startActivity(myIntent);
	}
	
	private void addSurveyTypeObjects() {
		// TODO add surveytypes received from server
		addSubwaySurveyTypeObject();
		addFoodVendorSurveyTypeObject();
	}
	
	private void addSubwaySurveyTypeObject() {

		// TODO add surveytypes received from server
		SurveyType st = new SurveyType();
		st.setId(1);
		st.setName("Subway Entrance Survey");

		RadioGroupQuestion q = new RadioGroupQuestion();
		q.setId(1);
		q.setDesc("Does this entrance have an attendant on duty?");
		q.setAnsType(AnswerType.RADIOGROUP);
		RadioGroupQuestion.Option o = q.new Option();
		o.setId(1);
		o.setDesc("Yes");
		q.addOption(o);
		o = q.new Option();
		o.setId(2);
		o.setDesc("No");
		q.addOption(o);
		
		st.addQuestion(q);

		q = new RadioGroupQuestion();
		q.setId(2);
		q.setDesc("Does this entrance have a turnstile for people with disabilities?");
		q.setAnsType(AnswerType.RADIOGROUP);
		o = q.new Option();
		o.setId(1);
		o.setDesc("Yes");
		q.addOption(o);
		o = q.new Option();
		o.setId(2);
		o.setDesc("No");
		q.addOption(o);

		st.addQuestion(q);
		
		SurveyStateHolder.addSurveyType(st);

	}

	private void addFoodVendorSurveyTypeObject() {

		// TODO add surveytypes received from server
		SurveyType st = new SurveyType();
		st.setId(1);
		st.setName("Food Vendor Survey");

		RadioGroupQuestion q = new RadioGroupQuestion();
		q.setId(1);
		q.setDesc("What kind of vendor is this?");
		q.setAnsType(AnswerType.RADIOGROUP);
		RadioGroupQuestion.Option o = q.new Option();
		o.setId(1);
		o.setDesc("Hot Dog");
		q.addOption(o);
		o = q.new Option();
		o.setId(2);
		o.setDesc("Toronto a la Carte");
		q.addOption(o);
		o = q.new Option();
		o.setId(3);
		o.setDesc("Food Truck");
		q.addOption(o);
		
		st.addQuestion(q);

		Question q2 = new Question();
		q2.setId(2);
		q2.setDesc("How much is the cheapest vegetarian option?");
		q2.setAnsType(AnswerType.NUMBER);

		st.addQuestion(q2);
		
		SurveyStateHolder.addSurveyType(st);

	}
	
}
