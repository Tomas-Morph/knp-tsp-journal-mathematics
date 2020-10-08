package org.uma.jmetal.problem.multiobjective.kp;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.BitSet;
import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

@SuppressWarnings("serial")

public class KNPMultiobjectiveSet1 extends AbstractBinaryProblem {

	private String instanceName;
	private static int numberOfVariables;
	private static int bitsPerVariable;
	private static int numberOfObjectives;
	private static int numberOfConstraints;

	private int[] profit1;
	private int[] profit2;
	private int[] weights;

	private static int capacity;

//	private double profit1Total;
//	private double profit2Total;

	public OverallConstraintViolation<BinarySolution> overallConstraintViolationDegree;
	public NumberOfViolatedConstraints<BinarySolution> numberOfViolatedConstraints;

//	// hereda de GenericSolutionAttribute
//    private OverallConstraintViolation<BinarySolution> overallConstraintViolationDegree = new OverallConstraintViolation<>();
//    private NumberOfViolatedConstraints<BinarySolution> numberOfViolatedConstraints = new NumberOfViolatedConstraints<>();

	public KNPMultiobjectiveSet1(String filename) throws IOException {

		
	    InputStream is = getClass().getResourceAsStream(filename);
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader in = new BufferedReader(isr);
	
//		FileReader f = new FileReader(filename);
//		BufferedReader in = new BufferedReader(f);

		this.instanceName = in.readLine();

		numberOfVariables = 1;
		numberOfConstraints = 1;

		bitsPerVariable = readNumber(in);
		numberOfObjectives = readNumber(in);
		numberOfConstraints = readNumber(in);

		this.profit1 = readList(in);
		this.profit2 = readList(in);
		this.weights = readList(in);

		System.out.println("instanceName :" + this.instanceName);
		System.out.println("bitsPerVariable # N :" + bitsPerVariable);
		System.out.println("objectives # P :" + numberOfObjectives);
		System.out.println("constraints # K :" + numberOfConstraints);

		capacity = Integer.parseUnsignedInt(in.readLine().trim());
		System.out.println("capacity :" + capacity);

//	    this.profit1Total = Arrays.stream(profit1).reduce(Integer::sum).getAsInt();
//	    this.profit2Total = Arrays.stream(profit2).reduce(Integer::sum).getAsInt();
//	    System.out.println("profit1R :"+this.profit1Total);
//	    System.out.println("profit2R :"+this.profit2Total);

		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(numberOfObjectives);
		setNumberOfConstraints(numberOfConstraints);
		setName("multiobjectiveKP");

		overallConstraintViolationDegree = new OverallConstraintViolation<BinarySolution>();
		numberOfViolatedConstraints = new NumberOfViolatedConstraints<BinarySolution>();
	}

	@Override
	public void evaluate(BinarySolution solution) {
		BitSet items = solution.getVariableValue(0);

		int[] f = new int[getNumberOfObjectives()];

		// int sumOfProfits1 = 0;
		for (int i = 0; i < profit1.length; i++) {
			if (items.get(i)) {
				// sumOfProfits1 += profit1[i];
				f[0] += profit1[i];
			}
		}

		// int sumOfProfits2 = 0;
		for (int i = 0; i < profit2.length; i++) {
			if (items.get(i)) {
				// sumOfProfits2 += profit2[i];
				f[1] += profit2[i];
			}
		}

//		System.out.println("========== Dentro de Evaluate =====================");
//		System.out.println("sumOfProfits1 : "+f[0] + "sumOfProfits2 : "+f[1]);

		// Multiobjective Knapsack is a maximization problem: multiply by -1 to minimize
//        solution.setObjective(0, -1.0 * sumOfProfits1);
//        solution.setObjective(1, -1.0 * sumOfProfits2);
//        
		solution.setObjective(0, -1.0 * f[0]);
		solution.setObjective(1, -1.0 * f[1]);

		// Otras opciones de representación

		// Opcion1 utilizar un ratio [0,1]
		// double rateOfProfit1 = sumOfProfits1 / profit1Total ;
		// double rateOfProfit2 = sumOfProfits2 / profit2Total ;
		// solution.setObjective(0, 1-sumOfProfits1);
		// solution.setObjective(1, 1-sumOfProfits1);

		// Opcion2
		// Lo mismo pero..in vez de dividir por el profit1Total, dividir por el máximo
		// total que se ha encontrado
		// pero esto no se sabe cual es hasta que termine el experimento???

		// jMetal assumes that all the objectives are to be minimized,
		// so if you need to maximize some of them you have to transform them to
		// minimization objectives by considering that
		// max F(x) = min -F(x).

		this.evaluateConstraints(solution);
	}

