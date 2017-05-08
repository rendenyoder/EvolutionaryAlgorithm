package Tutorial;

import GenerationLogic.Candidates;
import GenerationLogic.GenerationBreeder;

public class GameController {
    //The game itself
    private static Game game;
    //Final values for screen size
    public static final int WIDTH = 640, HEIGHT = (WIDTH/16) * 10;
    public static Window window;
    //Create initial random candidates
    public static Candidates candidates = new Candidates();

    public static void main(String[] args){
        game = new Game(window);
        //Set initial move set
        game.getComputer().setMoveSet(candidates.getCandidates().get(Candidates.index));
        //Make window for game
        window = new Window(WIDTH, HEIGHT, "Build Game", game);
    }

    public static void reset(){
        candidates.getScores().put(candidates.getParentMoves().get(Candidates.index), Player.moveIndex);
        //System.out.println(Candidates.index + " : " + Player.moveIndex + "   ");
        //System.out.println(candidates.getScores().size());

        //Create new generation
        if(Candidates.index >= 99){
            Candidates.index = -1;
            GenerationBreeder.createNewGeneration(candidates);
            Candidates.generation++;
        }

        //Reset move index aka fitness
        Player.moveIndex = 0;
        //Set the new move set
        game.getComputer().setMoveSet(candidates.getCandidates().get(++Candidates.index));
        //System.out.println(candidates.getCandidates().get(Candidates.index));
        //Set up game
        game.setBeginTrial(true);
    }

}
