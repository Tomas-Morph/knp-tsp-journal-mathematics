package org.uma.jmetal.experiment.singleObjectiveKp;

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
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.singleobjective.kp.KNPSingleObjective1Set2;
import org.uma.jmetal.problem.singleobjective.kp.KNPSingleObjective2Set2;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;


/**
 * Class to configure and run a generational genetic algorithm. The
 * target problem is KP set2.
 * @author Mohammed Mahrach <mmahrach@ull.edu.es>
 * based on  Antonio J. Nebro templates <antonio@lcc.uma.es>
 */
public class KPgGAStudySet2 {

	private static final int INDEPENDENT_RUNS = 100;
	private static final int[] populationSizes ={20/*,50,100,200*/};
	private static final int[] maxEvaluations ={/*25000,*/50000/*,100000*/};
	
	static String experimentBaseDirectory = "Experiments/KP/SingleObjective";

	static List<BinaryProblem> problemList = new ArrayList<>();
	
	static Map<String, Algorithm<BinarySolution>> mapOfAlgorithmsList = new HashMap<String, Algorithm<BinarySolution>>();
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

//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"F5050W01.KNP" ), "F5050W01"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W02.KNP" ), "F5050W02"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W03.KNP" ), "F5050W03"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W04.KNP" ), "F5050W04"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W05.KNP" ), "F5050W05"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W06.KNP" ), "F5050W06"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W07.KNP" ), "F5050W07"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W08.KNP" ), "F5050W08"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W09.KNP" ), "F5050W09"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W10.KNP" ), "F5050W10"));
//
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W01.KNP" ), "K5050W01"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W02.KNP" ), "K5050W02"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W03.KNP" ), "K5050W03"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W04.KNP" ), "K5050W04"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W05.KNP" ), "K5050W05"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W06.KNP" ), "K5050W06"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W07.KNP" ), "K5050W07"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W08.KNP" ), "K5050W08"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W09.KNP" ), "K5050W09"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"K5050W10.KNP" ), "K5050W10"));

    	
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S1W1.KNP" ), "1S1W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S250W1.KNP" ), "1S250W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S300W1.KNP" ), "1S300W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S350W1.KNP" ), "1S350W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S400W1.KNP" ), "1S400W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S450W1.KNP" ), "1S450W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S500W1.KNP" ), "1S500W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S600W1.KNP" ), "1S600W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S700W1.KNP" ), "1S700W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S800W1.KNP" ), "1S800W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"1S900W1.KNP" ), "1S900W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"S1C50W01.KNP" ), "S1C50W01"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"S1100W1.KNP" ), "S1100W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"S1150W1.KNP" ), "S1150W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(strCorrelated+"S1200W1.KNP" ), "S1200W1"));

