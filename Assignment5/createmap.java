import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.io.*;
import java.awt.Desktop;

public class createmap {

	private static final String FILENAME = "interface.svg";

	public static void main(String[] args) {

		BufferedWriter bw = null;
		FileWriter fw = null;

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
			List<String> colors = Arrays.asList("blue", "green", "red", "yellow", "purple");
			
			for (int k=1;k<=shapenumber;k++){
				System.out.println("Choose a color by number (blue|green|red|yellow|purple): ");
				Integer color = reader.nextInt();
				/*System.out.println(colors.get(color-1));*/
				
				System.out.println("Enter width of rectangle: ");
				Integer width = reader.nextInt();
				System.out.println("Enter height of rectangle: ");
				Integer height = reader.nextInt();
				System.out.println("Enter translation to the right: ");
				Integer transright = reader.nextInt();
				System.out.println("Enter translation to the bottom: ");
				Integer transbottom = reader.nextInt();
				
				String rec_tosvg =	"<rect transform=\"translate(" + String.valueOf(transright) + " " + String.valueOf(transbottom) + ")\" " +
									"width=\"" + width + "\" height=\"" + height + "\" " +
									"style=\"fill:" + colors.get(color-1) + ";stroke-width:3;stroke:rgb(0,0,0)\"/>\n";
				bw.write(rec_tosvg);
			}
			reader.close();
			/* ------------ End of shapes for map layout ------------ */
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