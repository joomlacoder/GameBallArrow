import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game extends Thread  implements ActionListener{

    private static final int[] START_BALL_PARAM = {0, 400, 50, 3, 0};
    private static final int MIN_BALL_D = 5;

    private Player player = new Player();
    private Arrow arrow = new Arrow();
    private CopyOnWriteArrayList <Ball> balls = new CopyOnWriteArrayList<Ball>();
    private List<DrowObj> drowObjs;

    Game(List<DrowObj> list){
        drowObjs = list;
        newBall(START_BALL_PARAM[0], START_BALL_PARAM[1],
                START_BALL_PARAM[2], START_BALL_PARAM[3], START_BALL_PARAM[4]);
        list.add(player.getDrowObj());
        list.add(arrow.getDrowObj());
    }

    private boolean isCollisionBall(Ball ball, String parm){

        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballXD = ballX + ball.getD();
        int ballYD = ballY + ball.getD();

        if(parm.equals("arow"))
            return ballX < arrow.getX() && arrow.getX() < ballXD
                    && ballY < arrow.getY() + arrow.getH() && arrow.getY() < ballYD;
        else
            return ballX < player.getX() + player.getWINDTH()
                    && player.getX() < ballXD && player.getY() < ballYD;
    }

    private void CollisionBall(Ball ball){
        if(ball.isTurn()) {
            if (isCollisionBall(ball, "arow")) {
                respawn(ball);
                removeBall(ball);
            } else if (isCollisionBall(ball, ""))
                removeBall(ball);
        } else ball.doTurn();
    }

    public void run() {
        for(Ball ball: balls){
           ball.aplay();
           CollisionBall(ball);
        }
        player.aplay();
        arrow.aplay();
    }

    private void respawn(Ball ball) {
        int BallD = ball.getD()/2;
        if(BallD >= MIN_BALL_D) {
            balls.add(newBall(ball.getX(), ball.getY(), BallD, 3, ball.getSpeed()));
            balls.add(newBall(ball.getX(), ball.getY(), BallD, -3, ball.getSpeed()));
        }
    }

    private Ball newBall(int x, int y, int d, int direction, int speed){
        Ball ball = new Ball(x, y, d, direction, speed);
        balls.add(ball);
        drowObjs.add(ball.getDrowObj());
        return ball;
    }

    private void removeBall(Ball ball){
        balls.remove(ball);
        drowObjs.remove(ball.getDrowObj());
    }

    public void actionPerformed(ActionEvent arg0) {
        run();
    }

    public KeyListener getKeyListener(){
        return new KeyListener() {
            public void keyPressed(KeyEvent arg0) {
// System.out.println("P="+arg0.getKeyCode());
                switch(arg0.getKeyCode()){
                    case 82:{newBall(10, 10, 100, 3, 0); break;}
                    case 37:{player.left = true; break;}
                    case 39:{player.right = true; break;}
                    case 32:{arrow.fire(player.getX()); break;}
                }}
            public void keyReleased(KeyEvent arg0) {
                switch(arg0.getKeyCode()){
                    case 37:{player.left=false;break;}
                    case 39:{player.right=false;break;}
                }}
            public void keyTyped(KeyEvent arg0) {
//System.out.println("T="+arg0.getKeyChar());
            }
        };
    }
}

