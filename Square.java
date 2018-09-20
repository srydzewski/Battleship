public class Square {
    public int x;
    public int y;
    public boolean isShot;
    public boolean containsBoat;

    public Square(int xDim, int yDim, boolean shot, boolean haveBoat) {
        x = xDim;
        y = yDim;
        isShot = shot;
        containsBoat = haveBoat;
    }

    public boolean getShot() {
        return isShot;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setShot(boolean tralse) {
        isShot = tralse;
    }

    public void setContainsBoat(boolean tralse) {
        containsBoat = tralse;
    }

    public boolean getContainsBoat() {
        return containsBoat;
    }
}
