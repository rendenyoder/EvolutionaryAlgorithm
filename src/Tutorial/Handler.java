package Tutorial;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
    Game gameInstance;

    public Handler(Game gameInstance){
        this.gameInstance = gameInstance;
    }

    //Tick function to change object variables
    public void tick(){
        for(int i = 0; i < gameObjects.size(); i++){
            GameObject tempObject = gameObjects.get(i);
            tempObject.tick();
        }
    }

    //Render graphic
    public void render(Graphics graphics){
        for(int i = 0; i < gameObjects.size(); i++){
            GameObject tempObject = gameObjects.get(i);
            tempObject.render(graphics);
        }
        checkForCollision();
    }

    public void checkForCollision(){
        for(int i = 1; i < gameObjects.size(); i++){
            if (!gameInstance.isBeginTrial()) {
                if (gameObjects.get(0).getX() - 20 <= gameObjects.get(i).getX() && gameObjects.get(0).getX() + 20 >= gameObjects.get(i).getX()) {
                    if (gameObjects.get(0).getY() - 20 <= gameObjects.get(i).getY() && gameObjects.get(0).getY() + 20 >= gameObjects.get(i).getY()) {
                        GameController.reset();
                        //Stop all objects
                        for(int j = 0; j < gameObjects.size(); j++) {
                            GameObject tempObject = gameObjects.get(j);
                            tempObject.setVelX(0);
                            tempObject.setVelY(0);
                        }
                    }
                }
            }
        }
    }

    //Add game object to list
    public void addObject(GameObject object){
        this.gameObjects.add(object);
    }

    //Remove game object from list
    public void removeObject(GameObject object){
        this.gameObjects.remove(object);
    }
}
