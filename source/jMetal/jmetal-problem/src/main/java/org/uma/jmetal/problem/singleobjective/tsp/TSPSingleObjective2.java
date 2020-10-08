package org.uma.jmetal.problem.singleobjective.tsp;


import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.JMetalException;

import java.io.*;

/**
 * Class representing a single-objective TSP (Traveling Salesman Problem) problem.
 * It accepts data files from TSPLIB:
 *   http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/tsp/
 */
@SuppressWarnings("serial")
public class TSPSingleObjective2 extends AbstractIntegerPermutationProblem {
  private int         numberOfCities ;
  protected double [][] costMatrix;
  /**
   * Creates a new TSP problem instance
   */
  public TSPSingleObjective2(String costFile) throws IOException {
    costMatrix     = readProblem(costFile);
    
    setNumberOfVariables(numberOfCities);
    setNumberOfObjectives(1);
    setName("singleObjectiveTSP2");
  }

  /** Evaluate() method */
  public void evaluate(PermutationSolution<Integer> solution){
	//double fitness1   ;
	double fitness2   ;

	//fitness1 = 0.0 ;
	fitness2 = 0.0 ;

    for (int i = 0; i < (numberOfCities - 1); i++) {
      int x ;
      int y ;

      x = solution.getVariableValue(i) ;
      y = solution.getVariableValue(i+1) ;

      //fitness1 += distanceMatrix[x][y] ;
      fitness2 += costMatrix[x][y];
      
    }
    int firstCity ;
    int lastCity  ;

    firstCity = solution.getVariableValue(0) ;
    lastCity = solution.getVariableValue(numberOfCities - 1) ;

//  fitness1 += distanceMatrix[firstCity][lastCity] ;
    fitness2 += costMatrix[firstCity][lastCity];

//  System.out.println("Solution: " + solution.getVariables());
//  System.out.println("fitness2 : "+fitness2);
    
    solution.setObjective(0, fitness2);
  }

  private double [][] readProblem(String file) throws IOException {
    double [][] matrix = null;

    InputStream in = getClass().getResourceAsStream(file);
    InputStreamReader isr = new InputStreamReader(in);
    BufferedReader br = new BufferedReader(isr);

    StreamTokenizer token = new StreamTokenizer(br);
    try {
      boolean found ;
      found = false ;

      token.nextToken();
      while(!found) {
        if ((token.sval != null) && ((token.sval.compareTo("DIMENSION") == 0)))
          found = true ;
        else
          token.nextToken() ;
      }

      token.nextToken() ;
      token.nextToken() ;

      numberOfCities =  (int)token.nval ;

      matrix = new double[numberOfCities][numberOfCities] ;

      // Find the string SECTION  
      found = false ;
      token.nextToken();
      while(!found) {
        if ((token.sval != null) &&
            ((token.sval.compareTo("SECTION") == 0)))
          found = true ;
        else
          token.nextToken() ;
      }

      double [] c = new double[2*numberOfCities] ;

      for (int i = 0; i < numberOfCities; i++) {
        token.nextToken() ;
        int j = (int)token.nval ;

        token.nextToken() ;
        c[2*(j-1)] = token.nval ;
        token.nextToken() ;
        c[2*(j-1)+1] = token.nval ;
      } // for

      double dist ;
      for (int k = 0; k < numberOfCities; k++) {
        matrix[k][k] = 0;
        for (int j = k + 1; j < numberOfCities; j++) {
          dist = Math.sqrt(Math.pow((c[k*2]-c[j*2]),2.0) +
              Math.pow((c[k*2+1]-c[j*2+1]), 2));
          dist = (int)(dist + .5);
          matrix[k][j] = dist;
          matrix[j][k] = dist;
        }
      }
    } catch (Exception e) {
      new JMetalException("TSP.readProblem(): error when reading data file " + e);
    }
    return matrix;
  }

  @Override public int getPermutationLength() {
    return numberOfCities ;
  }

public int getNumberOfCities() {
	return numberOfCities;
}

public double[][] getCostMatrix() {
	return costMatrix;
}
  
  
}
