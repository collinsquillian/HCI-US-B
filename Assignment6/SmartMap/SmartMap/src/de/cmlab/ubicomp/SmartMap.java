package de.cmlab.ubicomp;

import java.awt.Desktop;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.net.*;
import java.nio.file.Files;

public class SmartMap {
	private static final String FILENAME = "./lib/interface.svg";
	private static final String WORKFILENAME = "./lib/new.svg";
	
	// GPS domains to be retrieved from MATLAB sensor .txt files
	private static final String ALT = "./lib/SensorData/ALT.txt";
	private static final String LAT = "./lib/SensorData/LAT.txt";
	private static final String LON = "./lib/SensorData/LON.txt";
	private static final String SPEED = "./lib/SensorData/SPEED.txt";

	private static void copyFile(File source, File dest) throws IOException {
	    Files.copy(source.toPath(), dest.toPath());
	}
	
	/**
	 * Initializes .svg file (user map)
	 * 
	 */
	public static void initializeSVGFile() {
		try {
			File file = new File(FILENAME);
			File temp = new File(WORKFILENAME);
			temp.delete();
			copyFile(file, temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes svg closing tag
	 * 
	 */
	public static void deleteClosingSvgTag() {
		try {
			File file = new File(FILENAME);
			File temp = File.createTempFile("temp", ".svg", file.getParentFile());
			String charset = "UTF-8";
			String delete = "</svg>";

			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
			
			String line = reader.readLine();
			while (line != null) {
				line = line.replace(delete, "");
				writer.println(line);
				System.out.print(line + "\n");
				line = reader.readLine();
			}
			reader.close();
			writer.close();
			file.delete();
			temp.renameTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Append new <svg> circle indicating the coordinates on map
	 * 
	 */
	public static void showPositionOnMap(int x, int y) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		deleteClosingSvgTag();

		/* Append new <svg> objects */
		try {
			fw = new FileWriter(FILENAME, true);
			bw = new BufferedWriter(fw);

			String rec_start = "<circle cx=\"" + x + "\" cy=\"" + y
					+ "\" r=\"5\" stroke=\"black\" stroke-width=\"1\" fill=\"red\"/>\n";
			bw.write(rec_start);
			String endsvg = "</svg>";
			bw.write(endsvg);
			try {
				Desktop desktop = null;
				if (Desktop.isDesktopSupported()) {
					desktop = Desktop.getDesktop();
				}
				desktop.open(new File(FILENAME));
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/*
	 * Collect GPS data for a single time point
	 * 
	 */
	public static float[] collectGPSdata() {
		float[] timepoint_GPS = {0,0,0,0};
		
		/* Collect ALT data for latest time point */
		timepoint_GPS[0] = retrieveGPSdomain(ALT);
		/* Collect LAT data for latest time point */
		timepoint_GPS[1] = retrieveGPSdomain(LAT);
		/* Collect LON data for latest time point */
		timepoint_GPS[2] = retrieveGPSdomain(LON);
		/* Collect SPEED data for latest time point */
		timepoint_GPS[3] = retrieveGPSdomain(SPEED);
		
		return timepoint_GPS;
	}
	
	public static float retrieveGPSdomain(String domain){
		float result = 0;
		try {
			File file = new File(domain);
			File temp = File.createTempFile("temp", ".txt", file.getParentFile());
			String charset = "UTF-8";
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
			
			Integer counter = 1;
			for (String line; (line = reader.readLine()) != null;) {
				if (counter == 1){
					System.out.print("Collecting " + domain + "_coordinate:	" + line + "\n");
					result = Float.parseFloat(line.trim());
					System.out.print("**************** Remaining GPS information ****************" + "\n");
				} else {
					System.out.print(line + "\n");
					writer.println(line);
				}
				counter += 1;
			}
			//reader.close();
			//writer.close();
			file.delete();
			temp.renameTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static void main(String args[]) {
		float[] currentGPS = collectGPSdata();
		
		for (int k=0;k<=3;k++){
			System.out.print(currentGPS[k] + " | ");
			System.out.print("\n");
		}
		
		CreateMap createmap = new CreateMap();
		String[] arguments_cr = new String[] {"create"};
		int[] usermap_dim = createmap.main(arguments_cr);
	}
}