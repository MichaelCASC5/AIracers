import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Player{
    private ImageIcon player_sprite = new ImageIcon(Driver.class.getResource("player_sprite.png"));
    
    private double x, y;
    private double w, h;
    
    private int rot;
    
    private double v;
    private double a;
    
    private boolean up,down,left,right;
    
    private Configs con;
    private ArrayList<Obstacle> obs = new ArrayList<>();
    
    public Player(Configs c, ArrayList o){
        obs = o;
        
        con = c;
        
        w = con.getWidth();
        h = con.getHeight();
        
        x = con.getSpawnX();
        y = con.getSpawnY();
        
        rot = 0;
        
        v = 2;
        a = con.getAcceleration();
    }
    public void reset(){
        w = con.getWidth();
        h = con.getHeight();
        
        x = con.getSpawnX();
        y = con.getSpawnY();
        
        rot = 0;
        
        v = 2;
        a = con.getAcceleration();
    }
    public void toCenter(){
        x = con.getSpawnX();
        y = con.getSpawnY();
        
        rot = 0;
        
        v = 2;
        a = con.getAcceleration();
    }
    public void setUp(boolean b){
        up = b;
    }
    public boolean getUp(){
        return up;
    }
    public void setDown(boolean b){
        down = b;
    }
    public boolean getDown(){
        return down;
    }
    public void setLeft(boolean b){
        left = b;
    }
    public boolean getLeft(){
        return left;
    }
    public void setRight(boolean b){
        right = b;
    }
    public boolean getRight(){
        return right;
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
    public void collision(){
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
                    //a = 0;
                    v = 2;
                    //rot+=180;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void accel(){
        v = v + (a * v);
    }
    public void decel(){
        v = v + (a*-1 * v);
    }
    public void left(){
        int turn;
        turn = (int)(10.0/(v));
        
        rot-=turn;
    }
    public void right(){
        int turn;
        turn = (int)(10.0/(v));
        
        rot+=turn;
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
        
        rot = rot % 360;
        
        collision();
        
        //Rotation
        x+=v * Math.cos(rot*(Math.PI/180));
        y+=v * Math.sin(rot*(Math.PI/180));
        
//        int turn;
//        turn = (int)(10.0/(v));
//        
//        if(sub == theta){
//            rot-=turn;
//        }else{
//            rot+=turn;
//        }
    }
    public void draw(Graphics g){ 
        //Drawing Hitbox/s
        Graphics2D g2d = (Graphics2D)g;
//        g2d.setColor(Color.black);
//        g2d.drawRect((int)x, (int)y, 10, 10);
        
        
        //AffineTransforms and scale
        AffineTransform at;
        at = AffineTransform.getTranslateInstance(x,y);
        at.scale(0.02, 0.02);
        at.rotate(Math.toRadians(rot), player_sprite.getIconWidth()/2, player_sprite.getIconHeight()/2);
        
        //Drawing Image
        Image img = player_sprite.getImage();
        g2d.drawImage(img, at,null);
    }
}