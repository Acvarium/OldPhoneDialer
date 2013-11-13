package com.example.oldphonedialer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class SetingsActivity extends Activity {

	public static final String APP_PREFERENCES = "data";
	boolean soundEnable;
	SharedPreferences sPref;
	CheckBox chbSound;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setings);
		
		sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
		chbSound = (CheckBox) findViewById(R.id.chbSound);
		readSettings();

		OnClickListener checkBoxListener;
		checkBoxListener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (chbSound.isChecked()) {
					saveText("soundEnable", "Yes");
				}else{
					saveText("soundEnable", "no");
				}
			}

		};
		chbSound.setOnClickListener(checkBoxListener);

	}

	private void readSettings() {
		String sNo = "no";
		if (sNo.equals(loadText("soundEnable"))) {
			soundEnable = false;
			chbSound.setChecked(false);

		} else {

			soundEnable = true;
			chbSound.setChecked(true);
		}
	}

	void saveText(String lname, String data) {
		Editor ed = sPref.edit();
		ed.putString(lname, data);
		ed.commit();
	}

	String loadText(String lname) {
		String savedText = sPref.getString(lname, "");
		return savedText;
	}

}
