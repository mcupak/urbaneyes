package edu.toronto.ece1778.urbaneyes;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SubwayEntranceSurvey extends SherlockActivity {

  TextView tvAttendant;
	TextView tvTurnstile;
	TextView tvToken;
	TextView tvElev;
	
	RadioGroup rgAttendant;
	RadioGroup rgTurnstile;
	RadioGroup rgToken;
	RadioGroup rgElev;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subway_entrance_survey);
		
		tvAttendant = (TextView) findViewById(R.id.textViewAttendant);
		rgAttendant = (RadioGroup) findViewById(R.id.radioAttendant);
		
		tvTurnstile = (TextView) findViewById(R.id.textViewTurnstile);
		rgAttendant = (RadioGroup) findViewById(R.id.radioTurnstile);

		tvToken = (TextView) findViewById(R.id.textViewTokMac);
		rgAttendant = (RadioGroup) findViewById(R.id.radioTokMac);

		tvElev = (TextView) findViewById(R.id.textViewElev);
		rgAttendant = (RadioGroup) findViewById(R.id.radioElevator);

	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subway_entrance_survey, menu);
		return true;
	}
	*/

	public void onClickSubmitButton(View arg1) {

		finish();          // TODO comment out this later...
	}
}
