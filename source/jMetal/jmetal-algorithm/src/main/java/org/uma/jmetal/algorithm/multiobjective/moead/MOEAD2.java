package org.uma.jmetal.algorithm.multiobjective.moead;


import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD.NeighborType;
import org.uma.jmetal.algorithm.multiobjective.moead.util.MOEADUtils;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.impl.ViolationThresholdComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing the MOEA/D-DE algorithm described in :
 * Hui Li; Qingfu Zhang, "Multiobjective Optimization Problems With Complicated Pareto Sets,
 * MOEA/D and NSGA-II," Evolutionary Computation, IEEE Transactions on , vol.13, no.2, pp.284,302,
 * April 2009. doi: 10.1109/TEVC.2008.925798
 *
 * @author Antonio J. Nebro
 * @version 1.0
 */
@SuppressWarnings("serial")


public class MOEAD2<S extends Solution<?>> extends AbstractMOEAD<S> {

//  protected DifferentialEvolutionCrossover differentialEvolutionCrossover ;
//  protected SinglePointCrossover singlePointCrossover;
	protected ViolationThresholdComparator<S> violationThresholdComparator ;

  public MOEAD2(Problem<S> problem,
      int populationSize,
      int resultPopulationSize,
      int maxEvaluations,
      CrossoverOperator<S> crossoverOperator,
      MutationOperator<S> mutationOperator,
      FunctionType functionType,
      String dataDirectory,
      double neighborhoodSelectionProbability,
      int maximumNumberOfReplacedSolutions,
      int neighborSize) {
    
    super(problem, populationSize, resultPopulationSize, maxEvaluations, crossoverOperator, mutationOperator,
     functionType, dataDirectory,  neighborhoodSelectionProbability, maximumNumberOfReplacedSolutions, neighborSize);
    
    //differentialEvolutionCrossover = (DifferentialEvolutionCrossover)crossoverOperator ;
    
  }	
  	

  
  
  
  
  @Override public void run() {
	    initializePopulation() ;
	    initializeUniformWeight();
	    initializeNeighborhood();
	    idealPoint.update(population);

	    evaluations = populationSize ;

	    do {
	    	int[] permutation = new int[populationSize];
	    	MOEADUtils.randomPermutation(permutation, populationSize);
	    	for (int i = 0; i < populationSize; i++) {
	    		int subProblemId = permutation[i];
	    		NeighborType neighborType = chooseNeighborType() ;
	    		List<S> parents = parentSelection(subProblemId, neighborType) ;	    		

	        
//	        //singlePointCrossover.setCurrentSolution(population.get(subProblemId));
//	        List<S> children = crossoverOperator.execute(parents);
	        
	        
	        List<S> children = crossParents(parents);
	        
	        
	        S child = children.get(0) ;
	        mutationOperator.execute(child);
	        problem.evaluate(child);


	        evaluations++;

	        idealPoint.update(child.getObjectives());
	        updateNeighborhood(child, subProblemId, neighborType);
	      }

	    } while (!isStoppingConditionReached());
	    //} while (evaluations < maxEvaluations);
	  }



private List<S> crossParents(List<S> parents) {
	List<S> cros01 = new ArrayList<>(2);
	cros01.add((S) parents.get(0)) ;
	cros01.add((S) parents.get(1)) ;
	List<S> offspring01 = crossoverOperator.execute(cros01);
	
	List<S> cros12 = new ArrayList<>(2);
	cros12.add((S) offspring01.get(1)) ;
	cros12.add((S) parents.get(2)) ;
	List<S> offspring12 = crossoverOperator.execute(cros12);
	
	List<S> children = new ArrayList<>(3);
	children.add((S) offspring01.get(0)) ;
	children.add((S) offspring12.get(0)) ;
	children.add((S) offspring12.get(1)) ;
	
	return children;
}
  
  
//  @Override public void run() {
//    initializePopulation() ;
//    initializeUniformWeight();
//    initializeNeighborhood();
//    idealPoint.update(population); ;
//
//    evaluations = populationSize ;
//    do {
//      int[] permutation = new int[populationSize];
//      MOEADUtils.randomPermutation(permutation, populationSize);
//
//      for (int i = 0; i < populationSize; i++) {
//        int subProblemId = permutation[i];
//
//        NeighborType neighborType = chooseNeighborType() ;
//        List<S> parents = parentSelection(subProblemId, neighborType) ;
//
//        //singlePointCrossover.setCurrentSolution(population.get(subProblemId));
//        List<S> children = crossoverOperator.execute(parents);
//        
//        S child = children.get(0) ;
//        mutationOperator.execute(child);
//        problem.evaluate(child);
//
//        evaluations++;
//
//        idealPoint.update(child.getObjectives());
//        updateNeighborhood(child, subProblemId, neighborType);
//      }
//    } while (evaluations < maxEvaluations);
//
//  }
  
  // Esto es el del NSGA
//  public List<BinarySolution> reproduction(List<BinarySolution> matingPool) {
//	    int numberOfParents = crossoverOperator.getNumberOfRequiredParents() ;
//
//
//	    List<BinarySolution> offspringPopulation = new ArrayList<>(populationSize);
//	    for (int i = 0; i < matingPool.size(); i += numberOfParents) {
//	      List<BinarySolution> parents = new ArrayList<>(numberOfParents);
//	      for (int j = 0; j < numberOfParents; j++) {
//	        parents.add(population.get(i+j));
//	      }
//
//	      List<BinarySolution> offspring = crossoverOperator.execute(parents);
//
//	      for(BinarySolution s: offspring){
//	        mutationOperator.execute(s);
//	        offspringPopulation.add(s);
//	        if (offspringPopulation.size() >= populationSize)
//	          break;
//	      }
//	    }
//	    return offspringPopulation;
//	  }
  
  

  protected void initializePopulation() {
    population = new ArrayList<>(populationSize);
    for (int i = 0; i < populationSize; i++) {
      S newSolution = problem.createSolution();

      problem.evaluate(newSolution);
      population.add(newSolution);
    }
  }


  
  @Override public String getName() {
    return "MOEAD" ;
  }

  @Override public String getDescription() {
    return "Multi-Objective Evolutionary Algorithm based on Decomposition" ;
  }
}
