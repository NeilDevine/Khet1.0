///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
/**
 * This class represents a piece in a game of Khet.
 * 
 * @author (your name here)
 * @version 1.0
 */
public abstract class Piece
{
    int facing = 1; // Where the piece is facing (quadrant)
    boolean color; // The color of the piece (true for red, false for blue)
    int x, y; // The x and y coordinates of the Tile where the piece is
    
    /**
     * This method paints the piece.
     * @param g The graphics object
     */
    public abstract void paint(Graphics g);
    
    /**
     * This method determines the direction that the laser should go,
     * given its current direction, when it hits this piece.
     * @param laserDir The laser's current direction
     * @return The laser's next direction
     */
    public abstract Direction bounceLaser(Direction laserDir);
    
    /**
     * This method moves this piece to another Tile.
     * @param to The Tile to move to
     */
    public void move(Tile to){
        x = to.x;
        y = to.y;
        to.piece = this;
    }
    
    /** 
     * This method rotates the piece 90 degrees.
     */
    public void rotate(){
        facing++;
        facing = (facing % 4);
    }
}
