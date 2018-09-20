import java.util.Random;
import java.awt.Point;
public class Easy extends Level {

 public Easy() {
   super();

 }
 //this returns a random Point from a grid that will be the computer's guess
 public Point turn() {
   Random rand = new Random();
   Square guess = guesses.get(rand.nextInt(guesses.size()));
   guesses.remove(guess);
   return new Point(guess.getX(), guess.getY());
 }
}