//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W1W1.KNP" ), "4W1W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W150W1.KNP" ), "4W150W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W200W1.KNP" ), "4W200W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W250W1.KNP" ), "4W250W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W300W1.KNP" ), "4W300W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W350W1.KNP" ), "4W350W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W400W1.KNP" ), "4W400W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W450W1.KNP" ), "4W450W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W500W1.KNP" ), "4W500W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W600W1.KNP" ), "4W600W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W700W1.KNP" ), "4W700W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W800W1.KNP" ), "4W800W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"4W900W1.KNP" ), "4W900W1"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"W4C50W01.KNP" ), "W4C50W01"));
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(weakCorrelated+"W4100W1.KNP" ), "W4100W1"));
		

	  	String instanceSet = args[0];
	  	String instanceName = args[1];          	
	  	// Examples
		//String instanceSet = "strCorrelated";
	  	//String instanceName = "1S600W1";  		
	  	
	  	
		BinaryProblem problem1 = new KNPSingleObjective1Set2("/MCDMlibMOKP/"+instanceSet+"/"+instanceName+".KNP");
		BinaryProblem problem2 = new KNPSingleObjective2Set2("/MCDMlibMOKP/"+instanceSet+"/"+instanceName+".KNP");
		
		problemList.add(problem1);
		problemList.add(problem2);

		for (int problem = 0; problem < problemList.size(); problem++) {
				
			configureAlgorithmList(problem);
			//mapOfAlgorithmsList.forEach((k,v)->{System.out.println("algorithm Key : " + k + " algorithm.getName() : " + v.getName());});			
			executeAlgorithmList(instanceName,problem);
			//writeResultToFile();
		}

	}

	@SuppressWarnings("resource")
	private static void executeAlgorithmList(String instanceName, int ObjectiveNumber) {
		
    		for (Entry<String, Algorithm<BinarySolution>> algorithmEntry : mapOfAlgorithmsList.entrySet()) {
    		    //System.out.println("Key = " + algorithm.getKey() + ", Value = " + algorithm.getValue());
    			String algorithmName = algorithmEntry.getKey();
    			Algorithm<BinarySolution> algorithm = algorithmEntry.getValue();
    		
    			//System.out.println("algorithm Key : " + algorithmName + " algorithm.getName() : " + algorithm.getName());			

    			String fileName = experimentBaseDirectory+ "/" 
    			+ "SingleObjective" + (ObjectiveNumber+1) + "/" + instanceName + "/";
    			
				File DIR = new File(fileName+"/"+"data"+"/"+algorithmName+"/");
				DIR.mkdirs();
				
				List<BinarySolution> solutionList = new ArrayList<>();

    			try {
    				for (int run = 0; run < INDEPENDENT_RUNS; run++) {

						AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
						computingTime += algorithmRunner.getComputingTime();

						
						BinarySolution solution = algorithm.getResult();
						
						solution.getObjective(0);
						solution.setObjective(0, (-1.0 * solution.getObjective(0)));
						
						solutionList.add(solution);
								
						
						System.out.print(algorithmName+" -> ");
						System.out.print(problemList.get(ObjectiveNumber).getName()+" -> ");
						System.out.println("[FUN"+run+".tsv]");
						
	
						BufferedWriter writer = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+ "Obj" + (ObjectiveNumber+1) +"-"+"FUN"+ run + ".tsv"));
						BufferedWriter writer2 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+ "Obj" + (ObjectiveNumber+1) +"-"+"VAR"+ run + ".tsv"));
												
						writer.write(Double.toString(solution.getObjective(0)));
						writer2.write(solution.getVariableValueString(0));
						
						writer.close();
						writer2.close();
	
						JMetalLogger.logger.info("Fitness: " + solution.getObjective(0));
						JMetalLogger.logger.info("Variable Value: " + solution.getVariableValueString(0));
						JMetalLogger.logger.info("Computing Time: " + computingTime);

						
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
    				BufferedWriter writer1 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+ "Obj" +(ObjectiveNumber+1) +"-"+"MAX" + ".tsv"));
    				DIR = new File(fileName+"/"+"MINs"+"/");DIR.mkdirs();
    				BufferedWriter writer2 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+ "Obj" +(ObjectiveNumber+1) +"-"+"MIN" + ".tsv"));
    				DIR = new File(fileName+"/"+"MEANs"+"/");DIR.mkdirs();
    				BufferedWriter writer3 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+ "Obj" +(ObjectiveNumber+1) +"-"+"MEAN" + ".tsv"));
    				DIR = new File(fileName+"/"+"MEDIANs"+"/");DIR.mkdirs();
    				BufferedWriter writer4 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+ "Obj" +(ObjectiveNumber+1) +"-"+"MEDIAN" + ".tsv"));
    				DIR = new File(fileName+"/"+"SDEVs"+"/");DIR.mkdirs();
    				BufferedWriter writer5 = new BufferedWriter(new FileWriter(DIR + "/"+ algorithmName +"-"+ "Obj" +(ObjectiveNumber+1) +"-"+"SDEV" + ".tsv"));

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

	private static void configureAlgorithmList(int problem) {
		mapOfAlgorithmsList.clear();

	    BinaryTournamentSelection<BinarySolution> selection;
	    selection = new BinaryTournamentSelection<BinarySolution>(
	        	new RankingAndCrowdingDistanceComparator<BinarySolution>());
		
		for (int i = 0; i < maxEvaluations.length; i++) {
    	  for (int j = 0; j < populationSizes.length; j++) {
    		
    		int maxEval = maxEvaluations[i];
    		int popSize = populationSizes[j];
    		
    		  
			Algorithm<BinarySolution> gGA = new GeneticAlgorithmBuilder<>(problemList.get(problem),
					new SinglePointCrossover(0.8795),
					new BitFlipMutation(0.0081))
					.setPopulationSize(20)
					.setMaxEvaluations(maxEval)
					.setSelectionOperator(selection)
					.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.GENERATIONAL).build();

			//algorithmsList.add(gGA);
			mapOfAlgorithmsList.put("gGA"+"-p"+popSize+"-e"+maxEval, gGA);


//			Algorithm<BinarySolution> ssGA = new GeneticAlgorithmBuilder<>(problemList.get(problem),
//					new SinglePointCrossover(0.9),
//					new BitFlipMutation(1.0 / problemList.get(problem).getNumberOfBits(0))).setPopulationSize(popSize)
//							.setMaxEvaluations(maxEval)
//							.setSelectionOperator(selection)
//							.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE).build();
//
//			//algorithmsList.add(ssGA);
//			mapOfAlgorithmsList.put("ssGA"+"-p"+popSize+"-e"+maxEval, ssGA);
//
//			// (mu) is the size of the parent population
//			// (lambda) is the size of the offspring population
//			
//			Algorithm<BinarySolution> eES = new EvolutionStrategyBuilder<BinarySolution>(
//					problemList.get(problem),
//					new BitFlipMutation(1.0 / problemList.get(problem).getNumberOfBits(0)),
//					EvolutionStrategyBuilder.EvolutionStrategyVariant.ELITIST).setMaxEvaluations(maxEval).setMu(1)
//							.setLambda(popSize).build();
//
//			//algorithmsList.add(eES);
//			mapOfAlgorithmsList.put("eES"+"-p"+popSize+"-e"+maxEval, eES);
//			
//			
//			Algorithm<BinarySolution> neES = new EvolutionStrategyBuilder<BinarySolution>(
//					problemList.get(problem),
//					new BitFlipMutation(1.0 / problemList.get(problem).getNumberOfBits(0)),
//					EvolutionStrategyBuilder.EvolutionStrategyVariant.NON_ELITIST).setMaxEvaluations(maxEval).setMu(1)
//							.setLambda(popSize).build();
//
//			//algorithmsList.add(neES);
//			mapOfAlgorithmsList.put("neES"+"-p"+popSize+"-e"+maxEval, neES);
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
