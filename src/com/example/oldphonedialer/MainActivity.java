package com.example.oldphonedialer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener,
		OnTouchListener {

	float x, y;
	ImageView imageView1;
	View iv2;
	Matrix matrix;
	TextView tv1;
	RelativeLayout rl1;
	Display display;
	float pivotX, pivotY;
	float minScale;
	float deg, deg0, deltadeg, ivGetDeg, angle;

	RotateAnimation anim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		display = getWindowManager().getDefaultDisplay();
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		tv1 = (TextView) findViewById(R.id.tv1);
		iv2 = (View) findViewById(R.id.iv2);
		matrix = new Matrix();
		Point size = new Point();

		display.getSize(size);
		int displayWidth = size.x;
		int displayHeight = size.y;
		float imageHeight = imageView1.getDrawable().getIntrinsicHeight();
		float imageWidth = imageView1.getDrawable().getIntrinsicWidth();

		float scaleX = (float) (0.90 * displayWidth) / (float) imageWidth;
		float scaleY = (float) (0.9 * displayHeight) / (float) imageHeight;
		minScale = Math.min(scaleX, scaleY);
		imageView1.setOnClickListener(this);

		pivotX = imageWidth * minScale / 2;
		pivotY = imageHeight * minScale / 2;
		tv1.setText(" " + pivotX + "   " + pivotY);

		anim = new RotateAnimation(0f, 360f, pivotX, pivotX);
		anim.setInterpolator(new LinearInterpolator());
		// anim.setRepeatCount(Animation.ABSOLUTE);
		anim.setDuration(2000);

		iv2.setOnTouchListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		x = event.getX();
		y = event.getY();
		float deltaX = x - pivotX;
		float deltaY = y - pivotY;

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
			//if (deltadeg > 327){deltadeg = 0;}
			

			break;
		case MotionEvent.ACTION_UP: // отпускание

			anim = new RotateAnimation(imageView1.getRotation(), -0f, pivotX,
					pivotX);
			imageView1.setRotation(0);
			anim.setDuration((long) Math.abs(deltadeg * 5));

			imageView1.startAnimation(anim);

		case MotionEvent.ACTION_CANCEL:

			break;
		}
		imageView1.invalidate();
		tv1.setText("" + deltaX + " " + deltaY + "\n iv.angle = "
				+ imageView1.getRotation() + "\n Touch.angle = " + angle
				+ "\n delta.angle = " + deltadeg);
		return true;
	}
}
