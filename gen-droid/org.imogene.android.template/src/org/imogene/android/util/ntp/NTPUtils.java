package org.imogene.android.util.ntp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.NumberFormat;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpUtils;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;
import org.imogene.android.util.Logger;

/***
 * This is based on the example program demonstrating how to use the NTPUDPClient class. This program sends a Datagram
 * client request packet to a Network time Protocol (NTP) service port on a specified server, retrieves the time, and
 * prints it to standard output along with the fields from the NTP message header (e.g. stratum level, reference id,
 * poll interval, root delay, mode, ...) See <A HREF="ftp://ftp.rfc-editor.org/in-notes/rfc868.txt"> the spec </A> for
 * details.
 * <p>
 * Usage: NTPClient <hostname-or-address-list> <br>
 * Example: NTPClient clock.psu.edu
 * 
 * @author Jason Mathews, MITRE Corp
 ***/
public class NTPUtils {

	private static final String TAG = NTPUtils.class.getName();

	private static final NumberFormat numberFormat = new java.text.DecimalFormat("0.00");

	/**
	 * The timeout to use after opening the socket.
	 */
	private static final int TIMEOUT = 10 * 1000;

	/**
	 * Process <code>TimeInfo</code> object and print its details.
	 * 
	 * @param info <code>TimeInfo</code> object.
	 */
	public static String processResponse(TimeInfo info) {
		String output = "";

		NtpV3Packet message = info.getMessage();
		int stratum = message.getStratum();
		String refType;
		if (stratum <= 0) {
			refType = "(Unspecified or Unavailable)";
		} else if (stratum == 1) {
			refType = "(Primary Reference; e.g., GPS)";
		} else {
			refType = "(Secondary Reference; e.g. via NTP or SNTP)";
		}
		// stratum should be 0..15...
		output += "<p><b>Server:</b><br/>Stratum: " + stratum + " " + refType;

		int version = message.getVersion();
		int li = message.getLeapIndicator();
		output += "<br/>Leap: " + li + "<br/>" + "Version: " + version + "<br/>Precision: " + message.getPrecision();

		output += "<br/>Mode: " + message.getModeName() + " (" + message.getMode() + ")";
		int poll = message.getPoll();
		// poll value typically btwn MINPOLL (4) and MAXPOLL (14)
		output += "<br/>Poll: " + (poll <= 0 ? 1 : (int) Math.pow(2, poll)) + " seconds" + " (2 ** " + poll + ")";
		double disp = message.getRootDispersionInMillisDouble();
		output += "<br/>Rootdelay: " + numberFormat.format(message.getRootDelayInMillisDouble())
				+ "<br/>Rootdispersion: " + numberFormat.format(disp);

		int refId = message.getReferenceId();
		String refAddr = NtpUtils.getHostAddress(refId);
		String refName = null;
		if (refId != 0) {
			if (refAddr.equals("127.127.1.0")) {
				refName = "LOCAL"; // This is the ref address for the Local Clock
			} else if (stratum >= 2) {
				// If reference id has 127.127 prefix then it uses its own reference clock
				// defined in the form 127.127.clock-type.unit-num (e.g. 127.127.8.0 mode 5
				// for GENERIC DCF77 AM; see refclock.htm from the NTP software distribution.
				if (!refAddr.startsWith("127.127")) {
					try {
						InetAddress addr = InetAddress.getByName(refAddr);
						String name = addr.getHostName();
						if (name != null && !name.equals(refAddr)) {
							refName = name;
						}
					} catch (UnknownHostException e) {
						// some stratum-2 servers sync to ref clock device but fudge stratum level
						// higher... (e.g. 2)
						// ref not valid host maybe it's a reference clock name?
						// otherwise just show the ref IP address.
						refName = NtpUtils.getReferenceClock(message);
					}
				}
			} else if (version >= 3 && (stratum == 0 || stratum == 1)) {
				refName = NtpUtils.getReferenceClock(message);
				// refname usually have at least 3 characters (e.g. GPS, WWV, LCL, etc.)
			}
			// otherwise give up on naming the beast...
		}
		if (refName != null && refName.length() > 1) {
			refAddr += " (" + refName + ")";
		}
		output += "<p><b>Reference Identifier: </b><br/>" + refAddr + "</p>";

		TimeStamp refNtpTime = message.getReferenceTimeStamp();
		output += "<p><b>Reference Timestamp:</b><br/>" + refNtpTime.toDateString() + "</p>";

		// Originate Time is time request sent by client (t1)
		TimeStamp origNtpTime = message.getOriginateTimeStamp();
		output += "<p><b>Originate Timestamp: </b><br/>" + origNtpTime.toDateString() + "</p>";

		long destTime = info.getReturnTime();
		// Receive Time is time request received by server (t2)
		TimeStamp rcvNtpTime = message.getReceiveTimeStamp();
		output += "<p><b>Receive Timestamp: </b><br/>" + rcvNtpTime.toDateString() + "</p>";

		// Transmit time is time reply sent by server (t3)
		TimeStamp xmitNtpTime = message.getTransmitTimeStamp();
		output += "<p><b>Transmit Timestamp: </b><br/>" + xmitNtpTime.toDateString() + "</p>";

		// Destination time is time reply received by client (t4)
		TimeStamp destNtpTime = TimeStamp.getNtpTime(destTime);
		output += "<p><b>Destination Timestamp: </b><br/>" + destNtpTime.toDateString() + "</p>";

		info.computeDetails(); // compute offset/delay if not already done
		Long offsetValue = info.getOffset();
		Long delayValue = info.getDelay();
		String delay = (delayValue == null) ? "N/A" : delayValue.toString();
		String offset = (offsetValue == null) ? "N/A" : offsetValue.toString();

		// offset in ms
		output += "<p><b>Computed offset: </b><br/>Roundtrip delay(ms): " + delay + "<br/>Clock offset(ms): " + offset
				+ "</p>";

		return output;
	}

	/**
	 * Queries NTP server to get details
	 * 
	 * @param ntpServerHostname
	 */
	public static TimeInfo detailedQuery(String ntpServerHostname) throws IOException, SocketException {
		NTPUDPClient client = new NTPUDPClient();
		// We want to timeout if a response takes longer than 10 seconds
		client.setDefaultTimeout(TIMEOUT);

		TimeInfo info = null;
		try {
			client.open();

			InetAddress hostAddr = InetAddress.getByName(ntpServerHostname);
			Logger.i(TAG, "> " + hostAddr.getHostName() + "/" + hostAddr.getHostAddress());
			info = client.getTime(hostAddr);
		} finally {
			client.close();
		}

		return info;
	}

	/**
	 * Queries NTP server using UDP to get offset
	 * 
	 * @param ntpServerHostname
	 * @return offset
	 * @throws IOException , SocketException
	 */
	public static long query(String ntpServerHostname) throws IOException, SocketException {
		NTPUDPClient client = new NTPUDPClient();
		// We want to timeout if a response takes longer than 10 seconds
		client.setDefaultTimeout(TIMEOUT);

		TimeInfo info = null;
		try {
			client.open();

			InetAddress hostAddr = InetAddress.getByName(ntpServerHostname);
			Logger.i(TAG, "Trying to get time from " + hostAddr.getHostName() + "/" + hostAddr.getHostAddress());

			info = client.getTime(hostAddr);
		} finally {
			client.close();
		}

		// compute offset/delay if not already done
		info.computeDetails();

		return info.getOffset();
	}
}