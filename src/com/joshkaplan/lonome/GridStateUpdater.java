package com.joshkaplan.lonome;

import java.util.Stack;

public class GridStateUpdater extends Thread {
	
	public Stack<Point> queue = new Stack<Point>();

	int currentUpdateBuffer = 1;

	@Override
	public void run() {
		while (true) {
			byte[] bufferSwitchData = new byte[3];
			bufferSwitchData[0] = (byte) 144;
			bufferSwitchData[1] = 0;
			if (this.currentUpdateBuffer == 1) {
				bufferSwitchData[2] = 49;
				this.currentUpdateBuffer = 0;
			} else {
				bufferSwitchData[2] = 52;
				this.currentUpdateBuffer = 1;
			}

			/*for (int i = 0; i < 64; i += 2) {
				byte[] data = new byte[3];

				data[0] = (byte) 146;
				data[1] = (byte) (Lonome.grid.getStateForIndex(i) * 60);
				data[2] = (byte) (Lonome.grid.getStateForIndex(i + 1) * 60);
				Lonome.sendMidiData(data);
			}*/
			
			for (Point x : queue) {
				byte[] data = new byte[3];
				
				data[0] = (byte) 144;
				data[1] = (byte) Grid.getNoteForPoint(x);
				data[2] = (byte) (Lonome.grid.getStateForPoint(x) * Lonome.velocity);
				Lonome.sendMidiData(data);
			}
		}

	}

}
