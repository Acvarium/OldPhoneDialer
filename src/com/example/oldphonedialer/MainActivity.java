package com.example.oldphonedialer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnTouchListener {

	float x, y;
	ImageView imageView1, iv2;
	TextView tv1;
	// float pivotX, pivotY;
	float deg, deg0, deltadeg, ivGetDeg, angle;
	float ddalpha;
	float pivotX, pivotY;
	float deltaX, deltaY;
	RotateAnimation anim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		tv1 = (TextView) findViewById(R.id.tv1);
		iv2 = (ImageView) findViewById(R.id.iv2);
		iv2.setOnTouchListener(this);

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
		pivotX = iv2.getWidth() / 2;
		pivotY = iv2.getHeight() / 2;
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

			if (imageView1.getRotation() <= (325)) {
				deltadeg = deg0 - angle;
				if (deltadeg < 0) {
					deltadeg = 360 + deltadeg;
				}
				deg = ivGetDeg + deltadeg;
				imageView1.setRotation(deg);
			}

			break;
		case MotionEvent.ACTION_UP: // отпускание
			anim = new RotateAnimation(imageView1.getRotation(), -0f, pivotX,
					pivotY);
			imageView1.setRotation(0);
			anim.setDuration((long) Math.abs(deltadeg * 5));
			imageView1.startAnimation(anim);

		case MotionEvent.ACTION_CANCEL:
			break;
		}
		imageView1.invalidate();
		tv1.setText("x = " + x + " y = " + y);

		return true;
	}
}
