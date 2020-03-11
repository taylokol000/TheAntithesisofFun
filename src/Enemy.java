import java.awt.*;

public class Enemy extends Sprite{

    public Enemy(Color color, int x, int y, int width, int height, Board board,boolean eat,boolean last) {
        super(color, x, y, width, height, board,eat,last);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX(),getY(),getWidth(),getHeight());
    }
}