	/** EvaluateConstraints() method */
	public void evaluateConstraints(BinarySolution solution) {
		BitSet items = solution.getVariableValue(0);

		int sumOfWeights = 0;

		double overallConstraintViolation = 0.0;
		int violatedConstraints = 0;

		for (int i = 0; i < weights.length; i++) {
			if (items.get(i)) {
				sumOfWeights += weights[i];
			}
		}

		if (sumOfWeights > capacity) {
			overallConstraintViolation = (double) capacity - sumOfWeights;
			violatedConstraints = 1;
		}

//		System.out.println("========== Dentro de evaluateConstraints =====================");
//		System.out.println("capacity : " + capacity + "   |   sumOfWeights : " + sumOfWeights);

		overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
		numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);

	}

	@Override
	protected int getBitsPerVariable(int index) {
		if (index != 0) {
			throw new JMetalException("Problem OneMax has only a variable. Index = " + index);
		}
		// return numberOfVariables;
		return bitsPerVariable;
	}

	@Override
	public BinarySolution createSolution() {
		return new DefaultBinarySolution(this);
	}

	private static int readNumber(BufferedReader in) throws IOException {
		int setting = 0;
		String line = in.readLine();
		while ((line.isEmpty()) || (line.charAt(0) != '#')) {
			line = in.readLine();
		}
		setting = Integer.parseUnsignedInt(in.readLine());

		return setting;
	}

	private static int[] readList(BufferedReader in) throws IOException {

		int[] list = new int[bitsPerVariable];
		String line = in.readLine();

		while ((line.isEmpty()) || ((line).charAt(0) == '#')) {
			line = in.readLine();
		}

		int i = 0;
		list[i] = Integer.parseUnsignedInt(line.trim());

		while ((((line = in.readLine()).isEmpty()) || ((line).charAt(0) != '#')) && ((i < bitsPerVariable - 1))) {
			if (!line.isEmpty()) {
				i++;
				list[i] = Integer.parseUnsignedInt(line.trim());
			}
		}

		System.out.println(Arrays.toString(list));

		return list;
	}

	public int[] getProfit1() {
		return profit1;
	}

	public int[] getProfit2() {
		return profit2;
	}

	public int[] getWeights() {
		return weights;
	}

//    private static void readData(BufferedReader in) throws IOException {
//    	int setting = 0;
//    	String line = null;
//
//    	
//    		
//    		
//    		if(line.equals("# Objectif 1")) {
//    			while(!(line = in.readLine()).equals("# Objectif 2")) {
//        			//int line = Integer.parseUnsignedInt(in.readLine());
//    				if (!line.isEmpty()) {
//    					System.out.println("== Objectif 1 ==: "+line);
//    				}
//    			}
//        	}
//    		
//    		if(line.equals("# Objectif 2")) {
//    			while(!(line = in.readLine()).equals("# Contrainte 1")) {
//        			//int line = Integer.parseUnsignedInt(in.readLine());
//    				if (!line.isEmpty()) {
//    					System.out.println("== Objectif 2 ==: "+line);
//    				}
//    			}
//        	}
//    		
//
//    		if(line.equals("# Contrainte 1")) {
//    			while((line = in.readLine()).trim() != null) {
//        			//int line = Integer.parseUnsignedInt(in.readLine());
//    				
//    				if (!line.isEmpty()) {
//    					System.out.println("== Contrainte 1 ==: "+line);
//    				}
//    				
//    				capacity = Integer.parseUnsignedInt(line);
//    				System.out.println("====== capacity ======="+capacity);
//
//    			}
//    			in.close();
//        	}
//    		
//
//    		//System.out.println(line);
//
//    }

}
