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
public class Khet1 extends JApplet implements MouseListener, MouseWheelListener
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
        addMouseWheelListener(this);
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
                    if (e.getButton() == MouseEvent.BUTTON1){
                        if(tiles[i][j].piece == null)
                            tiles[i][j].piece = new Mirror(tiles[i][j]);
                        else if(tiles[i][j].piece instanceof Mirror)
                            ((Mirror)tiles[i][j].piece).rotate();
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3){
                        if(tiles[i][j].piece == null)
                            tiles[i][j].piece = new Scarab(tiles[i][j]);
                        else if(tiles[i][j].piece instanceof Scarab)
                            ((Scarab)tiles[i][j].piece).rotate();
                    }
                }

            }
        }
        repaint();
        //         int i = (e.getX()-50) / 50;
        //         int j = (e.getY() - 50) / 50;
        //         try{
        //             if(tiles[i][j].piece == null)
        //                 tiles[i][j].piece = new Mirror(tiles[i][j]);
        //             else if(tiles[i][j].piece instanceof Mirror)
        //                 ((Mirror)tiles[i][j].piece).rotate();
        //         }catch(IndexOutOfBoundsException exc){
        // 
        //         }
        //         repaint();
        e.consume();
        //shootLaser(527,475,Direction.NORTH);
    }

    public void mouseEntered(MouseEvent e){}

    public void mouseExited(MouseEvent e){}

    public void mousePressed(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}

    public void mouseWheelMoved(MouseWheelEvent e){
        shootLaser(525,449,Direction.NORTH);
    }

    private Tile tileAt(Point p){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                if(tiles[i][j].contains((int)p.getX(), (int)p.getY())){
                    return tiles[i][j];
                }
            }
        }
        return null;
    }

    public boolean inBoard(Point p){
        int x = (int)p.getX();
        int y = (int)p.getY();
        return x > 50 && x < 550 && y > 50 && y < 450;
    }

    class Laser
    {
        //ArrayList<Point> points = new ArrayList<Point>();
        Point prev;
        Point next;
        Direction dir;
        boolean firing = true;
        Graphics g;
        public Laser(Graphics g, Direction initialDir, Point initialPoint){
            this.g = g;
            dir = initialDir;
            prev = initialPoint;
        }

        public void run(){
            try{
                int i = 0;
                while(inBoard(prev)){
                    Thread.sleep(5);
                    if(tileAt(prev) != null && tileAt(prev).piece != null){
                        if (prev.getX() == tileAt(prev).x + 25){
                            if (prev.getY() == tileAt(prev).y + 25){
                                dir = ((tileAt(prev).piece)).bounceLaser(dir);
                                if(dir == null){
                                    tileAt(prev).clear();
                                    break;
                                }
                            }
                        }
                    }
                    paintNext();
                }
                repaint();
            }
            catch(InterruptedException e){}
        }

        public void paintNext(){
            switch(dir){
                case NORTH:
                next = new Point(prev);
                next.translate(0,-1);
                break;

                case SOUTH:
                next = new Point(prev);
                next.translate(0,1);
                break;

                case EAST:
                next = new Point(prev);
                next.translate(1,0);
                break;

                case WEST:
                next = new Point(prev);
                next.translate(-1,0);
                break;
                
            }
            g.drawLine((int)prev.getX(),(int)prev.getY(), (int)next.getX(), (int)next.getY());
            prev = new Point(next);
        }

        public void stopFiring(){ 
            firing = false; 
        }
    }
}