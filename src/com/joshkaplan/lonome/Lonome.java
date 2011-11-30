package com.joshkaplan.lonome;

import java.io.PrintStream;
import java.net.SocketException;

import netP5.NetAddress;
import oscP5.OscP5;
import oscP5.OscPacket;

import com.apple.dnssd.DNSSD;
import com.apple.dnssd.DNSSDException;
import com.apple.dnssd.DNSSDRegistration;

import de.humatic.mmj.CoreMidiDevice;
import de.humatic.mmj.MidiInput;
import de.humatic.mmj.MidiOutput;
import de.humatic.mmj.MidiSystem;

public class Lonome {

	static String destHost;
	static int destPort;
	static DNSSDRegistration registration;
	static JKMidiListener midiListener = new JKMidiListener();
	static JKOscListener oscListener = new JKOscListener();
	static JKDNSRegisterListener registerListener = new JKDNSRegisterListener();
	static Grid grid = new Grid();
	static GridStateUpdater updater = new GridStateUpdater();

	static OscP5 osc;
	static NetAddress address;

	static PrintStream o = System.out;
	static MidiInput input;
	static MidiOutput output;

	static String serialNumber = "Launchpad";
	static int velocity = 60;
	static int localPort = 9024;

	/**
	 * @param args
	 * @throws SocketException
	 * @throws DNSSDException
	 */
	public static void main(String[] args) {

		CoreMidiDevice[] devices = MidiSystem.getDevices();

		CoreMidiDevice launchpad = null;

		for (CoreMidiDevice x : devices) {
			if (x.getName().equals("Launchpad")) {
				launchpad = x;
				break;
			}
		}

		if (launchpad != null) {
			o.println(launchpad);
		} else {
			o.println("Couldn't find a connected Launchpad. Check your settings and try again, scrub.");
		}

		int inputIndex = launchpad.getInputIndex(0);
		int outputIndex = launchpad.getOutputIndex(0);

		try {
			input = MidiSystem.openMidiInput(inputIndex);
			output = MidiSystem.openMidiOutput(outputIndex);
			input.addMidiListener(midiListener);
		} catch (Exception e) {
			handleError(e);
		}

		updater.run();

		try {
			registration = DNSSD.register(serialNumber, "_monome-osc._udp",
					localPort, registerListener);
		} catch (Exception e) {
			handleError(e);
		}

		osc = new OscP5(oscListener, localPort);
		address = new NetAddress("127.0.0.1", localPort);

	}

	/**
	 * @param data
	 */
	public static void sendMidiData(byte[] data) {
		o.println("Sending midi data:" + data[0] + ", " + data[1] + ", "
				+ data[2]);
		try {
			output.sendMidi(data);
		} catch (Exception e) {
			handleError(e);
		}
	}

	public static void handleError(Exception e) {
		o.println("Something broke");
		e.printStackTrace();
	}

	public static void sendOscDataToDest(OscPacket thePacket) {
		osc.send(thePacket, new NetAddress(destHost, destPort));
	}

	public static void sendOscDataToHostAndPort(OscPacket thePacket,
			String theHost, int thePort) {
		osc.send(thePacket, new NetAddress(theHost, thePort));
	}
}