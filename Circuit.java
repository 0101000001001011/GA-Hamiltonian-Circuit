/*
 *  Circuit is the class that rappresent the chromosome
 *  
 *  Attributes:
 *      # Private
 *      -   int[] path                       -> The chromosome
 *
 *      # Public
 *      -   static Comparator<Circuit> circuitComparator
 *
 *  Methods:
 *      # Constructors
 *      -   Circuit()                        -> Inizializate path with random values
 *      -   Circuit(final int, final int)    -> Crossover constructor
 *      -   Circuit(final int)               -> Mutation constructor
 *
 *      # Public
 *      -   int getPathLenght()              -> Return path lenght
 *      -   final int[] getPath()            -> Return this.path  
 *      -   void drawCircuit(LinesComponent) -> Comunicate with LinesComponent to print circuit in GUI
 *      -   int compareTo(Circuit)           -> Required for Collection.sort() function
 *
 */
 
import java.util.Comparator;
import java.awt.Color;
import java.util.Random;

public class Circuit implements Comparable<Circuit>{
    
    private int[] path;

    //Random constructor
    public Circuit(){
        Random rand = new Random();
        
        this.path = new int[Main.cityNumber];
        
        for(int i=0;i<Main.cityNumber;i++){
            this.path[i] = i;
        }
        
        for(int i=0;i<Main.cityNumber;i++){
            int randPos = rand.nextInt(Main.cityNumber);
            
            int swap = this.path[i];
            this.path[i] = this.path[randPos];
            this.path[randPos] = swap;
        }
    }
    
    //Crossover constructor
    public Circuit(final int[] parentOne, final int[] parentTwo){        
        Random rand = new Random();
        
        this.path = parentOne.clone();
        
        int from = rand.nextInt(Main.cityNumber-2);
        int to = rand.nextInt((int)(Main.cityNumber*Main.crossoverProb))+from+1;
        if(to > Main.cityNumber-1){ to=Main.cityNumber-1; }
        
        for(int i=from;i<to;i++){
            int search = parentTwo[i];
            
            int j = 0;
            while(j<Main.cityNumber){
                if(parentOne[j] == search){
                    int swap = this.path[i];
                    this.path[i] = this.path[j];
                    this.path[j] = swap;
                }
                j++;
            }
        }
        
        // Mutate the crossovered chromosome
        this.path = new Circuit(this.path).getPath();
    }
    
    // Mutation constructor
    public Circuit(final int[] toMutate){
        Random rand = new Random();
        
        this.path = toMutate.clone();
        
        // Casting to avoid troubles
        double cityNumber = (double)(Main.cityNumber);
        double mutationtProb = (double)(Main.mutationProb)/100;
        
        int mutations = (int)(cityNumber*mutationtProb);
        
        for(int i=0;i<mutations;i++){
            int randPos1 = rand.nextInt(Main.cityNumber);
            int randPos2 = rand.nextInt(Main.cityNumber);
            
            int swap = this.path[randPos1];
            this.path[randPos1] = this.path[randPos2];
            this.path[randPos2] = swap;
        }
    }
    
    public int getPathLenght(){
        int pathLenght = 0;
        for(int i=0;i<Main.cityNumber;i++){
            if(i==Main.cityNumber-1){
                pathLenght += CitiesMap.getDistanceFrom(this.path[i], this.path[0]);
            }else{
                pathLenght += CitiesMap.getDistanceFrom(this.path[i], this.path[i+1]);
            }
        }
        return pathLenght;
    }
    
    public final int[] getPath(){
        return this.path;
    }
    
    public void drawCircuit(LinesComponent comp){
        final int[][] cities = CitiesMap.getCities();
        
        comp.clearLines();
        
        comp.setPathDistance(this.getPathLenght());
        
        for(int i=0;i<Main.cityNumber;i++){
            int x1,x2,y1,y2, distance;
            x2 = cities[this.path[i]][0];
            y2 = cities[this.path[i]][1];
            if(i==Main.cityNumber-1){
                x1 = cities[this.path[0]][0];
                y1 = cities[this.path[0]][1];
                distance = CitiesMap.getDistanceFrom(this.path[i], this.path[0]);
            }else{
                x1 = cities[this.path[i+1]][0];
                y1 = cities[this.path[i+1]][1];
                distance = CitiesMap.getDistanceFrom(this.path[i], this.path[i+1]);
            }
            comp.addLine(x1, y1, x2, y2, distance);
        }
    }
    
    public int compareTo(Circuit circuitB){
        return this.getPathLenght() - circuitB.getPathLenght();
    }
    
    public static Comparator<Circuit> circuitComparator = new Comparator<Circuit>() {

        public int compare(Circuit circuitA, Circuit circuitB) {
            return circuitA.getPathLenght() - circuitB.getPathLenght();
        }

    };
}
