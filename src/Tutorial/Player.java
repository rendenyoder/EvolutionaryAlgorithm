package Tutorial;

import GenerationLogic.Moves;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends GameObject{
    private ArrayList<Moves> moveSet;
    public static int moveIndex = 0;

    //Player constructor
    public Player(int x, int y, String imagePath, int width, int height, ID id){
        super(x,y,imagePath,width,height,id);
    }

    //Changes to player overtime
    public void tick(){
        x += velX;
        y += velY;
        clamp();

        //Make computer move
        if(getMoveSet() != null && !Game.beginTrial && Game.hasBegun) {
            setVelFromMove(getMoveSet().get(Player.moveIndex++));
        }
    }

    //Render player graphics
    public void render(Graphics graphics) {
        graphics.setColor(Color.CYAN);
        graphics.fillRect(x, y, width, height);
    }

    public void setVelFromMove(Moves x){
        //Reset all velocities
        resetVelocities();
        //Change velocities based on move
        switch (x){
            case L: setVelX(-1); break;
            case R: setVelX(1); break;
            case U: setVelY(-1); break;
            case D: setVelY(1); break;
            case LU: setVelX(-1); setVelY(-1); break;
            case LD: setVelX(-1); setVelY(1); break;
            case RU: setVelX(1); setVelY(-1); break;
            case RD: setVelX(1); setVelY(1); break;
            case NA: break;
        }
    }

    public void resetVelocities(){
        setVelX(0);
        setVelY(0);
    }

    public void setMoveSet(ArrayList<Moves> moveSet) {
        this.moveSet = moveSet;
    }

    public ArrayList<Moves> getMoveSet() {
        return moveSet;
    }
}
