package Tutorial;

import GenerationLogic.Candidates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{
    private static final long serialVersionUID = 2342345324532452342L;
    //Variable for speed of game
    double amountOfTicks = 120.0;
    //Create players
    Player computer;
    Enemy enemy1;
    Enemy enemy2;
    Enemy enemy3;
    //Create thread for game to run
    private Thread thread = new Thread();
    //Booling value to check whether game is running
    private volatile boolean running = false;
    Window window;
    //Create handler for game
    private volatile Handler handler;
    //Background Image
    protected BufferedImage background;
    //Game has begun
    static boolean hasBegun = false;
    //Begin trial
    static boolean beginTrial = false;
    //Change speed
    int signal = 0;

    //Main game function
    public Game(Window window){
        this.window = window;

        handler = new Handler(this);

        this.addKeyListener(new KeyInput(handler, this));

        setBackground("/terminalBackground.png");

        computer = new Player(GameController.WIDTH/2 - 10, GameController.HEIGHT/2 - 40,"", 20, 20, ID.Player);

        enemy1 = new Enemy(50, 95,"", 20, 20, ID.Enemey);
        enemy2 = new Enemy(50, 95,"", 20, 20, ID.Enemey);
        enemy3 = new Enemy(50, 95,"", 20, 20, ID.Enemey);

        handler.addObject(computer);
        handler.addObject(enemy1);
        handler.addObject(enemy2);
        handler.addObject(enemy3);
    }

    public synchronized void setupGame(){
        hasBegun = true;

        computer.setX(GameController.WIDTH/2 - 10);
        computer.setY(GameController.HEIGHT/2 - 40);

        enemy1.setX(50);
        enemy1.setY(95);
        enemy1.setVelX(1);
        enemy1.setVelY(1);

        enemy2.setX(50);
        enemy2.setY(95);
        enemy2.setVelX(-1);
        enemy2.setVelY(-1);

        enemy3.setX(50);
        enemy3.setY(95);
        enemy3.setVelX(1);
        enemy3.setVelY(-1);

    }

    //Start threads at same time
    public synchronized void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    //Stop threads at the same time
    public synchronized void stop(){
        try {
            thread.join();
            running = false;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //Game loop
    public void run() {
        int speed = 500000000;
        this.requestFocus();
        long lastTime = System.nanoTime();
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running) {
            double ns = speed / amountOfTicks;
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            handler.checkForCollision();
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running) {
                render();
            }
            frames++;


            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                //Wait for signal to increase or decrease speed
                switch (signal){
                    case 0: break;
                    case 1: increaseSpeed(); signal = 0; break;
                    case -1: decreaseSpeed(); signal = 0; break;
                }
                //Wait for game signal to start game
                if(beginTrial){
                    setupGame();
                    beginTrial = false;
                }
            }
        }
        stop();
    }

    //Tick function
    private void tick(){
        handler.tick();
    }

    //Render function to redraw frame every second
    private void render(){
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }

        //New graphics object
        Graphics graphics = bufferStrategy.getDrawGraphics();

        //Draw background image
        graphics.drawImage(background, 0, 0, window.getWindowWidth(), window.getWindowHeight(), null);

        //Maintain window ratio
        window.sustainWindowRatio(WIDTH, HEIGHT);

        //Redraw frame
        handler.render(graphics);

        //Draw messages
        graphics.setColor(Color.WHITE);
        graphics.drawString("Generation: " + Candidates.generation + "  Candidate: " + Candidates.index + "  Speed: " + (signal == 0 ? amountOfTicks/120+"x" : "Syncing"), 5, 15);
        graphics.drawString("△ Generation Fitness: " + Candidates.deltaFitness + "%  Fitness: " + Player.moveIndex, 5, 30);


        //Dispose of frame
        graphics.dispose();
        bufferStrategy.show();
    }

    //Set background image
    public void setBackground(String backgroundPath){
        //Try to set background
        try{
            this.background = ImageIO.read(getClass().getResourceAsStream(backgroundPath));
        } catch(Exception e){}
    }


    public double getSpeed(){
        return amountOfTicks;
    }

    public void increaseSpeed(){
        amountOfTicks *= 2;
    }

    public void decreaseSpeed(){
        amountOfTicks /= 2;
    }

    public Player getComputer(){
        return computer;
    }

    public void setBeginTrial(boolean beginTrial) {
        this.beginTrial = beginTrial;
    }

    public boolean isBeginTrial() {
        return beginTrial;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

}
