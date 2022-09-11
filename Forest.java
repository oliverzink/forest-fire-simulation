import java.awt.Color;

/**
* Represents a forest in the ForestFire simulation using a 2-dimensional array.
*
* @author Christine Reilly
*/
public class Forest {

  // PROJECT 2: you may not add instance or class variables

  /** The two-dimensional array that represents the forest */
  private ForestPatch[][] forest;

  /**
  * PROJECT 2: COMPLETE THIS METHOD
  *
  * Creates a Forest object by making a deep copy of the parameter array.
  *
  * @param f The array to copy into this Forest object.
  *
  * @throws IllegalArgumentException if the rows of the parameter array are not all the same length.
  */
  public Forest(ForestPatch[][] f) throws IllegalArgumentException {
    // Project 2: write this method
    this.forest = new ForestPatch[f.length][];
    for(int i = 0; i < f.length; i++){
      ForestPatch[] line = new ForestPatch[f[i].length];
      for(int j = 0; j < f[i].length; j++){

        // throw exception if any line's length doesn't match previous
        if (i > 0) {
            if (f[i].length != f[i-1].length) {
                throw new IllegalArgumentException();
            }
        }

        ForestPatch fP = new ForestPatch(f[i][j].getState());
        line[j] = fP;
      }
      this.forest[i] = line;
    }
  } // end of constructor

  /**
  * PROJECT 2: COMPLETE THIS METHOD
  *
  * Returns a deep copy of the array representing this Forest object.
  *
  * @return A deep copy of the array representing this Forest object.
  */
  public ForestPatch[][] getForest() {
    // Project 2: write this method
    ForestPatch[][] deepForest = new ForestPatch[this.forest.length][];
    for(int i = 0; i < this.forest.length; i++){
      ForestPatch[] line = new ForestPatch[this.forest[i].length];
      for(int j = 0; j < this.forest[i].length; j++){
        ForestPatch fP = new ForestPatch(this.forest[i][j].getState());
        line[j] = fP;
      }
      deepForest[i] = line;
    }
    return deepForest;

  } // end of getForest method

  /**
  * PROJECT 2: COMPLETE THIS METHOD
  *
  * Returns an array of the Color objects associated with each patch in this forest.
  *
  * @return An array of the Color objects associated with each patch in this forest.
  */
  public Color[][] getForestColors() {
    // Project 2: write this method
    Color[][] cArray = new Color[this.forest.length][];
    for(int i = 0; i < this.forest.length; i++){
      Color[] cLine = new Color[this.forest[i].length];
      for(int j = 0; j < this.forest[i].length; j++){
        ForestState fS = this.forest[i][j].getState();
        Color c = fS.getColor();
        cLine[j] = c;
      }
      cArray[i] = cLine;
    }
    return cArray;
  }

  /**
  * PROJECT 2: COMPLETE THIS METHOD
  *
  * Runs one step of the forest fire simulation. The next state of forest patch
  * is determined based on the current state of its neighbors.
  */
  public void runOneStep() {
    // Project 2: write this method

    ForestPatch[][] fCopy = new ForestPatch[this.forest.length][];
    for(int i = 0; i < this.forest.length; i++){
      ForestPatch[] lCopy = new ForestPatch[this.forest[i].length];
      for(int j = 0; j < forest[i].length; j++){
        ForestPatch above;
        ForestPatch below;
        ForestPatch left;
        ForestPatch right;

        int count = 0;

        if(i == 0){
            above = null;
        } else {
          above = forest[i-1][j];
          count += 1;
        }

        if (i == this.forest.length-1) {
          below = null;
        } else {
          below = forest[i+1][j];
          count += 1;
        }

        if (j == 0) {
          left = null;
        } else {
          left = forest[i][j-1];
          count += 1;
        }

        if (j == forest[i].length-1) {
          right = null;
        } else {
          right = forest[i][j+1];
          count += 1;
        }

        ForestPatch[] neighbors = new ForestPatch[count];
        if(right != null && count != 0){
          neighbors[count-1] = right;
          count -= 1;
        }
        if(left != null && count != 0){
          neighbors[count-1] = left;
          count -= 1;
        }
        if(above != null && count != 0){
          neighbors[count-1] = above;
          count -= 1;
        }
        if(below != null && count != 0){
          neighbors[count-1] = below;
          count -= 1;
        }
      ForestState newState = forest[i][j].nextState(neighbors);
      ForestPatch fP = new ForestPatch(newState);
      lCopy[j]= fP;
      }
    fCopy[i] = lCopy;
    }
  this.forest = fCopy;
    //NOTES from lab
    // for each ForestPatch in the grid (use nested loops)
    // - create array of the neighbors (ForestPatch[])
    // --> determining neighbors of a cell -> use conditional excecution to ask if a cell is a corner, if so it has two neighbors
    // --> see if its a non corner edge, then it has three neighbors, see which edge it is on
    // --> otherwise you are in the middle of the grid: 4 neighbors
    // - call this ForestPatch object's nextState method to determine state of this patch for next time step
    // ---> save this new state into a different 2d array object (not this.forest)
    // after every cell has been examined, set this.forest to the 2d array that has state of each patch for next time step

  } // end of runOneStep method

  /**
  * PROJECT 2: COMPLETE THIS METHOD
  *
  * Returns a string representation of this forest where each patch is represented
  * by its enum value. The string has one line per row of the forest.
  *
  * @return A string representation of this forest.
  */
  @Override
  public String toString() {
    // Project 2: write this method
    String forest_string = "";

    for (int i = 0; i < this.forest.length; i++) {
        String line = "";
        for(int j = 0; j < this.forest[i].length; j++)  {
            ForestPatch fP = new ForestPatch(forest[i][j].getState());
            if (forest[i][j].getState() == ForestState.ASH) {
                line += ForestState.ASH.getChar();
            } else if (forest[i][j].getState() == ForestState.GROW_LOW) {
                line += ForestState.GROW_LOW.getChar();
            } else if (forest[i][j].getState() == ForestState.GROW_MED) {
                line += ForestState.GROW_MED.getChar();
            } else if (forest[i][j].getState() == ForestState.GROW_HIGH) {
                line += ForestState.GROW_HIGH.getChar();
            } else if (forest[i][j].getState() == ForestState.BURN_HOT) {
                line += ForestState.BURN_HOT.getChar();
            } else if (forest[i][j].getState() == ForestState.BURN_MED) {
                line += ForestState.BURN_MED.getChar();
            } else if (forest[i][j].getState() == ForestState.BURN_MILD) {
                line += ForestState.BURN_MILD.getChar();
            }
        }
        line += "\n";
        forest_string += line;
    }

    return forest_string;

  } // end of toString method

} // end of Forest class
