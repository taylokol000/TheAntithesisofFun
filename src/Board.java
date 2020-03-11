import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    Game game;
    Timer timer;
    ArrayList<Sprite> actors;
    int paddingNum=25,mins,sec,hours;
    long nextMoment;
    int numPowerUps=0;
    long moment;
    long time;

    public Board(Game game){
        this.game=game;
        setPreferredSize(new Dimension(450,650));
        setBackground(Color.BLACK);
    }

    public void setup(){
        actors=new ArrayList<>();
        actors.add(new Player(Color.GREEN,getWidth()/2,getHeight()/2,15,15,this,game,true,false));

        timer = new Timer(1000/60,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(GameStates.isMenu()){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial",Font.PLAIN,72));
            printSimpleString("TORTURE",getWidth(),0,100,g);
            g.setFont(new Font("Sans",Font.PLAIN,36));
            printSimpleString("CLICK anywhere to play.",getWidth(),0,300,g);
        }

        if(GameStates.isPause()){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.PLAIN,72));
            printSimpleString("PAUSE",getWidth(),0,getWidth()/2-72/2,g);
        }

        if(GameStates.isWin()){
            g.setColor(new Color(255,215,0));
            g.setFont(new Font("Arial",Font.PLAIN,72));
            printSimpleString("YOU WIN!!!",getWidth(),0,100,g);
            g.setFont(new Font("Arial",Font.PLAIN,36));
            printSimpleString("You wasted "+hours+" hours "+mins+" minutes "+sec+" seconds on this game." , getWidth(), 0, 500, g);
        }

        if(GameStates.isDeath()){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial",Font.PLAIN,72));
            printSimpleString("YOU DIED",getWidth(),0,100,g);
            g.setFont(new Font("Arial",Font.PLAIN,36));
            if(STATS.getLife()<=0) {
                printSimpleString("OUT OF LIVES", getWidth(), 0, 300, g);
                printSimpleString("CLICK TO START OVER.", getWidth(), 0, 500, g);
            }else{
                printSimpleString("Click to try again.", getWidth(), 0, 300, g);
            }
        }

        if(GameStates.isNewLevel()){
            g.setColor(new Color(0,0,139));
            g.setFont(new Font("Arial",Font.PLAIN,72));
            printSimpleString("YOU BEAT IT!",getWidth(),0,100,g);
            g.setFont(new Font("Arial",Font.PLAIN,36));
            printSimpleString("Click for the next level.",getWidth(),0,300,g);
        }

        if(GameStates.isPlay()) {
            for (Sprite thisGuy : actors) {
                thisGuy.paint(g);
            }
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial",Font.PLAIN,25));
            printSimpleString("LIVES: "+STATS.getLife(),getWidth(),0,20,g);
            g.setColor(new Color(113,238,184));
            g.setFont(new Font("Arial",Font.PLAIN,25));
            printSimpleString("LEVEL: "+STATS.getLevel(),getWidth(),0,650,g);
        }
    }

    public void checkCollisions(){
        for(int i=1;i<actors.size();i++){
            if(actors.get(0).collidesWith(actors.get(i))){
                if(actors.get(i) instanceof Enemy) {
                    GameStates.setPlay(false);
                    GameStates.setDeath(true);
                    STATS.setNumDeaths(STATS.getNumDeaths() + 1);
                    game.notClicked();
                    actors.get(0).setHeight(actors.get(0).getInitialHeight());
                    actors.get(0).setWidth(actors.get(0).getInitialWidth());
                    if(STATS.getLife()<=0) {
                        STATS.setLevel(1);
                        STATS.setLife(3);
                    }else{
                        STATS.setLife(STATS.getLife()-1);
                    }
                    clear();
                    numPowerUps=0;
                }else if(actors.get(i) instanceof Powerup){
                    actors.get(0).setHeight(actors.get(0).getHeight()-1);
                    actors.get(0).setWidth(actors.get(0).getWidth()-1);
                    actors.get(i).setRemove();
                    numPowerUps-=1;
                }else if(actors.get(i) instanceof Food && !actors.get(i).isLast()){
                    actors.get(i).setRemove();
                    actors.get(0).setHeight(actors.get(0).getHeight()+1);
                    actors.get(0).setWidth(actors.get(0).getWidth()+1);
                    actors.add(new Food(Color.ORANGE,actors.get(i).getX(),actors.get(i).getY(),5,5,this,false,true));
                    actors.add(new Food(Color.ORANGE,actors.get(i).getX(),actors.get(i).getY(),5,5,this,false,true));
                    moment=System.currentTimeMillis();
                }else if(actors.get(i) instanceof Food && !actors.get(i).isEat()) {

                }else{
                    actors.get(i).setRemove();
                    actors.get(0).setHeight(actors.get(0).getHeight()+1);
                    actors.get(0).setWidth(actors.get(0).getWidth()+1);
                }
            }
        }

        for(int i=actors.size()-1;i>=0;i--){
            if(actors.get(i).isRemove()){
                actors.remove(i);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(game.getIsClicked()&&!GameStates.isPlay()){
            GameStates.setMenu(false);
            GameStates.setDeath(false);
            GameStates.setWin(false);
            GameStates.setPlay(true);
            GameStates.setNewLevel(false);
            clear();
            STATS.updateLevel();
            spawn();
            game.notClicked();
        }

        if(STATS.getLevel()>=5){
            GameStates.setPlay(false);
            GameStates.setWin(true);
            game.notClicked();
            nextMoment=System.currentTimeMillis();
            getTime();
        }

        if(game.isP()){
            if(GameStates.isPause()) {
                GameStates.setPause(false);
            }else{
                GameStates.setPause(true);
            }
        }

        if(GameStates.isPlay()&&!GameStates.isPause()) {
            nextMoment = System.currentTimeMillis();
            if ((nextMoment - game.getMoment()) >= 1000) {
                checkCollisions();
            }

            if ((nextMoment - moment) >= 500) {
                for(int i=1;i<actors.size();i++){
                    actors.get(i).setEat(true);
                }
            }

            spawnPowerup();

            for (Sprite thisGuy : actors) {
                thisGuy.move();
            }

            if ((actors.size()-numPowerUps) <= STATS.getNumEnemies() + 1 && !GameStates.isDeath()) {
                GameStates.setNewLevel(true);
                GameStates.setPlay(false);
                game.notClicked();
                actors.get(0).setHeight(actors.get(0).getInitialHeight());
                actors.get(0).setWidth(actors.get(0).getInitialWidth());
                STATS.setLevel(STATS.getLevel()+1);
            }

            repaint();
        }
    }

    private void printSimpleString(String s,int width,int XPos,int YPos,Graphics g){
        int stringLen=(int)g.getFontMetrics().getStringBounds(s,g).getWidth();
        int start=width/2-stringLen/2;
        g.drawString(s,start+XPos,YPos);
    }

    private void spawnPowerup(){
        if(((int)(Math.random()*1000)+1)<=5){
            actors.add(new Powerup(new Color(46,139,87),(int)(Math.random()*(getWidth()-paddingNum)+paddingNum),(int)(Math.random()*(getHeight()-paddingNum)+paddingNum),15,15,this,true,false));
            numPowerUps+=1;
        }
    }

    private void spawn(){
        for(int i=0;i<STATS.getNumFood();i++){
            actors.add(new Food(Color.ORANGE,(int)(Math.random()*(getWidth()-paddingNum)+paddingNum),(int)(Math.random()*(getHeight()-paddingNum)+paddingNum),10,10,this,true,false));
        }

        for(int i=0;i<STATS.getNumEnemies();i++){
            actors.add(new Enemy(Color.RED,(int)(Math.random()*(getWidth()-paddingNum)+paddingNum),(int)(Math.random()*(getHeight()-paddingNum)+paddingNum),15,15,this,true,false));
        }
    }

    private void clear(){
        for(int i=0;i<actors.size();i++){
            if(actors.get(i) instanceof Enemy||actors.get(i) instanceof Food||actors.get(i) instanceof Powerup){
                actors.get(i).setRemove();
            }
        }

        for(int i=actors.size()-1;i>0;i--){
            if(actors.get(i).isRemove()){
                actors.remove(i);
            }
        }
    }

    private void getTime(){
        time=nextMoment-game.getWaste();
        sec=(int)time/1000;
        sec=sec%60;
        mins=(int)time/60000;
        mins=mins%60;
        hours=(int)time/3600000;
    }
}
