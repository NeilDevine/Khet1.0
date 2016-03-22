import java.awt.*;
/**
 * Abstract class Piece - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Piece
{
    int facing =1;
    public abstract void paint(Graphics g);
    public abstract Direction bounceLaser(Direction laserDir);
}
