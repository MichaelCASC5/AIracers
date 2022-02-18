
import java.awt.Color;
import java.awt.Graphics;

public class Obstacle{
    private int x, y;
    
    public Obstacle(int a, int b){
        x = a;
        y = b;
    }
    public void setX(int a){
        x = a;
    }
    public int getX(){
        return x;
    }
    public void setY(int a){
        y = a;
    }
    public int getY(){
        return y;
    }
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillRect(x, y, 10, 10);
    }
}
