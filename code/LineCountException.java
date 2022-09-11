/**
* Thrown when the input file doesn't have the expected number of lines
*
*/
public class LineCountException extends Exception {

  /**
  * Creates a LineCountException object.
  *
  * @param expectedLines integer representing the number of lines the file was supposed to have
  * @param actualLines integer representing the number of lines the file actually had
  */
  public LineCountException(String expectedLines, int actualLines) {
    System.out.println("The input file was expected to have " + expectedLines + ", but instead had " + actualLines + " lines.");
  }

}
