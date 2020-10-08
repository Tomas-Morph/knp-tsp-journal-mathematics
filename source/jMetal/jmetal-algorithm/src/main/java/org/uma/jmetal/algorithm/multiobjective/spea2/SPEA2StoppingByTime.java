package org.uma.jmetal.algorithm.multiobjective.spea2;

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
public class SPEA2StoppingByTime<S extends Solution<?>> extends SPEA2<S> {
  private long initComputingTime ;
  private long thresholdComputingTime ;
  /**
   * Constructor
   */
  
  public SPEA2StoppingByTime(Problem<S> problem, int populationSize,long maxComputingTime,
	      CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
	      SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator,
	               int k) {
    super(problem, 0, populationSize, crossoverOperator, mutationOperator,
    		selectionOperator, evaluator,k);

    initComputingTime = System.currentTimeMillis() ;
    thresholdComputingTime = maxComputingTime ;
  }

  @Override protected boolean isStoppingConditionReached() {
    long currentComputingTime = System.currentTimeMillis() - initComputingTime ;
    return currentComputingTime > thresholdComputingTime ;
  }
}
