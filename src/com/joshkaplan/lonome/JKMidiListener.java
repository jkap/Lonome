package com.joshkaplan.lonome;

import java.io.PrintStream;

import oscP5.OscMessage;
import de.humatic.mmj.MidiListener;

public class JKMidiListener implements MidiListener {

	static PrintStream o = Lonome.o;

	/**
	 * @param data
	 */
	public void midiInput(byte[] data) {
		// o.println("woo! got some midi input!");
		// o.println("grid location:"+Grid.getPointForNote((int)data[1]).toString());

		// this.echoReceivedData(data); // only for testing, not really practical
		this.processKeyPress(data);

	}

	/**
	 * 
	 * @param data
	 */
	public void echoReceivedData(byte[] data) {
		if (data[0] == (byte) 0x9c) {
			data[0] = (byte) 0x9c;
			data[2] = (byte) Lonome.velocity;
			o.println("note on");
		} else if (data[0] == (byte) 0x8c) {
			data[0] = (byte) 0x9c;
			data[2] = 0;
			o.println("note off");
		}

		o.println("Sending midi data:" + data[0] + ", " + data[1] + ", "
				+ data[2]);

		Lonome.sendMidiData(data);
	}

	public void processKeyPress(byte[] data) {
		Point buttonPressed = Grid.getPointForNote(data[1]);

		o.println("sending key press event. x:" + buttonPressed.x + ", y:"
				+ buttonPressed.y + ", s:" + (data[2] / 127) + ", host:"
				+ Lonome.oscListener.destHost + ", port: "
				+ Lonome.oscListener.destPort);

		if (data[2] == (byte) 127) {
			OscMessage pressMessage = new OscMessage(
					Lonome.oscListener.GRIDKEYADDRESS);
			pressMessage.add(buttonPressed.x);
			pressMessage.add(buttonPressed.y);
			pressMessage.add(1);
			Lonome.sendOscDataToHostAndPort(pressMessage,
					Lonome.oscListener.destHost, Lonome.oscListener.destPort);
		} else {
			OscMessage pressMessage = new OscMessage(
					Lonome.oscListener.GRIDKEYADDRESS);
			pressMessage.add(buttonPressed.x);
			pressMessage.add(buttonPressed.y);
			pressMessage.add(1);
			Lonome.sendOscDataToHostAndPort(pressMessage,
					Lonome.oscListener.destHost, Lonome.oscListener.destPort);
		}
	}

}
