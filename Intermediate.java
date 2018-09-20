import java.util.ArrayList;
import java.awt.Point;
import java.util.Random;

public class Intermediate extends Level {
 //activeHits is a list that keeps track of the hits the computer has made that haven't sunk  a boat
 public ArrayList<Square> activeHits;
 public Random rand;
 public ArrayList<Boat> boatArray;

 public Intermediate(ArrayList<Boat> boats) {
   super();
   activeHits = new ArrayList<Square>();
   rand = new Random();
   boatArray = boats;
 }

//turn will return a Point which is the computer's guess
//when it doesn't hit it it keeps guessing the same spot which it already tried
public Point turn() {

  Square guess = pickGuess();
  removeFromList(guesses, guess);
  for (Boat b : boatArray)  {
      if (listContains(b.getLocations(), new Point(guess.getX(), guess.getY()))) {
           //we clear activeHits because if the boat is sunk we don't want it guessing around it still
           if (b.getLength() <= 1) {
                  activeHits.clear();
           }
           else {
               activeHits.add(guess);
          }
          break;
      }
    }

  return new Point(guess.getX(), guess.getY());
}

//this method picks the Square the computer will guessing
public Square pickGuess() {
 Square guess = null;
  if (activeHits.size() > 0) {
    //if the computer just has one hit it will guess one of the four boxes around it
    if (activeHits.size() == 1) {
      guess = oneHit();
    }
    else { // based off previous hits it will make an intelligent guess
      Square last = activeHits.get(activeHits.size() -1);
      Square before = activeHits.get(activeHits.size() -2);
      Square first = activeHits.get(0);
      if(last.getY() == before.getY()) { //the boat is horizontal
        guess = isHorizontal(last, before,first);

      }
      else { //boat is vertical
          guess = isVertical(last,before,first);
        }
      }
    }
  else { //no active hits means it will guess a random place
       guess = guesses.get(rand.nextInt(guesses.size()));
  }
  return guess;
  }

  //this checks if an ArrayList contains an equal object
  public boolean listContains(ArrayList<Point> list, Point p) {
     boolean b = false;
     for (Point g : list) {
       if(g.x == p.x && g.y == p.y) {
         b = true;
         break;
       }
     }
     return b;
  }

  //checks if an ArrayList contains an equal object
  public boolean listContains(ArrayList<Square> list, Square s) {
    boolean b = false;
    for (Square g : list) {
      if(g.getX() == s.getX() && g.getY() == s.getY()) {
        b = true;
        break;
      }
    }
    return b;
  }

  //removes the square from the list that has the same x and y as the square passed in
  public void removeFromList(ArrayList<Square> list, Square s) {
    for (Square g : list){
      if (g.getX() == s.getX() && g.getY() == s.getY()){
        list.remove(g);
        break;
      }
    }
  }

  //this method will run to pick what the computer will guess when the boat it is attacking is horizontal
  public Square isHorizontal(Square last, Square before, Square first) {
    Square guess = null;
    if (last.getX() > before.getX() && (last.getX() + 1) < 8 ) { // if the guesses have been increasing in the x direction
      if (listContains(guesses, new Square(last.getX() +1, last.getY(), false, false))){ //if you can guess here
          guess = new Square(last.getX() + 1, last.getY(), false, false);
      }
      else { //otherwise go in the other direction
        guess = new Square(first.getX()-1, first.getY(), false, false);
      }

    }
    else {
      if (listContains(guesses, new Square(last.getX() -1, last.getY(), false,false))) { //if you can guess here
          guess = new Square(last.getX() - 1, last.getY(), false, false);
      }
      else { //otherwise go in other direction
        guess = new Square(first.getX() + 1, first.getY(), false,false);
      }

      }
     return guess;
  }

  public Square isVertical(Square last, Square before, Square first) {
    Square guess = null;
    if (last.getY() > before.getY() && (last.getY() + 1) < 8 ) {
      if (listContains(guesses, new Square(last.getX(), last.getY() + 1, false, false))) { //if guesses have been increasing in the y direction
        guess = new Square(last.getX(), last.getY() + 1, false, false);
      }
      else {//go in other direction
        guess = new Square(first.getX(), first.getY() -1 , false, false);
      }

    }
    else {
      if (listContains(guesses, new Square(last.getX(), last.getY() - 1, false, false))) { //if guesses have been increasing in the y direction
        guess = new Square(last.getX(), last.getY() - 1, false, false);
      }
      else { //go in other direction
        guess = new Square(first.getX(), first.getY() + 1 , false, false);
      }
    }
    return guess;
  }

  //will run when activeHits.size() == 0 and will pick one of the squares around that square to guess
  public Square oneHit() {
    Square guess = null;
    Square b = activeHits.get(0);
   ArrayList<Square> fourPicks = new ArrayList<Square>();
   for (Square s : guesses) {
     if ((s.getX() == b.getX() -1 || s.getX() == b.getX() + 1) && s.getY() == b.getY()) {
         fourPicks.add(s);
     }
     if ((s.getY() == b.getY() -1 || s.getY() == b.getY() + 1) && s.getX() == b.getX()) {
       fourPicks.add(s);
     }
   }
   do {
     guess = fourPicks.get(rand.nextInt(fourPicks.size()));
   } while (!(listContains(guesses, guess)));
   return guess;
  }
}
