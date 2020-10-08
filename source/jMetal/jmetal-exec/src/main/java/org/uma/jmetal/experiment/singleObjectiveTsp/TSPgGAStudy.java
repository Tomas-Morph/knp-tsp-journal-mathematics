package org.uma.jmetal.experiment.singleObjectiveTsp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.EvolutionStrategyBuilder;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.kp.KNPSingleObjective1Set1;
import org.uma.jmetal.problem.singleobjective.kp.KNPSingleObjective2Set1;
import org.uma.jmetal.problem.singleobjective.tsp.TSPSingleObjective1;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;


/**
 * Class to configure and run a generational genetic algorithm. The
 * target problem is single objective TSP.
 * @author Mohammed Mahrach <mmahrach@ull.edu.es>
 * based on  Antonio J. Nebro templates <antonio@lcc.uma.es>
 */
public class TSPgGAStudy {

	private static final int INDEPENDENT_RUNS = 100;
	private static final int[] populationSizes ={20/*,50,100,200*/};
	private static final int[] maxEvaluations ={/*25000,*/10000000/*,100000*/};
	
	static String experimentBaseDirectory = "Experiments/TSP/SingleObjective";

	static List<BinaryProblem> problemList = new ArrayList<>();
	

	static Map<String, Algorithm<PermutationSolution<Integer>>> mapOfAlgorithmsList = new HashMap<String, Algorithm<PermutationSolution<Integer>>>();
	static List<Algorithm<BinarySolution>> algorithmsList = new ArrayList<>();
	static List<String> algorithmsName = new ArrayList<>();
		
	
	static List<Integer> algorithmsRun = new ArrayList<>();
	static long computingTime = 0;

	static List<String> experimentName = new ArrayList<>();
	//static String experimentBaseDirectory = "Experiments/KP/";

	/**
	 * Usage: java
	 * org.uma.jmetal.runner.singleobjective.ElitistEvolutionStrategyRunner
	 */

	
	public static void main(String[] args) throws IOException {
	//	if (args.length != 1) {
    //	    throw new JMetalException("Needed arguments: experimentBaseDirectory");
	//  }
	//  String experimentBaseDirectory = args[0];

		
		String path = "/tspInstances/";
		String instanceName = args[0];
//		Example:
		//String instanceName = "kroA100";
		
		
    	Problem<PermutationSolution<Integer>> problem = new TSPSingleObjective1(path+instanceName+".tsp");

				
		configureAlgorithmList(problem);
		//mapOfAlgorithmsList.forEach((k,v)->{System.out.println("algorithm Key : " + k + " algorithm.getName() : " + v.getName());});			
		executeAlgorithmList(instanceName,problem);
		//writeResultToFile();


	}

	@SuppressWarnings("resource")
	private static void executeAlgorithmList(String instanceName, Problem<PermutationSolution<Integer>> problem) {
		
    		for (Entry<String, Algorithm<PermutationSolution<Integer>>> algorithmEntry : mapOfAlgorithmsList.entrySet()) {
    		    //System.out.println("Key = " + algorithm.getKey() + ", Value = " + algorithm.getValue());
    			String algorithmName = algorithmEntry.getKey();
    			
    			
    			Algorithm<PermutationSolution<Integer>> algorithm = algorithmEntry.getValue();
    		
    			//System.out.println("algorithm Key : " + algorithmName + " algorithm.getName() : " + algorithm.getName());			

    			String fileName = experimentBaseDirectory+ "/" 
    			+ "/" + instanceName + "/";
    			
				File DIR = new File(fileName+"/"+"data"+"/"+algorithmName+"/");
				DIR.mkdirs();
				
				List<PermutationSolution<Integer>> solutionList = new ArrayList<>();

    			try {
    				for (int run = 0; run < INDEPENDENT_RUNS; run++) {

						AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
						computingTime += algorithmRunner.getComputingTime();
		
						
						PermutationSolution<Integer> solution = algorithm.getResult();
						solutionList.add(solution);
								
						
						System.out.print(algorithmName+" -> ");
						System.out.print(problem.getName()+" -> ");
						System.out.println("[FUN"+run+".tsv]");
						
	
						BufferedWriter writer = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+"FUN"+ run + ".tsv"));
						BufferedWriter writer2 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+"VAR"+ run + ".tsv"));
												
						writer.write(Double.toString(solution.getObjective(0)));
				        for (int j = 0; j < solution.getNumberOfVariables(); j++) {
				        	writer2.write(solution.getVariableValueString(j) + " ");  
				        }
						
						writer.close();
						writer2.close();
	
