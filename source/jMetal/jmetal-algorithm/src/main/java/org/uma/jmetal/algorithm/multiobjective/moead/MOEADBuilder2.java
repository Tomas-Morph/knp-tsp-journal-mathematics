package org.uma.jmetal.algorithm.multiobjective.moead;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;


/**
 * Builder class for algorithm MOEA/D and variants
 *
 * @author Antonio J. Nebro
 * @version 1.0
 */

public class MOEADBuilder2<S extends Solution<?>> implements AlgorithmBuilder<AbstractMOEAD<S>> {


//public class MOEADBuilder2 implements AlgorithmBuilder<AbstractMOEAD<BinarySolution>> {
  public enum Variant {MOEAD, ConstraintMOEAD, MOEADDRA, MOEADSTM, MOEADD} ;

  protected Problem<S> problem ;

  /** T in Zhang & Li paper */
  protected int neighborSize;
  /** Delta in Zhang & Li paper */
  protected double neighborhoodSelectionProbability;
  /** nr in Zhang & Li paper */
  protected int maximumNumberOfReplacedSolutions;

  protected ConstraintMOEAD2.FunctionType functionType;

  protected CrossoverOperator<S> crossoverOperator;
  protected MutationOperator<S> mutationOperator;
  
  
  protected String dataDirectory;

  protected int populationSize;
  protected int resultPopulationSize ;

  protected int maxEvaluations;

  protected int numberOfThreads ;

  protected Variant moeadVariant ;

  /** Constructor */
  
 
  
  
  public MOEADBuilder2(Problem<S> problem, Variant variant, CrossoverOperator<S> crossoverOperator,
	      MutationOperator<S> mutationOperator,  int populationSize) {
	  
//  public MOEADBuilder2(Problem<BinarySolution> problem, Variant variant) {
    this.problem = problem ;
    this.populationSize = populationSize ;
    resultPopulationSize = populationSize ;
    
//    this.crossover = crossoverOperator ;
//    this.mutation = mutationOperator ;
    
//    crossover = new DifferentialEvolutionCrossover() ;
//    mutation = new PolynomialMutation(1.0/problem.getNumberOfVariables(), 20.0);
    
    
    this.crossoverOperator = crossoverOperator ;
    this.mutationOperator = mutationOperator ;
//    
//    crossoverOperator = new SinglePointCrossover(0.9);
//	mutationOperator = new BitFlipMutation(1.0 / problem.getNumberOfVariables());

    
    functionType = ConstraintMOEAD2.FunctionType.TCHE ;
    neighborhoodSelectionProbability = 0.1 ;
    maximumNumberOfReplacedSolutions = 2 ;
    dataDirectory = "" ;
    neighborSize = 20 ;
    numberOfThreads = 1 ;
    moeadVariant = variant ;
  }

  /* Getters/Setters */
  public int getNeighborSize() {
    return neighborSize;
  }

  public int getMaxEvaluations() {
    return maxEvaluations;
  }

  public int getPopulationSize() {
    return populationSize;
  }

  public int getResultPopulationSize() {
    return resultPopulationSize;
  }

  public String getDataDirectory() {
    return dataDirectory;
  }

  public MutationOperator<S> getMutation() {
    return mutationOperator;
  }

  public CrossoverOperator<S> getCrossover() {
    return crossoverOperator;
  }

  public ConstraintMOEAD2.FunctionType getFunctionType() {
    return functionType;
  }

  public int getMaximumNumberOfReplacedSolutions() {
    return maximumNumberOfReplacedSolutions;
  }

  public double getNeighborhoodSelectionProbability() {
    return neighborhoodSelectionProbability;
  }

  public int getNumberOfThreads() {
    return numberOfThreads ;
  }

  public MOEADBuilder2<S> setPopulationSize(int populationSize) {
    this.populationSize = populationSize;

    return this;
  }

  public MOEADBuilder2<S> setResultPopulationSize(int resultPopulationSize) {
    this.resultPopulationSize = resultPopulationSize;

    return this;
  }

  public MOEADBuilder2<S> setMaxEvaluations(int maxEvaluations) {
    this.maxEvaluations = maxEvaluations;

    return this;
  }

  public MOEADBuilder2<S> setNeighborSize(int neighborSize) {
    this.neighborSize = neighborSize ;

    return this ;
  }

  public MOEADBuilder2<S> setNeighborhoodSelectionProbability(double neighborhoodSelectionProbability) {
    this.neighborhoodSelectionProbability = neighborhoodSelectionProbability ;

    return this ;
  }

  public MOEADBuilder2<S> setFunctionType(ConstraintMOEAD2.FunctionType functionType) {
    this.functionType = functionType ;

    return this ;
  }

  public MOEADBuilder2<S> setMaximumNumberOfReplacedSolutions(int maximumNumberOfReplacedSolutions) {
    this.maximumNumberOfReplacedSolutions = maximumNumberOfReplacedSolutions ;

    return this ;
  }

  
	  
  
  public MOEADBuilder2<S> setCrossover(CrossoverOperator<S> crossoverOperator) {
	this.crossoverOperator = crossoverOperator;
	return this ;
}

  public MOEADBuilder2<S> setMutation(MutationOperator<S> mutation) {
	this.mutationOperator = mutation ;

	return this ;
  }
	
	
public MOEADBuilder2<S> setDataDirectory(String dataDirectory) {
    this.dataDirectory = dataDirectory ;

    return this ;
  }

  public MOEADBuilder2<S> setNumberOfThreads(int numberOfThreads) {
    this.numberOfThreads = numberOfThreads ;

    return this ;
  }

  
  
  
  public AbstractMOEAD<S> build() {
	  AbstractMOEAD<S> algorithm = null ;
	    if (moeadVariant.equals(Variant.ConstraintMOEAD)) {
	  	  algorithm = new ConstraintMOEAD2<S>(problem, populationSize, resultPopulationSize, maxEvaluations, crossoverOperator, mutationOperator, functionType, dataDirectory, neighborhoodSelectionProbability,
		             maximumNumberOfReplacedSolutions, neighborSize);
	    } else if (moeadVariant.equals(Variant.MOEAD)) {
		  	  algorithm = new MOEAD2<S>(problem, populationSize, resultPopulationSize, maxEvaluations, crossoverOperator, mutationOperator, functionType, dataDirectory, neighborhoodSelectionProbability,
			             maximumNumberOfReplacedSolutions, neighborSize);
	    }
//	      else if (moeadVariant.equals(Variant.MOEADDRA)) {
//	      algorithm =  new MOEADDRA(problem, populationSize, resultPopulationSize, maxEvaluations, mutation,
//	          crossover, functionType, dataDirectory, neighborhoodSelectionProbability,
//	          maximumNumberOfReplacedSolutions, neighborSize);
//	    } else if (moeadVariant.equals(Variant.MOEADSTM)) {
//	        algorithm =  new MOEADSTM(problem, populationSize, resultPopulationSize, maxEvaluations, mutation,
//	                crossover, functionType, dataDirectory, neighborhoodSelectionProbability,
//	                maximumNumberOfReplacedSolutions, neighborSize);
//	    } else if (moeadVariant.equals(Variant.MOEADD)) {
//	      algorithm = new MOEADD<>(problem, populationSize, resultPopulationSize, maxEvaluations, crossover, mutation,
//	              functionType, dataDirectory, neighborhoodSelectionProbability,
//	              maximumNumberOfReplacedSolutions, neighborSize);
//	    }
	    return algorithm ;
	  }
  

}
