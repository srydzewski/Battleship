import java.awt.*;
import java.util.ArrayList;

public class Boat {
    private boolean sunk;
    private int length;
    private ArrayList<Point> locations;


    public Boat(ArrayList<Point> ray) {
        sunk = false;
        length = ray.size();
        locations = ray;
    }
    //checks if they hit the boat
    public boolean checkHit(Point p) {
        for (Point b : locations) {
            if (b.equals(p)) {
                length--;
                checkSunk();
                return true;
            }
        }
        return false;
    }
    //returns the value of sunk
    public boolean isSunk() {
        return sunk;
    }
   //checks if the boat is sunk
    public void checkSunk() {
        if (length == 0) {
            sunk = true;
        }
    }
    //returns the length of the boat that hasn't been hit
    public int getLength() {
        return length;
    }
   //returns the location of the boat
    public ArrayList<Point> getLocations() {
      return locations;
    }

}
