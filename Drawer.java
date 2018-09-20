    import javax.swing.*;
    import java.awt.event.MouseEvent;
    import java.awt.event.MouseListener;
    import java.util.ArrayList;
    import java.awt.Color;
    import java.awt.Graphics;
    import javax.swing.JFrame;
    import javax.swing.JPanel;
    import java.awt.Point;
    import java.awt.event.KeyEvent;
    import java.awt.event.KeyListener;
    import java.awt.Font;
    import java.awt.image.BufferedImage;
    import java.io.File;
    import java.io.IOException;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import javax.imageio.ImageIO;

    public class Drawer extends JPanel implements MouseListener, KeyListener {

        //ints for the panel size
        final int WIDTH = 400;
        final int HEIGHT = 400;
        boolean canClick;
        static GameLogic gl;
        Easy easy;
        Intermediate inter;
        Hard hard;
        private BufferedImage image;
        public int difficulty;

        public Drawer(int diff) {
          difficulty = diff;

            this.setSize(HEIGHT,WIDTH);
            addMouseListener(this);
            addKeyListener(this);
            setFocusable(true);

            canClick = true;
            gl = new GameLogic();
            gl.generateAllBoats(5);
            inter = new Intermediate(gl.humanBoats);
            hard = new Hard(gl.humanBoats);
            easy = new Easy();

            try{
                image = ImageIO.read(new File("images/EndScreen.jpg"));
            } catch (IOException ex){

            }
        }

        //player shoots the computer's grid
        @Override
        public void mouseClicked(MouseEvent e) {
          Point p = new Point(e.getX()/50, e.getY()/50);
          if (gl.getPlayerTurn() == 1 && canClick){
            gl.shoot(p, gl.compGrid, gl.compBoats);
            canClick = false;
          }
          repaint();



        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        public void keyPressed(KeyEvent e){

        }
        public void keyReleased(KeyEvent e){

        }
        //uses space bar to switch between grids and has the computer take a turn
        public void keyTyped(KeyEvent e){
          char c = e.getKeyChar();
          if (c == KeyEvent.VK_SPACE) {
            gl.switchTurns();
            repaint();
            if (gl.getPlayerTurn() == 2) {
              Point p =  new Point(0,0);
              if (difficulty == 1) {

                p = easy.turn();
              }
              else if (difficulty == 2) {
                p = inter.turn();
              }
              else {
                p = hard.turn();
              }
              gl.shoot(p, gl.humanGrid, gl.humanBoats);
              repaint();
            }
            if(gl.getPlayerTurn() == 1) {
              canClick = true;
            }

          }



        }
        //paints either the player's grid, the computer's grid, or the end screen if the game is over
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Color c = new Color(109,172,206);
            g.setColor(c);
            g.fillRect(0,0, WIDTH, HEIGHT);


            drawGrid(g);

            if(gl.areYaDeadYet(gl.humanBoats) || gl.areYaDeadYet(gl.compBoats)){
              canClick = false;
              g.setColor(Color.WHITE);
              g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
              g.drawImage(image,0,0,this);
              if (gl.areYaDeadYet(gl.humanBoats)){
                g.drawString("YOU LOSE", 120, 300);
              }
              else{
                g.drawString("YOU WIN", 120, 300);
              }
            }

            else if (gl.playerTurn == 1){
                //drawBoats(g, gl.compBoats); uncomment if you want to cheat
              for (Boat b : gl.compBoats){
                if (b.isSunk()){
                  drawBoat(g,b);
                }
              }
               drawShots(g, gl.compGrid);




            }
            else{
          drawBoats(g, gl.humanBoats);
          drawShots(g, gl.humanGrid);


        }
      }

        //draws an empty grid
        public void drawGrid(Graphics g) {
            for(int i = 0; i < WIDTH; i += 50) {
                g.setColor(Color.BLACK);
                g.drawLine(i,0, i, HEIGHT);
            }
            for(int j = 0; j < HEIGHT; j += 50) {
                g.drawLine(0, j, WIDTH, j);
            }
        }
        //draws a boat on the grid
        public void drawBoat(Graphics g, Boat b){
            Color c = g.getColor();
            g.setColor(Color.BLUE);
        for (int i = 0; i < b.getLocations().size(); i++){
          Point p = gl.convertPoints(b.getLocations().get(i).x, b.getLocations().get(i).y);
                g.fillRect(p.x, p.y, 50, 50);
        }

            g.setColor(c);
        }
        //draws all the boats on the grid
        public void drawBoats(Graphics g, ArrayList<Boat> boats){
            for (Boat b : boats) drawBoat(g, b);

        }
        //draws the shots on the grid
        public void drawShots(Graphics g, Square[][] grid){
          Color c = g.getColor();
          for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
              Square sq = grid[i][j];
              if (sq.getShot() == true){
                Point p = gl.convertPoints(sq.getX(), sq.getY());

                if (sq.getContainsBoat() == true){
                  g.setColor(Color.GREEN);
                  g.fillOval(p.x, p.y, 50, 50);
                }
                else{
                g.setColor(Color.RED);
                g.fillOval(p.x,p.y, 50, 50);}

              }
            }
          }
          g.setColor(c);
        }
        //starts the game
        public void runGame() {
            JFrame frame = new JFrame("Battleship");
            frame.setSize(400,422);
            frame.setContentPane(new Drawer(difficulty));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
            frame.setResizable(false);

        }

    }
