import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    Game game;
    Timer timer;
    ArrayList<Sprite> actors;
    int paddingNum=25;
    long nextMoment;

    public Board(Game game){
        this.game=game;
        setPreferredSize(new Dimension(450,650));
        setBackground(Color.BLACK);
    }

    public void setup(){
        actors=new ArrayList<>();
        actors.add(new Player(Color.GREEN,getWidth()/2,getHeight()/2,30,30,this,game));

        for(int i=0;i<STATS.getNumFood();i++){
            actors.add(new Food(Color.ORANGE,(int)(Math.random()*(getWidth()-paddingNum)+paddingNum),(int)(Math.random()*(getHeight()-paddingNum)+paddingNum),20,20,this));
        }

        for(int i=0;i<STATS.getNumEnemies();i++){
            actors.add(new Enemy(Color.RED,(int)(Math.random()*(getWidth()-paddingNum)+paddingNum),(int)(Math.random()*(getHeight()-paddingNum)+paddingNum),25,25,this));
        }

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

        if(GameStates.isDeath()){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial",Font.PLAIN,72));
            printSimpleString("YOU DIED",getWidth(),0,100,g);
            g.setFont(new Font("Arial",Font.PLAIN,36));
            printSimpleString("Click to play again.",getWidth(),0,300,g);
        }

        if(GameStates.isPlay()) {
            for (Sprite thisGuy : actors) {
                thisGuy.paint(g);
            }
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial",Font.PLAIN,25));
            printSimpleString("Deaths: "+STATS.getNumDeaths(),getWidth(),0,20,g);
        }
    }

    public void checkCollisions(){
        for(int i=1;i<actors.size();i++){
            if(actors.get(0).collidesWith(actors.get(i))){
                if(actors.get(i) instanceof Enemy){
                    GameStates.setPlay(false);
                    GameStates.setDeath(true);
                    STATS.setNumDeaths(STATS.getNumDeaths()+1);
                    game.notClicked();
                }else{
                    actors.get(i).setRemove();
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
        if(game.getIsClicked()){
            GameStates.setMenu(false);
            GameStates.setDeath(false);
            GameStates.setWin(false);
            GameStates.setPlay(true);
        }

        if(game.isP()){
            if(GameStates.isPause()) {
                GameStates.setPause(false);
                GameStates.setPlay(true);
            }else{
                GameStates.setPlay(false);
                GameStates.setPause(true);
            }
        }

        if(GameStates.isPlay()) {
            nextMoment = System.currentTimeMillis();
            if ((nextMoment - game.getMoment()) >= 1000) {
                checkCollisions();
            }

            for (Sprite thisGuy : actors) {
                thisGuy.move();
            }

            if (actors.size() <= STATS.getNumEnemies() + 1) {
                GameStates.setWin(true);
                GameStates.setPlay(false);
                game.notClicked();
            }

            repaint();
        }
    }

    private void printSimpleString(String s,int width,int XPos,int YPos,Graphics g){
        int stringLen=(int)g.getFontMetrics().getStringBounds(s,g).getWidth();
        int start=width/2-stringLen/2;
        g.drawString(s,start+XPos,YPos);
    }
}
