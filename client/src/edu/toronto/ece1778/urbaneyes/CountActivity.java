package edu.toronto.ece1778.urbaneyes;
import edu.toronto.ece1778.urbaneyes.model.*;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.Menu;
import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CountActivity extends SherlockActivity {

	Question q;
	TextView tvQuestion;
	TextView tvCurrentCount;
	Button plusCounter;
	Button minusCounter;
	Button submit;
	int count = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_question);

		q = SurveyStateHolder.getCurrentQuestion();
		
		tvQuestion = (TextView) findViewById(R.id.text_count_question);
		tvQuestion.setText(q.getDesc());

		tvCurrentCount = (TextView) findViewById(R.id.text_current_count);
		tvCurrentCount.setText("Current Count = " + count);

		plusCounter = (Button) findViewById(R.id.buttonPlusCounter);
		minusCounter = (Button) findViewById(R.id.buttonMinusCounter);
		
		submit = (Button) findViewById(R.id.buttonSubmit);
		
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.text_question, menu);
		return true;
	}
	 */

	public void onClickSubmitButton(View arg1) {
		// TODO send survey result to server
			
			Intent returnIntent = new Intent();
			setResult(RESULT_OK, returnIntent);

			Answer a = new Answer();
			a.answer = String.valueOf(count);
			a.questionId = q.getId();
			a.surveyTypeId = SurveyStateHolder.getCurrentSurveyType().getId();
			SurveyStateHolder.addAnswer(a);

			finish();
	}

	public void onClickPlusCounterButton(View arg1) {
		// TODO send survey result to server
		count += 1;
		tvCurrentCount.setText("Current Count = " + count);
	}

	public void onClickMinusCounterButton(View arg1) {
		// TODO send survey result to server
		if (count > 0)
			count -= 1;
		tvCurrentCount.setText("Current Count = " + count);
	}

}
