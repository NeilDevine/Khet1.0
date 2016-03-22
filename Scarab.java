import java.awt.*;
/**
 * Write a description of class Pharaoh here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scarab extends Piece
{
    
    private int x,y;
    public Scarab (Tile pos){
        x = pos.x;
        y = pos.y;
        
        facing = 1;
    }
    
    public void paint(Graphics g){
        if (facing == 1 || facing == 3){
            g.drawLine(x,y,x+50,y+50);
        }
        else{
            g.drawLine(x+50,y, x,y+50);
        }
    }
    
    
    public void rotate(){
        facing++;
        facing = (facing % 4);
    }
    
    
        /**
     * Returns the Direction that the laser must go laserDir the given Direction of the laser.
     */
    public Direction bounceLaser(Direction laserDir){
        switch(facing){
            case 1: case 3:// Quadrant I
            if(laserDir == Direction.WEST) return Direction.NORTH;
            else if(laserDir == Direction.SOUTH) return Direction.EAST;
            else if(laserDir == Direction.NORTH) return Direction.WEST;
            else if(laserDir == Direction.EAST) return Direction.SOUTH;
            return null;
            case 2: case 0: // Quadrant II
            if(laserDir == Direction.SOUTH) return Direction.WEST;
            else if(laserDir == Direction.EAST) return Direction.NORTH;
            else if(laserDir == Direction.WEST) return Direction.SOUTH;
            else if(laserDir == Direction.NORTH) return Direction.EAST;
            return null;
        }
        return null;
    }
}
