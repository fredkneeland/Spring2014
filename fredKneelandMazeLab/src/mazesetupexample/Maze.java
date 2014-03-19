/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazesetupexample;

import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

/**
 *
 * @author hunterl
 *
 * @author Fred Kneeland
 */
public class Maze extends JFrame {

    private static final int MAX_WIDTH = 255;
    private static final int MAX_HEIGHT = 255;
    
    private char [][] maze = new char[MAX_HEIGHT][MAX_WIDTH];

    private Random random = new Random();
    private JPanel mazePanel = new JPanel();
    private int width = 0;
    private int height = 0;
    private boolean step = false;
    
    private boolean timerFired = false;
    private Timer timer;
    private final int TIMER_DELAY = 200;
    
    private final int SPRITE_WIDTH = 25;
    private final int SPRITE_HEIGHT = 25;
    
    private BufferedImage mazeImage;
    private ImageIcon ground = new ImageIcon("sprites/ground.png");
    private ImageIcon wall1 = new ImageIcon("sprites/cactus.png");
    private ImageIcon wall2 = new ImageIcon("sprites/rock.png");
    private ImageIcon finish = new ImageIcon("sprites/well.png");
    private ImageIcon south1 = new ImageIcon("sprites/cowboy-forward-1.png");
    private ImageIcon south2 = new ImageIcon("sprites/cowboy-forward-2.png");
    private ImageIcon north1 = new ImageIcon("sprites/cowboy-back-1.png");
    private ImageIcon north2 = new ImageIcon("sprites/cowboy-back-2.png");
    private ImageIcon west1 = new ImageIcon("sprites/cowboy-left-1.png");
    private ImageIcon west2 = new ImageIcon("sprites/cowboy-left-2.png");
    private ImageIcon east1 = new ImageIcon("sprites/cowboy-right-1.png");
    private ImageIcon east2 = new ImageIcon("sprites/cowboy-right-2.png");
    
    private long startTime;
    private long currentTime;

    File output = new File("output.txt");
    BufferedReader in;
    boolean foundFile = false;
    
    
    /**
     * Constructor for class Maze.  Opens a text file containing the maze, then attempts to 
     * solve it.
     * 
     * @param   fname   String value containing the filename of the maze to open.
     */
    public Maze(String fname) {        
        openMaze(fname);
        mazeImage = printMaze();

        timer = new Timer(TIMER_DELAY, new TimerHandler());     // setup a Timer to slow the animation down.
        timer.start();
        
        
        addWindowListener(new WindowHandler());     // listen for window event windowClosing
        
        setTitle("Cowboy Maze");
        setSize(width*SPRITE_WIDTH+10, height*SPRITE_HEIGHT+30);
        setVisible(true);

        add(mazePanel);
        setContentPane(mazePanel);
        
        solveMaze();
    }
    
    /**
     * Called from the operating system.  If no command line arguments are supplied,
     * the method displays an error message and exits.  Otherwise, a new instace of
     * Maze() is created with the supplied filename from the command line.
     * 
     * @param   args[]  Command line arguments, the first of which should be the filename to open.
     */
    public static void main(String [] args) {
        int runny = 1;
        if (args.length > 0) {
            new Maze(args[0]);
        }
        else {
            System.out.println();
            System.out.println("Usage: java Maze <filename>.");
            System.out.println("Maximum Maze size:" + MAX_WIDTH + " x " + MAX_HEIGHT + ".");
            System.out.println();
            System.exit(1);
        }
    }
    
