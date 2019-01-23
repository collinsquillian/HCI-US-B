package de.cmlab.ubicomp;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

import org.apache.xmlrpc.*;

/**
 * Created by Jonas Witt and Georgios Mastoras on 21/02/2019.
 *
 */
public class JavaClient {

	/**
	 * Validates ip String input
	 * given by user
	 * 
	 * @return the validated ip string
	 */
	public static String getIp() {
		Scanner i = new Scanner(System.in);
		String inputStream = "127.0.0.1";
		Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

		for (boolean test = false; test == false;) {
			try {
				System.out.println("enter an ip: ");

				inputStream = i.nextLine(); // reading from user
				test = PATTERN.matcher(inputStream).matches();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return inputStream;
	}

	/**
	 * Validates integer input
	 * given by user
	 * 
	 * @return validated integer
	 */
	public static int getInteger() {
		Scanner i = new Scanner(System.in);
		int value = 0;

		for (boolean test = false; test == false;) {
			try {
				System.out.println("enter an integer: ");
				value = i.nextInt();

				test = true;
				return value;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input");
			}
			i.nextLine();
		}
		return value;
	}

	/**
	 * The main method of the JavaClient class
	 * 
	 * @param args (here not used)
	 */
	public static void main(String[] args) {
		int port = 8088;
		String ip = "127.0.0.1";
		Object result;
		int el1, el2;
		try {
			
			***********
			***********
			try {
				createmap.main(null);
				} catch (IOException e) {
					System.err.println("JavaClient: " + e);
				}
			System.out.print("*** Calibration successful ***");
			***********
			***********
			
				  
			System.out.println("Please enter the ip and port number of server...");
			ip = getIp();
			System.out.print("and for the port number ");
			port = getInteger();

			XmlRpcClient client = new XmlRpcClient("http://" + ip + ":" + String.valueOf(port) + "/");
			Vector<Integer> params = new Vector<Integer>();
			System.out.println();
			System.out.println(
					"Please enter a sequence of requests to remote procedure \'sum\'.  (Halt program to stop.)");
			System.out.println();

			do {
				/*
				 * All the parameters of the procedure call are collected in a Vector.
				 */
				params.clear();
				System.out.print("First number, ");
				el1 = getInteger();
				System.out.print("Second number, ");
				el2 = getInteger();
				params.addElement(new Integer(el1));
				params.addElement(new Integer(el2));
				/*
				 * Here "sample" denotes a handler that is defined in the server.
				 */
				result = client.execute("sample.sum", params);
				/*
				 * the result of the remote procedure call
				 * is always an Object and it has to be casted
				 * to the appropriate type.
				 */
			int sum = ((Integer) result).intValue();
				System.out.println("Server has returned the sum: " + sum);
				System.out.println();
			} while (Boolean.TRUE);

		} catch (Exception exception) {
			System.err.println("JavaClient: " + exception);
		}
	}
}
