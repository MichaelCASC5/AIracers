import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Car{
    private ImageIcon car_sprite = new ImageIcon(Driver.class.getResource("car_sprite.png"));
    private int pointCount;
    private int lap;
    
    private int number;
    
    private double x, y;
    private double w, h;
    
    private int rot;
    
    private double v;
    private double a;
    
    private boolean win;
    private boolean draw;
    
    private Configs con;
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Obstacle> obs = new ArrayList<>();
    
    public Car(Configs c, int n, ArrayList o){
        obs = o;
        
        number = n;
        con = c;
        
        w = con.getWidth();
        h = con.getHeight();
        
        x = con.getSpawnX();
        y = con.getSpawnY();
        
        rot = 0;
        
        v = 1;
        a = con.getAcceleration();
        
        pointCount = con.getPointCount();
        
        win = false;
        
        lap = 0;
        
        draw = true;
        
        Point p;
        for(int i=0;i<pointCount;i++){
            p = new Point(
                (int)((w-0)*Math.random()),
                (int)((h-0)*Math.random()),
                con
            );
            
            points.add(p);
        }
    }
    public void reset(){
        w = con.getWidth();
        h = con.getHeight();
        
        x = con.getSpawnX();
        y = con.getSpawnY();
        
        rot = 0;
        
        v = 1;
        a = con.getAcceleration();
        
        pointCount = con.getPointCount();
        
        lap = 0;
        
        win = false;
        draw = true;
        
        points = new ArrayList<>();
        Point p;
        for(int i=0;i<pointCount;i++){
            p = new Point(
                (int)(w*Math.random()),
                (int)(h*Math.random()),
                con
            );
            
            points.add(p);
        }
    }
    public void toCenter(){
        x = con.getSpawnX();
        y = con.getSpawnY();
        
        rot = 0;
        
        v = 1;
        a = con.getAcceleration();
        
        pointCount = con.getPointCount();
        
        lap = 0;
        
        win = false;
        draw = true;
        
        for(int i=0;i<pointCount;i++){
            points.get(i).setActive(pointCount);
        }
    }
    public double getAcceleration(){
        return a;
    }
    public void setDraw(boolean b){
        draw = b;
    }
    public boolean isDraw(){
        return draw;
    }
    public void setWin(boolean b){
        win = b;
    }
    public void setX(double a){
        x = a;
    }
    public double getX(){
        return x;
    }
     public void setY(double a){
        y = a;
    }
    public double getY(){
        return y;
    }
    public ArrayList getPoints(){
        return points;
    }
    public void setPoints(ArrayList<Point> p){
        points.removeAll(points);
        for(int i=0;i<p.size();i++){
            points.add(p.get(i));
        }
    }
    public void copy(Car copyCar){
        ArrayList<Point> copyPoints;
        copyPoints = copyCar.getPoints();
        for(int i=0;i<points.size();i++){
            points.get(i).copy(copyPoints.get(i));
        }
        
        //Reset
        x = con.getSpawnX();
        y = con.getSpawnY();
        
        rot = 0;
        
        v = 1;
        a = con.getAcceleration();
        
        lap = 0;
        win = false;
        
        for(int i=0;i<points.size();i++){
            points.get(i).setActive(con.getPointCount());
        }
    }
    public int getNumber(){
        return number;
    }
    public void setNumber(int a){
        number = a;
    }
    public boolean isWin(){
        return win;
    }
    public void printArray(){
        for(int i=0;i<points.size();i++){
            System.out.println(points.get(i).toString());
        }
    }
    public int closest(){
        int xDist = 0, yDist = 0;
        int hypo;
        int save = 10000;
        
        //Finding closest point + managing activity timer
        Point close = null;
        for(int i=0;i<points.size();i++){
            if(points.get(i).getActive() >= (int)(pointCount*0.7)){
                xDist = (int)x - points.get(i).getX();
                yDist = (int)y - points.get(i).getY();

                hypo = (int)Math.sqrt(Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0));

                if(hypo < save){
                    save = hypo;
                    close = points.get(i);
                }
                
            
                //Reset point timer back to 0
                if(Math.abs(xDist) < 10 && Math.abs(yDist) < 10){
                    for(int j=0;j<points.size();j++){
                        points.get(j).addActive();
                    }
                    points.get(i).resetActive();
                }
            }
        }
        
        //Calculating angle to closest point
        double theta;
        xDist = (int)x - close.getX();
        yDist = (int)y - close.getY();
        
        theta = Math.atan((double)yDist/(double)xDist);
        theta = Math.toDegrees(theta);
        
        if(close.getX() < x){
            theta+=180;
        }
        
        //Getting point acceleration
        a = close.getAcceleration();
        
        return (int)(theta);
    }
    public void collision(){
        if(x > con.getFinishX() - 10 && x < con.getFinishX() + 10 && y > con.getFinishY() - 50 && y < con.getFinishY() + 50){
            x = con.getFinishX() + 10;
        }else if(con.getFinishX() - 20 < x && x < con.getFinishX() - 10 && y > con.getFinishY() - 50 && y < con.getFinishY() + 50){
            x = con.getFinishX() + 20;
            lap++;
            if(lap >= 1){
                win = true;
                lap = 0;
            }
        }
        
        for(int i=0;i<obs.size();i++){
            try{
                if(Math.abs(x - obs.get(i).getX()) < 10 && Math.abs(y - obs.get(i).getY()) < 10){
                    if(obs.get(i).getX() > x){
                        x = obs.get(i).getX() - 20;
                    }else{
                        x = obs.get(i).getX() + 20;
                    }
                    if(obs.get(i).getY() > y){
                        y = obs.get(i).getY() - 20;
                    }else{
                        y = obs.get(i).getY() + 20;
                    }
                    a = 0;
                    v = 2;
                    //rot+=180;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void nav(){
        //Outer box
        if(x > w){
            v = 0;
            x = w;
        }else if(x < 0){
            v = 0;
            x = 0;
        }
        if(y > h){
            v = 0;
            y = h;
        }else if(y < 0){
            v = 0;
            y = 0;
        }
                
        //Limits
        if(v > 7){
            v = 7;
        }else if(v < 2){
            v = 2;
        }
        if(a > con.getAcceleration()){
            a = con.getAcceleration();
        }else if(a < con.getAcceleration()*-1){
            a = con.getAcceleration()*-1;
        }
        
        rot = rot % 360;
        
        collision();
        
        //Rotation
        x+=v * Math.cos(rot*(Math.PI/180));
        y+=v * Math.sin(rot*(Math.PI/180));
        
        //Acceleration
        v = v + (a * v);
        
        //Redirecting to closest point
        int theta;
        int turn;
        turn = (int)(10.0/(v));
        theta = closest();
        
        while(rot < 0){
            rot += 360;
        }
        while(theta < 0){
            theta += 360;
        }
        
        int sub = rot;
        int add = rot;
        while(true){
            sub--;
            add++;
            
            if(sub < 0){
                sub+=360;
            }
            if(add >= 360){
                add-=360;
            }
            
            if(sub == theta || add == theta){
                break;
            }
        }
        
        if(sub == theta){
            rot-=turn;
        }else{
            rot+=turn;
        }
    }
    public void draw(Graphics g){
        //Drawing Points
        if(con.isDraw()){
            for(int i=0;i<points.size();i++){
                points.get(i).draw(g);
            }
        }
        
        //Drawing Hitbox/s
        Graphics2D g2d = (Graphics2D)g;
//        g2d.setColor(Color.black);
//        g2d.drawRect((int)x, (int)y, 10, 10);
        
        
        //AffineTransforms and scale
        AffineTransform at;
        at = AffineTransform.getTranslateInstance(x,y);
        at.scale(0.02, 0.02);
        at.rotate(Math.toRadians(rot), car_sprite.getIconWidth()/2, car_sprite.getIconHeight()/2);
        
        //Drawing Image
        Image img = car_sprite.getImage();
        g2d.drawImage(img, at,null);
        
        //Drawing Numbers
        if(con.isDrawNum()){
            g.setColor(new Color(255,255,255,200));
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
            g.drawString("" + number, (int)x+10, (int)y+10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
        }
    }
}