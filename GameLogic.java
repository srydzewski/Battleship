

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    //we need a logical progression of the game. This means that the player will choose where their
    //boats will go, click start, and then the game will run for as long as the game has not ended yet,
    //which is when there are no more boats left to be hit.


    //state of the game. Whether it is done or not.
    boolean gameOver;

    //this indicates which player's turn it is. 1 for human, 2 for computer
    int playerTurn;

    //this is the 2d array of squares that hold the information for each players grid
    Square[][] humanGrid;
    Square[][] compGrid;

    //Randomizer
    Random rand;

    //making some arraylists that hold each players boats
    ArrayList<Boat> humanBoats;
    ArrayList<Boat> compBoats;

    //this is accepting the boat configuration
    boolean boatsAccepted;


    //sets the state of all the variables
    public GameLogic() {
        //setting all the instance variables to something aka initializing them
        boatsAccepted = false;
        humanBoats = new ArrayList<Boat>();
        compBoats = new ArrayList<Boat>();
        rand = new Random();
        gameOver = false;
        playerTurn = 1;
        humanGrid = new Square[8][8];
        compGrid = new Square[8][8];

        //making the grids as fresh grids
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                humanGrid[i][j] = new Square(i,j,false,false);
                compGrid[i][j] = new Square(i,j,false,false);
            }
        }
    }
    public int getPlayerTurn() {
      return playerTurn;
    }
    //this is just to get a visualization of what a grid looks like.
    public void printGrid(Square[][] ray) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(ray[i][j].getContainsBoat() == true) {
                    System.out.print(". ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(ray[i][j].getShot() == true) {
                    System.out.print(". ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }

    //this is a cheater method to test to see if the game ends when it should
    public void cheddar() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                shoot(new Point(i, j) ,humanGrid, humanBoats);
            }
        }
    }

    //converts the points to the system used in the painting of the game
    public Point convertPoints(int x, int y) {
        return new Point(x*50, y*50);
    }


    //this does something scientific
    public void generateRandomBoat(int boatSize, Square[][] ray, ArrayList<Boat> boatRay) {
        Random rand = new Random();
        int horzOrVert = rand.nextInt(2);
        int xc = 0;
        int yc = 0;
        ArrayList<Point> morePermanent = new ArrayList<Point>();

        while (morePermanent.size() < boatSize) {
            morePermanent.clear();
            ArrayList<Point> temp = new ArrayList<Point>();
            //horzontally oriented
            if (horzOrVert == 1){
                xc = rand.nextInt(8-boatSize);
                yc = rand.nextInt(8);
                for (int i = 0; i < boatSize; i++){
                    temp.add(new Point(i + xc, yc));
                }
            }

            //vertically oriented
            else if (horzOrVert == 0){
                xc = rand.nextInt(8);
                yc = (rand.nextInt(8-boatSize));
                for (int j = 0; j < boatSize; j++){
                    temp.add(new Point(xc, j + yc));
                }
            }
            for(Point p: temp) {
                for(int i = 0; i < 8; i++) {
                    for(int j = 0; j < 8; j++) {
                        if(ray[i][j].getX() == p.getX() && ray[i][j].getY() == p.getY()) {
                            if(ray[i][j].containsBoat == false) {
                                morePermanent.add(new Point(p.x, p.y));
                            }
                        }
                    }
                }
            }
        }
        for(Point a: morePermanent) {
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    if(ray[i][j].getX() == a.getX() && ray[i][j].getY() == a.getY()) {
                        ray[i][j].setContainsBoat(true);
                    }
                }
            }
        }
        boatRay.add(new Boat(morePermanent));
    }

    //this is shooting. Takes in a point that is *theoretically* given by a mouse click and
    //then you pass in the array that you are shooting at
    public void shoot(Point point, Square[][] gridRay, ArrayList<Boat> boatLocs) {
      boolean alreadyClicked = false;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(point.getX() == gridRay[i][j].getX() && point.getY() == gridRay[i][j].getY()) {
                  if(gridRay[i][j].getShot()){
                    alreadyClicked = true;
                  }
                    gridRay[i][j].setShot(true);
                }
            }
        }
        if (!(alreadyClicked)) {
          for(Boat b: boatLocs) {
              b.checkHit(point);
          }
        }

    }

    public void switchTurns() {
        if(playerTurn == 1) {
            playerTurn++;
        } else {
            playerTurn = 1;
        }
    }

    public boolean getGameOver() {
      return (areYaDeadYet(humanBoats) || areYaDeadYet(compBoats));
    }

    public boolean areYaDeadYet(ArrayList<Boat> boatRay) {
        int totalBoats = boatRay.size();
        int sunkBoats = 0;
        for(Boat b: boatRay) {
            if(b.isSunk()) {
                sunkBoats++;
            }
        }
        if(totalBoats == sunkBoats) {
            gameOver = true;
        } else {
            gameOver = false;
        }
        return gameOver;
    }

    public void generateAllBoats(int numberOfBoatsPerTeam) {
        for(int i = 0; i < numberOfBoatsPerTeam; i++) {
            generateRandomBoat(i+1, this.humanGrid, this.humanBoats);
            generateRandomBoat(i+1, this.compGrid, this.compBoats);
        }
    }


}
