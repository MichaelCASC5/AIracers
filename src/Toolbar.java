import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Toolbar{
    private int width;
    private int height;
    
    private String[] tools = {
        "File",
        "Edit",
        "View",
        "Help",
    };
    private boolean[] hov = new boolean[tools.length];
    
    private int mouseX, mouseY;
    private Drop[] drop = new Drop[tools.length];
            
    //Constructor
    public Toolbar(int w){
        width = w;
        height = 20;
        
        String[] drop0 = {
            "New Simulation",
            "",
            "Load Track",
            "Save Track",
            "",
            "Load Path",
            "Save Path",
            "",
            "Exit",
        };
        drop[0] = new Drop(drop0, 125);
        
        String[] drop1 = {
            "Restart",
            "",
            "Erase Track",
            "Reduce Track",
            "Remove Doubles",
            "",
            "Number of Cars",
            "Number of Points",
            "",
            "Set Threshold",
            "Set Limit",
            "Set Rand",
            "",
            "Remove Checkpoints",
            "",
            "Clone",
        };
        drop[1] = new Drop(drop1, 150);
        
        String[] drop2 = {
            "Show Points",
            "Show Car Numbers",
            "Show Only Champion"
        };
        drop[2] = new Drop(drop2, 150);
        
        String[] drop3 = {
            "How To Play",
            "Information",
            "Credits",
        };
        drop[3] = new Drop(drop3, 110);
    }
    public int getHeight(){
        return height;
    }
    public void pressed(MouseEvent e, ArrayList<Car> o, Configs c, ArrayList<Obstacle> obs, ArrayList<Checkpoint> chck, Player p){
        for(int i=0;i<hov.length;i++){
            if(hov[i] && !drop[i].getDisplay()){
                drop[i].setDisplay(true);
            }else{
                drop[i].setDisplay(false);
                drop[i].pressed(e,o,c,obs,chck,p);
            }
        }
    }
    public void hover(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }
    public void draw(Graphics g){
        //Bar
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        
        //Font
        Font font = new Font("Times", Font.PLAIN, 12);
        g.setFont(font);
        
        //Taskbar
        int space = 0;
        int wordLen;
        for(int i=0;i<tools.length;i++){
            wordLen = tools[i].length()*9;
            
            //Drawing Hover
            if(mouseX > space && mouseX < space + tools[i].length()*8 && mouseY < height*2.5){
                g.setColor(Color.cyan);
                g.fillRect(space, 0, wordLen, height);
                hov[i] = true;
            }else{
                hov[i] = false;
            }
            
            //Remove drop-down menu
            if(hov[i] != drop[i].getDisplay() && mouseY < height*2.5){
                drop[i].setDisplay(false);
            }
            
            //Drawing drop-down menus
            if(drop[i].getDisplay()){
                drop[i].setMouse(mouseX, mouseY);
                drop[i].setCoordinates(space, height);
                drop[i].draw(g);
            }
            
            //Drawing Words
            g.setColor(Color.black);
            g.drawString(tools[i], space+4, height-6);
            space+=wordLen;
        }
    }
}