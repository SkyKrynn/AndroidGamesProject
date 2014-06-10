package com.brsmith.android.games.slideme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.brsmith.android.games.framework.impl.AndroidGame;
import com.brsmith.android.games.framework.impl.AndroidPixmap;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.enums.PixmapFormat;
import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;
import com.brsmith.android.games.framework.interfaces.IPixmap;
import com.brsmith.android.games.framework.command.IActionCommand;
import com.brsmith.android.games.framework.action.EnableTouchAction;
import com.brsmith.android.games.framework.command.ReturnScreenCommand;
import com.brsmith.android.games.framework.control.BaseControl;
import com.brsmith.android.games.framework.control.Button;
import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.framework.control.LabelControl;
import com.brsmith.android.games.framework.control.VisualEnableDisableControl;
import com.brsmith.android.games.framework.action.TouchAction;

public class SettingsScreen extends Screen
{
	private static final String EXIT_BUTTON = "exit_button";
	private static final String IMAGE_BUTTON = "image_button";
	private static final String CAMERA_BUTTON = "camera_button";
	private static final String GRID_SIZE_UP_BUTTON = "grid_size_up_button";
	private static final String GRID_SIZE_DOWN_BUTTON = "grid_size_down_button";
	private static final String TILE_TYPE_LEFT_BUTTON = "tile_type_left_button";
	private static final String TILE_TYPE_RIGHT_BUTTON = "tile_type_right_button";
	
	private static final Settings.TileType[] TILE_TYPE_LIST = Settings.TileType.values();
	
	private static final int PICK_IMAGE = 1;
	private static final int CAPTURE_IMAGE = 2;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	
	LabelControl gridSizeLabel;
	LabelControl sizeLabel;
	int numRows;
	int numCols;
	String gridSize;
	
	LabelControl tileTypeLabel;
	LabelControl typeLabel;
	int typeIndex;
	
	Uri cameraImageUri;
	IPixmap selectedCameraPixmap;
	IPixmap selectedCameraThumbnail;

	Uri tileboardImageUri;
    IPixmap selectedPixmap;
    IPixmap selectedThumbnail;
    Assets assets = new Assets();
	
	public SettingsScreen(IGame game)
	{
		super(game);
		
		gridSizeLabel = new LabelControl("Grid Size", 160, 13, CoordinatePosition.CENTER, assets);
		gridSizeLabel.setLabelSize(24);
		
		int gridSizeYPos = 50;
		VisualEnableDisableControl smallerGrid = new VisualEnableDisableControl(Assets.arrowLeft, Assets.arrowLeftDisabled, 100, gridSizeYPos, CoordinatePosition.CENTER);
		controlHandler.add(GRID_SIZE_DOWN_BUTTON, new EnableTouchAction(smallerGrid, true, decreaseGridSize));
		VisualEnableDisableControl biggerGrid = new VisualEnableDisableControl(Assets.arrowRight, Assets.arrowRightDisabled, 220, gridSizeYPos, CoordinatePosition.CENTER);
		controlHandler.add(GRID_SIZE_UP_BUTTON, new EnableTouchAction(biggerGrid, false, increaseGridSize));
		
		numRows = Settings.numRows;
		numCols = Settings.numCols;
		gridSize = Integer.toString(numCols) + "x" + Integer.toString(numRows);
		sizeLabel = new LabelControl(gridSize, 160, gridSizeYPos, CoordinatePosition.CENTER, assets);
		updateGridSize();

		tileTypeLabel = new LabelControl("Tile Type", 160, 100, CoordinatePosition.CENTER, assets);
		tileTypeLabel.setLabelSize(24);
		
		gridSizeYPos = 137;
		VisualEnableDisableControl leftTileType = new VisualEnableDisableControl(Assets.arrowLeft, Assets.arrowLeftDisabled, 70, gridSizeYPos, CoordinatePosition.CENTER);
		controlHandler.add(TILE_TYPE_LEFT_BUTTON, new EnableTouchAction(leftTileType, true, leftChangeTileType));
		VisualEnableDisableControl rightTileTYpe = new VisualEnableDisableControl(Assets.arrowRight, Assets.arrowRightDisabled, 250, gridSizeYPos, CoordinatePosition.CENTER);
		controlHandler.add(TILE_TYPE_RIGHT_BUTTON, new EnableTouchAction(rightTileTYpe, true, rightChangeTileType));

		typeIndex = Settings.tileType.ordinal();
		typeLabel = new LabelControl(TILE_TYPE_LIST[typeIndex].toString(), 160, gridSizeYPos, CoordinatePosition.CENTER, assets);

		Button imageButton = new Button(Assets.menuButtonSkinny, 160, 160, CoordinatePosition.CENTER, "Choose Image", assets);
		imageButton.setCaptionSize(10);
		imageButton.setCaptionTypeface(Typeface.SANS_SERIF);
		controlHandler.add(IMAGE_BUTTON, new TouchAction(imageButton, true, browseCommand));

		Button cameraButton = new Button(Assets.menuButtonSkinny, 160, 160, CoordinatePosition.CENTER, "Take Snapshot", assets);
		cameraButton.setCaptionSize(10);
		cameraButton.setCaptionTypeface(Typeface.SANS_SERIF);
		controlHandler.add(CAMERA_BUTTON, new TouchAction(cameraButton, true, takeSnapshoteCommand));

		updateTileType();

		if(Settings.tileboardImageUri.length() > 0)
		{
			tileboardImageUri = Uri.parse(Settings.tileboardImageUri);
			getTileboardImage();
		}
		
		if(Settings.cameraImageUri.length() > 0)
		{
			cameraImageUri = Uri.parse(Settings.cameraImageUri);
			getCameraImage();
		}

		Button exitButton = new Button(Assets.menuButton, 160, 455, CoordinatePosition.CENTER, "Return", assets);
		controlHandler.add(EXIT_BUTTON, new TouchAction(exitButton, true, returnCommand));
	}
	
