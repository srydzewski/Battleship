import java.util.ArrayList;
import java.awt.Point;
public class Level {
   public ArrayList<Square> guesses; //this is a list of all the squares the computer hasn't guessed yet

   //the constructor initializes guesses to include every Square in a grid
   public Level() {
     Square[][] grid = new Square[8][8];
     guesses = new ArrayList<Square>();
     for (int i =0; i < 8; i++) {
       for (int j = 0; j < 8; j++) {
         grid[i][j] = new Square(i,j, false, false);
         guesses.add(grid[i][j]);
       }
     }

   }

   public Point turn() {
     return new Point(0,0);
   }

}
