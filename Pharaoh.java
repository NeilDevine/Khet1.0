import java.awt.*;
/**
 * Write a description of class Pharaoh here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pharaoh extends Piece
{
    public void paint(Graphics g){
        
    }
    
        /**
     * Returns the Direction that the laser must go laserDir the given Direction of the laser.
     */
    public Direction bounceLaser(Direction laserDir){
        switch(facing){
            case 1:// Quadrant I
            if(laserDir == Direction.WEST) return Direction.NORTH;
            else if(laserDir == Direction.SOUTH) return Direction.EAST;
            return null;
            case 2: // Quadrant II
            if(laserDir == Direction.SOUTH) return Direction.WEST;
            else if(laserDir == Direction.EAST) return Direction.NORTH;
            return null;
            case 3: // Quadrant III
            if(laserDir == Direction.NORTH) return Direction.WEST;
            else if(laserDir == Direction.EAST) return Direction.SOUTH;
            return null;
            case 0: // Quadrant IV
            if(laserDir == Direction.WEST) return Direction.SOUTH;
            else if(laserDir == Direction.NORTH) return Direction.EAST;
            return null;
        }
        return Direction.WEST;
    }
}
