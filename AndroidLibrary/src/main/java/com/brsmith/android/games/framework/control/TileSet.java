package com.brsmith.android.games.framework.control;

import java.util.Random;

import com.brsmith.android.games.framework.control.decorator.ITileDecorator;
import com.brsmith.android.games.framework.gl.GridPos;
import com.brsmith.android.games.framework.interfaces.IGraphics;

public class TileSet extends TouchControl
{
	private class Coords
	{
		int x;
		int y;
		
		Coords(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	int numRows;
	int numCols;
	Tile[] tiles;
	GridPos[] tilePositions;
	int touchedTileIndex;
	GridPos blankTilePos;
	ITileDecorator decorator;
	
	public TileSet(ITileDecorator decorator, int numRows, int numCols)
	{
		super(null, -1, -1);
		this.numRows = numRows;
		this.numCols = numRows;
		this.decorator = decorator;

		tiles = createTiles();
	}
	
	public int getRows() { return numRows; }
	public int getCols() { return numCols; }
	public int getTouchedTileIndex() { return touchedTileIndex; }
	
	public boolean canMove(int index)
	{
		GridPos tilePos = tilePositions[index];
		
		if(tilePos.right().isEqual(blankTilePos)) return true;
		if(tilePos.left().isEqual(blankTilePos)) return true;
		if(tilePos.up().isEqual(blankTilePos)) return true;
		if(tilePos.down().isEqual(blankTilePos)) return true;
		
		return false;
	}
	
	public void slideTile(int index)
	{
		if(!canMove(index))
			return;
		
		GridPos prevPos = new GridPos(tilePositions[index]);
		tilePositions[index].move(blankTilePos);
		Coords coords = getCoords(blankTilePos.getRow(), blankTilePos.getCol());
		tiles[index].move(coords.x, coords.y);
		blankTilePos.move(prevPos);
	}
	
	public boolean isSolved()
	{
		if(blankTilePos.getRow() != numRows) return false;
		if(blankTilePos.getCol() != numCols) return false;
		
		int numTiles = (numRows * numCols) - 1;
		int row = 1;
		int col = 1;
		
		for(int idx = 0; idx < numTiles; idx++)
		{
			if(tilePositions[idx].getRow() != row || tilePositions[idx].getCol() != col)
				return false;
			
			col++;
			if(col > numCols)
			{
				col = 1;
				row++;
			}
		}
		
		return true;
	}
	
	public void shuffle()
	{
		Random generator = new Random();

		for(int idx = 0; idx < (numRows*numCols*20); idx++)
		{
			int[] moveableTiles = new int[4];
			int numMoveable = 0;
			for(int tileIdx = 0; tileIdx < (numRows*numCols)-1; tileIdx++)
			{
				if(canMove(tileIdx))
				{
					moveableTiles[numMoveable] = tileIdx;
					numMoveable++;
				}
			}
			int randomIdx = moveableTiles[generator.nextInt(numMoveable)];
			slideTile(randomIdx);
		}
	}
	
	private Tile[] createTiles()
	{
		int numTiles = (numRows * numCols) - 1;
		
		tiles = new Tile[numTiles];
		tilePositions = new GridPos[numTiles];
		
		int row = 1;
		int col = 1;
		
		for(int idx = 0; idx < numTiles; idx++)
		{
			Coords coords = getCoords(row, col);
			Tile tile = new Tile(decorator.getTilePixmap(idx+1), coords.x, coords.y);
			tiles[idx] = tile;
			tilePositions[idx] = new GridPos(row, col);
			
			col++;
			if(col > numCols)
			{
				col = 1;
				row++;
			}
		}
		
		blankTilePos = new GridPos(numCols, numRows);
		
		return tiles;
	}
	
	private Coords getCoords(int row, int col)
	{
		int x = ((col-1) * decorator.getTileWidth()) + (decorator.getTileWidth() / 2);
		int y = ((row-1) * decorator.getTileHeight()) + (decorator.getTileHeight() / 2);

		return new Coords(x, y);
	}

	@Override
	public void draw(IGraphics g)
	{
		for(int idx = 0; idx < tiles.length; idx++)
			tiles[idx].draw(g);
	}
	
	@Override
	public boolean isHit(float x, float y)
	{
		return isHit((int)x, (int)y);
	}
	
	@Override
	public boolean isHit(int x, int y)
	{
		touchedTileIndex = -1;
		for(int idx = 0; idx < tiles.length; idx++)
			if (tiles[idx].isHit(x, y))
			{
				touchedTileIndex = idx;
				return true;
			}
			
		return false;
	}
}
