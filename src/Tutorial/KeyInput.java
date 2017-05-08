package Tutorial;
import GenerationLogic.GenerationBreeder;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

    private Handler handler;
    private Game gameInstance;

    public KeyInput(Handler handler, Game gameInstance){
        this.handler = handler;
        this.gameInstance = gameInstance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == 61){
            if(gameInstance.getSpeed() <= 960)
                gameInstance.setSignal(1);
        } else if(key == 45){
            if(gameInstance.getSpeed() >= 15)
                gameInstance.setSignal(-1);
        }
        if(key == 32 && !Game.hasBegun){
            gameInstance.setupGame();
        }

        if(key == 83 && Game.hasBegun){
            GenerationBreeder.writeGenToFile();
        } else if(key == 76 && !Game.hasBegun){
            GenerationBreeder.loadGenFromFile();
        }

        for(int i = 0; i < handler.gameObjects.size(); i++){
            GameObject tempObject = handler.gameObjects.get(i);

            if(tempObject.getId() == ID.Player){
                if(key == KeyEvent.VK_RIGHT) tempObject.setVelX(2);
                if(key == KeyEvent.VK_LEFT) tempObject.setVelX(-2);
                if(key == KeyEvent.VK_UP) tempObject.setVelY(-2);
                if(key == KeyEvent.VK_DOWN) tempObject.setVelY(2);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for(int i = 0; i < handler.gameObjects.size(); i++){
            GameObject tempObject = handler.gameObjects.get(i);

            if(tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_RIGHT) tempObject.setVelX(0);
                if (key == KeyEvent.VK_LEFT) tempObject.setVelX(0);
                if (key == KeyEvent.VK_UP) tempObject.setVelY(0);
                if (key == KeyEvent.VK_DOWN) tempObject.setVelY(0);
            }
        }
    }
}
