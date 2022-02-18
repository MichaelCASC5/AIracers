import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Driver extends JComponent implements KeyListener, MouseListener, MouseMotionListener{
    private int WIDTH;
    private int HEIGHT;
    
    private int counter, counterSave;
    
    private int mX, mY;
    
    private Toolbar tb;
    private Configs con;
    
    private Player p;
    
    private ArrayList<Car> cars;
    private ArrayList<Obstacle> obs;
    private ArrayList<Checkpoint> chck;
    
    public Driver(){
        cars = new ArrayList<>();
        obs = new ArrayList<>();
        chck = new ArrayList<>();
        
        con = new Configs();
        
        counter = 0;
        
        WIDTH = con.getWidth();
        HEIGHT = con.getHeight();
        
        tb = new Toolbar(WIDTH);
        
        p = new Player(con,obs);
        
        Car c;
        for(int i=0;i<con.getCarNum();i++){
            c = new Car(con,i,obs);
            cars.add(c);
        }
        
        //Setting up the GUI
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("GUI");
        gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30));
        gui.setResizable(false);
        gui.getContentPane().add(this);
        
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        gui.addKeyListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);
    }
    public void paintComponent(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //Spawner
        g.setColor(Color.yellow);
        g.fillRect(con.getSpawnX()-25, con.getSpawnY()-25, 50, 50);
        
        //Finish
        g.setColor(Color.white);
        g.fillRect(con.getFinishX()-5, con.getFinishY()-50, 10,100);
        
        for(int i=0;i<obs.size();i++){
            obs.get(i).draw(g);
        }
        
        for(int i=0;i<cars.size();i++){
            if(con.getOnlyChamp()){
                if(cars.get(i).getNumber() == con.getWinner()){
                    cars.get(i).draw(g);
                }
            }else{
                cars.get(i).draw(g);
            }
        }
        
        for(int i=0;i<chck.size();i++){
            chck.get(i).draw(g);
        }
        
        p.draw(g);
        
        //Generation
        g.setColor(new Color(255,255,255,255/2));
        g.fillRect(0, HEIGHT-25, WIDTH, HEIGHT);
        
        g.setColor(Color.black);
        Font font = new Font("Times", Font.PLAIN, 12);
        g.setFont(font);
        g.drawString("Generation " + con.getGen(), 10, HEIGHT-10);
        g.drawString("Champion: #" + con.getWinner(), 100, HEIGHT-10);
        g.drawString("Ticks: " + counterSave, 225, HEIGHT-10);
        g.drawString("Obstacles: " + obs.size(), 300, HEIGHT-10);
        g.drawString("Rand: " + con.getRand(), 400, HEIGHT-10);
        g.drawString("Threshold: " + con.getThreshold(), 500, HEIGHT-10);
        
        tb.draw(g);
    }
    public void loop(){
        counter++;
        
        for(int i=0;i<chck.size();i++){
            chck.get(i).check(cars);
            if(chck.get(i).isDone()){
                chck.remove(i);
            }
        }
        
        for(int i=0;i<cars.size();i++){
            cars.get(i).nav();
            if(cars.get(i).isWin()){
                con.setGen(con.getGen()+1);
                
                if(con.getThreshold() > con.getLimit()){
                    con.setThreshold(con.getThreshold()*0.9);
                }else{
                    con.setThreshold(con.getLimit());
                }
                
                Car temp = cars.get(i);
                for(int j=0;j<cars.size();j++){
                    if(j != i){
                        cars.get(j).copy(temp);
                    }else{
                        cars.get(i).toCenter();
                        con.setWinner(cars.get(i).getNumber());
                        counterSave = counter;
                        counter = 0;
                    }
                }
                p.reset();
            }
            cars.get(i).setWin(false);
        }
        
        p.nav();
        
        if(p.getUp()){
            p.accel();
        }
        if(p.getDown()){
            p.decel();
        }
        if(p.getLeft()){
            p.left();
        }
        if(p.getRight()){
            p.right();
        }
        
        repaint();
    }
    public void keyPressed(KeyEvent e){
        int k = e.getKeyCode();
        
        if(k == 81){
            con.setPen(!con.isPen());
        }else if(k == 69){
            con.setSpawnX(mX-10);
            con.setSpawnY(mY-30);
        }else if(k == 87){
            con.setFinishX(mX-10);
            con.setFinishY(mY-30);
        }else if(k == 82){
            chck.add(new Checkpoint(mX-10,mY-30));
        }
        
        //PLAYER
        if(k == 38){//UP
            p.setUp(true);
        }else if(k == 40){//DOWN
            p.setDown(true);
        }else if(k == 37){//LEFT
            p.setLeft(true);
        }else if(k == 39){//RIGHT
            p.setRight(true);
        }
    }
    public void keyReleased(KeyEvent e){
        int k = e.getKeyCode();
        
        //PLAYER
        if(k == 38){//UP
            p.setUp(false);
        }else if(k == 40){//DOWN
            p.setDown(false);
        }else if(k == 37){//LEFT
            p.setLeft(false);
        }else if(k == 39){//RIGHT
            p.setRight(false);
        }
    }
    public void keyTyped(KeyEvent e){
    }
    public void mousePressed(MouseEvent e){
        tb.pressed(e,cars,con,obs,chck,p);
    }
    public void mouseReleased(MouseEvent e){
    }
    public void mouseClicked(MouseEvent e){
    }
    public void mouseEntered(MouseEvent e){
    }
    public void mouseExited(MouseEvent e){
    }
    public void mouseMoved(MouseEvent e){
        tb.hover(e);
        mX = e.getX();
        mY = e.getY();
    }
    public void mouseDragged(MouseEvent e){
        if(con.isPen()){
            obs.add(new Obstacle(e.getX()-10,e.getY()-30));
        }else{
            for(int i=0;i<obs.size();i++){
                if(Math.abs(obs.get(i).getX() - e.getX()) < 20
                        && Math.abs(obs.get(i).getY() - e.getY()+30) < 20){
                    obs.remove(i);
                }
            }
        }
    }
    public void start(final int ticks){
        Thread gameThread = new Thread(){
            public void run(){
                while(true){
                    loop();
                    try{
                        Thread.sleep(1000 / ticks);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };	
        gameThread.start();
    }
    public static void main(String[] args){
        Driver g = new Driver();
        g.start(60);
    }
}