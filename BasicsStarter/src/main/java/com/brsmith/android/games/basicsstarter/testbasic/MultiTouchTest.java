package com.brsmith.android.games.basicsstarter.testbasic;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MultiTouchTest extends Activity implements OnTouchListener 
{
	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	float x[] = new float[10];
	float y[] = new float[10];
	boolean touched[] = new boolean[10];

	private void updateTextView()
	{
		builder.setLength(0);
		for(int idx = 0; idx < 10; idx++)
		{
			builder.append(touched[idx]);
			builder.append(", ");
			builder.append(x[idx]);
			builder.append(", ");
			builder.append(y[idx]);
			builder.append('\n');
		}
		textView.setText(builder.toString());
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		textView.setText("Touch and drag (multiple fingers supported)!");
		textView.setOnTouchListener(this);
		setContentView(textView);
	}

	public boolean onTouch(View v, MotionEvent event)
	{
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		int pointerId = event.getPointerId(pointerIndex);		
		
		switch(action)
		{
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			touched[pointerId] = true;
			x[pointerId] = (int)event.getX(pointerIndex);
			y[pointerId] = (int)event.getY(pointerIndex);
			break;
			
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_CANCEL:
			touched[pointerId] = false;
			x[pointerId] = (int)event.getX(pointerIndex);
			y[pointerId] = (int)event.getY(pointerIndex);
			break;
			
		case MotionEvent.ACTION_MOVE:
			int pointerCounter = event.getPointerCount();
			for(int idx = 0; idx < pointerCounter; idx++)
			{
				pointerIndex = idx;
				pointerId = event.getPointerId(pointerIndex);
				x[pointerId] = event.getX(pointerIndex);
				y[pointerId] = event.getY(pointerIndex);
			}
			break;
		}
		
		updateTextView();
		return true;
	}

}