    /**
     * Finds the starting location and passes it to the recursive algorithm solve(x, y, facing).
     * The starting location should be the only '.' on the outer wall of the maze.
     */
    public void solveMaze() {
        boolean startFound = false;
        if (!startFound) {
            for (int i=0; i<width; i++) {       // look for the starting location on the top and bottom walls of the Maze.
                if (maze[0][i] == '.') {
                    preSolve(i, 0, "south");
                    startFound = true;
                }
                else if (maze[height-1][i] == '.') {
                    preSolve(i, height-1, "north");
                    startFound = true;
                }
            }
        }
        if (!startFound) {
            for (int i=0; i<height; i++) {      // look for the starting location on the left and right walls of the Maze.
                if (maze[i][0] == '.') {
                    preSolve(0, i, "east");
                    startFound = true;
                }
                else if (maze[i][width-1] == '.') {
                    preSolve(width-1, i, "west");
                    startFound = true;
                }
            }
        }
        if (!startFound) {
            System.out.println("Start not found!");
        }        
    }
    
    
    public void preSolve(int x, int y, String facing)
    {
        //Graphics2D g2 = (Graphics2D)mazePanel.getGraphics();
        //g2.drawImage(mazeImage, null, 0, 0);
        //g2.drawImage(printGuy(facing), x*SPRITE_WIDTH, y*SPRITE_HEIGHT, null, null);
        Scanner input = new Scanner(System.in);
        System.out.println("Press 1 to start");
        input.nextLine();
        startTime = System.currentTimeMillis();
        try
        {
            in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(output)));
            foundFile = true;
        } catch (IOException e) {
            foundFile = false;
        }

        solve(x, y, facing);
    }
    
    /**
     * Recursive algorithm to solve a Maze.  Places a X at locations already visited.
     * This algorithm is very inefficient, it follows the right hand wall and will
     * never find the end if the path leads it in a circle.
     * 
     * @param   x       int value of the current X location in the Maze.
     * @param   y       int value of the current Y location in the Maze.
     * @param   facing  String value holding one of four cardinal directions 
     *                  determined by the current direction facing.
     */
    private void solve(int x, int y, String facing) {
        Graphics2D g2 = (Graphics2D)mazePanel.getGraphics(); //don't mess with the next 
 
        while (!timerFired) {   // wait for the timer.
          try{Thread.sleep(10);} catch(Exception e){}
        }
        timerFired = false;
        currentTime = System.currentTimeMillis();
        if((currentTime - startTime) > 50000)
        {
            closingMethod();
        }
        
        //Do not mess with the above part of this method
        
        
        //Below is where you put your solution to solving the maze.  
        
        if (maze[y][x] != 'F') {  //this is if it doesn't find the finish on a turn.........
            g2.drawImage(mazeImage, null, 0, 0); 
            g2.drawImage(printGuy(facing), x*SPRITE_WIDTH, y*SPRITE_HEIGHT, null, null);
            mazePanel.setSize(width*SPRITE_WIDTH+10, height*SPRITE_HEIGHT+30);
            maze[y][x] = 'X';   // mark this spot as visited. This is how you can keep track of where you've been. 


            //========================= Dis is my code ========================\\


            int newX = x;
            int newY = y;
            String direction = "";

            // first we check to see if we are adjacent to the end
            if (newX < MAX_WIDTH-1 && maze[newY][newX+1] == 'F')
            {
                newX++;
                direction = "east";
            }
            else if (newX > 0 && maze[newY][newX-1] == 'F')
            {
                newX--;
                direction = "east";
            }
            else if (newY < MAX_HEIGHT-1 && maze[newY+1][newX] == 'F')
            {
                newY++;
                direction = "south";
            }
            else if (newY > 0 && maze[newY-1][newX] == 'F')
            {
                newY--;
                direction = "north";
            }
            // if we are not adjacent we go
            else
            {
                if (foundFile)
                {
                    int numbOfTries = 0;
                    try
                    {
                        numbOfTries = in.read();
                    } catch(IOException e) {}

                    // in this case we will do left hand rule
                    if (numbOfTries < 2)
                    {
                        if (facing.equals("south"))
                        {
                            // first we will look to the left which is east
                            if (x < MAX_WIDTH && openSpace(x+1, y))
                            {
                                direction = "east";
                            }
                            // now we will look straight ahead
                            else if (y < MAX_HEIGHT && openSpace(x, y+1))
                            {
                                direction = "south";
                            }
                            // now we will look right or west
                            else if (x > 0 && openSpace(x-1, y))
                            {
                                direction = "west";
                            }
                            else
                            {
                                direction = "north";
                            }
                        }
                        else if (facing.equals("east"))
                        {
                            // first we will look left which is north
                            if (y > 0 && openSpace(x, y-1))
                            {
                                direction = "north";
                            }
                            // go straight if hand is on wall
                            else if (x < MAX_WIDTH-1 && openSpace(x+1, y))
                            {
                                direction = "east";
                            }
                            // other wise go right
                            else if (y < MAX_HEIGHT && openSpace(x, y+1))
                            {
                                direction = "south";
                            }
                            else
                            {
                                direction = "west";
                            }
                        }
                        else if (facing.equals("north"))
                        {
                            // first we look left which is west
                            if (x > 0 && openSpace(x-1, y))
                            {
                                direction = "west";
                            }
                            // if possible go straight
                            else if (y > 0 && openSpace(x, y-1))
                            {
                                direction = "north";
                            }
                            else if (x < MAX_WIDTH-1 && openSpace(x+1, y))
                            {
                                direction = "east";
                            }
                            else
                            {
                                direction = "south";
                            }
                        }
                        else if (facing.equals("west"))
                        {
                            // first we look left/south
                            if (y < MAX_HEIGHT-1 && openSpace(x, y+1))
                            {
                                direction = "south";
                            }
                            // if possible go straight
                            else if (x > 0 && openSpace(x-1, y))
                            {
                                direction = "west";
                            }
                            // else go right or north
                            else if (y > 0 && openSpace(x, y-1))
                            {
                                direction = "north";
                            }
                            else
                            {
                                direction = "east";
                            }
                        }
                    }
                    // in this case we will find the whether right or left was shorter
                    // and then take that path not following any dead ends
                    else
                    {

                    }
                }
                else
                {
                    // with right hand rule we want to look west when south
                    if (facing.equals("south"))
                    {
                        // first we look west
                        if (newX > 0 && openSpace(x-1,y))
                        {
                            direction = "west";
                        }
                        // if our right hand is on the wall go straight
                        else if (newY < MAX_HEIGHT-1 && openSpace(x,y+1))
                        {
                            direction = "south";
                        }
                        //if we are blocked in front we go east
                        else if (newX < MAX_WIDTH && openSpace(x+1, y))
                        {
                            direction = "east";
                        }
                        // last we look north
                        else if (newY > 0 && openSpace(x, y-1))
                        {
                            direction = "north";
                        }
                    }
                    else if (facing.equals("east"))
                    {
                        // first we look to our right or South
                        if (newY < MAX_HEIGHT-1 && openSpace(x, y+1))
                        {
                            direction = "south";
                        }
                        // if there is a wall on our right go straight if possible
                        else if (newX < (MAX_WIDTH-1) && openSpace(x+1, y))
                        {
                            direction = "east";
                        }
                        // if we can't go right or left then go left which is North
                        else if (newY > 0 && openSpace(x, y-1))
                        {
                            direction = "north";
                        }
                        // other wise we go back (west) if possible
                        else
                        {
                            direction = "west";
                        }
                    }
                    else if (facing.equals("north"))
                    {
                        // first we look to our right which is east
                        if (newX < (MAX_WIDTH-1) && openSpace(x+1, y))
                        {
                            direction = "east";
                        }
                        // if our hand is on right wall go forward
                        else if (newY > 0 && openSpace(x, y-1))
                        {
                            direction = "north";
                        }
                        // other wise we look to our left which is west
                        else if (newX > 0 && openSpace(x-1, y))
                        {
                            direction = "west";
                        }
                        // other wise if possible back up
                        else
                        {
                            direction = "south";
                        }
                    }
                    else if (facing.equals("west"))
                    {
                        // first we look to our right which is North
                        if (newY > 0 && openSpace(x, y-1))
                        {
                            direction = "north";
                        }
                        // if our right hand is on wall go straight
                        else if (newX > 0 && openSpace(x-1, y))
                        {
                            direction = "west";
                        }
                        // other wise we go to our left which is south
                        else if (newY < MAX_HEIGHT-1 && openSpace(x, y+1))
                        {
                            direction = "south";
                        }
                        else
                        {
                            direction = "east";
                        }
                    }
                }
            }

            if (direction == "east")
            {
                newX++;
            }
            else if (direction == "south")
            {
                newY++;
            }
            else if (direction == "west")
            {
                newX--;
            }
            else
            {
                newY--;
            }

            System.out.print(direction);
            System.out.println();
            solve(newX, newY, direction);
        }
        else {
            System.out.println("Found the finish!");
            
            //don't mess with the following 4 lines, but you can add stuff below that if you need. 
            currentTime = System.currentTimeMillis();
            long endTime = currentTime - startTime;
            long finalTime = endTime / 1000;
            System.out.println("Final Time = " + finalTime);

            // my stuff after maze is done
            try
            {
                PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            } catch (IOException e) {
                System.out.println();
                System.out.println("failed to set up writer");
            }

            // if this is first round
            if (!foundFile)
            {

            }
            else
            {

            }
            
        }        
    }

    public boolean openSpace(int x, int y)
    {
        if (maze[y][x] == '#' || maze[y][x] == '%')
        {
            return false;
        }
        return true;
    }
    

    
    /**
     * Opens a text file containing a maze and stores the data in the 2D char array maze[][].
     * 
     * @param   fname   String value containing the file name of the maze to open.
     */
    public void openMaze(String fname) {
        String in = "";
        int i = 0;
        try {
            Scanner sc = new Scanner(new File(fname));
            while (sc.hasNext()) {
                in = sc.nextLine();
                in = trimWhitespace(in);
                if (in.length() <= MAX_WIDTH && i < MAX_HEIGHT) {
                    for (int j=0; j<in.length(); j++) {
                        if (in.charAt(j) == '#') {      // if this spot is a wall, randomize the wall peice to display
                            if (random.nextInt(2) == 0) {
                                maze[i][j] = '#';   
                            }
                            else {
                                maze[i][j] = '%';
                            }
                        }
                        else {
                            maze[i][j] = in.charAt(j);
                        }
                    }
                }
                else {
                    System.out.println("Maximum maze size exceeded: (" + MAX_WIDTH + " x " + MAX_HEIGHT + ")!");
                    System.exit(1);
                }
                i++;
            }
            width = in.length();
            height = i;
            System.out.println("("+width+" x "+height+ ") maze opened.");
            System.out.println();
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + e);
        }
    }
    
    /**
     * Removes white space from the supplied string and returns the trimmed String.
     * 
     * @param   s   String value to strip white space from.
     * @return  String stripped of white space.
     */
    public String trimWhitespace(String s) {
        String newString = "";
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) != ' ') {
                newString += s.charAt(i);
            }
        }
        return newString;
    }
    
    /**
     * Returns the sprite facing the direction supplied.
     * 
     * @param   facing  String value containing 1 of 4 cardinal directions to make the sprite face.
     * @return  Image of the sprite facing the proper direction.
     */
    private Image printGuy(String facing) {
        if(facing.equals("south")) {  // draw sprite facing south
            if (step) {
                step = false;
                return south1.getImage();
             }
            else {
                step = true;
                return south2.getImage();
            }
        }
        else if(facing.equals("north")) {  // draw sprite facing north
            if (step) {
                step = false;
                return north1.getImage();
             }
            else {
                step = true;
                return north2.getImage();
            }
        }
        else if(facing.equals("east")) {  // draw sprite facing east
            if (step) {
                step = false;
                return east1.getImage();
            }
            else {
                step = true;
                return east2.getImage();
            }
        }
        else if(facing.equals("west")) {  // draw sprite facing west
            if (step) {
                step = false;
                return west1.getImage();
            }
            else {
                step = true;
                return west2.getImage();
            }
        }
        return null;
    }
    
    /**
     * Prints the Maze using sprites.
     * 
     * @return BufferedImage rendition of the maze.
     */
    public BufferedImage printMaze() {
        BufferedImage mi = new BufferedImage(width*SPRITE_WIDTH, height*SPRITE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = mi.createGraphics();
        
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                if (maze[i][j] == '#') {    // draw wall
                    g2.drawImage(wall1.getImage(), j*SPRITE_WIDTH, i*SPRITE_HEIGHT, null, null);
                }
                else if (maze[i][j] == '%') {   // draw wall
                    g2.drawImage(wall2.getImage(), j*SPRITE_WIDTH, i*SPRITE_HEIGHT, null, null);
                }
                else if (maze[i][j] == '.' || maze[i][j] == 'X') {  // draw ground
                    g2.drawImage(ground.getImage(), j*SPRITE_WIDTH, i*SPRITE_HEIGHT, null, null);
                }
                else if (maze[i][j] == 'F') {   // draw finish
                    g2.drawImage(finish.getImage(), j*SPRITE_WIDTH, i*SPRITE_HEIGHT, null, null);
                }
            }
        }
         return mi;
    }

     public void closingMethod()
     {
         
            long endTime = currentTime - startTime;
            long finalTime = endTime / 100;
            System.out.println("Final Time = " + ((double)finalTime/(double)10));  
            System.exit(0);
      }
    /**
     * Handles the Timer, updates the boolean timerFired every time the Timer ticks.
     * Used to slow the animation down.
     */
    private class TimerHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            timerFired = true;
        }
    }
    
    /**
     * Catch the windowClosing event and exit gracefully.
     */
    private class WindowHandler extends WindowAdapter {
        public void windowClosing (WindowEvent e) {
            removeAll();
            closingMethod();
            System.exit(0);
        }        
    }    

}