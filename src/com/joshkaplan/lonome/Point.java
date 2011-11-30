package com.joshkaplan.lonome;

public class Point {

	int x;
	int y;

	public Point() {
		this.x = 0;
		this.y = 0;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setRow(int row) {
		this.y = row;
	}

	public void setCol(int col) {
		this.x = col;
	}

	public void setRowCol(int row, int col) {
		this.y = row;
		this.x = col;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getRow() {
		return this.y;
	}

	public int getCol() {
		return this.x;
	}

	public String toString() {
		return Messages.getString("Point.rowString") + this.getRow() + Messages.getString("Point.colString") + this.getCol(); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
