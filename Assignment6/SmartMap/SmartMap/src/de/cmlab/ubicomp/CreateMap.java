package de.cmlab.ubicomp;

import java.util.*;
import java.io.*;
import java.awt.Desktop;

public class CreateMap {

	private static final String FILENAME = "./lib/interface.svg";
	
	// GPS domains to be retrieved from MATLAB sensor .txt files
	private static final String ALT = "./lib/SensorData/ALT.txt";
	private static final String LAT = "./lib/SensorData/LAT.txt";
	private static final String LON = "./lib/SensorData/LON.txt";
	private static final String SPEED = "./lib/SensorData/SPEED.txt";
	
	public static float[] calibrate() {
		Scanner scan = new Scanner(System.in);
		int move = scan.nextInt();
		
		float[] finalGPS = {0,0,0,0};
		SmartMap smartmap = new SmartMap();
		float[] calibratedGPS = smartmap.collectGPSdata();
		while (calibratedGPS[0] == 0.0){
			calibratedGPS = smartmap.collectGPSdata();
		}
		finalGPS[0] = calibratedGPS[0];
		
		calibratedGPS = smartmap.collectGPSdata();
		while (calibratedGPS[1] == 0.0){
			calibratedGPS = smartmap.collectGPSdata();
		}
		finalGPS[1] = calibratedGPS[1];
		
		calibratedGPS = smartmap.collectGPSdata();
		while (calibratedGPS[2] == 0.0){
			calibratedGPS = smartmap.collectGPSdata();
		}
		finalGPS[2] = calibratedGPS[2];
		
		calibratedGPS = smartmap.collectGPSdata();
		finalGPS[3] = calibratedGPS[3];
		
		return finalGPS;
	}
	
	public static int[] main(String[] args) {

		BufferedWriter bw = null;
		FileWriter fw = null;
		
		int[] mapcoordinates = {0,0,0,0};
		
		try {
			System.out.println("*** Create Map ***");
			
			String header = "<?xml version=\"1.0\" standalone=\"no\" ?>\n";
			String doctype = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n";
			String opensvg = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"800\" height=\"800\">\n";
			
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(header);
			bw.write(doctype);
			bw.write(opensvg);
			/* ------------ Shapes for map layout ------------ */
			Scanner reader = new Scanner(System.in);
			
			System.out.println("How many shapes would you like to specify?");
			Integer shapenumber = reader.nextInt();
			//Integer shapenumber = 1;
			List<String> colors = Arrays.asList("blue", "green", "red", "yellow", "purple");
			
			for (int k=1;k<=shapenumber;k++){
				System.out.println("Choose a color by number (blue|green|red|yellow|purple): ");
				Integer color = reader.nextInt();
				//Integer color = 2;
				/*System.out.println(colors.get(color-1));*/
				
				System.out.println("Enter width of rectangle: ");
				Integer width = reader.nextInt();
				//Integer width = 600;
				System.out.println("Enter height of rectangle: ");
				Integer height = reader.nextInt();
				//Integer height = 400;
				System.out.println("Enter translation to the right: ");
				Integer transright = reader.nextInt();
				//Integer transright = 100;
				System.out.println("Enter translation to the bottom: ");
				Integer transbottom = reader.nextInt();
				//Integer transbottom = 100;
				
				String rec_tosvg =	"<rect transform=\"translate(" + String.valueOf(transright) + " " + String.valueOf(transbottom) + ")\" " +
									"width=\"" + width + "\" height=\"" + height + "\" " +
									"style=\"fill:" + colors.get(color-1) + ";stroke-width:3;stroke:rgb(0,0,0)\"/>\n";
				bw.write(rec_tosvg);
			}
			/* ------------ End of shapes for map layout ------------ */
			String endsvg = "</svg>";
			bw.write(endsvg);
			bw.close();
			fw.close();
			
			System.out.println("Where did you place the upper left corner of your map?");
			System.out.println("X-Coordinate: ");
			Integer topleft_x = reader.nextInt();
			//Integer topleft_x = 100;
			System.out.println("Y-Coordinate: ");
			Integer topleft_y = reader.nextInt();
			//Integer topleft_y = 100;
			
			System.out.println("Where did you place the lower right corner of your map?");
			System.out.println("X-Coordinate: ");
			Integer bottomright_x = reader.nextInt();
			//Integer bottomright_x = 700;
			System.out.println("Y-Coordinate: ");
			Integer bottomright_y = reader.nextInt();
			//Integer bottomright_y = 500;
			
			mapcoordinates[0] = topleft_x;
			mapcoordinates[1] = topleft_y;
			mapcoordinates[2] = bottomright_x;
			mapcoordinates[3] = bottomright_y;
			float width = bottomright_x - topleft_x;
			float height = bottomright_y - topleft_y;
			
			float[] firstcalibration = calibrate();
			for (int k=0;k<=3;k++){
				System.out.print(firstcalibration[k] + " | ");
			}
			System.out.print("\n");
			float topleftGPS_x = firstcalibration[2];
			float topleftGPS_y = firstcalibration[1];
			
			float[] secondcalibration = calibrate();
			for (int k=0;k<=3;k++){
				System.out.print(secondcalibration[k] + " | ");
			}
			System.out.print("\n");
			float bottomrightGPS_x = secondcalibration[2];
			float bottomrightGPS_y = secondcalibration[1];
			float widthGPS = (bottomrightGPS_x - topleftGPS_x);
			float heightGPS = (bottomrightGPS_y - topleftGPS_y);
			
			reader.close();
			System.out.println("Done");
			
			SmartMap smartmap = new SmartMap();
			Random rand = new Random();
			
			while (1==1){
			int n = rand.nextInt(55) + 45;
			int m = rand.nextInt(25) + 20;
			
			float pointGPS[] = smartmap.collectGPSdata();
			while (pointGPS[1] == 0.0){
				pointGPS = smartmap.collectGPSdata();
			}
			//float point_one = pointGPS[1]*100000;
			float point_one = m;
			
			while (pointGPS[2] == 0.0){
				pointGPS = smartmap.collectGPSdata();
			}
			//float point_two = pointGPS[2]*100000;
			float point_two = n;
			
			widthGPS = 90;
			heightGPS = 25;
			
			float acc = (1/3);
			System.out.print("Current location: " + widthGPS + "\n");
			System.out.print("Current location: " + point_one + " | " + point_two + "\n");
			float x = topleft_x + (((point_two+acc) - topleftGPS_x)/widthGPS)*width;
			float y = topleft_y + (((point_one+acc) - topleftGPS_y)/heightGPS)*height + 500;
			System.out.print("Current location: " + x + " | " + y + "\n");
			
			smartmap.showPositionOnMap(Math.round(x), Math.round(y));
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
		return mapcoordinates;
	}
}