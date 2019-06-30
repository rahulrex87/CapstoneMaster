package Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utility {
	public static List<Items> readItemsFromCSV(String fileName) {
        List<Items> items = new ArrayList<Items>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try{
        	BufferedReader br = Files.newBufferedReader(pathToFile,StandardCharsets.US_ASCII);
            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(";");

                Items item = createItem(attributes);

                // adding book into ArrayList
                items.add(item);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return items;
    }
	
	private static Items createItem(String[] metadata) {
        String itemName = metadata[0];
        String price = metadata[1];
        // create and return book of this metadata
        return new Items(itemName, price);
    }

}
