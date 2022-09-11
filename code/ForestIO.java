import java.io.*;
import java.util.*;

/**
* Class for reading the input file and writing to the output file
*
* @author Joey Goff and Ollie Zink
*/
public class ForestIO {

    /**
    * Constructor for creating ForestIO objects
    *
    */
    public ForestIO() {
    }

    /**
    * Method for reading the input file and returning an array of arrays of Forest Patch objects
    *
    * @param filePath String text of the input file path
    * @return An array of arrays of ForestPatch objects
    *
    * @throws LineFormatException if any lines are not of the corret format
    * @throws LineCountException if the actual number of lines does not match the number of lines indicated in the input file
    */
    public static ForestPatch[][] reader(String filePath) throws LineFormatException, LineCountException {
        try {
            Scanner file = new Scanner(new File(filePath));

            int lineCount = 1;

            // Save information from the first line
            String s = file.nextLine();
            String[] fileInfo1 = s.split(" ");

            // If any elements can't be converted from strings into doubles, throw an exception
            for (int i = 0; i < fileInfo1.length; i++){
              try{
                Double.parseDouble(fileInfo1[i]);
              } catch(NumberFormatException e){
                  throw new LineFormatException(lineCount);
              }
            }

            // If there aren't 10 elements in first line, throw exception
            if(fileInfo1.length != 10){
              throw new LineFormatException(lineCount);
            }

            ForestPatch.rand = new Random(226);
            ForestPatch.burnHot_burnMed = Double.parseDouble(fileInfo1[0]);
            ForestPatch.burnMed_burnMild = Double.parseDouble(fileInfo1[1]);
            ForestPatch.burnMild_ashes = Double.parseDouble(fileInfo1[2]);
            ForestPatch.ashes_growLow = Double.parseDouble(fileInfo1[3]);
            ForestPatch.growLow_growMed = Double.parseDouble(fileInfo1[4]);
            ForestPatch.growMed_growHigh = Double.parseDouble(fileInfo1[5]);
            ForestPatch.growHigh_burnHot_spon = Double.parseDouble(fileInfo1[6]);
            ForestPatch.growHigh_burnHot_neighbor = Double.parseDouble(fileInfo1[7]);

            // Create an array of arrays of Forest Patch objects with dimensions given in input file
            ForestPatch[][] myForestArray = new ForestPatch[Integer.parseInt(fileInfo1[8])][Integer.parseInt(fileInfo1[9])];


            while(file.hasNextLine()) {
                  lineCount += 1;
                  String line = file.nextLine();

                  // If there any line doesn't have enough columns, throw an exception
                  if(line.length() != Integer.parseInt(fileInfo1[9])){
                      throw new LineFormatException(lineCount);
                  }

                  ForestPatch[] lineArray = new ForestPatch[Integer.parseInt(fileInfo1[9])];

                  // all lines except for the first line
                  for (int i=0; i<line.length(); i++) {

                      if (line.charAt(i) == ForestState.ASH.getChar()) {
                          lineArray[i] = new ForestPatch(ForestState.ASH);
                      } else if (line.charAt(i) == ForestState.GROW_LOW.getChar()) {
                          lineArray[i] = new ForestPatch(ForestState.GROW_LOW);
                      } else if (line.charAt(i) == ForestState.GROW_MED.getChar()) {
                          lineArray[i] = new ForestPatch(ForestState.GROW_MED);
                      } else if (line.charAt(i) == ForestState.GROW_HIGH.getChar()) {
                          lineArray[i] = new ForestPatch(ForestState.GROW_HIGH);
                      } else if (line.charAt(i) == ForestState.BURN_HOT.getChar()) {
                          lineArray[i] = new ForestPatch(ForestState.BURN_HOT);
                      } else if (line.charAt(i) == ForestState.BURN_MED.getChar()) {
                          lineArray[i] = new ForestPatch(ForestState.BURN_MED);
                      } else if (line.charAt(i) == ForestState.BURN_MILD.getChar()) {
                          lineArray[i] = new ForestPatch(ForestState.BURN_MILD);
                      }
                      else {
                          throw new LineFormatException(lineCount);
                      }

                  } // end of for loop
                  myForestArray[lineCount-2] = lineArray;


            } // end of while loop

            // subtract first line from line count so it can match line count indicated in input file
            lineCount -= 1;

            file.close();

            // If there are too many or too few rows, throw an exception
            if (lineCount != Integer.parseInt(fileInfo1[8])){
              throw new LineCountException(fileInfo1[8], lineCount);
            }

            return myForestArray;

        } catch(FileNotFoundException e) {
            System.out.println("Invalid File Name");
            e.printStackTrace();
            System.exit(1);
            return null;

        }
    } // end of reader method


    /**
    * Method for writing to the output file
    *
    * @param array Array of arrays of ForestPatch objects representing the forest
    * @param outFilePath String text of the output file path
    * @param outFilePath String text of the input file path in order to copy the first line information
    *
    * @throws FileNotFoundException if the file paths don't lead to files
    */
    public static void output(ForestPatch[][] array, String outFilePath, String inFilePath) {
        try {
            // Store input from first line of the input file
            Scanner inFile = new Scanner(new File(inFilePath));
            String s = inFile.nextLine();

            // Print information to the output file
            PrintStream outFile = new PrintStream(new File(outFilePath));
            outFile.println(s);

            // Grab each letter from the states of the array given and print to file
            for (int i=0; i<array.length; i++) {
                for (int j=0; j<array[i].length; j++) {
                    ForestState item_state = array[i][j].getState();
                    outFile.print(item_state.getChar());
                }
                outFile.println("");
            }

            outFile.close();

        } catch(FileNotFoundException e) {
            // System.out.println(e.getMessage());
            System.out.println("Invalid File Name");
            e.printStackTrace();
            System.exit(1);
        }

    } // end of output method

} // end of ForestIO class
