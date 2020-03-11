import java.awt.*;

public abstract class Sprite {

    Color color;
    int x,y,width,height,initialWidth,initialHeight;
    double dx,dy;
    boolean remove=false;
    boolean eat;
    boolean last;

    Board board;

    public Sprite(Color color,int x,int y,int width,int height,Board board, boolean eat,boolean last){
        this.color=color;
        this.y=y;
        this.x=x;
        this.width=width;
        initialWidth=width;
        this.height=height;
        initialHeight=height;
        this.board=board;
        this.eat=eat;
        this.last=last;

        while(((int)dx==0||(int)dy==0)){
            double angle=2*Math.PI*(Math.random()+1);
            double speed=(STATS.getRangeSpeed()*Math.random()+ STATS.getLowSpeed());
            dx=Math.cos(angle)*speed;
            dy=Math.sin(angle)*speed;
        }

    }

    public void move(){
        //predictive movement
        double nextLeft=x+dx;
        double nextRight=(x+width)+dx;
        double nextTop=y+dy;
        double nextBottom=(y+height)+dy;

        if(nextTop<0||nextBottom>(double)board.getHeight()){
            dy*=-1;
        }
        if(nextLeft<0||nextRight>(double)board.getWidth()){
            dx*=-1;
        }

        x+=dx;
        y+=dy;
    }

    public boolean collidesWith(Sprite other){
        return getBounds().intersects(other.getBounds());
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }

    public abstract void paint(Graphics g);

    public Color getColor() {
        return color;
    }

    public boolean isRemove(){
        return remove;
    }

    public void setRemove(){
        remove=true;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getInitialWidth() {
        return initialWidth;
    }

    public int getInitialHeight() {
        return initialHeight;
    }

    public boolean isEat() {
        return eat;
    }

    public void setEat(boolean eat) {
        this.eat = eat;
    }

    public boolean isLast() {
        return last;
    }
}
