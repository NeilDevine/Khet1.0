///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
/**
 * This class represents the Pyramid piece in a game of Khet.
 * 
 * @author (your name) 
 * @version 1.0
 */
public class Pyramid extends Piece
{
    Polygon graphic, graphic1, graphic2, graphic3, graphic4;
    /**
     * This is the constructor for Pyramid objects.
     * @param pos The Tile where the Pyramid resides
     * @param color The color of the Pyramid (true for red, false for blue)
     */
    public Pyramid(Tile pos, boolean color){
        this.color = color;
        x = pos.x;
        y = pos.y;
        setGraphic();
    }

    /** 
     * This method rotates the piece 90 degrees.
     */
    public void rotate(){
        super.rotate();
        setGraphic();
    }

    /**
     * This method sets the graphic polygon based on the position.
     */
    private void setGraphic(){
        graphic1 = new Polygon(); 
        graphic1.addPoint(x + 1,y + 1);
        graphic1.addPoint(x + 1,y+50);
        graphic1.addPoint(x + 50,y + 50);

        graphic2 = new Polygon();
        graphic2.addPoint(x + 50,y + 50);
        graphic2.addPoint(x + 50,y+1);
        graphic2.addPoint(x + 1,y + 50);

        graphic3 = new Polygon();
        graphic3.addPoint(x + 1,y+1);
        graphic3.addPoint(x + 50,y+1);
        graphic3.addPoint(x + 50,y + 50);

        graphic4 = new Polygon(); 
        graphic4.addPoint(x + 1,y+1);
        graphic4.addPoint(x + 50,y+1);
        graphic4.addPoint(x + 1,y + 50);  
        
        switch(facing){
            case 1: // Quadrant I
            graphic = graphic1; break;
            case 2: // Quadrant II
            graphic = graphic2; break;
            case 3: // Quadrant III
            graphic = graphic3; break;
            case 0: // Quadrant IV
            graphic = graphic4; break;
        }
    }

    /**
     * This method paints the piece.
     * @param g The graphics object
     */
    public void paint(Graphics g){
        g.setColor( color ? Color.RED : Color.BLUE );
        g.fillPolygon(graphic);
    }

    /**
     * This method determines the direction that the laser should go,
     * given its current direction, when it hits this piece.
     * @param laserDir The laser's current direction
     * @return The laser's next direction
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
        return null;
    }
    
    /**
     * This method moves this piece to another Tile.
     * @param to The Tile to move to
     */
    public void move(Tile to){
        super.move(to);
        setGraphic();
    }
}
