package Tutorial;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    //Protect object variables
    protected int x, y;
    protected int velX, velY;
    protected ID id;
    //Object image variables
    protected int width, height;
    protected BufferedImage characterImage;
    protected String imagePath;

    //Constructor
    public GameObject(int x, int y, String imagePath, int width, int height, ID id){
        //Set variables
        this.x = x;
        this.y = y;
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
        this.id = id;

        //Try to set player image
        try{
            characterImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //Tick and render functions
    public abstract void tick();
    public abstract void render(Graphics graphics);

    //Getters
    public int getX() {return x;}
    public int getY() {return y;}
    public int getVelX() {return velX;}
    public int getVelY() {return velY;}
    public ID getId() {return id;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public BufferedImage getCharacterImage() {return characterImage;}
    public String getImagePath() {return imagePath;}

    //Setters
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setVelX(int velX) {this.velX = velX;}
    public void setVelY(int velY) {this.velY = velY;}
    public void setId(ID id) {this.id = id;}
    public void setWidth(int width) {this.width = width;}
    public void setHeight(int height) {this.height = height;}
    public void setCharacterImage(BufferedImage characterImage) {this.characterImage = characterImage;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}

    //Keeps player within given bounds
    public void clamp(){
        if(GameController.window.getWindowWidth()-width <= getX() || getX() <= 0){
            if(GameController.window.getWindowWidth()-width <= getX())
                setX(GameController.window.getWindowWidth() - width);
            else
                setX(0);
            if(id == ID.Enemey)
                setVelX(getVelX()*-1);
        }

        if(GameController.window.getWindowHeight()-(height+20) <= getY() || getY() <= 0){
            if(GameController.window.getWindowHeight()-(height+20) <= getY())
                setY(GameController.window.getWindowHeight()-(height+20));
            else
                setY(0);
            if(id == ID.Enemey)
                setVelY(getVelY()*-1);
        }
    }
}
