import java.awt.Color;
import java.awt.Graphics;

public class Point{
    Configs con;
    
    private int x, y;
    private int active;
    private double acc;
    
    public Point(int a, int b, Configs configs){
        con = configs;
        
        x = a;
        y = b;
        
        double c = con.getAcceleration() * Math.random();
        if(Math.random() < 0.5)
            c*=-1;
        
        acc = c;
        active = configs.getPointCount();
    }
    public void write(Point p){
        x = p.getX();
        y = p.getY();
        active = p.getActive();
        acc = p.getAcceleration();
    }
    public void copy(Point p){
        double mutateX;
        mutateX = 1 - Math.pow(Math.sqrt(1 - Math.pow(Math.random(),2)),con.getThreshold());
        mutateX*=con.getWidth();

        if(Math.random() < 0.5){
            mutateX*=-1;
        }

        double mutateY;
        mutateY = 1 - Math.pow(Math.sqrt(1 - Math.pow(Math.random(),2)),con.getThreshold());
        mutateY*=con.getWidth();

        if(Math.random() < 0.5){
            mutateY*=-1;
        }
        
        if(Math.random() >= con.getRand()){
            x = p.getX()+(int)(mutateX);
            y = p.getY()+(int)(mutateY);
        }else{
            x = (int)(con.getWidth()*Math.random());
            y = (int)(con.getHeight()*Math.random());
        }
        
        //Point to borders
        if(x < 0){
            x = 0;
        }else if(x > con.getWidth()){
            x = con.getWidth();
        }
        if(y < 0){
            y = 0;
        }else if(y > con.getHeight()){
            y = con.getHeight();
        }
        
        //Mutating Acceleration
        double mutateAcc = Math.random() * p.getAcceleration() * con.getThreshold();
        int randNeg;
        if(Math.random() < 0.5){
            randNeg = -1;
        }else{
            randNeg = 1;
        }
        mutateAcc*=randNeg;
        
        if(Math.random() >= con.getRand()){
            acc = p.getAcceleration() + mutateAcc;
        }else{
            acc = Math.random() * con.getAcceleration() * randNeg;
        }
        
        active = p.getActive();
    }
    public void setAcceleration(double a){
        acc = a;
    }
    public double getAcceleration(){
        return acc;
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
    public void addActive(){
        active++;
    }
    public void resetActive(){
        active = 1;
    }
    public void setActive(int a){
        active = a;
    }
    public int getActive(){
        return active;
    }
    public void draw(Graphics g){
        Color col;
        int colNum = (int)((Math.abs(acc)/con.getAcceleration())*255);
        
        if(colNum > 255){
            colNum = 255;
        }else if(colNum < 0){
            colNum = 0;
        }
        if(acc > 0){
            col = new Color(0,colNum,0);
        }else{
            col = new Color(colNum,0,0);
        }
        g.setColor(col);
        g.fillRect(x, y, 5, 5);
        g.drawString("" + active, x, y);
    }
    public String toString(){
        return "x:" + x + ", y:" + y + ", acc:" + acc;
    }
}