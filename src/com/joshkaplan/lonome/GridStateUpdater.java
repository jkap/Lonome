package com.joshkaplan.lonome;

import java.io.PrintStream;
import java.util.Stack;

public class GridStateUpdater extends Thread {

	public Stack<Point> queue = new Stack<Point>();

	static PrintStream o = Lonome.o;

	int currentUpdateBuffer = 0;
	boolean updated = false;

	@Override
	public void run() {
		while (true) {

			while (!queue.empty()) {
				updated = true;
				Point x = queue.pop();

				byte[] data = new byte[3];

				data[0] = (byte) 144;
				data[1] = (byte) Grid.getNoteForPoint(x);
				data[2] = (byte) (Lonome.grid.getStateForPoint(x) * Lonome.velocity);
				Lonome.sendMidiData(data);
			}
		}
	}
}
