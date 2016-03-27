///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
/**
 * This class represents a Tile on the board in a game of Khet.
 * 
 * @author (your name) 
 * @version 1.0
 */
public class Tile
{
    int x, y; // The x and y coordinates (upper left) of the Tile
    Piece piece; // The piece, if any

    /**
     * Constructor for objects of class Tile.
     * @param x The x coordinate of the Tile
     * @param y The y coordinate of the Tile
     */
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        piece = null;
    }

    /**
     * This method clears the Tile.
     */
    public void clear(){
        piece = null;
    }

    /**
     * This method paints this Tile.
     * @param g The graphics object
     */
    public void paint(Graphics g){
        clear(g);
        if(piece != null){
            piece.paint(g);
        }
    }

    /**
     * This method redraws the Tile
     * @param g The graphics object
     */
    private void clear(Graphics g){
        g.setColor( Color.WHITE );
        g.fillRect(x,y,50,50);
        g.setColor( Color.BLACK );
        g.drawRect(x, y, 50, 50);
    }

    /**
     * This method determines if a point is in the Tile.
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     * @return Whether the point is in the Tile
     */
    public boolean contains(int x, int y){
        return x >= this.x && x < this.x+50 &&
        y >= this.y && y < this.y+50;
    }

    /**
     * This method adds a Pyramid to the Tile.
     * @param color The color of the piece (true for red, false for blue)
     */
    public void addPyramid(boolean color){
        piece = new Pyramid(this, color);
    }

    /**
     * This method adds a Scarab to the Tile.
     * @param color The color of the piece (true for red, false for blue)
     */
    public void addScarab(boolean color){
        piece = new Scarab(this, color);
    }

    /**
     * This method adds an Obelisk to the Tile.
     * @param color The color of the piece (true for red, false for blue)
     */
    public void addObelisk(boolean color){
        piece = new Obelisk(this, color);
    }

    /**
     * This method adds a Pharaoh to the Tile.
     * @param color The color of the piece (true for red, false for blue)
     */
    public void addPharaoh(boolean color){
        piece = new Pharaoh(this, color);
    }

    /**
     * Removes a piece from the Tile.
     */
    public void removePiece(){
        if(piece instanceof Obelisk){
            if(((Obelisk)piece).isStacked)
                ((Obelisk)piece).setStacked(false);
            else
                piece = null;
        }
        else{
            piece = null;
        }
    }

    /**
     * This method highlights this Tile with a certain color.
     * @param g The graphics object
     * @param c The color to highlight the Tile with
     */
    public void highlight(Graphics g, Color c){
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        g.setColor( new Color(red, green, blue, 100) );
        g.fillRect(x + 1,y + 1,49,49);
    }
}
