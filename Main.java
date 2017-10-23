/*
 *  Created by Alessandro Lodi
 *
 *  Genetic algoritm (GA) to find the best path in hamiltonian circuit
 *
 */

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main{
    public static int populationSize = 60;
    public static double crossoverProb = 0.6;
    public static int mutationProb = 20;
    public static int waitTime = 100;
    public static int cityNumber = 17;
    public static int maxBest = 2274;
    public static int maxIteration = 1000;
    public static boolean toInfinite = false;
    public static boolean cheat = false;

    public static List<Circuit> makeTournament(List<Circuit> population){
        Random rand = new Random();
        List<Circuit> tournament = new ArrayList<Circuit>();
        
        int randNu = (int)(population.size()/2)-(int)(population.size()/4);
        int membersNumb = rand.nextInt(randNu)+(int)(population.size()/4);
        
        for(int i=0;i<membersNumb;i++){
            int member = rand.nextInt(population.size());
            tournament.add(population.get(member));
            if(!cheat){
                population.remove(member);
            }
        }
            
        Collections.sort(tournament, Circuit.circuitComparator);
        
        return tournament;
    }

    public static void main(String[] args){
        Args.parseArgs(args);
        
        if(cheat){
            populationSize = 30;
        }
        
        // Initializiation of GUI
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final LinesComponent comp = new LinesComponent();
        comp.setPreferredSize(new Dimension(900, 600));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        testFrame.pack();
        testFrame.setVisible(true);
        // **********************
                
        Random rand = new Random();
    
        // Map distances init
        CitiesMap.initMap();
    
        // Main variable, contains all chromosomes during the program
        List<Circuit> population = new ArrayList<Circuit>();
        
        int minChilds = (int)(populationSize*crossoverProb);
        
        // Initial population
        for(int i=0;i<populationSize;i++){
            Circuit newChild = new Circuit();
            population.add(newChild);
        }
        
        
        int iteration = 0;
        int actualBest = 1000000;
        
        // Until it find the best solution
        while(toInfinite || (iteration<maxIteration && actualBest>maxBest)){
        
            List<Circuit> nextGeneration = new ArrayList<Circuit>();
                
            // Best of tournaments to increse diversity
            List<Circuit> bests = new ArrayList<Circuit>();
            
            while(nextGeneration.size() < minChilds){
                // Makes 2 tournament
                List<Circuit> tournament = makeTournament(population);
                List<Circuit> tournament2 = makeTournament(population);
                
                bests.add(tournament.get(0));
                bests.add(tournament2.get(0));
                
                // Re-add the tournament partecipant to the population
                population.addAll(tournament);
                population.addAll(tournament2);
                
                // Crossover    
                Circuit crossovered = new Circuit(tournament.get(0).getPath(),tournament2.get(0).getPath());
                nextGeneration.add(crossovered);
            }    
            
            // Re-add the best to give more chance to best individuals
            population.addAll(bests);  
            
            // Mutation process based on mutationProb
            for(Circuit toMutate: population){
                int mutationChance = rand.nextInt(100-(mutationProb));
                int randomNum = rand.nextInt(100);
                if(mutationChance <= randomNum && (mutationChance+mutationProb) >= randomNum){
                    nextGeneration.add(new Circuit(toMutate.getPath()));
                }
            }
            
            // Add childs
            population.addAll(nextGeneration);

            // Evaluation function
            Collections.sort(population, Circuit.circuitComparator);
            
            // Selection
            population.subList(populationSize,population.size()).clear();

            // Paint the best
            comp.setIteration(iteration);
            population.get(0).drawCircuit(comp);
            actualBest = population.get(0).getPathLenght();
            
            Collections.shuffle(population);
            
            if(waitTime != 0){
                try {
                    Thread.sleep(waitTime);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }   
            }
            
            iteration++;
        }
    }
}
