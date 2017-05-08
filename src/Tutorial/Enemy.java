package Tutorial;

import java.awt.*;

public class Enemy extends GameObject{
    //Player constructor
    public Enemy(int x, int y, String imagePath, int width, int height, ID id){
        super(x,y,imagePath,width,height,id);
    }

    //Changes to player overtime
    public void tick(){
        x += velX;
        y += velY;
        clamp();
    }

    //Render player graphics
    public void render(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillOval(x, y, width, height);
    }
}
