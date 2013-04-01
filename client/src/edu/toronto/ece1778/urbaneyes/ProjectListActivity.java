package edu.toronto.ece1778.urbaneyes;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;

import edu.toronto.ece1778.urbaneyes.model.*;

/**
 * Screen for the choice of projects.
 * 
 * @author mcupak
 * @author rkalyani
 * 
 */
public class ProjectListActivity extends SherlockListActivity {

	private List<String> projects = new ArrayList<String>();
	private Integer selectedProject = 0;

	private ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);

		pb = (ProgressBar) findViewById(R.id.stprogress);
		pb.setVisibility(View.VISIBLE);

		new DownloadSurveyListXmlTask().execute();
		/*
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, loadProjects()));
		*/
	}

	private List<String> loadProjects() {
		// TODO add surveytypes received from server
		addSurveyTypeObjects();   // + DOWNLOADED
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
		// SurveyStateHolder.clearSurveyTypes();
		
		// addSubwaySurveyTypeObject();
		// addFoodVendorSurveyTypeObject();
		
		addCyclePathSurveyTypeObject();
		
		addCyclePathSimulation();
		
		addCountSurveyObject();
		
		addPolygonSurveyObject();
		
		addPolygonSimulationObject();
	}
	
	private void addSubwaySurveyTypeObject() {

		// TODO add surveytypes received from server
		SurveyType st = new SurveyType();
		st.setId(1);
		st.setName("Subway Entrance Survey");
		st.setKind(SurveyKind.POINT);

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
		st.setId(2);
		st.setName("Food Vendor Survey");
		st.setKind(SurveyKind.POINT);

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

	private void addCyclePathSurveyTypeObject() {

		// TODO add surveytypes received from server
		SurveyType st = new SurveyType();
		st.setId(3);
		st.setName("Cycle Path Survey");
		st.setKind(SurveyKind.PATH);
		
		SurveyStateHolder.addSurveyType(st);

	}

	private void addCyclePathSimulation() {

		// TODO add surveytypes received from server
		SurveyType st = new SurveyType();
		st.setId(4);
		st.setName("Path Simulation");
		st.setKind(SurveyKind.PATH);
		
		SurveyStateHolder.addSurveyType(st);

	}

	private void addCountSurveyObject() {
		SurveyType st = new SurveyType();
		st.setId(5);
		st.setName("People Survey");
		st.setKind(SurveyKind.POINT);

		Question q = new Question();
		q.setId(1);
		q.setDesc("How many people are waiting to cross the road?");
		q.setAnsType(AnswerType.COUNT);

		st.addQuestion(q);
		
		SurveyStateHolder.addSurveyType(st);
		
	}

	private void addPolygonSurveyObject() {
		SurveyType st = new SurveyType();
		st.setId(6);
		st.setName("Building Area Survey");
		st.setKind(SurveyKind.POLYGON);

		Question q = new Question();
		q.setId(1);
		q.setDesc("How many floors is the building?");
		q.setAnsType(AnswerType.NUMBER);

		st.addQuestion(q);
		
		SurveyStateHolder.addSurveyType(st);
		
	}

	private void addPolygonSimulationObject() {
		SurveyType st = new SurveyType();
		st.setId(6);
		st.setName("Polygon Simulation : Building Survey");
		st.setKind(SurveyKind.POLYGON);

		Question q = new Question();
		q.setId(1);
		q.setDesc("How many floors in this building?");
		q.setAnsType(AnswerType.NUMBER);

		st.addQuestion(q);
		
		SurveyStateHolder.addSurveyType(st);
		
	}
	
	// for survey list
	private class DownloadSurveyListXmlTask extends AsyncTask<Void, Void, Void> {
	    @Override
	    protected Void doInBackground(Void... arg) {
			SurveyStateHolder.clearSurveyTypes();
			String url = "http://urbaneyes-mcupak.rhcloud.com/rest/surveys/user-" + SurveyStateHolder.getUserId() + "/";
	        try {
	            loadSurveyListXmlFromServer(url);
	        } catch (Exception e) {
				Toast.makeText(ProjectListActivity.this, "Error while downloading survey list", Toast.LENGTH_LONG).show();
	        }
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void unused) {  
			new DownloadSurveyXmlTask().execute();
	    }
	    
	    private void loadSurveyListXmlFromServer(String urlString) 
	    		throws Exception {
	    	
	    	URL url = new URL(urlString);
	    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    	conn.setRequestMethod("GET");
	    	conn.connect();
	    	InputStream stream = conn.getInputStream();
	    	SurveyListXMLParser parser = new SurveyListXMLParser();
	    	parser.parse(stream);
	    	stream.close();
	    }
	}
	
	// for individual survey
	private class DownloadSurveyXmlTask extends AsyncTask<Void, Void, Void> {

		SurveyType cst;   // current survey type
		@Override
	    protected Void doInBackground(Void... arg) {
	        try {
	        	String url = "http://urbaneyes-mcupak.rhcloud.com/rest/surveys/";
	        	for (SurveyType st : SurveyStateHolder.getSurveyTypes()) {
	        		cst = st;
	        		loadSurveyXmlFromServer(url + st.getId() + "/");
	        	}
	        } catch (Exception e) {
				ProjectListActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(ProjectListActivity.this, "Error while downloading survey", Toast.LENGTH_LONG).show();
					}
				});
	        }
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void unused) {  

			pb.setVisibility(View.INVISIBLE);
			ProjectListActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					setListAdapter(new ArrayAdapter<String>(ProjectListActivity.this,
							android.R.layout.simple_list_item_1, loadProjects()));
				}
			});

	    }
	    
	    private void loadSurveyXmlFromServer(String urlString) 
	    		throws Exception {
	    	
	    	URL url = new URL(urlString);
	    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    	conn.setRequestMethod("GET");
	    	conn.connect();
	    	InputStream stream = conn.getInputStream();
	    	SurveyXMLParser parser = new SurveyXMLParser(cst);
	    	parser.parse(stream);
	    	stream.close();
	    }
	}

}
