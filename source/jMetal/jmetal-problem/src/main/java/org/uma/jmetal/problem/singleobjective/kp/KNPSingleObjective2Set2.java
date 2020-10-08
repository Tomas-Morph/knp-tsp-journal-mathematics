package org.uma.jmetal.problem.singleobjective.kp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.BitSet;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

@SuppressWarnings("serial")
public class KNPSingleObjective2Set2 extends AbstractBinaryProblem {

	private String instanceName;
	private static int numberOfVariables;
	private static int bitsPerVariable;
	private static int numberOfObjectives;
	private static int numberOfConstraints;

	private int[][] dataSet = new int[3][];
	private int[] weights;
//	private int[] profit1;
	private int[] profit2;

	private static int capacity;

	public OverallConstraintViolation<BinarySolution> overallConstraintViolationDegree;
	public NumberOfViolatedConstraints<BinarySolution> numberOfViolatedConstraints;

	public KNPSingleObjective2Set2(String filename) throws IOException {

	    InputStream is = getClass().getResourceAsStream(filename);
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader in = new BufferedReader(isr);
		
		this.instanceName = in.readLine().substring(2).trim().split("\\s+")[2];

		numberOfVariables = 1;

		bitsPerVariable = readNumber(in);
		System.out.println("bitsPerVariable "+bitsPerVariable);
		numberOfObjectives = 1;
		numberOfConstraints = 1;

		dataSet = readList(in);
		
		this.weights = dataSet[0];
		//this.profit1 = dataSet[1];
		this.profit2 = dataSet[2];
		

		System.out.println("instanceName :" + this.instanceName);
		System.out.println("bitsPerVariable # N :" + bitsPerVariable);
		System.out.println("objectives # P :" + numberOfObjectives);
		System.out.println("constraints # K :" + numberOfConstraints);

		capacity = readNumber(in);
		System.out.println("capacity :" + capacity);

		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(numberOfObjectives);
		setNumberOfConstraints(numberOfConstraints);
		setName("SingleObjectiveKP2");

		overallConstraintViolationDegree = new OverallConstraintViolation<BinarySolution>();
		numberOfViolatedConstraints = new NumberOfViolatedConstraints<BinarySolution>();
	}

	@Override
	public void evaluate(BinarySolution solution) {
		BitSet items = solution.getVariableValue(0);

		int[] f = new int[getNumberOfObjectives()];
		
		//int sumOfProfits2 = 0;
		for (int i = 0; i < profit2.length; i++) {
			if (items.get(i)) {
				//sumOfProfits2 += profit2[i];
				f[0] += profit2[i];
			}
		}

//		System.out.println("========== Dentro de Evaluate =====================");
//		System.out.println("sumOfProfits2 : " + f[0]);

		solution.setObjective(0, -1.0 * f[0]);

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
		return this.bitsPerVariable;
	}

	@Override
	public BinarySolution createSolution() {
		return new DefaultBinarySolution(this);
	}

	private static int readNumber(BufferedReader in) throws IOException {
		int size = 0;
		String line = in.readLine();
		while (line.charAt(0) == 'c') {
			line = in.readLine();
		}
		
		String[] str = line.substring(2).trim().split("\\s+");
		
		size = Integer.parseUnsignedInt(str[0]);
		System.out.println("line.substring(2): "+size);
		line = in.readLine();
		return size;
	}

	private static int[][] readList(BufferedReader in) throws IOException {

		int[][] list = new int[3][bitsPerVariable];
		
		String line = in.readLine();

		int i = 0;
		while ((line).charAt(0) == 'i' ) {
			String[] str = line.substring(2).trim().split("\\s+");
		
			System.out.println("str: "+str[0]+" "+str[1]+" "+str[2]);
			
			list[0][i] = Integer.parseUnsignedInt(str[0]);
			list[1][i] = Integer.parseUnsignedInt(str[1]);
			list[2][i] = Integer.parseUnsignedInt(str[2]);
			
			i++;
			line = in.readLine();
		}	
		
		return list;
	}
	
	public int[] getProfit2() {
		return profit2;
	}

	public int[] getWeights() {
		return weights;
	}

}
