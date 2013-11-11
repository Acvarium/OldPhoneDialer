package com.example.oldphonedialer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

	float x, y;
	ImageView imageView1, iv2;
	TextView tv1, tv2;
	Button clear, call;
	float deg0, deltadeg, ivGetDeg, angle;
	float pivotX, pivotY;
	float deltaX, deltaY;
	String writeNumber;
	RotateAnimation anim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		iv2 = (ImageView) findViewById(R.id.iv2);
		call = (Button) findViewById(R.id.call);
		clear = (Button) findViewById(R.id.clear);
		writeNumber = "";
		iv2.setOnTouchListener(this);
		call.setOnClickListener(this);
		clear.setOnClickListener(this);
	}

	private int angleToNum(float angle) {
		// ----- 1
		int a = 12;
		if ((angle > 70) && (angle < 98)) {
			a = 1;
		}
		// ----- 2
		if ((angle > 98) && (angle < 120)) {
			a = 2;
		}
		// ----- 3
		if ((angle > 120) && (angle < 150)) {
			a = 3;
		}
		// ----- 4
		if ((angle > 150) && (angle < 177)) {
			a = 4;
		}
		// ----- 5
		if ((angle > 177) && (angle < 204)) {
			a = 5;
		}
		// ----- 6
		if ((angle > 204) && (angle < 230)) {
			a = 6;
		}
		// ----- 7
		if ((angle > 230) && (angle < 258)) {
			a = 7;
		}
		// ----- 8
		if ((angle > 258) && (angle < 285)) {
			a = 8;
		}
		// ----- 9
		if ((angle > 285) && (angle < 312)) {
			a = 9;
		}
		// ----- 0
		if (angle > 312) {
			a = 0;
		}
		return a;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		x = event.getX();
		y = event.getY();
		pivotX = imageView1.getWidth() / 2;
		pivotY = imageView1.getHeight() / 2;
		deltaX = x - (iv2.getWidth() / 2);
		deltaY = y - (iv2.getHeight() / 2);

		imageView1.setPivotX(pivotX);
		imageView1.setPivotY(pivotY);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // нажатие
			deg0 = (float) (Math.atan2(deltaX, deltaY) * (180 / Math.PI)) + 180;
			ivGetDeg = imageView1.getRotation();
			break;
		case MotionEvent.ACTION_MOVE: // движение
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

			break;
		case MotionEvent.ACTION_UP: // отпускание

			if (imageView1.getRotation() < 346) {
				anim = new RotateAnimation(imageView1.getRotation(), -0f,
						pivotX, pivotY);
				int num = angleToNum(imageView1.getRotation());
				if (num < 12) {
					writeNumber = writeNumber + String.valueOf(num);
					tv2.setText(writeNumber);
				}

				imageView1.setRotation(0);
				anim.setDuration((long) Math.abs(deltadeg * 4));
				imageView1.startAnimation(anim);
			} else {
				imageView1.setRotation(0);
			}
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		imageView1.invalidate();

		tv1.setText("x = " + x + " y = " + y + "  " + imageView1.getRotation()
				+ " \n deltadeg = " + deltadeg + " a = " + angle);

		return true;
	}

    private void call() {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + writeNumber));
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("helloandroid dialing example", "Call failed", e);
        }
    }

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.call:
			call();
			break;
		case R.id.clear:
			writeNumber = "";
			tv2.setText(writeNumber);
			break;
		default:
			break;
		}

	}
}
