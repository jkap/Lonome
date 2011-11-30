package com.joshkaplan.lonome;

import java.io.PrintStream;

import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscStatus;

public class JKOscListener implements OscEventListener {

	static PrintStream o = System.out;

	int destPort;
	String destHost = "127.0.0.1";
	String prefix = "";
	int rotation = 0;

	final String SYSPORTADDRESS = "/sys/port";
	final String SYSHOSTADDRESS = "/sys/host";
	final String SYSPREFIXADDRESS = "/sys/prefix";
	final String SYSROTATIONADDRESS = "/sys/rotattion";
	final String SYSINFOADDRESS = "/sys/info";
	final String SYSIDADDRESS = "/sys/id";
	final String GRIDLEDSETADDRESS = "/grid/led/set";
	final String GRIDLEDALLADDRESS = "/grid/led/all";
	final String GRIDLEDMAPADDRESS = "/grid/led/map";
	final String GRIDLEDROWADDRESS = "/grid/led/row";
	final String GRIDLEDCOLADDRESS = "/grid/led/col";
	final String GRIDKEYADDRESS = "/grid/key";

	/**
	 * @param theMessage
	 */
	public void oscEvent(OscMessage theMessage) {
		// TODO Auto-generated method stub

		if (theMessage.checkAddrPattern(SYSPORTADDRESS)) {
			this.handleDestPortChange(theMessage.get(0).intValue());
		} else if (theMessage.checkAddrPattern(SYSHOSTADDRESS)) {
			this.handleDestHostChange(theMessage.get(0).stringValue());
		} else if (theMessage.checkAddrPattern(SYSPREFIXADDRESS)) {
			this.handlePrefixChange(theMessage.get(0).stringValue());
		} else if (theMessage.checkAddrPattern(SYSROTATIONADDRESS)) {
			this.handleRotationChange(theMessage.get(0).intValue());
		} else if (theMessage.checkAddrPattern(SYSINFOADDRESS)) {
			int argumentCount = theMessage.arguments().length;

			if (argumentCount == 2) {
				this.handleInfoRequest(theMessage.get(0).stringValue(),
						theMessage.get(1).intValue());
			} else if (argumentCount == 1) {
				this.handleInfoRequest(destHost, theMessage.get(1).intValue());
			} else {
				this.handleInfoRequest(destHost, destPort);
			}
		} else if (theMessage.checkAddrPattern(this.prefix + GRIDLEDSETADDRESS)) {
			this.handleLedSetRequest(theMessage.get(0).intValue(), theMessage
					.get(1).intValue(), theMessage.get(2).intValue());
		} else if (theMessage.checkAddrPattern(this.prefix + GRIDLEDALLADDRESS)) {
			this.handleLedAllRequest(theMessage.get(0).intValue());
		} else if (theMessage.checkAddrPattern(prefix + GRIDLEDMAPADDRESS)) {
			// TODO: implement this
		} else if (theMessage.checkAddrPattern(prefix + GRIDLEDROWADDRESS)) {
			// TODO: implement this
		} else if (theMessage.checkAddrPattern(prefix + GRIDLEDCOLADDRESS)) {
			// TODO: implement this
		} else {
			o.println("Got a request. Don't know what to do with it.");
			o.println(theMessage.addrPattern());
		}

	}

	/**
	 * @param theStatus
	 */
	public void oscStatus(OscStatus theStatus) {
		// TODO Auto-generated method stub

	}

	public void handleLedAllRequest(int s) {
		o.println("got a led set all request. s:" + s);
		/*
		byte[] data = new byte[3];
		data[0] = (byte) 144;
		data[2] = (byte) ((Lonome.velocity - 12) * s);
		for (int i = 0; i < 64; i++) {
			data[1] = (byte) Grid.getNoteForIndex(i);
			Lonome.sendMidiData(data);
		}
		*/
		if(s == 0) {
			byte[] data = new byte[3];
			data[0] = (byte) 176;
			data[1] = (byte) 0;
			data[2] = (byte) 0;
			Lonome.sendMidiData(data);
		} else {
			byte[] data = new byte[3];
			data[0] = (byte) 176;
			data[1] = (byte) 0;
			data[2] = (byte) 127;
			Lonome.sendMidiData(data);
		}

	}

	public void handleLedSetRequest(int x, int y, int s) {
		o.println("got a led set request. x:" + x + ", y:" + y + ", s:" + s);

		Point gridLoc = new Point();
		gridLoc.setXY(x, y);

		Lonome.grid.setStateForPoint(s, gridLoc);
		Lonome.updater.queue.push(gridLoc);

		/*
		 * int pitch = Grid.getNoteForPoint(gridLoc);
		 * 
		 * byte[] data = new byte[3]; data[0] = -112; data[1] = (byte) pitch;
		 * data[2] = (byte) (Lonome.velocity * s);
		 * 
		 * o.println("Sending midi data:" + data[0] + ", " + data[1] + ", " +
		 * data[2]);
		 * 
		 * Lonome.sendMidiData(data);
		 */
	}

	public void handleRotationChange(int newRotation) {
		this.rotation = newRotation;
		o.print("got a rotation change request:");
		o.println(newRotation);
	}

	public void handleInfoRequest(String host, int port) {
		o.println("got an info request. host:" + host + ", port:" + port);

		OscMessage portMessage = new OscMessage(SYSPORTADDRESS);
		portMessage.add(destPort);
		Lonome.sendOscDataToHostAndPort(portMessage, host, port);

		OscMessage hostMessage = new OscMessage(SYSHOSTADDRESS);
		portMessage.add(destHost);
		Lonome.sendOscDataToHostAndPort(hostMessage, host, port);

		OscMessage idMessage = new OscMessage(SYSIDADDRESS);
		idMessage.add(Lonome.serialNumber);
		Lonome.sendOscDataToHostAndPort(idMessage, host, port);

		OscMessage prefixMessage = new OscMessage(SYSPREFIXADDRESS);
		prefixMessage.add(prefix);
		Lonome.sendOscDataToHostAndPort(prefixMessage, host, port);
	}

	public void handleDestPortChange(int newPort) {
		o.print("got a port change request:");
		o.println(newPort);
		this.destPort = newPort;
	}

	public void handleDestHostChange(String newHost) {
		o.print("got a host change request:");
		o.println(newHost);
		this.destHost = newHost;
	}

	public void handlePrefixChange(String newPrefix) {
		o.print("got a prefix change request:");
		o.println(newPrefix);
		this.prefix = newPrefix;
	}

	public int getRotation() {
		return this.rotation;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public int getDestPort() {
		return this.destPort;
	}

	public String getDestHost() {
		return this.destHost;
	}

}
