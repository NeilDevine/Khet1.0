///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
/**
 * This class represents Scarab pieces in a game of Khet.
 * 
 * @author (your name) 
 * @version 1.0
 */
public class Scarab extends Piece
{
    /**
     * This is the constructor for Scarab objects.
     * @param pos The Tile where the Scarab resides
     * @param color The color of the Scarab (true for red, false for blue)
     */
    public Scarab (Tile pos, boolean color){
        x = pos.x;
        y = pos.y;
        this.color = color;
    }
    
    /**
     * This method paints the piece.
     * @param g The graphics object
     */
    public void paint(Graphics g){
        g.setColor( color ? Color.RED : Color.BLUE );
        if (facing == 1 || facing == 3){
            g.drawLine(x,y,x+50,y+50);
        }
        else{
            g.drawLine(x+50,y, x,y+50);
        }
    }
    
    /**
     * This method determines the direction that the laser should go,
     * given its current direction, when it hits this piece.
     * @param laserDir The laser's current direction
     * @return The laser's next direction
     */
    public Direction bounceLaser(Direction laserDir){
        switch(facing){
            case 1: case 3:// Quadrant I or III
            if(laserDir == Direction.WEST) return Direction.NORTH;
            else if(laserDir == Direction.SOUTH) return Direction.EAST;
            else if(laserDir == Direction.NORTH) return Direction.WEST;
            else if(laserDir == Direction.EAST) return Direction.SOUTH;
            return null;
            case 2: case 0: // Quadrant II or IV
            if(laserDir == Direction.SOUTH) return Direction.WEST;
            else if(laserDir == Direction.EAST) return Direction.NORTH;
            else if(laserDir == Direction.WEST) return Direction.SOUTH;
            else if(laserDir == Direction.NORTH) return Direction.EAST;
            return null;
        }
        return null;
    }
}
