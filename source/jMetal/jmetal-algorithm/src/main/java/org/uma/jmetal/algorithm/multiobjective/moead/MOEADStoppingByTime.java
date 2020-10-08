package org.uma.jmetal.algorithm.multiobjective.moead;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import java.util.Comparator;
import java.util.List;

/**
 * This class shows a version of NSGA-II having a stopping condition depending on run-time
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class MOEADStoppingByTime<S extends Solution<?>> extends ConstraintMOEAD2<S> {
  private long initComputingTime ;
  private long thresholdComputingTime ;
  /**
   * Constructor
   */
  
  public MOEADStoppingByTime(
		  Problem<S> problem,
		  int populationSize,
		  int resultPopulationSize,
		  long maxComputingTime,//int maxEvaluations,
		  CrossoverOperator<S> crossoverOperator,
		  MutationOperator<S> mutationOperator,
		  FunctionType functionType,
		  String dataDirectory,
		  double neighborhoodSelectionProbability,
		  int maximumNumberOfReplacedSolutions,
		  int neighborSize) {
	  
			super(problem, populationSize, resultPopulationSize, 0, crossoverOperator, mutationOperator, functionType,
				dataDirectory, neighborhoodSelectionProbability, maximumNumberOfReplacedSolutions, neighborSize);
		
		
			initComputingTime = System.currentTimeMillis() ;
			thresholdComputingTime = maxComputingTime ;

		}
  
  
//  public MOEADStoppingByTime(Problem<S> problem, int populationSize,
//                              long maxComputingTime, int matingPoolSize, int offspringPopulationSize,
//                              CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
//                              SelectionOperator<List<S>, S> selectionOperator, Comparator<S> dominanceComparator,
//                              SolutionListEvaluator<S> evaluator) {
//    super(problem, 0, populationSize, matingPoolSize, offspringPopulationSize,
//            crossoverOperator, mutationOperator,
//        selectionOperator, dominanceComparator, evaluator);
//
//    initComputingTime = System.currentTimeMillis() ;
//    thresholdComputingTime = maxComputingTime ;
//  }

  @Override protected boolean isStoppingConditionReached() {
    long currentComputingTime = System.currentTimeMillis() - initComputingTime ;
    return currentComputingTime > thresholdComputingTime ;
  }

}
