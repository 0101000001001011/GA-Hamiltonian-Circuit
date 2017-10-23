import org.apache.commons.lang3.StringUtils;

public final class Args{
    public static void parseArgs(String[] args){
        String previous = "";
        
        for(String parameter: args){
        
            parameter = parameter.trim();
            
            if(parameter.equals("--infinite")){
                Main.toInfinite = true;
                
            }else if(parameter.equals("--cheat")){
                Main.cheat = true;
                
            }else if(parameter.equals("-r") || parameter.equals("--random")){
                // Random city generation
                CitiesMap.randomizeCities();
                Main.maxBest = 0;  
                
            }else if(parameter.equals("--help")){
                // Print the help
                System.out.println("");
                System.out.println("########################################");
                System.out.println("##       GA Hamiltonian Circuit       ##");
                System.out.println("########################################");
                System.out.println("");
                System.out.println("-> Created by Alessandro Lodi <-");
                System.out.println("");
                System.out.println("Usage: java Main <parameters>");
                System.out.println("");
                System.out.println("Possible parameters:");
                System.out.println("    | --help:       Print this :)");
                System.out.println("    | -r,--random:  Randomize city position");
                System.out.println("    | --infinite:   Loops until you press Ctrl+C");
                System.out.println("    | -p [30-1000]: Set the max population              Default:60");
                System.out.println("    | -t [n]:       Set wait time (milliseconds)        Default:100");
                System.out.println("    | -n [n]:       Set cities number                   Default:17");
                System.out.println("    | -i [n]:       Set the max iterations              Default:1000");
                System.out.println("    | -c [0-100]:   Set the crossover probability       Default:60%");
                System.out.println("    | -m [0-100]:   Set the mutation probability        Default:20%");
                System.out.println("");
                System.exit(0);
                
            }else if(StringUtils.isNumeric(parameter)){
                if(previous.equals("-p")){
                    // Change population size
                    Main.populationSize = Integer.parseInt(parameter);
                    Main.maxBest = 0;  
                    
                }else if(previous.equals("-t")){
                    // Change the wait time between generations
                    Main.waitTime = Integer.parseInt(parameter);  
                    
                }else if(previous.equals("-n")){
                    // Change the city number in the algorithm
                    Main.cityNumber = Integer.parseInt(parameter); 
                    CitiesMap.randomizeCities(); 
                    Main.maxBest = 0;                     
                    
                }else if(previous.equals("-i")){
                    // Change the max iteration number
                    Main.maxIteration = Integer.parseInt(parameter);     
                    
                }else if(previous.equals("-c")){
                    // Change the crossover probability
                    Main.crossoverProb = Integer.parseInt(parameter)/100;     
                    
                }else if(previous.equals("-m")){
                    // Change the mutation probability
                    Main.mutationProb = Integer.parseInt(parameter);         
                    
                }else{
                    System.out.println("Numbers cannot be used before this parameter");
                    System.exit(-1);
                    
                }
            }else if(parameter.equals("-p") || parameter.equals("-t") || parameter.equals("-n") || 
                     parameter.equals("-i") || parameter.equals("-c") || parameter.equals("-m")){
                previous = parameter;      
                
            }else{
                System.out.println("Unknown parameter!");
                System.out.println("Please use --help to see all parameters");
                System.exit(-1);
            }
        }
    }
} 
