package Tutorial;
import java.awt.*;
import javax.swing.*;

public class Window  extends Canvas{
    //Unique ID
    private static final long serialVersionUID = -2342345324532452342L;

    private static JFrame frame;

    //Create window
    public Window(int width, int height, String title, Game game){
        //Window Title
        this.frame = new JFrame(title);
        //Set window preferences
        frame.setPreferredSize(new Dimension(width, height));
        //frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        //Start game
        game.start();
    }

    //Return current window height
    public static int getWindowHeight() {return frame.getBounds().height;}

    //Return current window width
    public static int getWindowWidth() {return frame.getBounds().width;}

    //Set window height
    public static void sustainWindowRatio(int w, int h) {
        double ratio = w/h;
        frame.setSize(new Dimension(frame.getBounds().width, (frame.getBounds().width/16) * 10));
    }

    public void destroy(){
        this.frame.dispose();
    }
}
