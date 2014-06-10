package com.brsmith.android.games.basicsstarter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GLBasicsStarter extends ListActivity
{
	String 	tests[] = 
		{
			"GLSurfaceViewTest",
			"GLGameTest",
			"FirstTriangleTest",
			"ColoredTriangleTest",
			"TexturedTriangleTest",
			"IndexedTest",
			"BlendingTest",
			"BobTest"
		};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tests));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void onListItemClick(ListView list, View view, int position, long id)
	{
		super.onListItemClick(list, view, position, id);
		String testName = tests[position];
		try
		{
			Class clazz = Class.forName("com.brsmith.android.games.basicsstarter.testgl." + testName);
			Intent intent = new Intent(this, clazz);
			startActivity(intent);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
