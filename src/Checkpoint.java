import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Checkpoint{
    private int x,y;
    private int w;
    private boolean done;
    
    public Checkpoint(int a, int b){
        x = a;
        y = b;
        
        w = 50;
        done = false;
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
    public boolean isDone(){
        return done;
    }
    public void check(ArrayList<Car> cars){
        for(int i=0;i<cars.size();i++){
            if(cars.get(i).getX() > x - w && cars.get(i).getX() < x + w
                    && cars.get(i).getY() > y - w && cars.get(i).getY() < y + w){
                cars.get(i).setWin(true);
                done = true;
            }
        }
    }
    public void draw(Graphics g){
        g.setColor(Color.green);
        g.fillOval(x-w/2, y-w/2, w, w);
    }
}