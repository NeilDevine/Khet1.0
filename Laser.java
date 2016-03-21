import java.util.*;
import java.awt.*;
/**
 * Represents the laser in Khet 1.0
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Laser
{
    ArrayList<Point> points = new ArrayList<Point>();
    Point prev;
    Point next;
    Direction dir;
    boolean firing = true;
    Graphics g;
    public Laser(Graphics g, Direction initialDir, Point initialPoint){
        this.g = g;
        dir = initialDir;
        prev = initialPoint;
        //points.add(initialPoint);
    }

    public void run(){
        try{
            //g.fillRect(50,50,50,100);
            int i = 0;
            while(firing){
                Thread.sleep(5);
                //g.fillRect(i+40,40,40,40);
                //i++;
                paintNext();
                if(false) break;
            }
            g.fillRect(50,50,50,100);
        }
        catch(InterruptedException e){}
    }

    public void paintNext(){
        switch(dir){
            case NORTH:

            next = new Point(prev);
            next.translate(0,-1);
            //points.add(new Point((int)points.get(points.size()-1).getX(), 
            //        (int)points.get(points.size()-1).getY() - 1));
            //g.setColor( Color.RED );
            //g.fillRect(50,50,50,50);
            break;

            case SOUTH:
            points.add(new Point((int)points.get(points.size()-1).getX(), 
                    (int)points.get(points.size()-1).getY() + 1));
            break;

            case EAST:
            points.add(new Point((int)points.get(points.size()-1).getX() + 1, 
                    (int)points.get(points.size()-1).getY()));
            break;

            case WEST:
            points.add(new Point((int)points.get(points.size()-1).getX() - 1, 
                    (int)points.get(points.size()-1).getY()));
            break;

        }
        //g.setColor( Color.RED );
        //g.fillRect(50,50,50,50);
        g.drawLine((int)prev.getX(),(int)prev.getY(), (int)next.getX(), (int)next.getY());
        prev = new Point(next);
    }
    
    public void stopFiring(){ firing = false; }
}
