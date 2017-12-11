import java.awt.*;

class Player {
    boolean left, right;
    private int x = 600;
    private static final int Y = 650;
    private static final int WINDTH = 30;
    private static final int HIGHT = 60;
    private static final int SPEAD = 12;
    private static final int START_WINDOW = 0;
    private static final int END_WINDOW = Main.WIDTH;

    void aplay(){
        if(left)
            setX(x - SPEAD);
        if(right)
            setX(x + SPEAD);
    }

    public int getWINDTH() {
        return WINDTH;
    }

    public int getHIGHT() {
        return HIGHT;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if(x > END_WINDOW - WINDTH)
            this.x = END_WINDOW - WINDTH;
        else if(x < START_WINDOW)
                this.x = 0;
            else this.x = x;
    }

    public int getY() {
        return Y;
    }

    public DrowObj getDrowObj() {
        return g -> {
            g.setColor(new Color(0xffff0000));//Вынести в константы графики
            g.fillRoundRect(x, Y, WINDTH, HIGHT, 10, 10);
        };
    }
}