package com.brsmith.android.games.framework.gl;

public class GridPos
{
	int row;
	int col;
	public GridPos(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	public GridPos(GridPos pos)
	{
		this.row = pos.getRow();
		this.col = pos.getCol();
	}
	
	public int getRow() { return row; }
	public int getCol() { return col; }
	
	public boolean isEqual(GridPos pos)
	{
		if(pos.row != getRow()) return false;
		if(pos.col != getCol()) return false;
		return true;
	}
	
	public void move(GridPos pos)
	{
		this.row = pos.row;
		this.col = pos.col;
	}
	
	public GridPos right() { return new GridPos(row, col+1); }
	public GridPos left() { return new GridPos(row, col-1); }
	public GridPos up() { return new GridPos(row-1, col); }
	public GridPos down() { return new GridPos(row+1, col); }
}
