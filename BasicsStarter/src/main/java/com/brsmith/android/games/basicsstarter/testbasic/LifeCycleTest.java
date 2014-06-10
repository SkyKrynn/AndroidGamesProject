package com.brsmith.android.games.basicsstarter.testbasic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LifeCycleTest extends Activity 
{
	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	private void log(String text)
	{
		Log.d("LifeCycleTest", text);
		builder.append(text);
		builder.append('\n');
		textView.setText(builder.toString());
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		textView.setText(builder.toString());
		setContentView(textView);
		log("created");
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		log("Resumed");
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		log("Paused");
		
		if(isFinishing())
			log("Finishing");
	}
}
