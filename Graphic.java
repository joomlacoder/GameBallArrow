import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * Created by Andrej on 10.12.2017.
 */
public class Graphic {

    private static final int COLOR_BG = 0xff00ffff;

    private BufferedImage buffer;
    private int[] bufferData;
    private Graphics bufferGraphics;
    private BufferStrategy bufferStrategy;
    private Graphics g;
    private Game game;


    private void initWindow(String nameWindow, int width, int higth, Canvas content){
        JFrame window = new JFrame(nameWindow);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        content.setSize(width, higth);
        window.getContentPane().add(content);
        window.pack();
    }

    private void initGraphic(int width, int higth, Canvas content){
        buffer = new BufferedImage(width, higth, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();
        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        content.createBufferStrategy(4);
        bufferStrategy = content.getBufferStrategy();
        g = bufferStrategy.getDrawGraphics();
        content.setFocusable(true);
    }

    Graphic(String nameWindow, int width, int higth, Canvas content, Game game){
        initWindow(nameWindow, width, higth, content);
        initGraphic(width, higth, content);
        //нафиг удалить, когда графика будет работать корректно
        this.game = game;
    }

    void draw(Graphics g) {
        //очень плохой код, не буду трогать его
        g.setColor(new Color(0xff00ff00)); //дубина
        g.fillRoundRect(game.arrow.getX(), game.arrow.getY(), 2, game.arrow.getH(), 10, 10);
        g.setColor(new Color(0xffff0000));//игрок
        g.fillRoundRect(game.player.getX(), 650, 30, 60, 10, 10);
        g.setColor(new Color(0xff0000ff));
        for(Ball ball : game.balls){
            g.fillOval(ball.getX(),ball.getY(),ball.getD(),ball.getD());
        }
//g.fillOval(500,600,50,50);
    }

    public void actionPerformed() {
        Arrays.fill(bufferData, COLOR_BG);
        draw(bufferGraphics);
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }
}
