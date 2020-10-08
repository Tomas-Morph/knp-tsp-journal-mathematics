package org.uma.jmetal.experiment.multiObjectiveTsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder2;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.tsp.TSPMultiobjective;
import org.uma.jmetal.qualityindicator.impl.Epsilon;
import org.uma.jmetal.qualityindicator.impl.GenerationalDistance;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistancePlus;
import org.uma.jmetal.qualityindicator.impl.Spread;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ComputeQualityIndicators;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.component.GenerateBoxplotsWithR;
import org.uma.jmetal.util.experiment.component.GenerateFriedmanTestTables;
import org.uma.jmetal.util.experiment.component.GenerateLatexTablesWithStatistics;
import org.uma.jmetal.util.experiment.component.GenerateReferenceParetoFront;
import org.uma.jmetal.util.experiment.component.GenerateWilcoxonTestTablesWithR;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

/**
 * Class to configure and run NSGA-II, MOEAD and SMS-EMOA algorithms. The
 * target problem is multiobjective TSP.
 * @author Mohammed Mahrach <mmahrach@ull.edu.es>
 * based on  Antonio J. Nebro templates <antonio@lcc.uma.es>
 */

public class MOTSP {

	private static final int INDEPENDENT_RUNS = 100;
	private static final int[] populationSizes ={20/*,50,100,200*/};
	private static final int[] maxEvaluations ={/*1000000,*/10000000/*,20000000*/};
	

