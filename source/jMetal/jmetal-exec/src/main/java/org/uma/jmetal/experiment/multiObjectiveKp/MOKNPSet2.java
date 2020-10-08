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
import org.uma.jmetal.problem.multiobjective.kp.KNPMultiobjectiveSet2;
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
 * target problem is multiobjective KP set2.
 * @author Mohammed Mahrach <mmahrach@ull.edu.es>
 * based on  Antonio J. Nebro templates <antonio@lcc.uma.es>
 */

public class MOKNPSet2 {
	private static final int INDEPENDENT_RUNS = 100;
	private static final int[] populationSizes ={20/*,50,100,200*/};
	private static final int[] maxEvaluations ={/*25000,*/50000/*,100000*/};

	static String experimentBaseDirectory = "Experiments/KP/MultiObjective";

  public static void main(String[] args) throws IOException {
//    if (args.length != 1) {
//    throw new JMetalException("Needed arguments: experimentBaseDirectory");
//  }
//  String experimentBaseDirectory = args[0];

		List<ExperimentProblem<BinarySolution>> problemList = new ArrayList<>();
		
	  	String instanceSet = args[0];
	  	String instanceName = args[1];

	  	//Examples:
    	//String instanceSet = "uncorrelated";
	  	//String instanceName = "F5050W01";
		
    	//String instanceSet = "weakCorrelated";
	  	//String instanceName = "W4100W1";
		
    	//String instanceSet = "strCorrelated";
	  	//String instanceName = "1S250W1";
	  	
    	problemList.add(new ExperimentProblem<>(new KNPMultiobjectiveSet2("/MCDMlibMOKP/"+instanceSet+"/"+instanceName+".KNP"), instanceName));

//   	problemList.add(new ExperimentProblem<>(new MultiobjectiveKP2(uncorrelated+"F5050W01.KNP" ), "F5050W01"));
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
    	
    	List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithmList = configureAlgorithmList(problemList);

    Experiment<BinarySolution, List<BinarySolution>> experiment =
            new ExperimentBuilder<BinarySolution, List<BinarySolution>>(instanceName)//"KPStudy"
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
	        			  
	        			  
	        			  .setMaxEvaluations(maxEval)
	        			  .build() ;
	             algorithms.add(new ExperimentAlgorithm<>(algorithm, ("SMSEMOA"+"-p"+popSize+"-e"+maxEval), problemList.get(p), run));
	 	    }
	 	    

	    }
  	  }
    }
    return algorithms;
  }
}
