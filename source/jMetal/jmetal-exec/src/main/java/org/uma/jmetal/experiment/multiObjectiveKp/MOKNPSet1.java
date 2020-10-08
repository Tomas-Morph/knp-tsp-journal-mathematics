package org.uma.jmetal.experiment.multiObjectiveKp;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder2;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder2.Variant;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.kp.KNPMultiobjectiveSet1;
import org.uma.jmetal.qualityindicator.impl.*;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to configure and run NSGA-II, MOEAD and SMS-EMOA algorithms. The
 * target problem is multiobjective KP set1.
 * @author Mohammed Mahrach <mmahrach@ull.edu.es>
 * based on  Antonio J. Nebro templates <antonio@lcc.uma.es>
 */

public class MOKNPSet1 {
	private static final int INDEPENDENT_RUNS = 100;
	private static final int[] populationSizes ={20/*,50,100,200*/};
	private static final int[] maxEvaluations ={/*25000,*/50000/*,100000*/};

	static String experimentBaseDirectory = "Experiments/KP/MultiObjective";

  public static void main(String[] args) throws IOException {
//    if (args.length != 1) {
//    throw new JMetalException("Needed arguments: experimentBaseDirectory");
//  }
//  String experimentBaseDirectory = args[0];
	
//  	String set1A = "A"+"/";
//  	String set1B_A = "B-A"+"/";
//  	String set1B_B = "B-B"+"/";
//  	String set1B_C = "B-C"+"/";
//  	String set1B_D = "B-D"+"/";
	  
	  	String instanceSet = args[0];
	  	String instanceName = args[1];

//		Examples:
//	  	String instanceSet = "B-A";//args[0];
//	  	String instanceName = "2KP100-1A";//args[1];
	  	
		List<ExperimentProblem<BinarySolution>> problemList = new ArrayList<>();
    	
    	
    	String set = "/MCDMlibMOKP/2KPset1"+instanceSet+"/";
    	problemList.add(new ExperimentProblem<>(new KNPMultiobjectiveSet1(set+instanceName+".DAT" ), instanceName));

    	
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP50-11.DAT" ), "2KP50-11"));
//		problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP50-50.DAT" ), "2KP50-50"));
//		problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP50-92.DAT" ), "2KP50-92"));
//    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP100-50.DAT" ), "2KP100-50"));
//////  problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1A+"2KP500-41.DAT" ), "2KP500-41"));
//
//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_A+"2KP50-1A.DAT" ), "2KP50-1A"));
    	
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
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP50-1C.DAT" ), "2KP50-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP100-1C.DAT" ), "2KP100-1C"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP150-1C.DAT" ), "2KP150-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP200-1C.DAT" ), "2KP200-1C"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP250-1C.DAT" ), "2KP250-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP300-1C.DAT" ), "2KP300-1C"));
////    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP350-1C.DAT" ), "2KP350-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP400-1C.DAT" ), "2KP400-1C"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP450-1C.DAT" ), "2KP450-1C"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_C+"2KP500-1C.DAT" ), "2KP500-1C"));
//	    
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP50-1D.DAT" ), "2KP50-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP100-1D.DAT" ), "2KP100-1D"));
////	   problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP150-1D.DAT" ), "2KP150-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP200-1D.DAT" ), "2KP200-1D"));
//// 	   problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP250-1D.DAT" ), "2KP250-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP300-1D.DAT" ), "2KP300-1D"));
//// 	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP350-1D.DAT" ), "2KP350-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP400-1D.DAT" ), "2KP400-1D"));
////    	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP450-1D.DAT" ), "2KP450-1D"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveKP(set1B_D+"2KP500-1D.DAT" ), "2KP500-1D"));
	    

    List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithmList = configureAlgorithmList(problemList);

    Experiment<BinarySolution, List<BinarySolution>> experiment =
            new ExperimentBuilder<BinarySolution, List<BinarySolution>>("2KP100-1A")//"KPStudy"
                    .setAlgorithmList(algorithmList)
                    .setProblemList(problemList)
                    .setReferenceFrontDirectory("/pareto_fronts/kp")
                    .setExperimentBaseDirectory(experimentBaseDirectory)
                    .setOutputParetoFrontFileName("FUN")
                    .setOutputParetoSetFileName("VAR")
                    .setIndicatorList(Arrays.asList(
                            new Epsilon<BinarySolution>(),
                            new Spread<BinarySolution>(),
                            new GenerationalDistance<BinarySolution>(),
                            new PISAHypervolume<BinarySolution>(),
                            new InvertedGenerationalDistance<BinarySolution>(),
                            new InvertedGenerationalDistancePlus<BinarySolution>()))
                    .setIndependentRuns(INDEPENDENT_RUNS)
                    .setNumberOfCores(8)
                    .build();

    new ExecuteAlgorithms<>(experiment).run();
    new ComputeQualityIndicators<>(experiment).run();
    new GenerateLatexTablesWithStatistics(experiment).run();
    new GenerateWilcoxonTestTablesWithR<>(experiment).run();
    new GenerateFriedmanTestTables<>(experiment).run();
    new GenerateBoxplotsWithR<>(experiment).setRows(3).setColumns(3).setDisplayNotch().run();
  }

  /**
   * The algorithm list is composed of pairs {@link Algorithm} + {@link Problem} which form part of
   * a {@link ExperimentAlgorithm}, which is a decorator for class {@link Algorithm}.
   */
  static List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> configureAlgorithmList(
          List<ExperimentProblem<BinarySolution>> problemList) {
    List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithms = new ArrayList<>();
 
    SelectionOperator<List<BinarySolution>, BinarySolution> 
    
    selection = new BinaryTournamentSelection<BinarySolution>(
        	new RankingAndCrowdingDistanceComparator<BinarySolution>());
    
    Hypervolume<BinarySolution> hypervolume ;
    hypervolume = new PISAHypervolume<>() ;
    double offset = 100.0; 
    hypervolume.setOffset(offset);

    
    
	for (int i = 0; i < maxEvaluations.length; i++) {
  	  for (int j = 0; j < populationSizes.length; j++) {
  	    for (int run = 0; run < INDEPENDENT_RUNS; run++) {

  	    	int maxEval = maxEvaluations[i];
  	    	int popSize = populationSizes[j];
		    

		    for (int p = 0; p < problemList.size(); p++) {
		           Algorithm<List<BinarySolution>> 
		           algorithm = new NSGAIIBuilder<BinarySolution>(
		        		   problemList.get(p).getProblem(),
		        		   new SinglePointCrossover(0.9784),
		        		   new BitFlipMutation(0.0485),
		        		   20
		           )
		        		   .setSelectionOperator(selection)
		        		   .setMaxEvaluations(maxEval)
		        		   .build();
		           			           
		           algorithms.add(new ExperimentAlgorithm<>(algorithm, ("NSGAII"+"-p"+popSize+"-e"+maxEval), problemList.get(p), run));
		    }

		    
	 	    for (int p = 0; p < problemList.size(); p++) {
	 	          Algorithm<List<BinarySolution>>  algorithm = new MOEADBuilder2<BinarySolution>(
	 	        		  problemList.get(p).getProblem(),
	 	        		  Variant.ConstraintMOEAD,
	 	        		  new SinglePointCrossover(0.9578),
	 	        		  new BitFlipMutation(0.0578),
	 	        		  200
	 	        		  )
	 	        		  .setNeighborSize(20)
	 	        		  .setNeighborhoodSelectionProbability(0.8895)
	        			  .setMaxEvaluations(maxEval)
	        			  .build() ;
	 	          
	 	          algorithms.add(new ExperimentAlgorithm<>(algorithm,("MOEAD"+"-p"+popSize+"-e"+maxEval) , problemList.get(p), run));
		 	}
	 	    

	 	    for (int p = 0; p < problemList.size(); p++) {
	        	  Algorithm<List<BinarySolution>> algorithm = new SMSEMOABuilder<BinarySolution>(
	        			  problemList.get(p).getProblem(),
	        			  new SinglePointCrossover(0.9512),
	        			  new BitFlipMutation(0.0358),
	        			  20)
	        			  .setMaxEvaluations(maxEval)
	        			  .setOffset(200)
	        			  .build() ;
	             algorithms.add(new ExperimentAlgorithm<>(algorithm, ("SMSEMOA"+"-p"+popSize+"-e"+maxEval), problemList.get(p), run));
	 	    }
	 	    

	    }
  	  }
    }
    return algorithms;
  }
}
