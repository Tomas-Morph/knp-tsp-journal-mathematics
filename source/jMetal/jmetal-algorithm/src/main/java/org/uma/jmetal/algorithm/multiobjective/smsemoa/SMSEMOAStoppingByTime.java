package org.uma.jmetal.algorithm.multiobjective.smsemoa;

import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD.FunctionType;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import java.util.Comparator;
import java.util.List;

/**
 * This class shows a version of NSGA-II having a stopping condition depending on run-time
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class SMSEMOAStoppingByTime<S extends Solution<?>> extends SMSEMOA<S> {
  private long initComputingTime ;
  private long thresholdComputingTime ;
  /**
   * Constructor
   */
  

  public SMSEMOAStoppingByTime(
		  
		  Problem<S> problem,
		  long maxComputingTime,
		  int populationSize,
		  double offset ,
		  CrossoverOperator<S> crossoverOperator,
		  MutationOperator<S> mutationOperator,
		  SelectionOperator<List<S>, S> selectionOperator,
		  Comparator<S> dominanceComparator,
		  Hypervolume<S> hypervolumeImplementation) {
	  
    super(problem, 0, populationSize, offset, crossoverOperator, mutationOperator, selectionOperator, dominanceComparator, hypervolumeImplementation);
    
    //hypervolumeImplementation.setOffset(offset);
    initComputingTime = System.currentTimeMillis() ;
    thresholdComputingTime = maxComputingTime ;

  }

  @Override protected boolean isStoppingConditionReached() {
    long currentComputingTime = System.currentTimeMillis() - initComputingTime ;
    return currentComputingTime > thresholdComputingTime ;
  }
}