						JMetalLogger.logger.info("Fitness: " + solution.getObjective(0));
						JMetalLogger.logger.info("Solution: " + solution.getVariables());
						JMetalLogger.logger.info("Computing Time: " + computingTime+ "ms");								

						
					    String timeFile = fileName + "runTimeSummary.tsv";
						FileWriter w = new FileWriter(timeFile, true);
						BufferedWriter bw = new BufferedWriter(w);
						PrintWriter wr = new PrintWriter(bw);	
						wr.write(algorithmName+"\t\t"+"run"+run+"\t\t"+String.valueOf(computingTime)+"\n");
						wr.close();
						bw.close();
    				}

    				
    				List<Double> solutions = new ArrayList<>();
    				for (int i = 0; i < solutionList.size(); i++) {
    					solutions.add(solutionList.get(i).getObjective(0));
    				}
    				
    				DIR = new File(fileName+"/"+"MAXs"+"/");DIR.mkdirs();
    				BufferedWriter writer1 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+"MAX" + ".tsv"));
    				DIR = new File(fileName+"/"+"MINs"+"/");DIR.mkdirs();
    				BufferedWriter writer2 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+"MIN" + ".tsv"));
    				DIR = new File(fileName+"/"+"MEANs"+"/");DIR.mkdirs();
    				BufferedWriter writer3 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+"MEAN" + ".tsv"));
    				DIR = new File(fileName+"/"+"MEDIANs"+"/");DIR.mkdirs();
    				BufferedWriter writer4 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+"MEDIAN" + ".tsv"));
    				DIR = new File(fileName+"/"+"SDEVs"+"/");DIR.mkdirs();
    				BufferedWriter writer5 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+"SDEV" + ".tsv"));

    				Collections.sort(solutions);
    				for(int x=0;x<solutions.size();x++) {
    					  System.out.println(solutions.get(x));
    				}

    				writer1.write(Double.toString(getMax(solutions)));
    				writer2.write(Double.toString(getMin(solutions)));
    				writer3.write(Double.toString(getMean(solutions)));
    				writer4.write(Double.toString(getMedian(solutions)));
    				writer5.write(Double.toString(sDev(solutions)));
    				     
    				writer1.close();
    				writer2.close();
    				writer3.close();
    				writer4.close();
    				writer5.close();

					
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
//		}
    		

	}

	private static void configureAlgorithmList(Problem<PermutationSolution<Integer>> problem) {
		mapOfAlgorithmsList.clear();

		for (int i = 0; i < maxEvaluations.length; i++) {
    	  for (int j = 0; j < populationSizes.length; j++) {
    		
    		int maxEval = maxEvaluations[i];
    		int popSize = populationSizes[j];
    		


			Algorithm<PermutationSolution<Integer>> gGA = new GeneticAlgorithmBuilder<>(
		    		problem, 
		    		new PMXCrossover(0.7895), 
		    		new PermutationSwapMutation<Integer>(0.1116))
		            .setPopulationSize(20)
		            .setMaxEvaluations(maxEval)
		            .setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.GENERATIONAL)
		            .build() ;
			
			//algorithmsList.add(gGA);
			mapOfAlgorithmsList.put("gGA"+"-p"+popSize+"-e"+maxEval, gGA);

    	}

	}

	}


	public static double getMax(List<Double> solutions){
//		for (int solution = 0; solution < solutions.size(); solution++) {
//			System.out.println(solutions.get(solution));
//		}
//	    double max = solutions.get(0);
//	    for(int i=0;i<solutions.size();i++){
//	        if(max<solutions.get(i))
//	        max=solutions.get(i);
//	    }
//
		Double max = solutions.get(solutions.size() -1 );
		System.out.println("Max"+max);
	    return max;
	}
	
	public static double getMin(List<Double> solutions){
//		Double min = solutions.get(0);
//	    for(int i=0;i<solutions.size();i++){                         
//	        if(min>solutions.get(i)){
//	            min=solutions.get(i);
//	        }
//	    }

		Double min = solutions.get(0);
		System.out.println("min"+min);
	    return min;
	}

	//Collections.sort(arraylist);
	
    public static double getMedian (List<Double> solutions){
        int middle = solutions.size()/2;
		double median;
        if (solutions.size() % 2 == 1) {
        	median = solutions.get(middle);
        } else {
        	median = (solutions.get(middle-1) + solutions.get(middle)) / 2.0;
        }
		System.out.println("median"+median);
		 return median;
    }
	
	
    public static double getMean (List<Double> solutions){
    	double total = 0.0;
    	double mean = 0.0;
        for ( int i= 0;i < solutions.size(); i++){
        	double currentNum = solutions.get(i);
            total+= currentNum;
        }
        mean = (double) total/(double) solutions.size();
		System.out.println("mean"+mean);

        return mean;
    }
    
    public static double sDev (List<Double> solutions){
        double mean = getMean(solutions);
        double temp = 0.0;
        double sDev = 0.0;
        for (int i = 0; i < solutions.size(); i++){
        	double val = solutions.get(i);
            double squrDiffToMean = Math.pow(val - mean, 2);
            temp += squrDiffToMean;
        }
        double meanOfDiffs = (double) temp / (double) (solutions.size());
        sDev = Math.sqrt(meanOfDiffs);
        System.out.println("sDev "+sDev);
        return sDev;
    }
}
