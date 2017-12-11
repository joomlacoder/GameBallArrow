import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Andrej on 10.12.2017.
 */
public class Main {

        private final static String NAME = "Game";
        final static int WIDTH = 1360;
        final static int HIGTH = 700;
        private final static int TIME_ONE_SEC = 1000;
        private final static int FPS = 60;
        private static CopyOnWriteArrayList<DrowObj> drowObjs = new CopyOnWriteArrayList<DrowObj>();

    public static void main(String[] args) {
        Canvas content;
        content = new Canvas();
        Game game = new Game(drowObjs);
        Graphic graphic = new Graphic(NAME, WIDTH, HIGTH, content, drowObjs);
        Timer timer = new Timer(TIME_ONE_SEC / FPS, e -> {
                graphic.actionPerformed();
                game.run();
        });
        content.addKeyListener(game.getKeyListener());
        timer.start();
    }
}
