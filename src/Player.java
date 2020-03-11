import java.awt.*;

public class Player extends Sprite {

    Game game;

    public Player(Color color,int x,int y,int width, int height, Board board,Game game,boolean eat,boolean last) {
        super(color, x, y, width, height, board,eat,last);
        initialWidth=width;
        initialHeight=height;
        this.game=game;
    }

    @Override
    public void move(){
        x=game.getPositionX()-getWidth()/2;
        y=game.getPositionY()-getHeight()/2;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(),getY(),getWidth(),getHeight());
    }
}
