class Ball {

    private static final int ACCELERATION = 1;
    private static final int END_WINDOW_Y = Main.HIGTH;
    private static final int END_WINDOW_X = Main.WIDTH;
    private static final int START_WINDOW_X = 0;


    private int x;
    private int y;
    private int d;
    private int speed;
    private int direction = 3;
    private int oldX;
    private boolean turn = false;

    void aplay(){
        setSpeed(speed + ACCELERATION);
        setY(y + speed);
        setX(x + direction);
    }

    Ball(int x, int y, int d, int direction, int speed) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.direction = direction;
        this.speed = speed;
        oldX = x;
    }

    void doTurn(){
        if(Math.abs(oldX - x) > d*2)
            turn = true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Ball(x, y, d, direction, speed);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if(x > END_WINDOW_X - d || x < START_WINDOW_X) {
            reDirection();
            setX(x + direction);
        }else
            this.x = x;
    }

    private void reDirection(){
        direction *= -1;
    }

    private void reSpeed(){
        speed *= -1;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if(y > END_WINDOW_Y - d){
            reSpeed();
            setY(END_WINDOW_Y - d);
        } else {
            this.y = y;
        }
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isTurn() {
        return turn;
    }
}