public class Configs{
    private int w,h;
    private boolean draw, drawNum;
    private boolean pen;
    
    private int carNum;
    private int pointCount;
    
    private int spawnX, spawnY;
    private int finishX, finishY;
    
    private int winner;
    
    private double accel;
    
    private boolean onlyChamp;
    
    private double threshold, rand, limit;
    private int gen;
    
    public Configs(){
        w = 1000;//1000   2736/2
        h = 800;//800   (1824/2)-75
        
        carNum = 0;
        pointCount = 10;
        accel = 0.02;
        
        draw = true;
        drawNum = true;
        
        pen = true;
        
        spawnX = w/2;
        spawnY = h/2;
        
        finishX = w/2;
        finishY = h/2;
        
        threshold = 1;//Reminder: also hardcoded in "drop" under "restart" and "New Simulation"
        rand = 0.05;
        limit = 0.01;
        
        gen = 0;
        winner = 0;
        
        onlyChamp = false;
    }
    public void setRand(double d){
        rand = d;
    }
    public double getRand(){
        return rand;
    }
    public void setOnlyChamp(boolean b){
        onlyChamp = b;
    }
    public boolean getOnlyChamp(){
        return onlyChamp;
    }
    public void setWinner(int a){
        winner = a;
    }
    public int getWinner(){
        return winner;
    }
    public void setGen(int a){
        gen = a;
    }
    public int getGen(){
        return gen;
    }
    public void setThreshold(double d){
        threshold = d;
    }
    public double getThreshold(){
        return threshold;
    }
    public void setLimit(double d){
        limit = d;
    }
    public double getLimit(){
        return limit;
    }
    public void setWidth(int a){
        w = a;
    }
    public int getWidth(){
        return w;
    }
    public void setHeight(int a){
        h = a;
    }
    public int getHeight(){
        return h;
    }
    public void setCarNum(int a){
        carNum = a;
    }
    public int getCarNum(){
        return carNum;
    }
    public void setPointCount(int a){
        pointCount = a;
    }
    public int getPointCount(){
        return pointCount;
    }
    public void setAcceleration(int a){
        accel = a;
    }
    public double getAcceleration(){
        return accel;
    }
    public void setDraw(boolean b){
        draw = b;
    }
    public boolean isDraw(){
        return draw;
    }
    public void setDrawNum(boolean b){
        drawNum = b;
    }
    public boolean isDrawNum(){
        return drawNum;
    }
    public void setPen(boolean b){
        pen = b;
    }
    public boolean isPen(){
        return pen;
    }
    public void setSpawnX(int a){
        spawnX = a;
    }
    public int getSpawnX(){
        return spawnX;
    }
    public void setSpawnY(int a){
        spawnY = a;
    }
    public int getSpawnY(){
        return spawnY;
    }
    public void setFinishX(int a){
        finishX = a;
    }
    public int getFinishX(){
        return finishX;
    }
    public void setFinishY(int a){
        finishY = a;
    }
    public int getFinishY(){
        return finishY;
    }
}
