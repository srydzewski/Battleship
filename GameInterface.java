import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInterface extends JFrame implements ActionListener {
    public JFrame frame = new JFrame("Battleship");

    public String[] diffs = {"Easy","Medium","Hard"};
    public JComboBox diffBox = new JComboBox(diffs);
    ImageIcon image;

    int difficulty = 1;

    public Drawer drawer;

    public GameInterface() {
        try {
            image = new ImageIcon("images/battleship-logo.gif");
        } catch (Exception e) {
            System.out.println("The Image thing didnt work!");
        }
        mainMenu();

    }

    public void mainMenu() {
        //clearing the frame
        frame.getContentPane().removeAll();

        //This makes the basics of the JFrame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        //creating a JPanel to hold all the stuff in the window
        JPanel panel = new JPanel();
        JPanel a = new JPanel(new GridLayout(6,0));

        //this is the logo panel at the top
        JPanel logo = new JPanel(new BorderLayout());
        JLabel picLabel = new JLabel(image);
        logo.add(picLabel);

        //creating a JButtons to have for SP and MP games
        JButton single = new JButton("Start Game");
        single.setActionCommand("startSingle");
        single.addActionListener(this);
        a.add(single);

        //creating a JLabel
        JLabel label = new JLabel("Select a Difficulty :");
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        a.add(label);

        //This is adding the ComboBox with all the difficulties
        diffBox.addActionListener(this);
        diffBox.setActionCommand("diff");
        diffBox.setAlignmentX(SwingConstants.TRAILING);
        a.add(diffBox);


        //adding the little panels to the big panel
        panel.add(logo);
        panel.add(a);

        //adding the big panel to the frame
        frame.add(panel);

        //adding everything to the frame
        frame.setResizable(false);
        frame.setVisible(true);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedDiff = (String) diffBox.getSelectedItem();

        if("diff".equals(e.getActionCommand())) {
            if(selectedDiff.equals("Easy")) {
                difficulty = 1;
                System.out.println("Diff = " + difficulty);
            } else if(selectedDiff.equals("Medium")) {
                difficulty = 2;
                System.out.println("Diff = " + difficulty);
            } else if(selectedDiff.equals("Hard")) {
                difficulty = 3;
                System.out.println("Diff = " + difficulty);
            }
        } else if("startSingle".equals(e.getActionCommand())) {
            if(difficulty == 1) {
                System.out.println("The game has started");
                drawer = new Drawer(1);
                drawer.runGame();
            } else if(difficulty == 2) {
              System.out.println("New Intermediate game has started");
              drawer = new Drawer(2);
              drawer.runGame();
            } else if(difficulty == 3) {
              System.out.println("New Hard game has started");
              drawer = new Drawer(3);
              drawer.runGame();
            }
        }
    }
}
