import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.io.*;
import java.awt.Desktop;

public class calibratemap {

	private static final String FILENAME = "interface.svg";

	public static void main(String[] args) {
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		/* Deletes closing tag </svg> */
		try{
		File file = new File("interface.svg");
		File temp = File.createTempFile("new", ".svg", file.getParentFile());
		String charset = "UTF-8";
		String delete = "</svg>";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
		
		for (String line; (line = reader.readLine()) != null;) {
		    line = line.replace(delete, "");
			writer.println(line);
		}
		reader.close();
		writer.close();
		file.delete();
		temp.renameTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* Append new <svg> objects */
		try {
			fw = new FileWriter(FILENAME, true);
			bw = new BufferedWriter(fw);
			
			/* ------------ Shapes for map calibration - starting point ------------ */
			Scanner reader = new Scanner(System.in);
			
			System.out.println("*** Calibrate Map ***");
			System.out.println("At which coordinates did you set the upper left edge?");
			System.out.println("X-coordinate: ");
			Integer upx = reader.nextInt();
			System.out.println("Y-coordinate: ");
			Integer upy = reader.nextInt();
			System.out.println("At which coordinates did you set the lower right edge?");
			System.out.println("X-coordinate: ");
			Integer lowx = reader.nextInt();
			System.out.println("Y-coordinate: ");
			Integer lowy = reader.nextInt();
			
			/* Map dimensions */
			Integer x = lowx - upx;
			Integer y = lowy - upy;
			
			String rec_start =	"<circle cx=\"" + lowx + "\" cy=\"" + lowy + "\" r=\"10\" stroke=\"black\" stroke-width=\"1\" fill=\"red\"/>\n";
			bw.write(rec_start);
			/* ------------ End of shapes for map calibration - starting point ------------ */
			String endsvg = "</svg>";
			bw.write(endsvg);
			
			System.out.println("Move to starting point and press Enter.");
			try {
			      Desktop desktop = null;
			      if (Desktop.isDesktopSupported()) {
			        desktop = Desktop.getDesktop();
			      }
			       desktop.open(new File(FILENAME));
			    } catch (IOException ioe) {
			      ioe.printStackTrace();
			    }
			
			/*User presses enter, his GPS location will be saved.*/
			/*The user is then asked to move to the second red circle at the upper left of his map. By pressing enter, his GPS location is saved*/

			System.out.println("Done");

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

}