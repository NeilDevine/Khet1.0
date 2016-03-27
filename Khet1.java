///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * This class simulates, in an applet, Khet 1.0.
 * 
 * @author (your name) 
 * @version 1.0
 */
public class Khet1 extends JApplet implements MouseListener,
MouseWheelListener, ActionListener
{
    private final int WIDTH = 10;
    private final int HEIGHT = 8;

    private Tile[][] tiles = new Tile[WIDTH][HEIGHT];

    private Tile heldTile; // For moving pieces

    private int phase; // 0 for red setup, 1 for blue setup, 2 for playing
    private boolean turn; // false for blue, true for red
    private boolean turnDone; // for gameplay phase

    private int pieceHolding; // For custom setup

    private Button restartButton; // For restarting the game

    private class Side // PDS for each player's pieces
    {
        int pyramids, scarabs, obelisks, pharaohs;
        /**
         * The method determines if a Side has all of its pieces
         * @return Whether the Side is full
         */
        public boolean isFull(){
            return pyramids == 7 &&
            scarabs == 2 &&
            obelisks == 4 &&
            pharaohs == 1;
        }
    }
    private Side redSide, blueSide;

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    public void init(){    
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                tiles[i][j] = new Tile(50 + 50 *i,50 + 50*j);
            }
        }
        startGame();

        setLayout(new FlowLayout());

        restartButton = new Button("Restart Game");
        add(restartButton);
        restartButton.addActionListener(this);

        addMouseListener(this);
        addMouseWheelListener(this);
    }

    /**
     * Paint method for applet. Note: it paints all Tiles on the board,
     * both dots, and phase dependent information
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {
        g.setColor( Color.WHITE );
        g.fillRect(0, 0, getWidth(), getHeight()); // Reset Screen

        g.setColor( Color.BLACK );
        // Creating the board
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                tiles[i][j].paint(g);
            }
        }

        // Highlight each side
        for(int h = 0; h < HEIGHT; h++){
            tiles[0][h].highlight(g, Color.BLUE);
            tiles[9][h].highlight(g, Color.RED);
        }

        // Dot for the Red Side
        g.setColor( Color.RED );
        g.fillOval(519,475,16,16);
        // Dot of the Blue Side
        g.setColor( Color.BLUE );
        g.fillOval(67,17,16,16);

        switch(phase){ 
            case 0: // Red setup
            g.setColor( Color.RED );
            g.drawString("Red Setup", 10, 20);
            break;

            case 1: // Blue setup
            g.setColor( Color.BLUE );
            g.drawString("Blue Setup", 10, 20);
            break;

            case 2: // During gameplay
            g.setColor( turn ? Color.RED : Color.BLUE );
            g.drawString(turn ? "Red's turn" : "Blue's turn", 10, 20);
            break;
        }
    }

    /**
     * This method animates either the laser for either side.
     * @param color true for Red, false for Blue
     */
    private void shootLaser(boolean color){
        Graphics g = getGraphics();
        g.setColor(Color.RED);
        Point p = color ? new Point(525, 449) : new Point(75, 51);
        Direction d = color ? Direction.NORTH : Direction.SOUTH;
        Laser laser = new Laser(g, d, p);
        laser.run();
    }

    /**
     * This method runs when the mouse is clicked.
     * @param e The MouseEvent representing the state of the mouse
     */
    public void mouseClicked(MouseEvent e){
        Tile t = tileAt(new Point(e.getX(), e.getY()));
        switch(phase){
            case 0: case 1: // Setup phase
            boolean red = phase == 0 ? true : false;
            Side thisSide = red ? redSide : blueSide;

            boolean valid = t.piece == null && 
                (red ? t.x != 50 : t.x != 500);
            switch(pieceHolding){
                case 0: // Pyramids
                if(thisSide.pyramids < 7 && valid){
                    t.addPyramid(red);
                    thisSide.pyramids++;
                }
                break;

                case 1: // Scarabs
                if(thisSide.scarabs < 2 && valid){
                    t.addScarab(red);
                    thisSide.scarabs++;
                }
                break;

                case 2: // Obelisks
                if(thisSide.obelisks < 4 && valid){
                    t.addObelisk(red);
                    thisSide.obelisks++;
                }
                else if(thisSide.obelisks < 4 && t.piece instanceof Obelisk){
                    if(!((Obelisk)t.piece).isStacked){
                        ((Obelisk)t.piece).setStacked(true);
                        thisSide.obelisks++;
                    }
                }
                break;

                case 3: // Pharaohs
                if(thisSide.pharaohs < 1 && valid){
                    t.addPharaoh(red);
                    thisSide.pharaohs++;
                }
                break;
            }
            // For easy rotation
            if(t.piece instanceof Pyramid)
                ((Pyramid)t.piece).rotate();
            else if(t.piece instanceof Scarab)
                ((Scarab)t.piece).rotate();

            if(thisSide.isFull())
                phase++;
            break;

            case 2: // Gameplay phase

            if(t == null){
                if(e.getX() > 500 && e.getX() < 550 &&
                e.getY() > 450 && e.getY() < 500){
                    if(turnDone && turn){
                        // Shoot red laser
                        shootLaser(true);
                        turn = !turn;
                        turnDone = false;
                    }
                }
                else if(e.getX() > 50 && e.getX() < 100 &&
                e.getY() > 0  && e.getY() < 50){
                    if(turnDone && !turn){
                        // Shoot blue laser
                        shootLaser(false);
                        turn = !turn;
                        turnDone = false;
                    }
                }
            }
            else if(t != null && t.piece.color == turn && !turnDone){
                if(t.piece instanceof Pyramid){
                    ((Pyramid)t.piece).rotate();
                    turnDone = true;
                }
                else if(t.piece instanceof Scarab){
                    ((Scarab)t.piece).rotate();
                    turnDone = true;
                }
            }
            break;
        }

        repaint();
        e.consume();
    }

    /**
     * This method runs when the mouse enters the screen.
     * @param e The MouseEvent representing the state of the mouse
     */
    public void mouseEntered(MouseEvent e){}

    /**
     * This method runs when the mouse exits the screen.
     * @param e The MouseEvent representing the state of the mouse
     */
    public void mouseExited(MouseEvent e){}

    /**
     * This method runs when the mouse is pressed. It handles moving pieces.
     * @param e The MouseEvent representing the state of the mouse
     */
    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Tile t = tileAt(new Point(x, y));
        if(phase == 2 && t.piece.color == turn && !turnDone){
            // highlight adjacent tiles
            for(int r = x - 50; r <= x + 50; r+= 50){
                for(int c = y - 50; c <= y + 50; c += 50){
                    Tile adj = tileAt(new Point(r, c));
                    if(adj != null && validMove(t, adj)){
                        adj.highlight(getGraphics(), Color.YELLOW);
                    }
                }
            }
            // Store the current Tile
            heldTile = t;
        }

        e.consume();
    }

    /**
     * This method determines if a move from a Tile, from, to a Tile, to,
     * is valid.
     * @param from The starting Tile
     * @param to The ending Tile
     * @return Whether this move is valid (true for valid, false otherwise)
     */
    private boolean validMove(Tile from, Tile to){
        if(Math.abs(from.x - to.x) <= 50 && Math.abs(from.y - to.y) <= 50
        && from.piece != null && from != to){
            if(to.piece == null){
                if(from.piece.color && to.x != 50) return true;
                else if(!from.piece.color && to.x != 500) return true;
            }
            if(from.piece instanceof Scarab){
                return to.piece instanceof Pyramid || 
                to.piece instanceof Obelisk;
            }
            else if(from.piece instanceof Obelisk && 
            to.piece instanceof Obelisk){
                return !((Obelisk)from.piece).isStacked && 
                !((Obelisk)(to.piece)).isStacked &&
                from.piece.color == to.piece.color;
            }
        }
        return false;
    }

    /**
     * This method runs when the mouse is released. It handles moving pieces.
     * @param e The MouseEvent representing the state of the mouse
     */
    public void mouseReleased(MouseEvent e){
        if(heldTile!= null && heldTile.piece != null){
            int x = e.getX();
            int y = e.getY();
            Tile t = tileAt(new Point(x, y));

            if(validMove(heldTile, t) && phase == 2 && !turnDone){
                if(t.piece == null){
                    heldTile.piece.move(t);
                    heldTile.clear();
                }
                else if(heldTile.piece instanceof Scarab){
                    // Switch the pieces
                    Piece temp = new Scarab(heldTile,heldTile.piece.color);
                    temp.facing = heldTile.piece.facing;

                    t.piece.move(heldTile);
                    heldTile.piece = t.piece;

                    temp.move(t);
                    t.piece = temp;
                }
                else if(heldTile.piece instanceof Obelisk){
                    // Remove the held piece and stack the next piece
                    heldTile.removePiece();
                    ((Obelisk)t.piece).setStacked(true);
                }
                turnDone = true;
            }
        }
        heldTile = null; // Reset heldTile
        repaint();
        e.consume();
    }

    /**
     * This method runs when the mouse wheel is scrolled. It is used
     * for determining which piece to place in the setup phase(s).
     * @param e The MouseWheelEvent representing the Mouse Scroll itself
     */
    public void mouseWheelMoved(MouseWheelEvent e){
        pieceHolding++;
        pieceHolding = pieceHolding % 4;
        showStatus(pieceHolding + ""); // Notify user
    }

    /**
     * This method determines the Tile (in tiles) given a certain point.
     * @param p The point which may or may not be on the board.
     * @return The Tile in which p resides in (null if it off the board)
     */
    private Tile tileAt(Point p){
        if(inBoard(p)){
            int row = ((int)p.getX() - 50) / 50;
            int col = ((int)p.getY() - 50) / 50;
            return tiles[row][col];
        }
        return null;
    }

    /**
     * This method determines if a Point, p, is in the board.
     * @param p The given point or location
     * @return Whether p is in the board (true for yes, false for no)
     */
    private boolean inBoard(Point p){
        int x = (int)p.getX();
        int y = (int)p.getY();
        return x > 50 && x < 550 && y > 50 && y < 450;
    }

    /**
     * This method sets the board (tiles) to a preset board based on 'setup'
     * @param setup The preset board layout: valid values are 0, 1, or 2
     */
    private void setBoard(int setup){
        switch(setup){
            case 0: // Set board to Classic Layout
            tiles[2][2].addPyramid(true);
            tiles[2][3].addScarab(true);
            tiles[2][4].addPharaoh(true);
            tiles[2][5].addObelisk(true);

            tiles[5][2].addPyramid(false);
            tiles[5][3].addScarab(false);
            tiles[5][4].addPharaoh(false);
            tiles[5][5].addObelisk(false);
            break;

            case 1: // Set board to Imhotep Layout

            break;

            case 2: // Set board to Dynasty Layout

            break;
        }
        phase = 2; // Start playing
    }

    /**
     * This method runs when an action is performed (in this case, a button).
     * @param e The ActionEvent that represents the action occured
     */
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == restartButton){
            startGame();
        }
        repaint();
    }

    /**
     * This method merely sets up all relevant fields to start the game.
     */
    private void startGame(){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                tiles[i][j].clear();
            }
        }
        phase = 0;
        turnDone = false;
        turn = true;
        Object[] layouts = { "Classic", "Imhotep", "Dynasty", "Custom" };
        int n = JOptionPane.showOptionDialog(
                null,
                "Which layout would you like?",
                "Choose Layout",
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE,
                null, layouts, layouts[0]);
        if(n < 3 && n > -1)
            setBoard(n);
        redSide = new Side();
        blueSide = new Side();
    }

    private class Laser // Class specifically for the laser
    {
        Point prev; // The previous point for the animation
        Point next; // The next point for the animation
        Direction dir; // The direction of the laser
        Graphics g; // The graphics object

        /**
         * This is the constructor for a laser object.
         * @param g The graphics object
         * @param initialDir The initial Direction of the laser
         * @param initialPoint The starting position of the laser
         */
        public Laser(Graphics g, Direction initialDir, Point initialPoint){
            this.g = g;
            dir = initialDir;
            prev = initialPoint;
        }

        /**
         * This method merely animates the laser after construction.
         */
        private void run(){
            try{
                while(inBoard(prev)){
                    Thread.sleep(5);
                    Tile t = tileAt(prev);
                    if(t != null && t.piece != null){
                        if (prev.getX() == t.x + 25){
                            if (prev.getY() == t.y + 25){
                                // Reset the direction
                                dir = ((t.piece)).bounceLaser(dir); 
                                if(dir == null){
                                    if(t.piece instanceof Pharaoh){ 
                                        // The game is over
                                        String team = 
                                            t.piece.color ? "Blue" : "Red";
                                        JOptionPane.showMessageDialog(null,
                                            team + " wins!");
                                        startGame();
                                        break;
                                    }
                                    // Kill the piece
                                    tileAt(prev).removePiece();
                                    play(getDocumentBase(), "scream.wav");
                                    break;
                                }
                            }
                        }
                    }
                    paintNext();
                }
                repaint();
            }
            catch(InterruptedException e){}
        }

        /**
         * This method paints the next line in the animation based on its
         * current direction.
         */
        private void paintNext(){
            next = new Point(prev);
            switch(dir){
                case NORTH:
                next.translate(0,-1);
                break;

                case SOUTH:
                next.translate(0,1);
                break;

                case EAST:
                next.translate(1,0);
                break;

                case WEST:
                next.translate(-1,0);
                break;
            }
            g.drawLine((int)prev.getX(),(int)prev.getY(), 
                (int)next.getX(), (int)next.getY());
            prev = new Point(next);
        }
    }
}