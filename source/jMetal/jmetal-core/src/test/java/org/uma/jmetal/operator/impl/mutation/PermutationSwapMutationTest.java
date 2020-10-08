package org.uma.jmetal.operator.impl.mutation;

import org.junit.Test;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.impl.AuditableRandomGenerator;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class PermutationSwapMutationTest {

	@Test
	public void shouldJMetalRandomGeneratorNotBeUsedWhenCustomRandomGeneratorProvided() {
		// Configuration
		double mutationProbability = 1.0;
		@SuppressWarnings("serial")
		PermutationProblem<PermutationSolution<Integer>> problem = new AbstractIntegerPermutationProblem() {

			@Override
			public void evaluate(PermutationSolution<Integer> solution) {
				// Do nothing
			}
			
			@Override
			public int getNumberOfVariables() {
				return 5;
			}

			@Override
			public int getPermutationLength() {
				return 5;
			}
			
		};
		PermutationSolution<Integer> solution = problem.createSolution();

		// Check configuration leads to use default generator by default
		final int[] defaultUses = { 0 };
		JMetalRandom defaultGenerator = JMetalRandom.getInstance();
		AuditableRandomGenerator auditor = new AuditableRandomGenerator(defaultGenerator.getRandomGenerator());
		defaultGenerator.setRandomGenerator(auditor);
		auditor.addListener((a) -> defaultUses[0]++);

		PermutationSwapMutation<Integer> crossover1 = new PermutationSwapMutation<>(mutationProbability);
		crossover1.execute(solution);
		assertTrue("No use of the default generator", defaultUses[0] > 0);

		// Test same configuration uses custom generator instead
		defaultUses[0] = 0;
		final int[] custom1Uses = { 0 };
		final int[] custom2Uses = { 0 };
		PermutationSwapMutation<Integer> crossover2 = new PermutationSwapMutation<>(mutationProbability, () -> {
			custom2Uses[0]++;
			return new Random().nextDouble();
		}, (a, b) -> {
			custom1Uses[0]++;
			return new Random().nextInt(b - a + 1) + a;
		});
		crossover2.execute(solution);
		assertTrue("Default random generator used", defaultUses[0] == 0);
		assertTrue("No use of the custom generator 1", custom1Uses[0] > 0);
		assertTrue("No use of the custom generator 2", custom2Uses[0] > 0);
	}

}
