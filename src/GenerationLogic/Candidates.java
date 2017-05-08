package GenerationLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Candidates {
    private ArrayList<ArrayList<Moves>> candidates = new ArrayList<>();
    private ArrayList<ArrayList<Moves>> parentMoves = new ArrayList<>();
    private HashMap<ArrayList<Moves>, Integer> scores = new HashMap<>();
    private ArrayList<Integer> averageScores = new ArrayList<>();
    public static double deltaFitness = 0.0;

    public static int index = 0;
    public static int generation = 1;

    public Candidates(){
        //Create random variable
        Random r = new Random();
        //Make 100 initial random move sequences
        for(int i = 0; i < 100; i++){
            ArrayList<Moves> moveSequences = new ArrayList<>();
            ArrayList<Moves> parentSet = new ArrayList<>();
            //Create a list of moves
            for(int j = 0; j < 100; j++){
                Moves randMove = Moves.fromInteger(r.nextInt(9));
                parentSet.add(randMove);

                for(int k = 0; k < 100; k++) {
                    moveSequences.add(randMove);
                }
            }
            parentMoves.add(parentSet);
            candidates.add(moveSequences);
        }
    }

    public ArrayList<ArrayList<Moves>> getCandidates() {
        return candidates;
    }

    public HashMap<ArrayList<Moves>, Integer> getScores() {
        return scores;
    }

    public void setCandidates(ArrayList<ArrayList<Moves>> candidates) {
        this.candidates = candidates;
    }

    public void setScores(HashMap<ArrayList<Moves>, Integer> scores) {
        this.scores = scores;
    }

    public ArrayList<ArrayList<Moves>> getParentMoves() {
        return parentMoves;
    }

    public void setParentMoves(ArrayList<ArrayList<Moves>> parentMoves) {
        this.parentMoves = parentMoves;
    }

    public ArrayList<Integer> getAverageScores() {
        return averageScores;
    }

    public void setAverageScores(ArrayList<Integer> averageScores) {
        this.averageScores = averageScores;
    }

    public void setDeltaFitness(double deltaFitness) {
        Candidates.deltaFitness = deltaFitness;
    }
}
