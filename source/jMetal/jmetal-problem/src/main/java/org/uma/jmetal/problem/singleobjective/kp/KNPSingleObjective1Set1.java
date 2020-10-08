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
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

@SuppressWarnings("serial")
public class KNPSingleObjective1Set1 extends AbstractBinaryProblem {

	private String instanceName;
	private static int numberOfVariables;
	private static int bitsPerVariable;
	private static int numberOfObjectives;
	private static int numberOfConstraints;

	private int[] profit1;
	private int[] weights;

	private static int capacity;

	public OverallConstraintViolation<BinarySolution> overallConstraintViolationDegree;
	public NumberOfViolatedConstraints<BinarySolution> numberOfViolatedConstraints;

	public KNPSingleObjective1Set1(String filename) throws IOException {

	    InputStream is = getClass().getResourceAsStream(filename);
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader in = new BufferedReader(isr);
		
//		FileReader f = new FileReader(filename);
//		BufferedReader in = new BufferedReader(f);


		this.instanceName = in.readLine();

		this.numberOfVariables = 1;
		this.numberOfConstraints = 1;

		this.bitsPerVariable = readNumber(in);
		readNumber(in);
		this.numberOfObjectives = 1;
		this.numberOfConstraints = readNumber(in);

		this.profit1 = readList(in);
		readList(in);
		this.weights = readList(in);

		System.out.println("instanceName :" + this.instanceName);
		System.out.println("bitsPerVariable # N :" + this.bitsPerVariable);
		System.out.println("objectives # P :" + this.numberOfObjectives);
		System.out.println("constraints # K :" + this.numberOfConstraints);

		this.capacity = Integer.parseUnsignedInt(in.readLine().trim());
		System.out.println("capacity :" + this.capacity);

		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(numberOfObjectives);
		setNumberOfConstraints(numberOfConstraints);
		setName("SingleObjectiveKP1");

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

//		System.out.println("========== Dentro de Evaluate =====================");
//		System.out.println("sumOfProfits1 : "+f[0]);

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

	public int[] getWeights() {
		return weights;
	}

}
