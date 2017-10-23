/*
 *  CitiesMap is the static class of all cities to visit
 *  
 *  Attributes:
 *      # Private
 *      -   int[][] cities                  -> All cities X and Y positions
 *      -   int[][] map                     -> All distances between cities
 *
 *  Methods:
 *      # Public
 *      -   void randomizeCities()          -> Randomize all cities X and Y positions
 *      -   void initMap()                  -> Initializate all cities distances
 *      -   int getDistanceFrom(int, int)   -> Return distance between two cities   
 *      -   int[][] getCities()             -> Return this.cities
 *
 *      # Private
 *      -   int calculateDistance(int, int) -> Calculate distance between two cities
 *
 */

import java.text.*;
import java.util.Random;

public final class CitiesMap{

    private static int[][] cities = {{768,135},
        {570,166},{69,145},{462,99},{742,100},
        {70,171},{700,371},{468,535},{830,324},
        {701,254},{63,457},{685,344},{105,242},
        {272,500},{347,147},{520,446},{42,187},
    };
                              
    private static int[][] map;
    
    private CitiesMap(){}
    
    public static void randomizeCities(){
        CitiesMap.cities = new int[Main.cityNumber][2];
        
        Random rand = new Random();
        
        for(int i=0;i<Main.cityNumber;i++){
            int x = rand.nextInt(825)+25;
            int y = rand.nextInt(525)+25;
            
            CitiesMap.cities[i][0] = x;
            CitiesMap.cities[i][1] = y;
        }
    }
    
    public static void initMap(){        
        /*
         *  Code to generate random cities 
         *
         * java.util.Random rand = new java.util.Random();
         * for(int i=0;i<Main.cityNumber;i++){
         *    int a = rand.nextInt(825)+25;
         *    int b = rand.nextInt(525)+25;
         *   
         *    System.out.print("{"+a+","+b+"},");
         *    if(i%4==0) System.out.println();
         *}
         */
         
        CitiesMap.map = new int[Main.cityNumber][];
        
        for(int i=0;i<Main.cityNumber;i++){
            CitiesMap.map[i] = new int[i+1];
            for(int j=0;j<i+1;j++){
                CitiesMap.map[i][j] = CitiesMap.calculateDistance(i,j);
                //System.out.print(CitiesMap.map[i][j]+" ");
            }
            //System.out.println();
        }
    }
    
    private static int calculateDistance(int firstCity, int secondCity){
    
        // Calculate X e Y distance with absolute value
        int xDistance = java.lang.Math.abs(CitiesMap.cities[firstCity][0] - CitiesMap.cities[secondCity][0]);
        int yDistance = java.lang.Math.abs(CitiesMap.cities[firstCity][1] - CitiesMap.cities[secondCity][1]);

        // Pitagora
        int distance = (int)java.lang.Math.sqrt(java.lang.Math.pow(xDistance,2.0) + java.lang.Math.pow(yDistance,2.0));
        
        return distance;
    }
    
    public static int getDistanceFrom(int cityA, int cityB){
        if(cityA > cityB){
            return CitiesMap.map[cityA][cityB];
        }else{
            return CitiesMap.map[cityB][cityA];        
        }
    }
    
    public static int[][] getCities(){
        return CitiesMap.cities;
    }
}
