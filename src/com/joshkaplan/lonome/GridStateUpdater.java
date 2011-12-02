package com.joshkaplan.lonome;

import java.util.Stack;

public class GridStateUpdater extends Thread {
	
	public Stack<Point> queue = new Stack<Point>();

	int currentUpdateBuffer = 0;
	boolean updated = false;

	@Override
	public void run() {
		/*
		byte[] bufferSwitchData = new byte[3];
		bufferSwitchData[0] = (byte) 144;
		bufferSwitchData[1] = 0;
		bufferSwitchData[2] = 49;
		Lonome.sendMidiData(bufferSwitchData);*/
		while (true) {
			

			/*for (int i = 0; i < 64; i += 2) {
				byte[] data = new byte[3];

				data[0] = (byte) 146;
				data[1] = (byte) (Lonome.grid.getStateForIndex(i) * 60);
				data[2] = (byte) (Lonome.grid.getStateForIndex(i + 1) * 60);
				Lonome.sendMidiData(data);
			}*/
			while(!queue.empty()) {
				updated = true;
				Point x = queue.pop();
				
				byte[] data = new byte[3];
				
				data[0] = (byte) 144;
				data[1] = (byte) Grid.getNoteForPoint(x);
				data[2] = (byte) (Lonome.grid.getStateForPoint(x) * Lonome.velocity);
				Lonome.sendMidiData(data);
			}
			/*if(updated) {
				System.out.println("queue empty, flipping buffer");
				updated = false;
				byte[] bufferSwitchData1 = new byte[3];
				bufferSwitchData1[0] = (byte) 144;
				bufferSwitchData1[1] = 0;
				if (this.currentUpdateBuffer == 1) {
					bufferSwitchData1[2] = 49;
					this.currentUpdateBuffer = 0;
				} else {
					bufferSwitchData1[2] = 52;
					this.currentUpdateBuffer = 1;
				}
				Lonome.sendMidiData(bufferSwitchData1);
			}*/
		}

	}

}
