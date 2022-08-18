import java.io.*;
import java.util.*;

 /*--------------------------------------------------
    Author: Tum Jomkhanthiphol
    Class: COMP282 (M & W, 2:00 - 3:15 pm)
    Assignment #1
    Date handed in: 9/15/2021
    Programming assignment #1, solves sudoku puzzles.
   --------------------------------------------------*/

class Spot {
  private int row, col;

    public Spot(int row, int col) {  
      setRow(row);
      setCol(col);
    }
    public void setRow(int row) {
      this.row = row;
    }
    public void setCol(int col) {    
      this.col = col;
    }
    public int getRow() {    
      return row;
    }
    public int getCol() {    
      return col;
    }
}

class Sudoku {
  private int[][] grid;

  // Construct a Sudoku puzzle from an array of strings
  public Sudoku(String s[]) {
    grid = new int[9][9];
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        grid[row][col] = (int) (s[row].charAt(col + col/3)) - 48;
      }
    }
  }
 
  // Copy constructor
  // Creates a new grid for a new Sudoku problem.
  public Sudoku(Sudoku p) { 
    grid = new int[9][9];
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        grid[row][col] = p.grid[row][col];
      }
    }
  }

  public String toString() { 
    String answer = "\n";
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        if (((col % 3) != 0) || col == 0)
          answer = answer + String.valueOf(grid[row][col]);
        else 
          answer = answer + " | " + String.valueOf(grid[row][col]);
      }
      answer = answer + "\n";
      if ((((row + 1) % 3) == 0) && row != 8)
        answer = answer + "---------------\n";
    }
    return answer;
  }

  // This is for me to use to make it easier to check your answers
  public String toString2() {
    String result = new String();
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        result = result + String.valueOf(grid[row][col]);
      }
    }
    return result;
  }


 // Rotate the sudoku puzzle. Again, this is for me to use in my test program.
  public void rotate() {
    int[][] temp = new int[9][9];
    int row, col;
    for (row = 0; row < 9; row++) {
      for (col = 0; col < 9; col++) {
        temp[col][8 - row] = grid[row][col];
      }
    }
    for (row = 0; row < 9; row++) {
      for (col = 0; col < 9; col++) {
        grid[row][col] = temp[row][col];
      }
    }
  }
 
  // Does the current grid satisfy all the Sudoku rules?
  // In other words, checks to see that no row, col, or box contains some
  // value more than once.
  public boolean isValid() { 
    int num = 0;
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        if (grid[row][col] != 0) {
          num = grid[row][col];
          if (!XdoesRowContain(row, num, col) && !XdoesColContain(col, num, row) && !XdoesBoxContain(row, col, num))
            continue;
          else
            return false;
        }
        if (grid[row][col] == 0)
          continue;
      }
    }
    return true;
  }
 
  private boolean XdoesRowContain(int row, int val, int xcol) {
    for (int i = 0; i < 9; i++) {
      if (i == xcol)
        continue;
      if (val == grid[row][i])
        return true;
    }
    return false;
  }

  private boolean XdoesColContain(int col, int val, int xrow) {
    for (int i = 0; i < 9; i++) {
      if (i == xrow)
        continue;
      if (val == grid[i][col])
        return true;
    }
    return false;
  }

  private boolean XdoesBoxContain(int rowbox, int colbox, int valbox) {
    int rowboxstart = (rowbox / 3) * 3;
    int rowboxend = rowboxstart + 3;
    int colboxstart = (colbox / 3) * 3;
    int colboxend = colboxstart + 3;

    for (int row = rowboxstart; row < rowboxend; row++) {
      for (int col = colboxstart; col < colboxend; col++) {
        if (row == rowbox && col == colbox)
          continue;
        if (grid[row][col] == valbox)
          return true;
      }
    }
    return false;
  }

  // Does val appears in this row of the grid?
  private boolean doesRowContain(int row, int val) { 	
    for (int i = 0; i < 9; i++) {
      if (val == grid[row][i])
        return true;
    }
    return false;
  }

  // Does val appears in this col of the grid?
  private boolean doesColContain(int col, int val) { 
    for (int i = 0; i < 9; i++) {
      if (val == grid[i][col])
        return true;
    }
    return false;
  }

  // Does val appears in this box of the grid?
  private boolean doesBoxContain(int rowbox, int colbox, int val) { 
    int rowboxstart = (rowbox / 3) * 3;
    int rowboxend = rowboxstart + 3;
    int colboxstart = (colbox / 3) * 3;
    int colboxend = colboxstart + 3;

    for (int row = rowboxstart; row < rowboxend; row++) {
      for (int col = colboxstart; col < colboxend; col++) {
        if (grid[row][col] == val)
          return true;
      }
    }
    return false;
  }

  // Return n if n is the only legal value for this spot
  // Return 0 otherwise
  private int uniqueSpotValue(Spot sq) {
    boolean bool = false;
    int answer = 0;
    int num = 0;
    int counter = 0;
    int row = sq.getRow();
    int col = sq.getCol();
    int rowboxstart = (sq.getRow() / 3) * 3;
    int rowboxend = rowboxstart + 3;
    int colboxstart = (sq.getCol() / 3) * 3;
    int colboxend = colboxstart + 3;

    for (int n = 1; n <= 9; n++) {
      if(!doesRowContain(row, n)&&!doesColContain(col, n)&&!doesBoxContain(row, col, n)){
        if (!bool) {
          answer = n;
          bool = true;
          continue;
        }
        if (bool)
          return 0;
      }
    }
    if (bool)
      return answer;
    return 0;
  }
 
  // If there is only one legal spot to put val in this row of the grid
  // then return that Spot
  // Otherwise return null
  private Spot onlyOneSpotinRowForVal(int row, int val) {
    Spot spot = null;
    int counter = 0;
    int arrcounter = 0;
    int valuecounter = 0;
    boolean bool = false;
    boolean first = false; 
    int value = 0;

    // checks if there's one space left and how many zeros in row
    for (int col = 0; col < 9; col++) {
      if (grid[row][col] == 0) {
        counter++;
        spot = new Spot(row, col);
      }
    }
    if (counter == 1) 
      return spot;
    if (counter == 0)
      return null;

    int[] arr = new int[counter];

    for (int col = 0; col < 9; col++) {
      if (grid[row][col] == 0) {
        arr[arrcounter] = col;
        arrcounter++;
      }
    }

    for (int i = 0; i < counter; i++) {
      if (!doesColContain(arr[i], val) && !doesBoxContain(row, arr[i], val)) {
        if (valuecounter == 0) {
          spot = new Spot(row, arr[i]);
          valuecounter++;
        }
        else
          valuecounter++;
      }
    }

    if (valuecounter == 1) 
      return spot;

    return null;
  }

 
  // If there is only one legal spot to put val in this col of the grid
  // then return that Spot
  // Otherwise return null
  private Spot onlyOneSpotinColForVal(int col, int val) {
    Spot spot = null;
    int counter = 0;
    int arrcounter = 0;
    int valuecounter = 0;
    boolean bool = false;
    boolean first = false; 
    int value = 0;

    //checks if there's one space left and how many zeros in row
    for (int row = 0; row < 9; row++) {
      if (grid[row][col] == 0) {
        counter++;
        spot = new Spot(row, col);
      }
    }
    if (counter == 1) 
      return spot;
    if (counter == 0)
      return null;

    int[] arr = new int[counter];

    for (int row = 0; row < 9; row++) {
      if (grid[row][col] == 0) {
        arr[arrcounter] = row;
        arrcounter++;
      }
    }

    for (int i = 0; i < counter; i++) {
      if (!doesRowContain(arr[i], val) && !doesBoxContain(arr[i], col, val)) {
        if (valuecounter == 0) {
          spot = new Spot(arr[i], col);
          valuecounter++;
        }
        else
          valuecounter++;
      }
    }

    if (valuecounter == 1) 
      return spot;

    return null;
  }

 
  // If there is only one legal spot to put val in this box of the grid
  // then return that Spot
  // Otherwise return null
  private Spot onlyOneSpotinBoxForVal(int rowInBox, int colInBox, int val) { 
    Spot spot = null;
    int counter = 0;
    int arrcounter = 0;
    int loopcount = 0;
    int valuecounter = 0;
    boolean bool = false;
    int row = rowInBox * 3;
    int col = colInBox * 3;

    // checks if there's one space left and how many zeros in box
    for (int r = row; r < row + 3; r++) {
      for (int c = col; c < col + 3; c++) {
        if (grid[r][c] == 0) {
          counter++;
          spot = new Spot(r, c);
        }
      }
    }
    if (counter == 1)
      return spot;
    if (counter == 0)
      return null;

    int[] arr = new int[counter * 2];

    for (int r = row; r < row + 3; r++) {
      for (int c = col; c < col + 3; c++) {
        if (grid[r][c] == 0) {
          arr[arrcounter] = r;
          arr[arrcounter + 1] = c;
          arrcounter += 2;
        }
      }
    }
    arrcounter = 0;

    while (loopcount < counter) {
      if (!doesRowContain(arr[arrcounter], val) && !doesColContain(arr[arrcounter + 1], val)) {
        if (valuecounter == 0) {
          spot = new Spot(arr[arrcounter], arr[arrcounter + 1]);
          valuecounter++;
        }
        else
          valuecounter++;
      }
      arrcounter += 2;
      loopcount++;
    }

    if (valuecounter == 1)
      return spot;

    return null;
  }


  // Put as many values as possible into the grid using only the above methods
  // Do this by repeated calling the 4 previous methods until the grid no longer changes
  public void solve() {
    Spot place;
    boolean complete = false;
    while (!complete) {
      complete = true;

      //check rows
      for (int row = 0; row < 9; row++) {
        for (int n = 1; n <= 9; n++) {
          if (!doesRowContain(row, n)) {
            place = onlyOneSpotinRowForVal(row, n);
            if (place != null) {
              grid[place.getRow()][place.getCol()] = n;
              complete = false;
            }
          }
        }
      }
      place = null;

      //check columns
      for (int col = 0; col < 9; col++) {
        for (int n = 1; n <= 9; n++) {
          if (!doesColContain(col, n)) {
            place = onlyOneSpotinColForVal(col, n);
            if (place != null) {
              grid[place.getRow()][place.getCol()] = n;
              complete = false;
            }
          }
        }
      }
      place = null;

      //check boxes
      for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 3; col++) {
          for (int n = 1; n <= 9; n++) {
            if (!doesBoxContain(row * 3, col * 3, n)) {
              place = onlyOneSpotinBoxForVal(row, col, n);
              if (place != null) {
                grid[place.getRow()][place.getCol()] = n;
                complete = false;
              }
            }
          }
        }
      }
      place = null;

      //check unique spot
      for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
          if (grid[row][col] == 0) {
            Spot place1 = new Spot(row, col);
            int n = uniqueSpotValue(place1);
            if (n != 0) {
              grid[place1.getRow()][place1.getCol()] = n;
              complete = false;
            }
          }
        }
      }
    }
  }

  public boolean solved() {
    if (isValid())
      return true;
    return false;
  }
 
  // I haven't decided yet if I want to talk about this -- or if I want you
  // to program it. Using this method allows virtually any Sudoku to be solved.
  // What do you think? :)
  /* public void solveWithRandom() { 

  } */

  public static String myName() {
    return "Tum Jomkhanthiphol";
  }
}