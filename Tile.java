import java.awt.*;
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile
{
    int x, y;
    Piece piece;

    /**
     * Constructor for objects of class Tile
     */
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        piece = null;
    }

    /**
     * Constructor for objects of class Tile
     */
    public Tile(Piece p){
        piece = p;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece p){
        piece = p;
    }

    public void clear(){
        piece = null;
    }

    public void paint(Graphics g){
        clear(g);
        if(piece != null){
            piece.paint(g);
        }
    }

    private void clear(Graphics g){
        g.setColor( Color.WHITE );
        g.fillRect(x,y,50,50);
        g.setColor( Color.BLACK );
        g.drawRect(x, y, 50, 50);
    }

    public boolean contains(int x, int y){
        return x >= this.x && x < this.x+50 && y >= this.y && y < this.y+50;
    }
}
