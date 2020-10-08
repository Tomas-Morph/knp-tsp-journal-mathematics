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
import org.uma.jmetal.problem.singleobjective.kp.KNPSingleObjective1Set1;
import org.uma.jmetal.problem.singleobjective.kp.KNPSingleObjective2Set1;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;


/**
 * Class to configure and run a generational genetic algorithm. The
 * target problem is KP set1.
 * @author Mohammed Mahrach <mmahrach@ull.edu.es>
 * based on  Antonio J. Nebro templates <antonio@lcc.uma.es>
 */
public class KPgGAStudySet1 {

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

//	  	String instanceSet = "A";//args[0];
//	  	String instanceName = "2KP50-11";//args[1];
	  	String instanceSet = args[0];
	  	String instanceName = args[1];

    	String set = "/MCDMlibMOKP/2KPset1"+instanceSet+"/";
    	
		
		BinaryProblem problem1 = new KNPSingleObjective1Set1(set+instanceName+".DAT");
		BinaryProblem problem2 = new KNPSingleObjective2Set1(set+instanceName+".DAT");
    	
    	
		
		problemList.add(problem1);
		problemList.add(problem2);
		
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP50-11.DAT" ), "2KP50-11"));
//		problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP50-50.DAT" ), "2KP50-50"));
//		problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP50-92.DAT" ), "2KP50-92"));
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP100-50.DAT" ), "2KP100-50"));
//////  problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP500-41.DAT" ), "2KP500-41"));
//
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP50-1A.DAT" ), "2KP50-1A"));
//		problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP100-1A.DAT" ), "2KP100-1A"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP150-1A.DAT" ), "2KP150-1A"));
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP200-1A.DAT" ), "2KP200-1A"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP250-1A.DAT" ), "2KP250-1A"));
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP300-1A.DAT" ), "2KP300-1A"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP350-1A.DAT" ), "2KP350-1A"));
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP400-1A.DAT" ), "2KP400-1A"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP450-1A.DAT" ), "2KP450-1A"));
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP500-1A.DAT" ), "2KP500-1A"));
//		
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP50-1B.DAT" ), "2KP50-1B"));
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP100-1B.DAT" ), "2KP100-1B"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP150-1B.DAT" ), "2KP150-1B"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP200-1B.DAT" ), "2KP200-1B"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP250-1B.DAT" ), "2KP250-1B"));
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP300-1B.DAT" ), "2KP300-1B"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP350-1B.DAT" ), "2KP350-1B"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP400-1B.DAT" ), "2KP400-1B"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP450-1B.DAT" ), "2KP450-1B"));
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_B+"2KP500-1B.DAT" ), "2KP500-1B"));
//		
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP50-1C.dat" ), "2KP50-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP100-1C.dat" ), "2KP100-1C"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP150-1C.dat" ), "2KP150-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP200-1C.dat" ), "2KP200-1C"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP250-1C.dat" ), "2KP250-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP300-1C.dat" ), "2KP300-1C"));
////    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP350-1C.dat" ), "2KP350-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP400-1C.dat" ), "2KP400-1C"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP450-1C.dat" ), "2KP450-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP500-1C.dat" ), "2KP500-1C"));
//	    
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP50-1D.dat" ), "2KP50-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP100-1D.dat" ), "2KP100-1D"));
////	   problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP150-1D.dat" ), "2KP150-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP200-1D.dat" ), "2KP200-1D"));
//// 	   problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP250-1D.dat" ), "2KP250-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP300-1D.dat" ), "2KP300-1D"));
//// 	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP350-1D.dat" ), "2KP350-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP400-1D.dat" ), "2KP400-1D"));
////    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP450-1D.dat" ), "2KP450-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP500-1D.dat" ), "2KP500-1D"));

		
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
