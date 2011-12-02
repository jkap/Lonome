package com.joshkaplan.lonome;

public class Grid {

	private static int[][] gridNotes = { { 0, 1, 2, 3, 4, 5, 6, 7 },
			{ 16, 17, 18, 19, 20, 21, 22, 23 },
			{ 32, 33, 34, 35, 36, 37, 38, 39 },
			{ 48, 49, 50, 51, 52, 53, 54, 55 },
			{ 64, 65, 66, 67, 68, 69, 70, 71 },
			{ 80, 81, 82, 83, 84, 85, 86, 87 },
			{ 96, 97, 98, 99, 100, 101, 102, 103 },
			{ 112, 113, 114, 115, 116, 117, 118, 119 } };

	private int[][] gridStates = { { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 } };

	public static final int GRIDSIZE = 64;

	public boolean stateChanged = false;

	/**
	 * 
	 * @param note
	 * @return Point
	 */
	public static Point getPointForNote(int note) {

		return findIndex(note);

	}

	/**
	 * 
	 * @param point
	 * @return int
	 */
	public static int getNoteForPoint(Point point) {

		return gridNotes[point.getRow()][point.getCol()];

	}
	
	public static Point getPointForIndex(int index) {
		int row = index / 8;
		int col = index % 8;
		Point p = new Point();
		p.setRowCol(row, col);
		return p;
	}
	
	/**
	 * 
	 * @param search
	 * @return Point
	 */
	private static Point findIndex(int search) {
		int[][] array = gridNotes;
		for (int rowIndex = 0; rowIndex < array.length; rowIndex++) {
			int[] row = array[rowIndex];
			if (row != null) {
				for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
					if (search == (row[columnIndex])) {
						Point point = new Point();
						point.setRowCol(rowIndex, columnIndex);
						return point;
					}
				}
			}
		}

		return null;
	}

	public static int getNoteForIndex(int index) {
		int row = index / 8;
		int col = index % 8;
		return gridNotes[row][col];
	}

	public void setStateForPoint(int state, Point point) {
		this.gridStates[point.getRow()][point.getCol()] = state;

		this.stateChanged = true;

	}

	public void setStateForIndex(int state, int index) {
		int row = index / 8;
		int col = index % 8;
		this.gridStates[row][col] = state;

		this.stateChanged = true;
	}

	public int getStateForPoint(Point point) {
		return this.gridStates[point.getRow()][point.getCol()];
	}

	public int getStateForIndex(int index) {
		int row = index / 8;
		int col = index % 8;
		return this.gridStates[row][col];
	}

	public void sendStateUpdates() {

	}
}
