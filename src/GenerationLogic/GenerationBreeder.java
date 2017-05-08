package GenerationLogic;

import Tutorial.GameController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class GenerationBreeder {
    public static boolean getSavedState = false;
    public static ArrayList<ArrayList<Moves>> bestSelection;
    private static String SAVEFILE = "./savedState.txt";
    private static int averageScore = 0;

    public static void createNewGeneration(Candidates candidates){
        if(getSavedState){
            getSavedState = false;
        } else {
            findMax(candidates);
            //Get average score
            for (ArrayList<Moves> each : bestSelection) averageScore += candidates.getScores().get(each);
            averageScore /= bestSelection.size();
            System.out.println("Generation " + Candidates.generation + " average: " + averageScore);
        }

        candidates.getAverageScores().add(averageScore);

        ArrayList<ArrayList<Moves>> nextGen = new ArrayList<>();
        ArrayList<ArrayList<Moves>> geneticParentMoves = new ArrayList<>();
        Random r = new Random();

        double deltaGenFitness = 0.0;
        if(candidates.getAverageScores().size() >= 2){
            int size = candidates.getAverageScores().size();
            deltaGenFitness = (((candidates.getAverageScores().get(size-1) * 1.0) / candidates.getAverageScores().get(size-2)) - 1)*100;
            deltaGenFitness = Math.round (deltaGenFitness * 100.0) / 100.0;
        }

        Candidates.deltaFitness = deltaGenFitness;



        for(ArrayList<Moves> eachSet : bestSelection){

            int averageMoves = averageScore / 100;

            for(int i = 0; i < 20; i++) {

                ArrayList<Moves> moveSequences = new ArrayList<>();
                ArrayList<Moves> parentSet = new ArrayList<>();


                for (Moves eachMove : eachSet) {
                    Moves gene = eachMove;
                    parentSet.add(gene);
                }

                //Randomly try to mutate ~2% of genes
                for(int j = 0; j < parentSet.size()/50; j++){
                    if(r.nextInt(10) == 5){
                        int randGeneIndex = r.nextInt(parentSet.size());
                        Moves mutatedMove = Moves.fromInteger(r.nextInt(9));
                        parentSet.set(randGeneIndex, mutatedMove);
                    }
                }

                while(parentSet.size() < averageMoves * 5){
                    Moves randGene = Moves.fromInteger(r.nextInt(9));
                    parentSet.add(randGene);
                }

                for(Moves eachMove : parentSet){
                    for(int j = 0; j < 100; j++){
                        moveSequences.add(eachMove);
                    }
                }

                geneticParentMoves.add(parentSet);
                nextGen.add(moveSequences);
            }

        }

        candidates.setParentMoves(geneticParentMoves);
        candidates.setCandidates(nextGen);
        candidates.setScores(new HashMap<>());
    }


    private static void findMax(Candidates candidates){

        bestSelection = new ArrayList<>();

        List<Integer> mapScores = new ArrayList<>(candidates.getScores().values());
        Collections.sort(mapScores);
        Collections.reverse(mapScores);


        for(Integer score : mapScores){
            for(ArrayList<Moves> each : candidates.getScores().keySet()) {
                if(candidates.getScores().get(each) == score){
                    bestSelection.add(each);
                } else if(bestSelection.size() >= 5){
                    return;
                }
            }
        }
    }


    public static void writeGenToFile(){
        try{
            PrintWriter writer = new PrintWriter(SAVEFILE, "UTF-8");
            writer.println(Candidates.generation);
            writer.println(averageScore);
            for(ArrayList<Moves> each : bestSelection){
                writer.println(each);
            }
            writer.close();
        } catch (java.io.IOException e) {

        } catch (NullPointerException n){

        }
    }

    public static void loadGenFromFile(){
        bestSelection = new ArrayList<>();
        getSavedState = true;

        List<String> records = new ArrayList<String>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(SAVEFILE));
            String line;
            while ((line = reader.readLine()) != null){
                records.add(line);
            }
            reader.close();

            Candidates.generation = Integer.parseInt(records.get(0));
            averageScore = Integer.parseInt(records.get(1));


            for(int i = 2; i < records.size(); i++){
                ArrayList<Moves> movesOfCandidate = new ArrayList<>();

                String eachCandidate = records.get(i).replaceAll("\\[", "").replaceAll(" ", "").replaceAll("\\]", "");

                for(String each : eachCandidate.split(",")){
                    movesOfCandidate.add(Moves.fromString(each));
                }

                bestSelection.add(movesOfCandidate);
            }
            createNewGeneration(GameController.candidates);
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", SAVEFILE);
            e.printStackTrace();
        }
    }
}
