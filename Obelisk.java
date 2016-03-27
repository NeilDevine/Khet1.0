///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
/**
 * This class represents Obelisk pieces in a game of Khet.
 * 
 * @author (your name) 
 * @version 1.0
 */
public class Obelisk extends Piece
{
    boolean isStacked; // Whether the Obelisk is stacked
    
    /**
     * This is the constructor for Obelisk objects.
     * @param t The Tile where this Obelisk resides
     * @param color The color of this Obelisk (true for red, false for blue)
     */
    public Obelisk(Tile t, boolean color){
        x = t.x;
        y = t.y;
        this.color = color;
        isStacked = false;
    }

    /**
     * This method paints the piece.
     * @param g The graphics object
     */
    public void paint(Graphics g){
        g.setColor( color ? Color.RED : Color.BLUE );
        g.fillRect(x + 10,y + 10,29,30);
        if(isStacked) {
            g.setColor( Color.GREEN );
            g.fillRect(x + 10, y + 40, 29, 5);
        }
    }
    
    /**
     * This method sets the isStacked variable to s.
     * @param s The new isStacked variable
     */
    public void setStacked(boolean s){
        isStacked = s;
    }

    /**
     * This method determines the direction that the laser should go,
     * given its current direction, when it hits this piece.
     * @param laserDir The laser's current direction
     * @return The laser's next direction
     */
    public Direction bounceLaser(Direction laserDir){
        return null;
    }
}
