import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Pchola extends Thread implements ActionListener {
    static Canvas content;
    static BufferedImage buffer;
    static int[] bufferData;
    static Graphics bufferGraphics;
    static BufferStrategy bufferStrategy;
    static Graphics g;
    Player player = new Player();
    Arrow arrow = new Arrow();
    Game game = new Game();
    CopyOnWriteArrayList <Ball> balls = new CopyOnWriteArrayList<Ball>();

    //Инициализаю и графику можно в блоки вынести и читать удобне будет
    public static void main(String[] args) {
        JFrame window = new JFrame("Коты");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        content = new Canvas();
        content.setSize(1360, 700);
        window.getContentPane().add(content);
        window.pack();
        Timer timer=new Timer(1000/60, new Pchola()); timer.start();
        buffer=new BufferedImage(1360,700,BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();
        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        content.createBufferStrategy(4);
        bufferStrategy = content.getBufferStrategy();
        g = bufferStrategy.getDrawGraphics();
    }

    //Что за пчела? Почему тут все? Создай нормальный майн) Тогда и актионлисенер спрячется
    Pchola(){
        content.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent arg0) {
// System.out.println("P="+arg0.getKeyCode());
                switch(arg0.getKeyCode()){
                    case 37:{player.left=true;break;}
                    case 39:{player.right=true;break;}
                    case 32:{arrow.fire(player.x);break;}
                }}
            public void keyReleased(KeyEvent arg0) {
                switch(arg0.getKeyCode()){
                    case 37:{player.left=false;break;}
                    case 39:{player.right=false;break;}
                }}
            public void keyTyped(KeyEvent arg0) {
//System.out.println("T="+arg0.getKeyChar());
            }
        });
        content.setFocusable(true);
        balls.add(new Ball(0,400,50,3));

    }

    //вынести в логику надо и параметры через константы и все задать. + Размеры может тоже вынести для удобного чтения
    private boolean collisionBall(Ball ball, String parm){
        if(parm.equals("arow"))
            return ball.x<arrow.x && arrow.x<ball.x+ball.d && ball.y<arrow.y+arrow.h && arrow.y<ball.y+ball.d;
        else
            return ball.x < player.x+player.width && player.x<ball.x + ball.d && player.y<ball.y+ball.d;
    }

    //Запускать пчелу? Это надо в класс игра или еще где логика
    public void run() {
        for(Ball ball: balls){
//движение шариков
            //разберись с простыней кода... Или одно действие или вызов функций
            ball.speed+=1;
            ball.y+=ball.speed;
            ball.x+=ball.direction;

            if(ball.y>670){
                ball.speed*=-1;
                ball.y+=ball.speed;
            }

            if(ball.x>1300)
                ball.direction=-3;

            else if(ball.x<0)
                ball.direction=3;
//состыковка
            if(collisionBall(ball, "arow") ){
                game.respawn(ball);
                //arrow.x = arrow.y = -1;
                balls.remove(ball);
            }
            else if (collisionBall(ball, "")) {
                balls.remove(ball);
            }
        }
//player
        if(player.left & player.x>0){player.x-=12;}
        if(player.right & player.x<1330){player.x+=12;}
//дубина
        if(arrow.fire)arrow.y-=10;//летит безконечно
    }

    public void actionPerformed(ActionEvent arg0) {
        Arrays.fill(bufferData, 0xff00ffff);
        draw(bufferGraphics);
        run();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }

    void draw(Graphics g) {

        g.setColor(new Color(0xff00ff00)); //дубина
        g.fillRoundRect(arrow.x, arrow.y, 2, arrow.h, 10, 10);
        g.setColor(new Color(0xffff0000));//игрок
        g.fillRoundRect(player.x, 650, 30, 60, 10, 10);
        g.setColor(new Color(0xff0000ff));
        for(Ball ball:balls){ g.fillOval(ball.x,ball.y,ball.d,ball.d);}
//g.fillOval(500,600,50,50);
    }




    //Странный класс, он нужен, но сюда больше функций
    class Game{
        void respawn(Ball ball) {
                balls.add(new Ball(ball.x+50,ball.y,50,3));
                balls.add(new Ball(ball.x-50,ball.y,50,-3));
            }
        }
    }

