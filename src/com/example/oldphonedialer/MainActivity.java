package com.example.oldphonedialer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnTouchListener,
		OnClickListener {
	private MediaPlayer rotateS1, rotateS2;
	float x, y;
	ImageView imageView1, iv2, iv3;
	TextView tv2;
	Button clear, plus, asterisk, gr;
	float deg0, deltadeg, ivGetDeg, angle;
	float pivotX, pivotY;
	float deltaX, deltaY;
	Typeface font;
	String writeNumber;
	RotateAnimation anim;
	boolean newtouch;
	float mainButtonRadius;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		imageView1 = (ImageView) findViewById(R.id.imageView1);
		tv2 = (TextView) findViewById(R.id.tv2);
		iv2 = (ImageView) findViewById(R.id.iv2);
		iv3 = (ImageView) findViewById(R.id.iv3);
		plus = (Button) findViewById(R.id.plus);
		asterisk = (Button) findViewById(R.id.asterisk);
		plus = (Button) findViewById(R.id.plus);
		gr = (Button) findViewById(R.id.gr);
		clear = (Button) findViewById(R.id.clear);

		writeNumber = "";
		tv2.setText(writeNumber);

		iv2.setOnTouchListener(this);
		asterisk.setOnClickListener(this);
		clear.setOnClickListener(this);
		plus.setOnClickListener(this);
		asterisk.setOnClickListener(this);
		gr.setOnClickListener(this);

		font = Typeface.createFromAsset(getAssets(), "fonts/QumpellkaNo12.otf");
		tv2.setTypeface(font);
	}

	public String removeLastChar(String s) {
	    if (s == null || s.length() == 0) {
	        return s;
	    }
	    return s.substring(0, s.length()-1);
	}



	private String angleToNum(float angle) {
		// ----- 1
		String a = "";
		if ((angle > 70) && (angle < 98)) {
			a = "1";
		}
		// ----- 2
		if ((angle > 98) && (angle < 120)) {
			a = "2";
		}
		// ----- 3
		if ((angle > 120) && (angle < 150)) {
			a = "3";
		}
		// ----- 4
		if ((angle > 150) && (angle < 177)) {
			a = "4";
		}
		// ----- 5
		if ((angle > 177) && (angle < 204)) {
			a = "5";
		}
		// ----- 6
		if ((angle > 204) && (angle < 230)) {
			a = "6";
		}
		// ----- 7
		if ((angle > 230) && (angle < 258)) {
			a = "7";
		}
		// ----- 8
		if ((angle > 258) && (angle < 285)) {
			a = "8";
		}
		// ----- 9
		if ((angle > 285) && (angle < 312)) {
			a = "9";
		}
		// ----- 0
		if (angle > 312) {
			a = "0";
		}
		return a;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private boolean pointInPosition(float x, float y, float x1, float y1,
			float r) {
		if ((Math.pow((x - x1), 2) + Math.pow((y - y1), 2)) <= Math.pow(r, 2)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		x = event.getX();
		y = event.getY();
		pivotX = imageView1.getWidth() / 2;
		pivotY = imageView1.getHeight() / 2;
		deltaX = x - (iv2.getWidth() / 2);
		deltaY = y - (iv2.getHeight() / 2);

		mainButtonRadius = pivotX / 2.3f;

		imageView1.setPivotX(pivotX);
		imageView1.setPivotY(pivotY);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // нажатие

			if (pointInPosition(x, y, pivotX, pivotY, mainButtonRadius)) {
				iv3.setVisibility(View.VISIBLE);
				newtouch = false;
			} else {
				iv3.setVisibility(View.INVISIBLE);
				newtouch = true;
				deg0 = (float) (Math.atan2(deltaX, deltaY) * (180 / Math.PI)) + 180;
				ivGetDeg = imageView1.getRotation();
				rotateS1 = MediaPlayer.create(this, R.raw.frontsound);
				rotateS1.setLooping(false);
				rotateS1.start();
			}

			break;
		case MotionEvent.ACTION_MOVE: // движение

			if (newtouch) {
				imageView1.setAnimation(null);
				angle = (float) (Math.atan2(deltaX, deltaY) * (180 / Math.PI)) + 180;

				if ((imageView1.getRotation() <= 325)
						|| (imageView1.getRotation() > 350)) {
					deltadeg = deg0 - angle;
					if (deltadeg < 0) {
						deltadeg = 360 + deltadeg;
					}
					imageView1.setRotation(ivGetDeg + deltadeg);
				}
			}

			break;
		case MotionEvent.ACTION_UP: // отпускание
			iv3.setVisibility(View.INVISIBLE);
			// iv2.setImageResource(R.drawable.d);
			if (newtouch) {
				if (imageView1.getRotation() < 346) {
					anim = new RotateAnimation(imageView1.getRotation(), -0f,
							pivotX, pivotY);
					rotateS2 = MediaPlayer.create(this, R.raw.backsound);
					rotateS2.setLooping(false);
					rotateS2.seekTo(1150 - (int) Math.abs(deltadeg * 4));
					rotateS2.start();

					writeNumber = writeNumber
							+ angleToNum(imageView1.getRotation());
					tv2.setText(writeNumber);

					imageView1.setRotation(0);
					anim.setDuration((long) Math.abs(deltadeg * 4));
					imageView1.startAnimation(anim);
				} else {
					imageView1.setRotation(0);
				}
			} else {
				call();
			}
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		imageView1.invalidate();

		return true;
	}

	// Made phone call
	private void call() {
		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + writeNumber));
			startActivity(callIntent);
		} catch (ActivityNotFoundException e) {
			Log.e("helloandroid dialing example", "Call failed", e);
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case R.id.action_settings:
			intent = new Intent(this, SetingsActivity.class);
			startActivity(intent);
			break;
		case R.id.about:
			intent = new Intent(this, About.class);
			startActivity(intent);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	// Processing button presses
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clear:
			writeNumber = removeLastChar(writeNumber);
			tv2.setText(writeNumber);
			break;
		case R.id.asterisk:
			writeNumber = writeNumber + "*";
			tv2.setText(writeNumber);
			break;
		case R.id.gr:
			writeNumber = writeNumber + "#";
			tv2.setText(writeNumber);
			break;
		case R.id.plus:
			writeNumber = writeNumber + "+";
			tv2.setText(writeNumber);
			break;
		default:
			break;
		}

	}
}
