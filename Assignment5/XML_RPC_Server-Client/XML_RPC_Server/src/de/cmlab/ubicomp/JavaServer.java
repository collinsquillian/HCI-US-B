package de.cmlab.ubicomp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.xmlrpc.*;

/**
 * Created by Georgios Mastoras on 17/11/2018.
 * 
 * A server that uses Java to process XML-RPC messages.
 * It makes use of built-in classes available in org.apache.xmlrpc.*
 * 
 */
public class JavaServer {

	/**
	 * The procedure sum that is called remotely 
	 * is implemented as a public method in a class.
	 * It takes two parameters and returns their sum
	 * 
	 * @param x first integer
	 * @param y second integer
	 * @return the sum of two parameters x, y
	 */
	public Integer sum(int x, int y) {
		System.out.println("I have just received a request with parameters " + x + ", " + y);
		return new Integer(x + y);
	}

	/**
	 * The main method of the JavaServer class
	 * 
	 * @param args (here not used)
	 */
	public static void main(String[] args) {
		int port = 8088;
		String ip;
		try {
			// start a web server at a certain port, usually above 1000
			System.out.println("Attempting to start XML-RPC Server...");

			try {
				Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
				while (interfaces.hasMoreElements()) {
					NetworkInterface iface = interfaces.nextElement();
					// filters out 127.0.0.1 and inactive interfaces like Virtual Machines
					if (iface.isLoopback() || !iface.isUp() || iface.getDisplayName().contains("VMware")
							|| iface.getDisplayName().contains("Virtual"))
						continue;

					Enumeration<InetAddress> addresses = iface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						InetAddress addr = addresses.nextElement();
						ip = addr.getHostAddress();
						if (!ip.contains(":")) { // if not ipv6
							System.out.println("on ip: " + ip + " port " + port);
						}
					}
				}
			} catch (SocketException e) {
				throw new RuntimeException(e);
			}

			/*
			 * The package org.apache.xmlrpc 
			 * contains the class WebServer 
			 * for a XML-RPC Server implementation.
			 * 
			 * The server is initialized by the port number (here: 80).
			 */
			WebServer server = new WebServer(port);
			/*
			 * An instance of the same server class 
			 * is then associated with a handler (here "sample")
			 * that is accessible by the client.
			 */
			server.addHandler("sample", new JavaServer()); 
			server.start();

			System.out.println("... started successfully.");
			System.out.println();
			System.out.println("Accepting requests. (Halt program to stop.)");

		} catch (Exception exception) {
			System.err.println("JavaServer: " + exception);
		}
	}
}
