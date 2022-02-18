import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Drop{
    private boolean display;
    private int x,y;
    
    private int mouseX;
    private int mouseY;
    
    private String[] options;
    private boolean[] hov;
    
    private int width, height;
    
    public Drop(String[] strs, int w){
        options = strs;
        display = false;
        
        width = w;
        
        height = 10 + options.length*20;
        hov = new boolean[options.length];
    }
    public void setCoordinates(int a, int b){
        x = a;
        y = b;
    }
    public void setMouse(int a, int b){
        mouseX = a;
        mouseY = b;
    }
    public void setDisplay(boolean b){
        display = b;
    }
    public boolean getDisplay(){
        return display;
    }
    public void pressed(MouseEvent e, ArrayList<Car> o, Configs c, ArrayList<Obstacle> obs, ArrayList<Checkpoint> chck, Player p){
        for(int i=0;i<hov.length;i++){
            if(hov[i]){
                if(options[i].equals("New Simulation")){
                    o.removeAll(o);
                    c.setGen(0);
                    c.setThreshold(1);
                    obs.removeAll(obs);
                    chck.removeAll(chck);
                    p.reset();
                    
                    c.setFinishX(c.getWidth()/2);
                    c.setFinishY(c.getHeight()/2);
                    
                    c.setSpawnX(c.getWidth()/2);
                    c.setSpawnY(c.getHeight()/2);
                }
                else if(options[i].equals("Restart")){
                    o.removeAll(o);
                    p.reset();
                    Car car;
                    for(int j=0;j<c.getCarNum();j++){
                        car = new Car(c,j,obs);
                        o.add(car);
                    }
                    c.setGen(0);
                    c.setThreshold(1);
                }else if(options[i].equals("Erase Track")){
                    obs.removeAll(obs);
                }else if(options[i].equals("Reduce Track")){
                    //Remove Doubles
                    for(int j=0;j<obs.size();j++){
                        for(int k=0;k<obs.size();k++){
                            if(j != k &&
                                    obs.get(j).getX() == obs.get(k).getX() &&
                                    obs.get(j).getY() == obs.get(k).getY()){
                                obs.remove(j);
                            }
                        }
                    }
                    
                    //Reduce Track
                    String input = JOptionPane.showInputDialog("Pixel threshold:");
                    int num;
                    if(input != null || !input.equals("")){
                        num = Integer.parseInt(input);
                        int j=0,k=0;
                        while(j < obs.size()){
                            while(k < obs.size()){
                                if(j != k){
                                    if(obs.get(j).getX() > obs.get(k).getX()-num
                                            && obs.get(j).getX() < obs.get(k).getX()+num
                                            && obs.get(j).getY() > obs.get(k).getY()-num
                                            && obs.get(j).getY() < obs.get(k).getY()+num){
                                        obs.remove(k);
                                        k--;
                                    }
                                }
                                k++;
                            }
                            j++;
                            k=0;
                        }
                    }
                }else if(options[i].equals("Remove Doubles")){
                    for(int j=0;j<obs.size();j++){
                        for(int k=0;k<obs.size();k++){
                            if(j != k &&
                                    obs.get(j).getX() == obs.get(k).getX() &&
                                    obs.get(j).getY() == obs.get(k).getY()){
                                obs.remove(j);
                            }
                        }
                    }
                }else if(options[i].equals("Load Track")){
                    JFileChooser fileChooser = new JFileChooser();
                    if(fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        System.out.println(file.getPath());
                        try{
                            Scanner fileReader = new Scanner(file);
                            int xData, yData;
                            int comma;
                            obs.removeAll(obs);
                            int line = 0;
                            while(fileReader.hasNextLine()){
                                //FORMATTING DATA
                                String data = fileReader.nextLine();
                                data = data.toLowerCase();
                                data = data.replaceAll(" ", "");
                                
                                //READING DATA
                                comma = data.indexOf(",");
                                xData = Integer.parseInt(data.substring(0,comma));
                                yData = Integer.parseInt(data.substring(comma+1));
                                
                                //System.out.println(data);
                                if(line == 0){
                                    c.setFinishX(xData);
                                    c.setFinishY(yData);
                                }else if(line == 1){
                                    c.setSpawnX(xData);
                                    c.setSpawnY(yData);
                                }else{
                                    Obstacle obstacle;
                                    obstacle = new Obstacle(xData, yData);
                                    obs.add(obstacle);
                                }
                                line++;
                            }
                            fileReader.close();
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }else if(options[i].equals("Save Track")){
                    JFileChooser fileChooser = new JFileChooser();
                    if(fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        System.out.println(file.getPath());
                        try{
                            FileWriter f = new FileWriter(file);
                            f.write("" + c.getFinishX() + "," + c.getFinishY() + "\n");
                            f.write("" + c.getSpawnX() + "," + c.getSpawnY() + "\n");
                            for(int j=0;j<obs.size();j++){
                                f.write("" + obs.get(j).getX() + "," + obs.get(j).getY() + "\n");
                            }
                            f.close();
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }else if(options[i].equals("Load Path")){
                    JFileChooser fileChooser = new JFileChooser();
                    if(fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        System.out.println(file.getPath());
                        try{
                            Scanner fileReader = new Scanner(file);
                            int xData, yData;
                            double accData;
                            int comma, aste;
                            ArrayList<Point> temp = new ArrayList<>();
                            //obs.removeAll(obs);
                            while(fileReader.hasNextLine()){
                                //FORMATTING DATA
                                String data = fileReader.nextLine();
                                data = data.toLowerCase();
                                data = data.replaceAll(" ", "");
                                
                                //READING DATA
                                comma = data.indexOf(",");
                                aste = data.indexOf("*");
                                
                                xData = Integer.parseInt(data.substring(0,comma));
                                yData = Integer.parseInt(data.substring(comma+1,aste));
                                accData = Double.parseDouble(data.substring(aste+1));
                                
//                                Point point;
//                                point = new Point(xData, yData, accData, false, c);
//                                temp.add(point);
                            }
                            for(int j=0;j<o.size();j++){
                                o.get(j).reset();
                                o.get(j).setPoints(temp);
                            }
                            fileReader.close();
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }else if(options[i].equals("Save Path")){
                    JFileChooser fileChooser = new JFileChooser();
                    String input = JOptionPane.showInputDialog("Which car to save?:");
                    int num = Integer.parseInt(input);
                    ArrayList<Point> temp = null;
                    for(int j=0;j<o.size();j++){
                        if(o.get(j).getNumber() == num){
                            temp = o.get(j).getPoints();
                        }
                    }
                    
                    if(fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        System.out.println(file.getPath());
                        try{
                            FileWriter f = new FileWriter(file);
                            for(int j=0;j<temp.size();j++){
                                f.write("" + temp.get(j).getX() + "," + temp.get(j).getY() + "*" + temp.get(j).getAcceleration() + "\n");
                            }
                            f.close();
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }else if(options[i].equals("Exit")){
                    System.exit(0);
                }else if(options[i].equals("Number of Cars")){
                    String input = JOptionPane.showInputDialog("Enter number of cars: [" + c.getCarNum() + "]");
                    if(input != null || !input.equals("")){
                        c.setCarNum(Integer.parseInt(input));

                        o.removeAll(o);
                        Car car;
                        for(int j=0;j<c.getCarNum();j++){
                            car = new Car(c,j,obs);
                            o.add(car);
                        }
                    }
                    p.reset();
                }else if(options[i].equals("Number of Points")){
                    String input = JOptionPane.showInputDialog("Enter number of Points: [" + c.getPointCount() + "]");
                    if(input != null || !input.equals("")){
                        c.setPointCount(Integer.parseInt(input));

                        o.removeAll(o);
                        Car car;
                        for(int j=0;j<c.getCarNum();j++){
                            car = new Car(c,j,obs);
                            o.add(car);
                        }
                    }
                }else if(options[i].equals("Show Points")){
                    c.setDraw(!c.isDraw());
                }else if(options[i].equals("Show Car Numbers")){
                    c.setDrawNum(!c.isDrawNum());
                }else if(options[i].equals("Show Only Champion")){
                    c.setOnlyChamp(!c.getOnlyChamp());
                }else if(options[i].equals("Set Threshold")){
                    String input = JOptionPane.showInputDialog("Enter threshold:");
                    if(input != null || !input.equals("")){
                        double num = Double.parseDouble(input);
                        c.setThreshold(num);
                    }
                }else if(options[i].equals("Set Limit")){
                    String input = JOptionPane.showInputDialog("Enter limit:");
                    if(input != null || !input.equals("")){
                        double num = Double.parseDouble(input);
                        c.setLimit(num);
                    }
                }else if(options[i].equals("Set Rand")){
                    String input = JOptionPane.showInputDialog("Enter Rand:");
                    if(input != null || !input.equals("")){
                        double num = Double.parseDouble(input);
                        c.setRand(num);
                    }
                }else if(options[i].equals("Remove Checkpoints")){
                    chck.removeAll(chck);
                }else if(options[i].equals("Clone")){
                    String input = JOptionPane.showInputDialog("Which car to clone?:");
                    if(input != null || !input.equals("")){
                        int num = Integer.parseInt(input);
                        for(int j=0;j<o.size();j++){
                            if(o.get(j).getNumber() == num){
                                o.get(j).setWin(true);
                            }
                        }
                    }
                    p.reset();
                }
            }
            hov[i] = false;
        }
    }
    public void draw(Graphics g){
        //Font
        Font font = new Font("Times", Font.PLAIN, 12);
        g.setFont(font);
        
        //Draws Menu
        g.setColor(Color.white);
        g.fillRect(x, y, width, 10 + options.length*20);
        g.setColor(Color.gray);
        g.drawRect(x, y, width, 10 + options.length*20);
        
        int space = 20;
        for(int i=0;i<options.length;i++){
            //Draws hovers
            if(mouseX > x && mouseX < x + width && mouseY > space + 35 && mouseY < space + 55){
                g.setColor(Color.cyan);
                g.fillRect(x, space + 5, width, 20);
                hov[i] = true;
            }else{
                hov[i] = false;
            }
            
            //Draws text
            g.setColor(Color.black);
            g.drawString(options[i], x + 20, y + space);
            space+=20;
        }
    }
}