	private void updateGridSize()
	{
		gridSize = Integer.toString(numCols) + "x" + Integer.toString(numRows);
		sizeLabel.setLabel(gridSize);
		
		if(numRows < 7)
			controlHandler.actionEnabled(GRID_SIZE_UP_BUTTON);
		else
			controlHandler.actionDisabled(GRID_SIZE_UP_BUTTON);

		if(numRows > 3)
			controlHandler.actionEnabled(GRID_SIZE_DOWN_BUTTON);
		else
			controlHandler.actionDisabled(GRID_SIZE_DOWN_BUTTON);
	}
	
	private void updateTileType()
	{
		typeLabel.setLabel(TILE_TYPE_LIST[typeIndex].toString());
		
		controlHandler.actionInvisable(IMAGE_BUTTON);
		controlHandler.actionDisabled(IMAGE_BUTTON);
		controlHandler.actionInvisable(CAMERA_BUTTON);
		controlHandler.actionDisabled(CAMERA_BUTTON);
		
		if(TILE_TYPE_LIST[typeIndex] == Settings.TileType.Image)
		{
			controlHandler.actionVisable(IMAGE_BUTTON);
			controlHandler.actionEnabled(IMAGE_BUTTON);
		}

		if(TILE_TYPE_LIST[typeIndex] == Settings.TileType.Camera)
		{
			controlHandler.actionVisable(CAMERA_BUTTON);
			controlHandler.actionEnabled(CAMERA_BUTTON);
		}
	}
	
	private void getTileboardImage()
	{
		if(tileboardImageUri == null)
		{
			selectedPixmap = null;
			selectedThumbnail = null;
			return;
		}

        selectedPixmap = game.getGraphics().newPixmap(tileboardImageUri, PixmapFormat.RGB565);
        if(selectedPixmap != null)
        	selectedThumbnail = new AndroidPixmap(selectedPixmap, 70, 70);
	}

	private void getCameraImage()
	{
		if(cameraImageUri == null)
		{
			selectedCameraPixmap = null;
			selectedCameraThumbnail = null;
			return;
		}

        selectedCameraPixmap = game.getGraphics().newPixmap(cameraImageUri, PixmapFormat.RGB565);
        if(selectedCameraPixmap != null)
        	selectedCameraThumbnail = new AndroidPixmap(selectedCameraPixmap, 70, 70);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch(requestCode)
		{
		case PICK_IMAGE:
			if(data == null) return;
			tileboardImageUri = data.getData();
			if(tileboardImageUri == null) return;
				
			getTileboardImage();
			break;
		case CAPTURE_IMAGE:
			if(resultCode == Activity.RESULT_OK)
			{
				getCameraImage();
			}
			else
			{
				cameraImageUri = null;
			}
			//if(data == null) return;
			//cameraImageUri = data.getData();
			//if(cameraImageUri == null) return;
			//getCameraImage();
			break;
		default:
			break;
		}
	}
	
	IActionCommand browseCommand = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			((AndroidGame)game).startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE);
		}
	};

	private static Uri getOutputMediaFileUri(int type)
	{
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type)
	{
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}

    IActionCommand takeSnapshoteCommand = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			Intent intent = new Intent();
 			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
 			cameraImageUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
 			intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
			((AndroidGame)game).startActivityForResult(intent, CAPTURE_IMAGE);
		}
	};

    IActionCommand returnScreenCommand = new ReturnScreenCommand<UnknownScreen>(game, UnknownScreen.class);
    IActionCommand returnCommand = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			Settings.numCols = numCols;
			Settings.numRows = numRows;
			Settings.tileboardImageUri = "";
			Settings.cameraImageUri = "";
			if(tileboardImageUri != null)
				Settings.tileboardImageUri = tileboardImageUri.toString();
			if(cameraImageUri != null)
				Settings.cameraImageUri = cameraImageUri.toString();
			Settings.tileType = TILE_TYPE_LIST[typeIndex];
			Settings.save(game.getFileIO());
			returnScreenCommand.run(control);
		}
	};

    IActionCommand leftChangeTileType = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			typeIndex--;
			if(typeIndex < 0)
				typeIndex = TILE_TYPE_LIST.length - 1;
			updateTileType();
		}
	};

    IActionCommand rightChangeTileType = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			typeIndex++;
			if(typeIndex >= TILE_TYPE_LIST.length)
				typeIndex = 0;
			updateTileType();
		}
	};

    IActionCommand increaseGridSize = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			numRows++;
			numCols++;
			updateGridSize();
		}
	};

    IActionCommand decreaseGridSize = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			numRows--;
			numCols--;
			updateGridSize();
		}
	};

	@Override
	public void update(float deltaTime) 
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        sendTouchEventsThruControlHandler(touchEvents);
	}

	@Override
	public void present(float deltaTime) 
	{
        IGraphics g = game.getGraphics();
		g.clear(Color.BLACK);
		
		if(TILE_TYPE_LIST[typeIndex] == Settings.TileType.Image && selectedThumbnail != null)
			g.drawPixmap(selectedThumbnail, 125, 180);

		if(TILE_TYPE_LIST[typeIndex] == Settings.TileType.Camera && selectedCameraThumbnail != null)
			g.drawPixmap(selectedCameraThumbnail, 125, 180);

		controlHandler.drawControls(g);
		
		gridSizeLabel.draw(g);
		sizeLabel.draw(g);
		
		tileTypeLabel.draw(g);
		typeLabel.draw(g);
	}

	@Override
	public void pause() 
	{
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
