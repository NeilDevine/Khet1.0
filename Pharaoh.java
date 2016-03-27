///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
/**
 * This class represents the Pharaoh piece in a game of Khet.
 * 
 * @author (your name) 
 * @version 1.0
 */
public class Pharaoh extends Piece
{
    /**
     * The Constructor for Pharaoh objects.
     * @param t The Tile where the Pharaoh resides
     * @param color The color of the Pharaoh (true for red, false for blue)
     */
    public Pharaoh(Tile t, boolean color){
        x = t.x;
        y = t.y;
        this.color = color;
    }
    
    /**
     * This method paints the piece.
     * @param g The graphics object
     */
    public void paint(Graphics g){
        g.setColor( color ? Color.RED : Color.BLUE );
        g.fillOval(x + 20, y + 20, 10, 10);
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
