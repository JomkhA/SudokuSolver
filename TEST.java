import java.io.*;
import java.util.*;

 // Author: Tum Jomkhanthiphol
 // Class: COMP282 (M & W, 2:00 - 3:15 pm)
 // Assignment #1
 // Date handed in: 
 // Programming assignment #1, solves sudoku puzzles.


class Sudoku {

  private int[][] grid = new int[9][9];

  // Construct a Sudoku puzzle from an array of strings
  // There is one string per row and each row has a space
  // between each group of 3 int's
  // *** Looking at the data at the top of the test program will make this clear ***

  // charAt is a String method that returns a char from a string
  // This is similar to using [] to access an element in an array

  // if ch is a char with a value between '0' and '9' then
  // (int) ch - 48 will cast it to the actual int value

  // Because of the 2 extra spaces in each row string we need to be a little tricky
  // extracting the int's from those strings

  // This is the code that extracts the int from the string:
  // (int) (s[row].charAt(col + col/3)) - 48
  public Sudoku(String s[]) { 
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        grid[row][col] = (int) (s[row].charAt(col + col/3)) - 48;
      }
    }
  }

  // Hint: use String.valueOf(i) to convert an int to a String
  // Your goal is to make your output look exactly like mine.
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

  public static String myName() {
    return "Tum Jomkhanthiphol";
  }
}