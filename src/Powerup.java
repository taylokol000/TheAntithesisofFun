import java.awt.*;

public class Powerup extends Sprite {

    public Powerup(Color color, int x, int y, int width, int height, Board board,boolean eat,boolean last) {
        super(color, x, y, width, height, board,eat,last);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(),getY(),getWidth(),getHeight());
    }
}
