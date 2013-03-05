package edu.toronto.ece1778.urbaneyes;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;

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
		projects = new ArrayList<String>();
		projects.add("Food vendor");
		projects.add("Subway entrance");
		return projects;
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		selectedProject = position;
		Intent myIntent = new Intent(getBaseContext(), MapActivity.class);
		myIntent.putExtra("selectedProject", selectedProject);
		startActivity(myIntent);
	}
}
