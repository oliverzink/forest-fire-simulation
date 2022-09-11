/**
* Thrown when a line from input file is of incorrect format
*
*/
public class LineFormatException extends Exception {

  /**
  * Creates a LineFormatException object.
  *
  * @param lineNumber integer of the line that is of incorrect format
  */
  public LineFormatException(int lineNumber) {
    System.out.println("Line " + lineNumber + " from the input file is not of the correct format.");
  }

}
