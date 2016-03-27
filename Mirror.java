import java.awt.*;
/**
 * Write a description of class Mirror here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mirror extends Piece
{
    private int facing; // One for each state (or rotation) that the mirror could be in
    Polygon graphic, graphic1, graphic2, graphic3, graphic4;
    /**
     * Constructor for objects of class Mirror
     */
    public Mirror(Tile pos){
        facing = 1;

        graphic1 = new Polygon();
        graphic1.addPoint(pos.x + 1,pos.y+1);
        graphic1.addPoint(pos.x + 50,pos.y+1);
        graphic1.addPoint(pos.x + 50,pos.y + 50);

        graphic2 = new Polygon();
        graphic2.addPoint(pos.x + 50,pos.y + 50);
        graphic2.addPoint(pos.x + 50,pos.y+1);
        graphic2.addPoint(pos.x + 1,pos.y + 50);

        graphic3 = new Polygon();
        graphic3.addPoint(pos.x + 1,pos.y + 1);
        graphic3.addPoint(pos.x + 1,pos.y+50);
        graphic3.addPoint(pos.x + 50,pos.y + 50);

        graphic4 = new Polygon();
        graphic4.addPoint(pos.x + 1,pos.y+1);
        graphic4.addPoint(pos.x + 50,pos.y+1);
        graphic4.addPoint(pos.x + 1,pos.y + 50);    

        graphic = graphic1;
    }

    public void rotate(){
        facing++;
        facing = (facing % 4);
        setGraphic();
    }

    private void setGraphic(){
        switch(facing){
            case 1: graphic = graphic1; break;
            case 2: graphic = graphic2; break;
            case 3: graphic = graphic3; break;
            case 0: graphic = graphic4; break;
        }
    }

    public void paint(Graphics g){
        g.setColor( Color.GREEN );
        g.fillPolygon(graphic);
    }
}
