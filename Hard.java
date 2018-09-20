import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;

public class Hard extends Intermediate {

  public Hard(ArrayList<Boat> boatArray) { //can we pass this into the constructor
   super(boatArray);
   changeGuesses(boatArray);
  }

  //eliminates a portion of the empty locations from guesses so the computer has a higher chance of shooting a boat
  public void changeGuesses(ArrayList<Boat> boatArray) {
    ArrayList<Point> boatLocs = new ArrayList<Point>();
    //adds the locations of all the boats to an ArrayList
    for (Boat b : boatArray) {
      for (Point p : b.getLocations()) {
        boatLocs.add(p);
      }
    }
    //removes 32 squares from guesses as long as a Boat doesn't exist there
    Square b = null;
    for (int i = 0; i < 32; i++) {
      do {
        b = guesses.get(rand.nextInt(guesses.size()));
      } while (listContains(boatLocs, new Point(b.getX(), b.getY()))); //all my squares will be false is that a problem
      removeFromList(guesses, b);
    }
  }
}
