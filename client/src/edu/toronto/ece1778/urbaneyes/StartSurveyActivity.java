package edu.toronto.ece1778.urbaneyes;

import com.actionbarsherlock.app.SherlockActivity;

import edu.toronto.ece1778.urbaneyes.common.AnswerType;
import edu.toronto.ece1778.urbaneyes.common.Question;
import edu.toronto.ece1778.urbaneyes.common.SurveyType;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class StartSurveyActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_survey);
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_survey, menu);
		return true;
	}
	*/
	
	public void onResume() {
		super.onResume();

		SurveyType st = SurveyStateHolder.getCurrentSurveyType();
		if (SurveyStateHolder.hasQuestion()) {
			Question q = SurveyStateHolder.getNextQuestion();
			if (q.getAnsType() == AnswerType.NUMBER) {
				Intent i = new Intent(this, NumberQuestionActivity.class);
				startActivityForResult(i, 1);
			} else if (q.getAnsType() == AnswerType.TEXT) {
				Intent i = new Intent(this, TextQuestionActivity.class);
				startActivityForResult(i, 1);
			} else if (q.getAnsType() == AnswerType.RADIOGROUP) {
				Intent i = new Intent(this, RadioGroupQuestionActivity.class);
				startActivityForResult(i, 1);
			}
		} else {
			SurveyStateHolder.reset();
			Intent returnIntent = new Intent();
			setResult(RESULT_OK, returnIntent);
			SurveyStateHolder.reset();
			finish();
		}
	}

}