	public static void main(String[] args) throws IOException {
//    if (args.length != 1) {
//      throw new JMetalException("Needed arguments: experimentBaseDirectory");
//    }
//    String experimentBaseDirectory = args[0];
		String experimentBaseDirectory = "Experiments/TSP/MultiObjective";
		List<ExperimentProblem<PermutationSolution<Integer>>> problemList = new ArrayList<>();

		String path = "/tspInstances/";
		String instanceName1 = args[0];
		String instanceName2 = args[1];
		
		//Examples
		//String instanceName = "kroA100";
		//String instanceName = "kroB100";

	    problemList.add(new ExperimentProblem<>(new TSPMultiobjective(path+instanceName1+".tsp", path+instanceName2+".tsp"), instanceName1+instanceName2));
		
		
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA100.tsp", path+"kroB100.tsp"), "kroAB100"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA100.tsp", path+"kroC100.tsp"), "kroAC100"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA100.tsp", path+"kroD100.tsp"), "kroAD100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA100.tsp", path+"kroE100.tsp"), "kroAE100")); 
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroB100.tsp", path+"kroC100.tsp"), "kroBC100"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroB100.tsp", path+"kroD100.tsp"), "kroBD100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroB100.tsp", path+"kroE100.tsp"), "kroBE100"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroC100.tsp", path+"kroD100.tsp"), "kroCD100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroC100.tsp", path+"kroE100.tsp"), "kroCE100")); 
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroD100.tsp", path+"kroE100.tsp"), "kroDE100"));
//
//	    
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA150.tsp", path+"kroB150.tsp"), "kroAB150")); 
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA200.tsp", path+"kroB200.tsp"), "kroAB200"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA300.tsp", path+"kroB300.tsp"), "kroAB300"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA400.tsp", path+"kroB400.tsp"), "kroAB400"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA500.tsp", path+"kroB500.tsp"), "kroAB500"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA750.tsp", path+"kroB750.tsp"), "kroAB750"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"kroA1000.tsp", path+"kroB1000.tsp"), "kroAB1000"));
////
////	    
//	    
//		problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidA100.tsp", path+"euclidB100.tsp"), "euclAB100"));
//		problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidA300.tsp", path+"euclidB300.tsp"), "euclAB300"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidA500.tsp", path+"euclidB500.tsp"), "euclAB500"));
	    ////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidA100.tsp", path+"euclidC100.tsp"), "euclAC100"));		
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidA100.tsp", path+"euclidD100.tsp"), "euclAD100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidA100.tsp", path+"euclidE100.tsp"), "euclAE100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidA100.tsp", path+"euclidF100.tsp"), "euclAF100")); 
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidB100.tsp", path+"euclidC100.tsp"), "euclBC100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidB100.tsp", path+"euclidD100.tsp"), "euclBD100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidB100.tsp", path+"euclidE100.tsp"), "euclBE100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidB100.tsp", path+"euclidF100.tsp"), "euclBF100")); 
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidC100.tsp", path+"euclidD100.tsp"), "euclCD100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidC100.tsp", path+"euclidE100.tsp"), "euclCE100"));
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidC100.tsp", path+"euclidF100.tsp"), "euclCF100")); 
////	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidD100.tsp", path+"euclidE100.tsp"), "euclDE100"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidE100.tsp", path+"euclidF100.tsp"), "euclEF100"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidA300.tsp", path+"euclidA300.tsp"), "euclA300"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"euclidA500.tsp", path+"euclidA500.tsp"), "euclA500"));
//
//	    problemList.add(new ExperimentProblem<>(new TSPMultiobjective(path+"ClusterA100.tsp", path+"ClusterB100.tsp"), "clusAB100"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"ClusterA300.tsp", path+"ClusterB300.tsp"), "clusAB300"));
//	    problemList.add(new ExperimentProblem<>(new MultiobjectiveTSP(path+"ClusterA500.tsp", path+"ClusterB500.tsp"), "clusAB500"));
//	    

		
		List<ExperimentAlgorithm<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>> algorithmList = configureAlgorithmList(
				problemList);

		ExperimentBuilder<PermutationSolution<Integer>, List<PermutationSolution<Integer>>> algorithm 
		= new ExperimentBuilder<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>("clusAB100");
		algorithm.setAlgorithmList(algorithmList);
		algorithm.setProblemList(problemList);		
		algorithm.setReferenceFrontDirectory("/pareto_fronts/tsp");
		algorithm.setExperimentBaseDirectory(experimentBaseDirectory);
		algorithm.setOutputParetoFrontFileName("FUN");
		algorithm.setOutputParetoSetFileName("VAR");		
		algorithm.setIndicatorList(Arrays.asList(new Epsilon<PermutationSolution<Integer>>(),
				new Spread<PermutationSolution<Integer>>(), new GenerationalDistance<PermutationSolution<Integer>>(),
				new PISAHypervolume<PermutationSolution<Integer>>(),
				new InvertedGenerationalDistance<PermutationSolution<Integer>>(),
				new InvertedGenerationalDistancePlus<PermutationSolution<Integer>>()));
		algorithm.setIndependentRuns(INDEPENDENT_RUNS);
		algorithm.setNumberOfCores(8);
		Experiment<PermutationSolution<Integer>, List<PermutationSolution<Integer>>> experiment = algorithm.build();

		new ExecuteAlgorithms<>(experiment).run();
		new GenerateReferenceParetoFront(experiment).run();
		new ComputeQualityIndicators<>(experiment).run();
		new GenerateLatexTablesWithStatistics(experiment).run();
		new GenerateWilcoxonTestTablesWithR<>(experiment).run();
		new GenerateFriedmanTestTables<>(experiment).run();
		new GenerateBoxplotsWithR<>(experiment).setRows(3).setColumns(3).setDisplayNotch().run();
	}

	/**
	 * The algorithm list is composed of pairs {@link Algorithm} + {@link Problem}
	 * which form part of a {@link TaggedAlgorithm}, which is a decorator for class
	 * {@link Algorithm}.
	 *
	 * @param problemList
	 * @return
	 */
	/**
	 * The algorithm list is composed of pairs {@link Algorithm} + {@link Problem}
	 * which form part of a {@link ExperimentAlgorithm}, which is a decorator for
	 * class {@link Algorithm}.
	 */

		
		static List<ExperimentAlgorithm<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>> configureAlgorithmList(
				List<ExperimentProblem<PermutationSolution<Integer>>> problemList) {
			
			List<ExperimentAlgorithm<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>> algorithms = new ArrayList<>();

		BinaryTournamentSelection<PermutationSolution<Integer>> selection;
		selection = new BinaryTournamentSelection<PermutationSolution<Integer>>(
		        	new RankingAndCrowdingDistanceComparator<PermutationSolution<Integer>>());
			
		for (int i = 0; i < maxEvaluations.length; i++) {
		  	  for (int j = 0; j < populationSizes.length; j++) {
		  	    for (int run = 0; run < INDEPENDENT_RUNS; run++) {

		  	    	int maxEval = maxEvaluations[i];
		  	    	int popSize = populationSizes[j];
    	    	
	    	    
			for (int p = 0; p < problemList.size(); p++) {
				Algorithm<List<PermutationSolution<Integer>>> algorithm = new NSGAIIBuilder<PermutationSolution<Integer>>(
						problemList.get(p).getProblem(), 
						new PMXCrossover(0.9843),
						new PermutationSwapMutation<Integer>(0.0163),
						20)
						.setOffspringPopulationSize(20)
						.setMaxEvaluations(maxEval).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm,("NSGAII"+"-p"+popSize+"-e"+maxEval), problemList.get(p), run));
			}
			
			
			for (int p = 0; p < problemList.size(); p++) {
				Algorithm<List<PermutationSolution<Integer>>> algorithm = new MOEADBuilder2<PermutationSolution<Integer>>(
						problemList.get(p).getProblem(),
						MOEADBuilder2.Variant.MOEAD,
						new PMXCrossover(0.9421),
						new PermutationSwapMutation<Integer>(0.0105),
						300)
						.setNeighborSize(50)
						.setNeighborhoodSelectionProbability(0.9354)
			            .setMaxEvaluations(maxEval)
			            .build() ;
	 	        algorithms.add(new ExperimentAlgorithm<>(algorithm,("MOEAD"+"-p"+popSize+"-e"+maxEval) , problemList.get(p), run));

			}
			
			for (int p = 0; p < problemList.size(); p++) {
				Algorithm<List<PermutationSolution<Integer>>>  algorithm = new SMSEMOABuilder<PermutationSolution<Integer>>(
						problemList.get(p).getProblem(), 
						new PMXCrossover(0.9754),
						new PermutationSwapMutation<Integer>(0.0093),
						20)
						.setOffset(200)
			    		.setSelectionOperator(selection)
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
