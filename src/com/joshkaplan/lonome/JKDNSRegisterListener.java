package com.joshkaplan.lonome;

import java.io.PrintStream;

import com.apple.dnssd.DNSSDRegistration;
import com.apple.dnssd.DNSSDService;
import com.apple.dnssd.RegisterListener;

public class JKDNSRegisterListener implements RegisterListener {

	static PrintStream o = System.out;

	@Override
	public void operationFailed(DNSSDService service, int errorCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serviceRegistered(DNSSDRegistration registration, int flags,
			String serviceName, String regType, String domain) {

		o.println("registered");

	}

}
