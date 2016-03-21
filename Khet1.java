import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
 * Class Khet1 - write a description of the class here
 * 
 * @author (your name) 
 * @version (a version number)
 */
public class Khet1 extends JApplet implements MouseListener
{
    private final int WIDTH = 10;
    private final int HEIGHT = 8;

    private Tile[][] tiles = new Tile[WIDTH][HEIGHT];
    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    public void init(){
        addMouseListener(this);
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                tiles[i][j] = new Tile(50 + 50 *i,50 + 50*j);
            }
        }
        //tiles[9][1].piece = new Mirror(tiles[9][1]);
    }

    /**
     * Paint method for applet.
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {
        // Creating the board
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                tiles[i][j].paint(g);
            }
        }
        // Dot for the Red Side
        g.setColor( Color.RED);
        g.fillOval(519,475,16,16);
        // Dot of the Blue Side
        g.setColor( Color.BLUE);
        g.fillOval(67,17,16,16);

    }

    public void shootLaser(int x, int y, Direction initialDir){
        Laser laser = new Laser(getGraphics(), initialDir, new Point(x,y));
        laser.run();
    }

    public void mouseClicked(MouseEvent e){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                if(tiles[i][j].contains(e.getX(), e.getY())){
                    if(tiles[i][j].piece == null)
                        tiles[i][j].piece = new Mirror(tiles[i][j]);
                    else if(tiles[i][j].piece instanceof Mirror)
                        ((Mirror)tiles[i][j].piece).rotate();
                }
            }
        }
        repaint();
        e.consume();
        //shootLaser(527,475,Direction.NORTH);
    }

    public void mouseEntered(MouseEvent e){}

    public void mouseExited(MouseEvent e){}

    public void mousePressed(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}
}